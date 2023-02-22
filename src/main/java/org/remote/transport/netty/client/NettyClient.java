package org.remote.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.remote.dto.RPCRequest;
import org.remote.transport.RpcRequestTransport;


@AllArgsConstructor
@Data
public class NettyClient implements RpcRequestTransport {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;




    @Override
    public Object sendRpcRequest(RPCRequest request) {
        return null;
    }
}
