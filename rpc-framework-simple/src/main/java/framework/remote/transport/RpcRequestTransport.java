package framework.remote.transport;

import framework.extension.SPI;
import framework.remote.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {

    /**
     * send rpcRequest and receive result
     * @param request
     * @return
     */
    Object sendRpcRequest(RpcRequest request);
}
