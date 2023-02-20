package org.transport;

import org.dto.RPCRequest;

public interface RpcRequestTransport {

    /**
     * send rpcRequest and receive result
     * @param request
     * @return
     */
    Object sendRpcRequest(RPCRequest request);
}
