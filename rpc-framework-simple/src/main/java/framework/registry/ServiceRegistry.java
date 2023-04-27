package framework.registry;

import framework.extension.SPI;

import java.net.InetSocketAddress;

@SPI
public interface ServiceRegistry {
    void registerService(String rpcServiceName, InetSocketAddress address);
}
