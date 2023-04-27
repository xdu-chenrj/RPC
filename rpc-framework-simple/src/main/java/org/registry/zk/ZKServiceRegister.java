package org.registry.zk;

import org.apache.curator.framework.CuratorFramework;
import org.registry.ServiceRegistry;
import org.registry.zk.util.CuratorUtils;

import java.net.InetSocketAddress;

public class ZKServiceRegister implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress address) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + address.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
