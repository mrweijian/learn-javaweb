package com.pomelo.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyConfig
 * @Author weijian
 * @Date 2021/8/24
 */
@Configuration
public class MyConfig {

    @Autowired
    private PrometheusMeterRegistry registry;

    // Histogram：自带buckets区间分布统计，
    // 请求耗时（毫秒）区间：100,500,1000,3000，+Inf正无穷 ，不
    // 指定则默认0.01,0.025,0.05,0.075,....7.5,10,+Inf
    @Bean
    Histogram getHistogram() {
        return Histogram.build().labelNames("uri", "accessType", "code")
                .name("api_cost_histogram").help("请求耗时histogram")
                .buckets(100, 500, 1000, 3000).register(registry.getPrometheusRegistry());
    }


    // 客户端收集中位数，九分位数的数据指标，
    // 不指定则不统计分位数（timmer也属于summary，默认单位sencond,并统计max值）
    @Bean
    Summary getSummary() {
        return Summary.build().labelNames("uri", "accessType", "code")
                .name("api_cost_summary").help("请求耗时summary")
                .quantile(0.5, 0.05)
                .quantile(0.9, 0.01)
                .register(registry.getPrometheusRegistry());
    }

    // 只增不减的计数器
    @Bean
    Counter getCounter() {
        return Counter.build().labelNames("uri", "accessType", "code")
                .name("api_counter").help("Counter测试")
                .register(registry.getPrometheusRegistry());
    }

    // 可增可减的计数器
    @Bean
    Gauge getGauge() {
        return Gauge.build().labelNames("uri", "accessType", "code")
                .name("api_gauge").help("Gauge测试")
                .register(registry.getPrometheusRegistry());
    }

}
