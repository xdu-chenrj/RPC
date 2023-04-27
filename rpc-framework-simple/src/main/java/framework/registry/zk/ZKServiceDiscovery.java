package framework.registry.zk;

import framework.enums.RpcErrorMessageEnum;
import framework.loadbalance.LoadBalance;
import framework.remote.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import framework.exception.RpcException;
import framework.extension.ExtensionLoader;
import framework.registry.ServiceDiscovery;
import framework.registry.zk.util.CuratorUtils;
import framework.utils.CollectionUtil;

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
