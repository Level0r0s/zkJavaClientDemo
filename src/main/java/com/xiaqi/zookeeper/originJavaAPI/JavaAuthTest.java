package com.xiaqi.zookeeper.originJavaAPI;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaqi
 * @date 2019/7/7
 */
public class JavaAuthTest implements Watcher {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    // the zookeeper server ip:port
    private static final String connectString = "47.102.201.31:2181";

    // session timeout
    private static final int timeout = 5000;

    // authentication type
    private static final String authentication_type = "digest";

    // the correct password
    private static final String correctPwd = "123456";

    // the incorrect password
    private static final String notCorrectPwd = "1234561";

    // the parent node path
    private static final String pathParent = "/cxy";

    // the parent node value
    private static final String parentVal = "cxy";

    // the child node value
    private static final String childVal = "xiaqi";

    // the child node path
    private static final String pathChild = "/xiaqi";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // async connect the zookeeper server
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,new JavaAuthTest());
        // add the auth information
        zooKeeper.addAuthInfo(authentication_type,correctPwd.getBytes());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // create the nodes
        String parentPath = zooKeeper.create(pathParent, parentVal.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        System.out.println("创建结点："+pathParent+"，路径为："+parentPath);
        String childrenPath = zooKeeper.create(pathParent + pathChild, childVal.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        System.out.println("创建结点："+pathParent+ pathChild +"，路径为："+childrenPath);

        // get node data without auth info
        getDataWithoutAuth();
        setDataWithoutAuth();
        deleteDataWithoutAuth();

        // get by incorrect password
        getDataByIncorPwd();
        setDataByIncorPwd();
        deleteDataByIncorPwd();

        // get by the correct password
        getDataBycorPwd(zooKeeper);
        setDataBycorPwd(zooKeeper);
        deleteDataBycorPwd(zooKeeper);
    }

    private static void deleteDataBycorPwd(ZooKeeper zooKeeper) {
        try {
            zooKeeper.delete(pathChild,-1);
            System.out.println("correct：删除数据成功");
        } catch (Exception e) {
            System.err.println("correct：删除数据失败："+e.getMessage());
        }
    }

    private static void setDataBycorPwd(ZooKeeper zooKeeper) {
        try {
            zooKeeper.setData(pathChild,"new child val".getBytes(),-1);
            System.out.println("correct：设置数据成功");
        } catch (Exception e) {
            System.err.println("correct：设置数据失败："+e.getMessage());
        }
    }

    private static void getDataBycorPwd(ZooKeeper zooKeeper) {
        try {
            byte[] data = zooKeeper.getData(pathParent, null, null);
            System.out.println("correct：获取数据成功："+new String(data));
        } catch (Exception e) {
            System.err.println("correct：获取数据失败："+e.getMessage());
        }
    }

    private static void deleteDataByIncorPwd() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,null);
        zooKeeper.addAuthInfo(authentication_type,notCorrectPwd.getBytes());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            zooKeeper.delete(pathParent,-1);
            System.out.println("incorrect:删除数据成功");
        } catch (Exception e) {
            System.err.println("incorrect:删除数据失败："+e.getMessage());
        }
    }

    private static void setDataByIncorPwd() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,null);
        zooKeeper.addAuthInfo(authentication_type,notCorrectPwd.getBytes());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            zooKeeper.setData(pathParent,"new val".getBytes(),-1);
            System.out.println("incorrect:设置数据成功");
        } catch (Exception e) {
            System.err.println("incorrect:设置数据失败："+e.getMessage());
        }
    }

    private static void getDataByIncorPwd() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,null);
        zooKeeper.addAuthInfo(authentication_type,notCorrectPwd.getBytes());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            byte[] data = zooKeeper.getData(pathParent, null, null);
            System.out.println("incorrect:获取数据成功："+new String(data));
        } catch (Exception e) {
            System.err.println("incorrect:获取数据失败："+e.getMessage());
        }
    }

    private static void deleteDataWithoutAuth() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            zooKeeper.delete(pathParent,-1);
            System.out.println("no auth:删除数据成功");
        } catch (Exception e) {
            System.err.println("no auth:设置数据失败："+e.getMessage());
        }
    }

    private static void setDataWithoutAuth() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            zooKeeper.setData(pathParent,"new parent value".getBytes(),-1);
            System.out.println("no auth:设置数据成功");
        } catch (Exception e) {
            System.err.println("no auth:设置数据失败："+e.getMessage());
        }
    }

    private static void getDataWithoutAuth() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(connectString,timeout,null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            byte[] data = zooKeeper.getData(pathParent, null, null);
            System.out.println("no auth:获取数据成功："+new String(data));
        } catch (Exception e) {
            System.err.println("no auth:获取数据失败："+e.getMessage());
        }
    }

    @Override
    public void process(WatchedEvent event) {
        // wait the connection to finish
        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }
}
