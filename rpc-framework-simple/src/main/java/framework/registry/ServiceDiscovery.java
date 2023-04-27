package framework.registry;

import framework.extension.SPI;
import framework.remote.dto.RpcRequest;

import java.net.InetSocketAddress;

@SPI
public interface ServiceDiscovery {

    InetSocketAddress discoveryService(RpcRequest rpcRequest);
}
