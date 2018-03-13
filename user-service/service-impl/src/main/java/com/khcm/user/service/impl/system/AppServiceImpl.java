package com.khcm.user.service.impl.system;


import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.entity.business.system.QApp;
import com.khcm.user.dao.repository.master.system.AppRepository;
import com.khcm.user.dao.repository.master.system.AuthorizationRepository;
import com.khcm.user.service.api.system.AppService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.AppDTO;
import com.khcm.user.service.mapper.system.AppMapper;
import com.khcm.user.service.param.business.system.AppParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wangtao
 * @date 2017/12/1.
 */
@Service
@Transactional
@Slf4j
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Override
    public AppDTO saveOrUpdate(AppParam appParam) {
        Optional<Integer> appParamId = Optional.ofNullable(appParam.getId());
        return appParamId.map(id -> {
            App app = appRepository.findOne(appParam.getId());
            AppMapper.MAPPER.paramToEntity(appParam, app);
            return AppMapper.MAPPER.entityToDTO(appRepository.save(app));
        }).orElse(AppMapper.MAPPER.entityToDTO(appRepository.save(AppMapper.MAPPER.paramToEntity(appParam))));
    }

    /**
     * 删除系统-->同时删除系统关联系统资源、权限等数据
     *
     * @param ids 要删除的id集合
     */
    @Override
    public void remove(List<Integer> ids) {
        ids.forEach(id -> {
            authorizationRepository.deleteByAppId(id);
            appRepository.delete(id);
        });
    }

    @Override
    public AppDTO getById(Integer id) {
        return AppMapper.MAPPER.entityToDTO(appRepository.getOne(id));
    }

    /**
     * 根据系统编码查询系统信息
     *
     * @param appCode
     * @return
     * @author liuwenqi
     */
    @Override
    public AppDTO getByCode(String appCode) {
        Optional<String> optionalAppCode = Optional.ofNullable(appCode);
        return optionalAppCode.map(code -> AppMapper.MAPPER.entityToDTO(appRepository.findByCodeIs(code))
        ).orElse(null);
    }

    /**
     * 根据参数获取唯一对象
     *
     * @param appParam
     * @return
     */
    @Override
    public AppDTO getOne(AppParam appParam) {
        //1、构造条件
        QApp qApp = QApp.app;
        BooleanExpression p;

        //2、条件
        if (StringUtils.isNotBlank(appParam.getName())) {
            p = qApp.name.eq(appParam.getName());
        } else if (StringUtils.isNotBlank(appParam.getCode())) {
            p = qApp.code.eq(appParam.getCode());
        } else {
            p = qApp.isNotNull();
        }
        Optional<Integer> appParamId = Optional.ofNullable(appParam.getId());
        p = p.and(appParamId.map(id -> qApp.id.ne(id)).orElse(null));
        App app = Optional.ofNullable(appRepository.findList(p)).filter(apps -> apps.size() > 0).map(apps -> apps.get(0)).orElse(null);
        return AppMapper.MAPPER.entityToDTO(app);
    }

    @Override
    public List<AppDTO> getList(AppParam appParam) {
        //1、构造条件
        QApp app = QApp.app;
        BooleanExpression p = app.isNotNull();

        //2、条件
        if (StringUtils.isNotBlank(appParam.getCode())) {
            p = p.and(app.code.eq(appParam.getCode()));
        }

        //3、返回
        return AppMapper.MAPPER.entityToDTO(appRepository.findList(p));
    }

    /**
     * @param appParam
     * @return
     * @update for Qimeng Duan
     * @当用户输入关键字时，进行模糊查询
     * @date 2018-01-24
     */
    @Override
    public PageDTO<AppDTO> getPage(AppParam appParam) {
        //1、构造条件
        QApp app = QApp.app;
        BooleanExpression predicate = app.isNotNull();
        String name = appParam.getName();
        //name不为空时，添加like条件
        if (StringUtils.isNotBlank(name)) {
            predicate = app.name.like("%" + name + "%");
        }
        //code不为空时，添加like条件
        String code = appParam.getCode();
        if (StringUtils.isNotBlank(code)) {
            predicate = predicate.and(app.code.like("%" + code + "%"));
        }
        //Changed by liuwenqi
        Optional<Date> optionalBeginDate = Optional.ofNullable(appParam.getBeginTime());
        predicate = predicate.and(optionalBeginDate.map(beginDate -> app.gmtCreate.goe(beginDate)).orElse(null));
        Optional<Date> optionalEndDate = Optional.ofNullable(appParam.getEndTime());
        predicate = predicate.and(optionalEndDate.map(endDate -> app.gmtCreate.loe(endDate)).orElse(null));
        //Changed end
        //2、查询
        Page<App> page = appRepository.findPage(predicate);

        //3、转换
        List<AppDTO> appList = AppMapper.MAPPER.entityToDTO(page.getContent());
        //4、返回
        return PageDTO.of(page.getTotalElements(), appList);
    }


}
