package framework.remote.transport;

import framework.remote.dto.RpcRequest;

public interface RpcRequestTransport {

    /**
     * send rpcRequest and receive result
     * @param request
     * @return
     */
    Object sendRpcRequest(RpcRequest request);
}
