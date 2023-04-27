package org.registry;

import org.remote.dto.RpcRequest;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {

    InetSocketAddress discoveryService(RpcRequest rpcRequest);
}
