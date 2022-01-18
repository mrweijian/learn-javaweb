package com.jvm;

/**
 * @ClassName ImitationBI
 * @Author weijian
 * @Date 2021/10/18
 */
public class ImitationBI {

    // -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=2097152000 -XX:MaxHeapSize=2097152000 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log -XX:MaxTenuringThreshold=15
    // jmap -dump:live,format=b,file=dump.hprof 10580
    public static void main(String[] args) throws Exception {
        Thread.sleep(30000);
        while (true) {
            loadData();
        }
    }

    public static void loadData() throws Exception {
        byte[] data = null;
        for (int i = 0; i < 50; i++) {
            data = new byte[100 * 1024];
        }
        data = null;
        Thread.sleep(1000);
    }
}
