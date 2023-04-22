package org.registry.zk;

import org.registry.ServiceDiscovery;
import org.remote.dto.RpcRequest;

import java.net.InetSocketAddress;

public class ZKServiceDiscovery implements ServiceDiscovery {
    @Override
    public InetSocketAddress discoveryService(RpcRequest rpcRequest) {
        return null;
    }
}
