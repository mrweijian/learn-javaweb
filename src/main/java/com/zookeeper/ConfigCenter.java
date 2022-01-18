package com.zookeeper;

import lombok.SneakyThrows;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ConfigCenter
 * @Author weijian
 * @Date 2021/10/11
 */

public class ConfigCenter {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public static void main(String[] args) throws Exception {


        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.None
                        && watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("连接已建立！");
                }
                countDownLatch.countDown();
            }

        };
        ZooKeeper zooKeeper = new ZooKeeper("116.62.223.130", 30 * 1000, watcher);
        zooKeeper.create("/myconfig", "weixiaojian".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 数据变化监听
        Watcher watcher1 = new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeDataChanged
                        && watchedEvent.getPath().equals("/myconfig")) {
                    System.out.println("数据发生变化："+
                            new String(zooKeeper.getData("/myconfig", this, null)));
                }
            }
        };
        System.out.println(new String(zooKeeper.getData("/myconfig", watcher1, null)));

        countDownLatch.await();

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
