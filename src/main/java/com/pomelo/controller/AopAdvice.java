package com.pomelo.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Tag;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AopAdvice
 * @Author weijian
 * @Date 2021/8/25
 */
@Aspect
@Component
public class AopAdvice {

    @Autowired
    private PrometheusMeterRegistry registry;

    private long startTime = 0;

    private long endTime = 0;

    @Pointcut("execution (* com.pomelo.controller.*.*(..))")
    public void point(){

    }


    @Before("point()")
    public void beforeAdvice() {
        startTime = System.currentTimeMillis();
        System.out.println("beforeAdvice");

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes instanceof ServletRequestAttributes) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        Tag tag1 = new ImmutableTag("url",request.getRequestURI());
        Tag tag2 = new ImmutableTag("method",request.getMethod());
        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        Counter.builder("cost_inteface_count")
                .tags(tags)
                .description("测试接口请求次数")
                .register(registry)
                .increment(1);
    }

    @After("point()")
    public void afterAdvice() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes instanceof ServletRequestAttributes) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        endTime = System.currentTimeMillis();
        long time = (endTime - startTime)/1000;

        Tag tag1 = new ImmutableTag("url",request.getRequestURI());
        Tag tag2 = new ImmutableTag("method",request.getMethod());
        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        Gauge.builder("cost_inteface_time", ()->time)
                .tags(tags)
                .description("测试接口请求消耗的时间！")
                .baseUnit("s")
                .register(registry);

        System.out.println("afterAdvice");
    }
}
