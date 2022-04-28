package com.wj.demo.domain;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName FlowableDomain
 * @Author weijian
 * @Date 2022/3/24
 */
@Data
public class FlowableDomain {

    /**
     * 必要参数
     */
    private String param;

    /**
     * 补充参数
     */
    private Map<String,Object> variables;
}
