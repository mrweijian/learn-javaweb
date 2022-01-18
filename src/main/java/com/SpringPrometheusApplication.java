package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

//@SpringBootApplication(exclude = {LogbackMetricsAutoConfiguration.class, JvmMetricsAutoConfiguration.class})
@EnableScheduling
@SpringBootApplication
@EnableWebSocket
public class SpringPrometheusApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringPrometheusApplication.class, args);
    }

}