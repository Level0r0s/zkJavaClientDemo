package com.xiaqi.originJavaAPI;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaqi
 * @date 2019/7/7
 */
public class JavaAuthTest implements Watcher {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String connectString = "47.102.201.31:2181";

    private static final int timeout = 5000;

    private static final String correctPwd = "123456";

    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,new JavaAuthTest());

    }

    @Override
    public void process(WatchedEvent event) {
        // wait the connection to finish
        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }
}
