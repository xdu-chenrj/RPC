package org.registry.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.enums.RpcErrorMessageEnum;
import org.exception.RpcException;
import org.extension.ExtensionLoader;
import org.loadbalance.LoadBalance;
import org.registry.ServiceDiscovery;
import org.registry.zk.util.CuratorUtils;
import org.remote.dto.RpcRequest;
import org.utils.CollectionUtil;

import java.net.InetSocketAddress;
import java.util.List;


@Slf4j
public class ZKServiceDiscovery implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZKServiceDiscovery() {
        // TODO
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress discoveryService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVER_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        String targetUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        String[] socketAddressArray = targetUrl.split(":");
        String address = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(address, port);
    }
}
