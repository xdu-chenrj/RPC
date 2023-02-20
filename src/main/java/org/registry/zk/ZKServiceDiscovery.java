package org.registry.zk;

import org.registry.ServiceDiscovery;

import java.net.InetSocketAddress;

public class ZKServiceDiscovery implements ServiceDiscovery {
    @Override
    public InetSocketAddress discoveryService(String rpcServiceName) {
        return null;
    }
}
