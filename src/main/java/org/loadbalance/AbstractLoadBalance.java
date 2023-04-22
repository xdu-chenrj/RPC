package org.loadbalance;

import org.remote.dto.RpcRequest;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance{
    @Override
    public String selectServiceAddress(List<String> serviceAddress, RpcRequest rpcRequest) {
        if(serviceAddress.isEmpty()) {
            return null;
        }
        if(serviceAddress.size() == 1) {
            return serviceAddress.get(0);
        }
        return doSelect(serviceAddress, rpcRequest);
    }
    protected abstract String doSelect(List<String> serviceListAddress, RpcRequest rpcRequest);
}
