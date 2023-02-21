package org.remote.transport;

import org.remote.dto.RPCRequest;

public interface RpcRequestTransport {

    /**
     * send rpcRequest and receive result
     * @param request
     * @return
     */
    Object sendRpcRequest(RPCRequest request);
}
