package com.xiaochengxu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName WeappTemplateMsg
 * @Author weijian
 * @Date 2021/10/9
 */
@Data
public class WeAppTemplateMsg {

    /**
     * 小程序模板ID
     */
    @JSONField(name = "template_id")
    private String templateId;

    /**
     * 小程序页面路径
     */
    @JSONField(name = "page")
    private String page;

    /**
     * 小程序模板消息formid
     */
    @JSONField(name = "form_id")
    private String formId;

    /**
     * 小程序模板数据
     */
    @JSONField(name = "data")
    private String data;

    /**
     * 小程序模板放大关键词
     */
    @JSONField(name = "emphasis_keyword")
    private String emphasisKeyword;

}
