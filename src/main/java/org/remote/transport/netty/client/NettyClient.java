package org.remote.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.remote.common.factory.SingletonFactory;
import org.remote.dto.RPCRequest;
import org.remote.transport.RpcRequestTransport;
import org.remote.transport.netty.codec.RPCMessageDecoder;
import org.remote.transport.netty.codec.RPCMessageEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


public class NettyClient implements RpcRequestTransport {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    private final ChannelProvider channelProvider;

    private final UnprocessedRequests unprocessedRequests;


    public NettyClient() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                       protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        // 心跳机制，如果Server超过5秒都没有接受到数据，则触发userEventTrigger事件
                        channelPipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                                .addLast(new RPCMessageEncoder())
                                .addLast(new RPCMessageDecoder())
                                .addLast(new NettyClientHandler());
                    }
                });
        channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
        unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    public Channel connect(InetSocketAddress inetSocketAddress) {

        return null;
    }


    @Override
    public Object sendRpcRequest(RPCRequest request) {
        return null;
    }
}
