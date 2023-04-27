package framework.registry;

import framework.remote.dto.RpcRequest;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {

    InetSocketAddress discoveryService(RpcRequest rpcRequest);
}
