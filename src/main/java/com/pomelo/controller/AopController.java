package com.pomelo.controller;

import com.pomelo.event.MyEvent;
import com.pomelo.event.MyEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AopController
 * @Author weijian
 * @Date 2021/8/25
 */
@RestController
public class AopController {

    @Autowired
    private MyEventPublisher myEventPublisher;

    @GetMapping("say")
    public String sayHello(HttpServletRequest request){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myEventPublisher.publishEvent(new MyEvent("11"));
        return "hello";
    }


}
