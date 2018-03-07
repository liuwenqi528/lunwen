package com.khcm.user.service.impl.orgnization;

import com.khcm.user.dao.entity.business.orgnization.Department;
import com.khcm.user.dao.entity.business.orgnization.Organization;
import com.khcm.user.dao.entity.business.orgnization.QDepartment;
import com.khcm.user.dao.repository.master.organization.DepartmentRepository;
import com.khcm.user.dao.repository.master.organization.OrganizationRepository;
import com.khcm.user.service.api.organization.DepartmentService;
import com.khcm.user.service.dto.business.organization.DepartmentDTO;
import com.khcm.user.service.mapper.organization.DepartmentMapper;
import com.khcm.user.service.param.business.organization.DepartmentParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 部门业务逻辑实现类
 * <p>
 *
 * @author wangtao
 * on 2017/9/5
 */
@Service
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    /**
     * @param departmentParam
     * @return
     * @author QimengDuan
     * @date 2018-01-25
     * description:增加了多条件查询
     */
    @Override
    public List<DepartmentDTO> getList(DepartmentParam departmentParam) {
        //获取到的参数
        String deptName = departmentParam.getName();
        Date beginTime = departmentParam.getBeginTime();
        Date endTime = departmentParam.getEndTime();
        Integer parentId = departmentParam.getParentId();
        Integer organizationId = departmentParam.getOrganizationId();
        QDepartment qDepartment = QDepartment.department;
        BooleanExpression p = qDepartment.enable.eq(true);
        //如果所属机构ID不为空，则查询所属机构
        if (Objects.nonNull(organizationId)) {
            Organization organization = organizationRepository.getOne(organizationId);
            p = p.and(qDepartment.organization.id.in(organizationRepository.getIdsById(organization.getLft(), organization.getRgt())));
        }
        //如果部门名称不为空，则查询部门名称
        if (StringUtils.isNotBlank(deptName)) {
            p = p.and(qDepartment.fullName.like("" + deptName + "").or(qDepartment.name.like("%" + deptName + "%")));
        }
        //如果开始时间与结束时间都不为空，则查询日期范围内的部门
        if (Objects.nonNull(beginTime) && Objects.nonNull(endTime)) {
            p = p.and(qDepartment.gmtCreate.between(beginTime, endTime));
        }
        //如果父级id不为空
        if (Objects.nonNull(parentId)) {
            p = p.and(qDepartment.parent.id.eq(parentId));
        }
        List<Department> departmentList = departmentRepository.findList(p);
        return convertDepartment(departmentList);
    }

    @Override
    public DepartmentDTO saveOrUpdate(DepartmentParam departmentParam) {
        if (departmentParam.getId() == null) {
            Department department = DepartmentMapper.MAPPER.paramToEntity(departmentParam);
            department.setEnable(true);
            return DepartmentMapper.MAPPER.entityToDTO(departmentRepository.save(department));
        }

        Department department = departmentRepository.findOne(departmentParam.getId());
        DepartmentMapper.MAPPER.paramToEntity(departmentParam, department);
        return DepartmentMapper.MAPPER.entityToDTO(departmentRepository.save(department));
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.forEach(id -> departmentRepository.updateEnable(id,false));
    }

    @Override
    public DepartmentDTO getById(Integer id) {
        return DepartmentMapper.MAPPER.entityToDTO(departmentRepository.getOne(id));
    }

    private List<DepartmentDTO> convertDepartment(List<Department> departmentList) {
        List<DepartmentDTO> root = new ArrayList<>();

        Map<Integer, DepartmentDTO> map = new HashMap<>();
        departmentList.forEach(department -> {

            DepartmentDTO departmentDTO = DepartmentMapper.MAPPER.entityToDTO(department);
            map.put(department.getId(), departmentDTO);

            Integer parentId = department.getParent() == null ? null : department.getParent().getId();
            departmentDTO.setParentId(parentId);
            if (map.containsKey(parentId)) {
                map.get(parentId).addChild(departmentDTO);
            } else {
                root.add(departmentDTO);
            }

        });

        return root;
    }
}
