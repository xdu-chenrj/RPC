package org.loadbalance.loadbalancer;

import org.loadbalance.AbstractLoadBalance;
import org.remote.dto.RpcRequest;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceListAddress, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceListAddress.get(random.nextInt(serviceListAddress.size()));
    }
}
