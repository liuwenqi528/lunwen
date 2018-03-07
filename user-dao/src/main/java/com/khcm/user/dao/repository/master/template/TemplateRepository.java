package com.khcm.user.dao.repository.master.template;

import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.common.enums.TemplateStatus;
import com.khcm.user.dao.entity.business.system.Template;
import com.khcm.user.dao.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by IntelliJ IDEA.
 * User: rqn
 * Date: 2018/3/2
 * Time: 9:31
 */
public interface TemplateRepository extends BaseRepository<Template,Integer> {
    @Modifying
    @Query("update Template st set st.templateStatus = ?2 where st.tplId=?1")
    void updateTplStatus(Integer tplId, TemplateStatus smsTplStatus);
    
    @Modifying
    @Query("update Template st set st.rejectReply = ?2 where st.tplId=?1")
    void updateTplreply(Integer tplId, String reply);

    //Template findBySmsTypeEquals(SmsCodeType smsCodeType);
    Template findByBusinessTypeEquals(BusinessType businessType);
}
