package org.loadbalance.loadbalancer;


import org.loadbalance.AbstractLoadBalance;
import org.remote.dto.RpcRequest;

import java.util.List;

/**
 *
 * refer to dubbo consistent hash load balance
 */
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceListAddress, RpcRequest rpcRequest) {
        return null;
    }
}
