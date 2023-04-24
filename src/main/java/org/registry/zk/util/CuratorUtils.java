package org.registry.zk.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.common.enums.RpcConfigEnum;
import org.common.utils.PropertiesFileUtil;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CuratorUtils {
    public static final String ZK_REGISTER_ROOT_PATH = "/rpc";

    private static final int BASE_SLEEP_TIME = 1000;

    private static final int MAX_RETRIES = 3;
    private static final Map<String, List<String>> SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();

    private static final Set<String> REGISTERD_PATH_SET = ConcurrentHashMap.newKeySet();

    private static CuratorFramework zkClient;

    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";


    public static void createPersistentNode(CuratorFramework zkClient, String path) {
        try {
            if (REGISTERD_PATH_SET.contains(path) || zkClient.checkExists().forPath(path) != null) {
                log.info("the node already exists. the node is {}", path);
            } else {
                // /rpc/helloService/127.0.0.1:8888
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                log.info("the node was created successfully. the node is {}", path);
            }
            REGISTERD_PATH_SET.add(path);
        } catch (Exception e) {
            log.error("create persistent node for path [{}] fail", path);
        }
    }

    public static List<String> getChildrenNodes(CuratorFramework zkClient, String rpcServiceName) {
        if(SERVICE_ADDRESS_MAP.containsKey(rpcServiceName)) {
            return SERVICE_ADDRESS_MAP.get(rpcServiceName);
        }
        List<String> result = null;
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" +rpcServiceName;
        try {
            result = zkClient.getChildren().forPath(servicePath);
            SERVICE_ADDRESS_MAP.put(rpcServiceName, result);
            registerWatcher(rpcServiceName, zkClient);
        } catch (Exception e) {
            log.error("get children nodes for path {} fail", servicePath);
        }
        return result;
    }


    public static void clearRegistry(CuratorFramework zkClient, InetSocketAddress inetSocketAddress) {
        REGISTERD_PATH_SET.stream().parallel().forEach(p -> {
            try {
                if (p.endsWith(inetSocketAddress.toString())) {
                    zkClient.delete().forPath(p);
                }
            } catch (Exception e) {
                log.error("clear registry for path {} fail", p);
            }
        });
        log.info("all registered services on the server are cleared: {}", REGISTERD_PATH_SET);
    }

    public static CuratorFramework getZkClient() {
        // check if user has set zk address
        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyVal());
        String zookeeperAddress;
        if (properties != null && properties.getProperty(RpcConfigEnum.ZK_ADDRESS.getPropertyVal()) != null) {
            zookeeperAddress = RpcConfigEnum.ZK_ADDRESS.getPropertyVal();
        } else {
            zookeeperAddress = DEFAULT_ZOOKEEPER_ADDRESS;
        }
        if (zkClient != null && zkClient.getState() == CuratorFrameworkState.STARTED) {
            return zkClient;
        }
        // retry strategy
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        zkClient = CuratorFrameworkFactory.builder()
                .retryPolicy(retryPolicy).connectString(zookeeperAddress).build();
        zkClient.start();

        try{
            if(zkClient.blockUntilConnected(30, TimeUnit.SECONDS)) {
                log.error("time out waiting ti connect to zk");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return zkClient;
    }

    private static void registerWatcher(String rpcServiceName, CuratorFramework zkClient) {
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName;
        // TODO use AsyncCuratorFramework
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, servicePath, true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            List<String> serviceAddresses = curatorFramework.getChildren().forPath(servicePath);
            SERVICE_ADDRESS_MAP.put(rpcServiceName, serviceAddresses);
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            log.error("pathChildrenCache start fail");
        }
    }

}
