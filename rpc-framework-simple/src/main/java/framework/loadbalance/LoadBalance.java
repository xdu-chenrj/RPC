package framework.loadbalance;

import framework.extension.SPI;
import framework.remote.dto.RpcRequest;

import java.util.List;

@SPI
public interface LoadBalance {
    /**
     * select a service from the existing service address list
     *
     * @param serviceAddress
     * @param rpcRequest
     * @return
     */
    String selectServiceAddress(List<String> serviceAddress, RpcRequest rpcRequest);
}
