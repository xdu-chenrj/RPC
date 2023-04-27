package framework.loadbalance.loadbalancer;

import framework.loadbalance.AbstractLoadBalance;
import framework.remote.dto.RpcRequest;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceListAddress, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceListAddress.get(random.nextInt(serviceListAddress.size()));
    }
}
