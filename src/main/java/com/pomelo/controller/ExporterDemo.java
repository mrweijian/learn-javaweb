package com.pomelo.controller;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName ExporterDemo
 * @Author weijian
 * @Date 2021/8/24
 */
@Component
public class ExporterDemo {


    @PostConstruct
    public void initJvmExporter() {
        //io.prometheus.client.hotspot.DefaultExports.initialize();
    }

}
