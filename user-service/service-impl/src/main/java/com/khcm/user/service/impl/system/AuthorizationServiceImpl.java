package com.khcm.user.service.impl.system;

import com.khcm.user.common.ensure.Ensure;
import com.khcm.user.dao.entity.business.system.Authorization;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.repository.master.system.AuthorizationRepository;
import com.khcm.user.dao.repository.master.system.ResourceRepository;
import com.khcm.user.dao.repository.master.system.RoleRepository;
import com.khcm.user.dao.repository.master.system.UserRepository;
import com.khcm.user.service.api.system.AuthorizationService;
import com.khcm.user.service.dto.business.system.ModuleDTO;
import com.khcm.user.service.dto.business.system.OperationDTO;
import com.khcm.user.service.mapper.system.AuthorizationMapper;
import com.khcm.user.service.mapper.system.ModuleMapper;
import com.khcm.user.service.mapper.system.OperationMapper;
import com.khcm.user.service.param.business.system.AuthorizationParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangwb
 * @date 2017/12/4
 */
@Service
@Transactional
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository permissionRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<ModuleDTO> getAppModulesByUserId(Integer userId, String appCode) {
        Ensure.that(appCode).isNotNullOrEmpty().orThrowArgumentException("app.required");
        Ensure.that(userId).isNotNull().orThrowArgumentException("user.required");

        User user = userRepository.findOne(userId);
        Ensure.that(user).isNotNull().orThrowStateException("user.notexist");

        if (user.getAdmin()) {
            //查询所有权限
            List<Resource> permissions = permissionRepository.findByAppCodeOrderByLft(appCode);
            return convertResourceToModule(permissions);
        }

        //查询分配权限
        List<Resource> permissions = authorizationRepository.getResourcesByUserId(appCode, userId);
        return convertResourceToModule(permissions);
    }

    @Override
    public Set<String> getAppPermsByUserId(Integer userId, String appCode) {
        List<Resource> resources = authorizationRepository.getResourcesByUserId(appCode, userId);
        return resources.stream()
                .map(Resource::getCode)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }

    @Override
    public void saveOrUpdate(AuthorizationParam authorizationParam) {
        List<AuthorizationParam> list = new ArrayList<>();
        authorizationParam.getResourceIds().forEach(resourceId -> {
            AuthorizationParam authParam = new AuthorizationParam();
            authParam.setAppId(authorizationParam.getAppId());
            authParam.setRoleId(authorizationParam.getRoleId());
            authParam.setResourceId(resourceId);
            list.add(authParam);
        });

        authorizationRepository.deleteByAppIdAndByRoleId(authorizationParam.getAppId(), authorizationParam.getRoleId());
        if (Objects.nonNull(list)) {
            Set<Authorization> authorizations = AuthorizationMapper.MAPPER.paramToSetEntity(list);
            authorizations.forEach(authorization -> authorizationRepository.save(authorization));
        }
    }

    private List<ModuleDTO> convertResourceToModule(List<Resource> resources) {
        List<ModuleDTO> root = new ArrayList<>();

        Map<Integer, ModuleDTO> map = new HashMap<>();
        resources.forEach(resource -> {
            //模块
            if (Resource.ResourceType.MODULE.equals(resource.getResourceType())) {
                ModuleDTO moduleDTO = ModuleMapper.MAPPER.entityToDTO(resource);
                map.put(resource.getId(), moduleDTO);

                Integer parentId = resource.getParent() == null ? null : resource.getParent().getId();
                if (map.containsKey(parentId)) {
                    map.get(parentId).addChild(moduleDTO);
                } else {
                    root.add(moduleDTO);
                }
            }

            //操作
            if (Resource.ResourceType.OPERATION.equals(resource.getResourceType())) {
                OperationDTO operationDTO = OperationMapper.MAPPER.entityToDTO(resource);
                map.get(resource.getParent().getId()).addOperation(operationDTO);
            }
        });

        return root;
    }

}
