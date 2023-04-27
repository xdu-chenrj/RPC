package org.loadbalance;

import org.remote.dto.RpcRequest;

import java.util.List;

public interface LoadBalance {
    /**
     * select a service from the existing service address list
     * @param serviceAddress
     * @param rpcRequest
     * @return
     */
    String selectServiceAddress(List<String> serviceAddress, RpcRequest rpcRequest);
}
