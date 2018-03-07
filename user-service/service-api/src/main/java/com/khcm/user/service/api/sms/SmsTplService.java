package com.khcm.user.service.api.sms;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.sms.SmsTplDTO;
import com.khcm.user.service.param.business.sms.SmsTplParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rqn
 * Date: 2018/3/2
 * Time: 9:25
 */
public interface SmsTplService {
    SmsTplDTO saveOrUpdate(SmsTplParam smsTplParam);

    /**
     * 分页查询--可带有参数
     * @param smsTplParam
     * @return
     */
    PageDTO<SmsTplDTO> getPage(SmsTplParam smsTplParam);

    /**
     * 根据id查询某条数据
     * @param id
     * @return
     */
    SmsTplDTO getById(Integer id);

    /**
     * 删除
     * @param idList
     */
    void remove(List<Integer> idList);

    /**
     * 不带有分页的查询-可带参数
     * @param smsTplParam
     * @return
     */
    List<SmsTplDTO> getList(SmsTplParam smsTplParam);

    /**
     * 根据参数查询是否字段值重复
     * @param smsTplParam
     * @return
     */
    SmsTplDTO getOne(SmsTplParam smsTplParam);

    /**
     * 查询更新短信模板状态
     * @param id
     * @return
     */
    String changeSmsTplTencent(Integer id);
}
