package com.pomelo.controller;

import com.pomelo.entiy.Demo;

/**
 * @ClassName Test
 * @Author weijian
 * @Date 2021/9/9
 */
public class Test {
    public Demo test() {
        System.out.println("java调用成功");
        Demo demo = new Demo();
        demo.setName("魏建");
        demo.setAge(18);
        return demo;
    }
}
