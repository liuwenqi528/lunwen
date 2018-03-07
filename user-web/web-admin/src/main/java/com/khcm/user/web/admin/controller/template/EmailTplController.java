package com.khcm.user.web.admin.controller.template;

import com.khcm.user.service.api.template.TemplateService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.sms.SmsTplDTO;
import com.khcm.user.service.param.business.sms.SmsTplParam;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.model.mapper.sms.SmsTplDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.sms.SmsTplPM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.business.sms.SmsTplVM;
import com.khcm.user.web.admin.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by
 *
 * @author: liuwenqi on 2018-03-02.
 * Description:
 */
@Controller
@RequestMapping("/template/emailTpl")
public class EmailTplController {

    public static final String BASE_PATH = "business/template/email/";

//    @Autowired
//    private SmsTplService smsTplService;

    @Autowired
    @Qualifier("emailTplService")
    private TemplateService emailTplService;

    @RequiresPermissions("template:emailTpl:view")
    @GetMapping
    public String toIndexPage() {
        return BASE_PATH + "index";
    }

    @RequiresPermissions("template:emailTpl:view")
    @RequestMapping("/doDatagrid")
    @ResponseBody
    @OperationLog(name = "查询短信模板信息")
    public PageVM<SmsTplVM> doDatagrid(SmsTplPM SmsTplPM) {
        SmsTplParam smsTplParam = SmsTplDTOMapper.MAPPER.pmToParam(SmsTplPM);
        PageDTO<SmsTplDTO> page = emailTplService.getPage(smsTplParam);
        return VMUtils.pageSuccess(page.getTotalCount(), SmsTplDTOMapper.MAPPER.dtoToVM(page.getContent()));
    }

    @RequiresPermissions("template:emailTpl:add")
    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return BASE_PATH + "add";
    }

    @RequiresPermissions("template:emailTpl:add")
    @RequestMapping("/doSave")
    @ResponseBody
    @OperationLog(name = "保存短信模板信息")
    public ResultVM doSave(SmsTplPM smsTplPM) {
        SmsTplParam smsTplParam = SmsTplDTOMapper.MAPPER.pmToParam(smsTplPM);
        emailTplService.saveOrUpdate(smsTplParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("template:emailTpl:edit")
    @RequestMapping("/toEditPage")
    public String toEditPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            SmsTplDTO smsTplDTO = emailTplService.getById(id);
            model.addAttribute("smsTplVM", SmsTplDTOMapper.MAPPER.dtoToVM(smsTplDTO));
        }
        return BASE_PATH + "edit";
    }

    @RequiresPermissions("template:emailTpl:info")
    @RequestMapping("/toInfoPage")
    public String toInfoPage(Integer id, Model model) {
        if (Objects.nonNull(id)) {
            SmsTplDTO smsTplDTO = emailTplService.getById(id);
            model.addAttribute("smsTplVM", SmsTplDTOMapper.MAPPER.dtoToVM(smsTplDTO));
        }
        return BASE_PATH + "info";
    }

    /**
     * 根据id查询模板状态。 如果状态不为“待审核”，则将数据库中的状态进行更改，返回id对应的数据。
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("template:emailTpl:edit")
    @RequestMapping("/searchStatus")
    @ResponseBody
    public String searchStatus(Integer id, Model model) {
        String res=emailTplService.changeSmsTplTencent(id);
        return res;
    }

    @RequiresPermissions("template:emailTpl:edit")
    @RequestMapping("/doUpdate")
    @ResponseBody
    @OperationLog(name = "修改短信模板信息")
    public ResultVM doUpdate(SmsTplPM smsTplPM) {
        SmsTplParam smsTplParam = SmsTplDTOMapper.MAPPER.pmToParam(smsTplPM);
        emailTplService.saveOrUpdate(smsTplParam);
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("template:emailTpl:remove")
    @RequestMapping("/doRemove")
    @ResponseBody
    @OperationLog(name = "删除短信模板信息")
    public ResultVM doRemove(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idList = Arrays.stream(ids.split(",")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            emailTplService.remove(idList);
        }
        return VMUtils.resultSuccess();
    }

    @RequiresPermissions("template:emailTpl:view")
    @RequestMapping("/doComboBox")
    @ResponseBody
    public List<SmsTplVM> doComboBox(SmsTplPM smsTplPM) {
        SmsTplParam smsTplParam = SmsTplDTOMapper.MAPPER.pmToParam(smsTplPM);
        return SmsTplDTOMapper.MAPPER.dtoToVM(emailTplService.getList(smsTplParam));
    }

    /**
     * created by liuwenqi
     * 校验数据是否重复
     *
     * @param smsTplPM
     * @return
     * @date 2018-03-02
     */
    @RequiresPermissions("template:emailTpl:view")
    @RequestMapping("/doValidate")
    @ResponseBody
    public ResultVM doValidate(SmsTplPM smsTplPM) {
        SmsTplParam smsTplParam = SmsTplDTOMapper.MAPPER.pmToParam(smsTplPM);
        SmsTplDTO smsTplDTO = emailTplService.getOne(smsTplParam);
        ResultVM resultVM = Optional.ofNullable(smsTplDTO).map(ad->VMUtils.resultFailure()).orElse(VMUtils.resultSuccess());
        return resultVM;
    }
}
