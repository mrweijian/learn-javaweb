package com.xiaochengxu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName Message
 * @Author weijian
 * @Date 2021/10/9
 */
@Data
public class Message {

    /**
     * 用户openid
     */
    @JSONField(name = "touser")
    private String toUser;

    /**
     * 小程序模板消息相关的信息，可以参考小程序模板消息接口; 有此节点则优先发送小程序模板消息；（小程序模板消息已下线，不用传此节点）
     */
    @JSONField(name = "weapp_template_msg")
    private WeAppTemplateMsg weAppTemplateMsg;

    /**
     * 公众号模板消息相关的信息，可以参考公众号模板消息接口；有此节点并且没有weapp_template_msg节点时，发送公众号模板消息
     */
    @JSONField(name = "mp_template_msg")
    private MpTemplateMsg mpTemplateMsg;
}
