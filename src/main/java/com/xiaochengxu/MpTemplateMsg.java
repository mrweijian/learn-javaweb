package com.xiaochengxu;

import lombok.Data;

/**
 * @ClassName MpTemplateMsg
 * @Author weijian
 * @Date 2021/10/9
 */
@Data
public class MpTemplateMsg {

    /**
     *公众号appid，要求与小程序有绑定且同主体
     */
    private String appId;

    /**
     * 公众号模板id
     */
    private String templateId;

    /**
     * 公众号模板消息所要跳转的url
     */
    private String url;

    /**
     * 公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系
     */
    private String miniProgram;

    /**
     * 公众号模板消息的数据
     */
    private String data;
}
