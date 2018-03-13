package com.khcm.user.service.impl.orgnization;

import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.entity.business.orgnization.QOrganization;
import com.khcm.user.dao.repository.master.organization.OrganizationRepository;
import com.khcm.user.service.api.organization.OrganizationService;
import com.khcm.user.service.dto.business.organization.OrganizationDTO;
import com.khcm.user.service.mapper.organization.OrganizationMapper;
import com.khcm.user.service.param.business.organization.OrganizationParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 机构业务逻辑实现类
 *
 * @author wangtao
 * on 2017/9/29
 */
@Service
@Transactional
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;


    @Override
    public OrganizationDTO saveOrUpdate(OrganizationParam organizationParam) {
        Optional<Integer> organinzationParamId = Optional.ofNullable(organizationParam.getId());
        return organinzationParamId.map(Id -> {
            Organization organization = organizationRepository.findOne(organizationParam.getId());
            OrganizationMapper.MAPPER.paramToEntity(organizationParam, organization);
            return OrganizationMapper.MAPPER.entityToDTO(organizationRepository.save(organization));
        }).orElseGet(() -> {
                    Organization organization = OrganizationMapper.MAPPER.paramToEntity(organizationParam);
                    organization.setEnable(true);
                    return OrganizationMapper.MAPPER.entityToDTO(organizationRepository.save(organization));
                }
        );
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.forEach(id -> organizationRepository.updateEnable(id, false));
    }

    @Override
    public OrganizationDTO getById(Integer id) {
        return OrganizationMapper.MAPPER.entityToDTO(organizationRepository.getOne(id));
    }

    @Override
    public List<OrganizationDTO> getList(OrganizationParam organizationParam) {
        QOrganization qOrganization = QOrganization.organization;
        BooleanExpression predicate = qOrganization.enable.eq(true);

        List<Organization> organization = organizationRepository.findList(predicate);

        return convertOrganization(organization);
    }

    private List<OrganizationDTO> convertOrganization(List<Organization> organizations) {
        List<OrganizationDTO> root = new ArrayList<>();

        Map<Integer, OrganizationDTO> map = new HashMap<>();
        organizations.forEach(organization -> {

            OrganizationDTO organizationDTO = OrganizationMapper.MAPPER.entityToDTO(organization);
            map.put(organization.getId(), organizationDTO);

            Integer parentId = organization.getParent() == null ? null : organization.getParent().getId();
            organizationDTO.setParentId(parentId);
            if (map.containsKey(parentId)) {
                map.get(parentId).addChild(organizationDTO);
            } else {
                root.add(organizationDTO);
            }

        });

        return root;
    }
}
