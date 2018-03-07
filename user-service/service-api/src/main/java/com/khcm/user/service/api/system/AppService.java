package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.AppDTO;
import com.khcm.user.service.param.business.system.AppParam;

import java.util.List;

/**
 * 系统服务
 * Created by yangwb on 2017/12/4.
 */
public interface AppService {

    /**
     * 系统保存或者修改
     * @param appParam
     * @return
     */
    AppDTO saveOrUpdate(AppParam appParam);

    /**
     * 系统删除
     * @param ids 要删除的id集合
     */
    void remove(List<Integer> ids);

    /**
     * 根据ID获取系统
     * @param id
     * @return
     */
    AppDTO getById(Integer id);

    /**
     * 根据系统编码获取系统
     * @param appCode
     * @return
     */
    AppDTO getByCode(String appCode);
    /**
     * 表单查询
     * @param appParam
     * @return
     */
    List<AppDTO> getList(AppParam appParam);

    /**
     * 带有分页功能的表单查询
     * @param appParam
     * @return
     */
    PageDTO<AppDTO> getPage(AppParam appParam);

    /**
     * 根据参数获取唯一数据
     * @param appParam
     * @return
     */
    AppDTO getOne(AppParam appParam);
}
