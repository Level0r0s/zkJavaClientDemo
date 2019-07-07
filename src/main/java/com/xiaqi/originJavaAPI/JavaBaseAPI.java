package com.xiaqi.originJavaAPI;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiaqi
 * @date 2019/7/6
 */
public class JavaBaseAPI implements Watcher {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String CONNECT_STRING = "47.102.201.31:2181";

    private static final int timeout = 5000;

    public static void main(String[] args) throws Exception {
        // create zookeeper client
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING,timeout,new JavaBaseAPI());
        // wait until the connection finished
        countDownLatch.await();
        // delete the node if need
        if (zooKeeper.exists("/xiaqi",false) != null) {
            // -1 show this node can be any version
            zooKeeper.delete("/xiaqi",-1);
        }
        // create the node whose path is /xiaqi
        String s = zooKeeper.create("/xiaqi", "xiaqi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("create--->s:"+s);
        // store the node stat
        Stat sta = new Stat();
        // read the node data whose path is /xiaqi
        byte[] data = zooKeeper.getData("/xiaqi", null, sta);
        System.out.println(sta);
        System.out.println("/xiaqi:"+new String(data));
        // set the node data whose path is /xiaqi
        Stat stat = zooKeeper.setData("/xiaqi", "夏齐".getBytes(), 0);
        System.out.println(stat);
        System.out.println("twice get:"+new String(zooKeeper.getData("/xiaqi",null,null)));
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        // if connected success
        if (event.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("connect zk server success!");
            // notify the main thread continue to run
            countDownLatch.countDown();
        }
    }
}
