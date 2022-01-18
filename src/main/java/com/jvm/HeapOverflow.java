package com.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HeapOverflow
 * @Author weijian
 * @Date 2021/10/19
 */
public class HeapOverflow {

    // -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\weijian\gc_hprof -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
    public static void main(String[] args) {
        long count = 0;
        List<Object> objects = new ArrayList<>();
        while (true){
            objects.add(new Object());
            System.out.println("当前创建的对象个数----"+count++);
        }
    }
}
