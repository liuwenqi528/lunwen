package com.khcm.user.service.impl.system;

import com.khcm.user.common.ensure.Ensure;
import com.khcm.user.dao.entity.business.system.QResource;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.dao.repository.master.system.AuthorizationRepository;
import com.khcm.user.dao.repository.master.system.ResourceRepository;
import com.khcm.user.service.api.system.ResourceService;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.mapper.system.ResourceMapper;
import com.khcm.user.service.param.business.system.ResourceParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

/**
 * @author wangtao
 * 2017/12/1.
 */
@Service
@Transactional
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Override
    public ResourceDTO saveOrUpdate(ResourceParam resourceParam) {
        Optional<Integer> resourceParamId = Optional.ofNullable(resourceParam.getId());
        return resourceParamId.map(id -> {
            Resource resource = resourceRepository.findOne(resourceParam.getId());
            ResourceMapper.MAPPER.paramToEntity(resourceParam, resource);
            //todo 处理 parentId
            return ResourceMapper.MAPPER.entityToDTO(resourceRepository.save(resource));
        }).orElse(
                ResourceMapper.MAPPER.entityToDTO(resourceRepository.save(ResourceMapper.MAPPER.paramToEntity(resourceParam)))
        );
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.forEach(id -> {
            resourceRepository.delete(id);
            authorizationRepository.deleteByResourceId(id);
        });
    }

    @Override
    public ResourceDTO getById(Integer id) {
        return ResourceMapper.MAPPER.entityToDTO(resourceRepository.getOne(id));
    }

    /**
     * 根据参数获取唯一对象
     *
     * @param resourceParam
     * @return
     */
    @Override
    public ResourceDTO getOne(ResourceParam resourceParam) {
        //1、构造条件
        QResource qResource = QResource.resource;
        BooleanExpression p = qResource.app.id.eq(resourceParam.getAppId());

        //2、条件
        Optional<Integer> parentIdOptional = Optional.ofNullable(resourceParam.getParentId());
        p = p.and(parentIdOptional.map(pId -> qResource.parent.id.eq(pId)).orElse(p.and(qResource.parent.id.isNull())));

        if (StringUtils.isNotBlank(resourceParam.getName())) {
            p = p.and(qResource.name.eq(resourceParam.getName()));
        } else if (StringUtils.isNotBlank(resourceParam.getCode())) {
            p = p.and(qResource.code.eq(resourceParam.getCode()));
        }
        Optional<Integer> idOptional = Optional.ofNullable(resourceParam.getId());
        p = p.and(idOptional.map(id -> qResource.id.ne(id)).orElse(null));
        Resource resource = Optional.ofNullable(resourceRepository.findList(p)).filter(resources -> resources.size() > 0).map(resources -> resources.get(0)).orElse(null);
        return ResourceMapper.MAPPER.entityToDTO(resource);
    }

    @Override
    public List<ResourceDTO> getList(ResourceParam resourceParam) {
        Ensure.that(resourceParam).isNotNull().orThrowArgumentException("resource.required");
//        Ensure.that(resourceParam.getAppCode()).isNotNullOrEmpty().orThrowArgumentException("resource.appcode.required");
        QResource resource = QResource.resource;
        BooleanExpression predicate = resource.app.code.eq(resourceParam.getAppCode());
        if (StringUtils.isNotBlank(resourceParam.getResourceType())) {
            predicate = predicate.and(resource.resourceType.eq(Enum.valueOf(Resource.ResourceType.class, resourceParam.getResourceType())));
        }
        Optional<Date> optionalBeginDate = Optional.ofNullable(resourceParam.getBeginTime());
        predicate = predicate.and(optionalBeginDate.map(beginDate -> resource.gmtCreate.goe(beginDate)).orElse(null));
        Optional<Date> optionalEndDate = Optional.ofNullable(resourceParam.getEndTime());
        predicate = predicate.and(optionalEndDate.map(endDate -> resource.gmtCreate.loe(endDate)).orElse(null));

        List<Resource> resources = resourceRepository.findList(predicate);
        return convertResource(resources);
    }

    private List<ResourceDTO> convertResource(List<Resource> resources) {
        List<ResourceDTO> root = new ArrayList<>();

        Map<Integer, ResourceDTO> map = new HashMap<>();
        resources.forEach(resource -> {

            ResourceDTO resourceDTO = ResourceMapper.MAPPER.entityToDTO(resource);
            map.put(resource.getId(), resourceDTO);

            Integer parentId = resource.getParent() == null ? null : resource.getParent().getId();
            resourceDTO.setParentId(parentId);
            if (map.containsKey(parentId)) {
                map.get(parentId).addChild(resourceDTO);
            } else {
                root.add(resourceDTO);
            }

        });

        return root;
    }
}
