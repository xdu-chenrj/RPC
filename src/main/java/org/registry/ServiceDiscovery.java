package org.registry;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {

    InetSocketAddress discoveryService(String rpcServiceName);
}
