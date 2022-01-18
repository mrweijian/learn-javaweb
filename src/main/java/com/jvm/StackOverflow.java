package com.jvm;

/**
 * @ClassName StackOverflow
 * @Author weijian
 * @Date 2021/10/19
 */
public class StackOverflow {

    // -XX:ThreadStackSize=1m

    private static long count = 0;

    public static void main(String[] args) {
        work();
    }

    private static void work() {
        System.out.println("调用方法次数------"+count++);
        work();
    }
}
