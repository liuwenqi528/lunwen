package com.khcm.user.dao.interceptor;

import com.khcm.user.common.utils.SpringUtils;
import com.khcm.user.dao.entity.base.TreeEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.io.Serializable;

@Slf4j
public class TreeInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        EntityManager em = SpringUtils.getBean(EntityManager.class);
        if (entity instanceof TreeEntity) {
            TreeEntity<?> treeEntity = (TreeEntity<?>)entity;
            String beanName = treeEntity.getClass().getName();
            Integer parentId = treeEntity.getParent() == null ? null : treeEntity.getParent().getId();

            FlushModeType flushModeType = em.getFlushMode();
            em.setFlushMode(FlushModeType.COMMIT);

            Integer myPosition,myLevel;
            if (parentId != null) {
                // 如果父节点不为null，则获取节点的右边位置
                String hql = "select bean.rgt,bean.level from " + beanName + " bean where bean.id=:pid";
                Object[] result = (Object[]) em.createQuery(hql).setParameter("pid", parentId).getSingleResult();

                myPosition = ((Number)result[0]).intValue();
                myLevel = ((Number)result[1]).intValue()+1;

                String hql1 = "update " + beanName + " bean set bean.rgt = bean.rgt + 2 WHERE bean.rgt >= :myPosition";
                String hql2 = "update " + beanName + " bean set bean.lft = bean.lft + 2 WHERE bean.lft >= :myPosition";
                em.createQuery(hql1).setParameter("myPosition", myPosition).executeUpdate();
                em.createQuery(hql2).setParameter("myPosition", myPosition).executeUpdate();
            } else {
                // 否则查找最大的右边位置
                String hql = "select max(bean.rgt) from " + beanName + " bean";
                Integer myPositionInt = (Integer) em.createQuery(hql).getSingleResult();
                myPosition = myPositionInt == null ? 1 : myPositionInt + 1;
                myLevel = 1;
            }

            em.setFlushMode(flushModeType); //还原刷新模式

            for (int i = 0; i < propertyNames.length; i++) {
                if ("lft".equals(propertyNames[i])) {
                    state[i] = myPosition;
                }
                if ("rgt".equals(propertyNames[i])) {
                    state[i] = myPosition + 1;
                }
                if ("level".equals(propertyNames[i])) {
                    state[i] = myLevel;
                }
            }
            return true;

        }

        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof TreeEntity) {
            TreeEntity<?> treeEntity = (TreeEntity<?>)entity;
            for (int i = 0; i < propertyNames.length; i++) {
                if ("parent".equals(propertyNames[i])) {
                    TreeEntity<?> prevParent = (TreeEntity<?>)previousState[i];
                    TreeEntity<?> currParent = (TreeEntity<?>)currentState[i];
                    return updateParent(treeEntity, prevParent, currParent, currentState, propertyNames);
                }
            }
        }

        return false;
    }

    private boolean updateParent(TreeEntity<?> treeEntity, TreeEntity<?> prevParent, TreeEntity<?> currParent, Object[] currentState, String[] propertyNames) {
        EntityManager em = SpringUtils.getBean(EntityManager.class);
        // 都为空、或都不为空且相等时，不作处理
        if ((prevParent == null && currParent == null)
                || (prevParent != null && currParent != null && prevParent.getId().equals(currParent.getId()))) {
            return false;
        }
        String beanName = treeEntity.getClass().getName();
        if (log.isDebugEnabled()) {
            log.debug("update Tree {}, id={}, "
                    + "pre-parent id={}, curr-parent id={}", new Object[] {
                    beanName, treeEntity.getId(),
                    prevParent == null ? null : prevParent.getId(),
                    currParent == null ? null : currParent.getId() });
        }

        FlushModeType flushModeType = em.getFlushMode();
        em.setFlushMode(FlushModeType.COMMIT);

        Integer currParentRgt, currParentLevel;
        if (currParent != null) {
            // 获得节点跨度
            String hql = "select bean.lft, bean.rgt, bean.level from " + beanName + " bean where bean.id=:id";
            Object[] result = (Object[])em.createQuery(hql).setParameter("id", treeEntity.getId()).getSingleResult();
            int nodeLft = ((Number)result[0]).intValue();
            int nodeRgt = ((Number)result[1]).intValue();
            int span = nodeRgt - nodeLft + 1;
            log.debug("current node span={}", span);

            // 获得当前父节点右位置
            result = (Object[]) em.createQuery(hql).setParameter("id", currParent.getId()).getSingleResult();
            int currParentLft = ((Number)result[0]).intValue();
            currParentRgt = ((Number)result[1]).intValue();
            currParentLevel = ((Number)result[2]).intValue();
            log.debug("current parent lft={} rgt={},level={}", currParentLft, currParentRgt, currParentLevel);

            // 空出位置
            String hql1 = "update " + beanName + " bean set bean.rgt = bean.rgt + " + span + " WHERE bean.rgt >= :parentRgt";
            String hql2 = "update " + beanName + " bean set bean.lft = bean.lft + " + span + " WHERE bean.lft >= :parentRgt";
            em.createQuery(hql1).setParameter("parentRgt", currParentRgt).executeUpdate();
            em.createQuery(hql2).setParameter("parentRgt", currParentRgt).executeUpdate();
            log.debug("vacated span hql: {}, {}, parentRgt={}", new Object[] {hql1, hql2, currParentRgt });
        } else {
            // 否则查找最大的右边位置
            String hql = "select max(bean.rgt) from "+ beanName + " bean";
            currParentRgt = (Integer) em.createQuery(hql).getSingleResult();
            currParentRgt ++;
            currParentLevel = 0;
            log.debug("max rgt node left = {}, level = {}", currParentRgt, currParentLevel);
        }

        // 再调整自己
        String hql = "select bean.lft, bean.rgt, bean.level from " + beanName + " bean where bean.id=:id";
        Object[] result = (Object[])em.createQuery(hql).setParameter("id", treeEntity.getId()).getSingleResult();
        int nodeLft = ((Number)result[0]).intValue();
        int nodeRgt = ((Number)result[1]).intValue();
        int nodeLevel = ((Number)result[2]).intValue();
        int span = nodeRgt - nodeLft + 1;
        if (log.isDebugEnabled()) {
            log.debug("before adjust self left={} right={} span={}, nodeLevel={}",
                    new Object[] { nodeLft, nodeRgt, span, nodeLevel });
        }
        int offset = currParentRgt - nodeLft;
        int levelOffset = nodeLevel - currParentLevel - 1;
        hql = "update " + beanName + " bean set bean.lft = bean.lft + :offset, bean.rgt = bean.rgt + :offset, bean.level = bean.level + :levelOffset  WHERE bean.lft between :nodeLft and :nodeRgt";
        em.createQuery(hql).setParameter("offset", offset).setParameter("levelOffset", levelOffset).setParameter("nodeLft", nodeLft).setParameter("nodeRgt", nodeRgt).executeUpdate();
        if (log.isDebugEnabled()) {
            log.debug("adjust self hql: {}, offset={}, nodeLft={}, nodeRgt={}, levelOffset={}",
                    new Object[] { hql, offset, nodeLft, nodeRgt, levelOffset});
        }

        // 最后删除（清空位置）
        String hql1 = "update " + beanName + " bean set bean.rgt = bean.rgt - " + span + " WHERE bean.rgt > :nodeRgt";
        String hql2 = "update " + beanName + " bean set bean.lft = bean.lft - " + span + " WHERE bean.lft > :nodeRgt";
        em.createQuery(hql1).setParameter("nodeRgt", nodeRgt).executeUpdate();
        em.createQuery(hql2).setParameter("nodeRgt", nodeRgt).executeUpdate();
        if (log.isDebugEnabled()) {
            log.debug("clear span hql:{}, hql2:{}, nodeRgt:{}", new Object[] {hql1, hql2, nodeRgt });
        }

        //给当前节点设置新值
        hql = "select bean.lft, bean.rgt, bean.level from " + beanName + " bean where bean.id=:id";
        result = (Object[])em.createQuery(hql).setParameter("id", treeEntity.getId()).getSingleResult();
        nodeLft = ((Number)result[0]).intValue();
        nodeRgt = ((Number)result[1]).intValue();
        nodeLevel = ((Number)result[2]).intValue();
        if (log.isDebugEnabled()) {
            log.debug("last set value left={} right={}, nodeLevel={}",
                    new Object[] { nodeLft, nodeRgt, nodeLevel });
        }

        em.setFlushMode(flushModeType); //还原刷新模式

        for (int i = 0; i < propertyNames.length; i++) {
            if ("lft".equals(propertyNames[i])) {
                currentState[i] = nodeLft;
            }
            if ("rgt".equals(propertyNames[i])) {
                currentState[i] = nodeRgt;
            }
            if ("level".equals(propertyNames[i])) {
                currentState[i] = nodeLevel;
            }
        }

        return true;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        EntityManager em = SpringUtils.getBean(EntityManager.class);
        if (entity instanceof TreeEntity) {
            TreeEntity<?> treeEntity = (TreeEntity<?>)entity;
            String beanName = treeEntity.getClass().getName();

            FlushModeType flushModeType = em.getFlushMode();
            em.setFlushMode(FlushModeType.COMMIT);

            String hql = "select bean.lft from " + beanName + " bean where bean.id=:id";
            Integer myPosition = (Integer)em.createQuery(hql).setParameter("id", treeEntity.getId()).getSingleResult();

            String hql1 = "update " + beanName + " bean set bean.rgt = bean.rgt - 2 WHERE bean.rgt > :myPosition";
            String hql2 = "update " + beanName + " bean set bean.lft = bean.lft - 2 WHERE bean.lft > :myPosition";
            em.createQuery(hql1).setParameter("myPosition", myPosition).executeUpdate();
            em.createQuery(hql2).setParameter("myPosition", myPosition).executeUpdate();

            em.setFlushMode(flushModeType);
        }
    }

}
