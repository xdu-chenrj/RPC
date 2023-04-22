package org.loadbalance.loadbalancer;

import org.loadbalance.AbstractLoadBalance;
import org.remote.dto.RpcRequest;

import java.util.List;

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceListAddress, RpcRequest rpcRequest) {
        return null;
    }
}
