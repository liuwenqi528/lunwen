package com.khcm.user.service.impl.system;

import com.khcm.user.dao.entity.business.system.Area;
import com.khcm.user.dao.entity.business.system.QArea;
import com.khcm.user.dao.repository.master.system.AreaRepository;
import com.khcm.user.service.api.system.AreaService;
import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.mapper.system.AreaMapper;
import com.khcm.user.service.param.business.system.AreaParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author rqn
 * @date 2018/1/12
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public AreaDTO saveOrUpdate(AreaParam areaParam) {
        if (areaParam.getId() == null) {
            return AreaMapper.MAPPER.entityToDTO(areaRepository.save(AreaMapper.MAPPER.paramToEntity(areaParam)));
        } else {
            Area area = areaRepository.findOne(areaParam.getId());
            AreaMapper.MAPPER.paramToEntity(areaParam, area);
            return AreaMapper.MAPPER.entityToDTO(areaRepository.save(area));
        }
    }

    @Override
    public List<AreaDTO> getList() {
        List<Area> areas = areaRepository.findAll();
        List<AreaDTO> root = new ArrayList<>();
        Map<Integer, AreaDTO> map = new HashMap<>();
        areas.forEach(area -> {
            AreaDTO areaDTO = AreaMapper.MAPPER.entityToDTO(area);
            map.put(area.getId(), areaDTO);
            Integer parentId = area.getParent() == null ? null : area.getParent().getId();
            areaDTO.setParentId(parentId);
            if (map.containsKey(parentId)) {
                map.get(parentId).addChild(areaDTO);
            } else {
                root.add(areaDTO);
            }
        });
        return root;
    }


    @Override
    public AreaDTO getById(Integer id) {
        return AreaMapper.MAPPER.entityToDTO(areaRepository.getOne(id));
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.stream().forEach(id -> areaRepository.delete(id));
    }

    @Override
    public List<AreaDTO> findAllByParentId(Integer pid) {
        List<Area> list = areaRepository.findAllByParentId(pid);
        return AreaMapper.MAPPER.entityToDTO(list);
    }

    /**
     * @param areaParam
     * @return
     * @author Liuwenqi
     * 根据条件判断是否已存在相同数据
     */
    @Override
    public AreaDTO getOne(AreaParam areaParam) {
        QArea qArea = QArea.area;
        BooleanExpression predicate ;
        if(StringUtils.isNotBlank(areaParam.getName())){
            predicate = qArea.name.eq(areaParam.getName());
            Optional<Integer> appParamId = Optional.ofNullable(areaParam.getParentId());
            predicate = predicate.and(appParamId.map(parentId -> qArea.parent.id.eq(parentId)).orElse(null));
        }else if(StringUtils.isNotBlank(areaParam.getCode())){
            predicate = qArea.code.eq(areaParam.getCode());
        }else{
            predicate = qArea.isNotNull();
        }
        Optional<Integer> appParamId = Optional.ofNullable(areaParam.getId());
        predicate = predicate.and(appParamId.map(id -> qArea.parent.id.eq(id)).orElse(null));

        Area area = Optional.ofNullable(areaRepository.findList(predicate)).filter(areas -> areas.size() > 0).map(areas -> areas.get(0)).orElse(null);
        return AreaMapper.MAPPER.entityToDTO(area);
    }

}
