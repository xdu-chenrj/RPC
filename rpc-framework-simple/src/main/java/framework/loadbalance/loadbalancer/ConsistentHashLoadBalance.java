package framework.loadbalance.loadbalancer;


import framework.loadbalance.AbstractLoadBalance;
import framework.remote.dto.RpcRequest;

import java.util.List;

/**
 *
 * refer to dubbo consistent hash load balance
 */
// TODO
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceListAddress, RpcRequest rpcRequest) {
        return null;
    }
}
