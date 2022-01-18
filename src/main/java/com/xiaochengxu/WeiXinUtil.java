package com.xiaochengxu;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName WeiXinUtil
 * @Author weijian
 * @Date 2021/10/9
 */

@Slf4j
public class WeiXinUtil {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();


    /**
     * 获取小程序 access_token ,每天最多获取200次，一次2小时失效
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/access-token/getAccessToken.html
     */
    public static synchronized void getAccessTokenResult(String appid, String secret) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        // 微信的接口
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appid, secret);
        // 进行网络请求,访问url接口
        responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            JSONObject parse = JSONObject.parseObject(body);
            String accessToken = parse.getString("access_token");
            String expiresIn = parse.getString("expires_in");
            threadLocal.set(accessToken);
        }

    }


    public static void main(String[] args) {
        String appid = "wx0d577090e1bd64d5";
        String secret = "15cf6803bd02c483c95d5a1f01868cbd";
        getAccessTokenResult(appid, secret);
    }


    /**
     * 发送统一模板消息
     *
     * @return
     */
    public static boolean sendUniformMessage(Message message) {

        log.info("发送统一模板消息:" + message.toString());

        String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=%s",
                threadLocal.get());
        log.info("发送统一模板消息到:" + requestUrl);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Message> requestEntity = new HttpEntity<>(message, headers);

        // 进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity,
                String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("统一消息发送成功！");
            return true;
        }
        log.error("统一消息发送失败！");
        return false;
    }

    /**
     * 发送订阅消息
     *
     * @param subscribeMessage
     * @return
     */
    public static boolean sendSubscribeMessage(SubscribeMessage subscribeMessage) {

        log.info("发送订阅的模板消息:" + subscribeMessage.toString());

        String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s",
                threadLocal.get());
        log.info("发送统一模板消息到:" + requestUrl);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SubscribeMessage> requestEntity = new HttpEntity<>(subscribeMessage, headers);

        // 进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity,
                String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("推送消息发送成功！");
            return true;
        }
        log.error("推送消息发送失败！");
        return false;
    }
}
