package com.jvm;

/**
 * @ClassName YongGC
 * @Author weijian
 * @Date 2021/10/14
 */
public class YongGC {

    // jvm参数设置
    // -XX:NewSize=5242880 -XX:MaxNewSize=5242880 设置初始新生代
    // -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 初始堆大小
    // -XX:SurvivorRatio=8 设置8:1:1
    // -XX:PretenureSizeThreshold=10485760  制定大对象阈值
    // -XX:+UseParNewGC -XX:+UseConcMarkSweepGC  使用ParNew+CMS GC
    // -XX:+PrintGCDetails 打印详细GC日志
    // -XX:+PrintGCTimeStamps 打印每次GC发生的时间
    // -Xloggc:gc.log gc日志写入磁盘
    // -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
    public static void main(String[] args) {
        byte[] array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = new byte[1024 * 1024];
        array1 = null;
        byte[] array2 = new byte[2 * 1024*1024];
    }
}
