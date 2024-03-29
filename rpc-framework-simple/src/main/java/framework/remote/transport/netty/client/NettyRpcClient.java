package framework.remote.transport.netty.client;

import framework.enums.CompressTypeEnum;
import framework.remote.dto.RpcMessage;
import framework.remote.dto.RpcRequest;
import framework.remote.dto.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import framework.enums.SerializationTypeEnum;
import framework.extension.ExtensionLoader;
import framework.factory.SingletonFactory;
import framework.registry.ServiceDiscovery;
import framework.remote.constants.RpcConstants;
import framework.remote.transport.RpcRequestTransport;
import framework.remote.transport.netty.codec.RpcMessageDecoder;
import framework.remote.transport.netty.codec.RpcMessageEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyRpcClient implements RpcRequestTransport {
    private final EventLoopGroup eventLoopGroup;
    private final ServiceDiscovery serviceDiscovery;
    private final Bootstrap bootstrap;
    private final ChannelProvider channelProvider;
    private final UnprocessedRequests unprocessedRequests;


    public NettyRpcClient() {
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
                        // heartbeat mechanism
                        channelPipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                                .addLast(new RpcMessageEncoder())
                                .addLast(new RpcMessageDecoder())
                                .addLast(new NettyRpcClientHandler());
                    }
                });
        channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
        unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
    }


    /**
     * Establish a connection with the service and obtain a channel that can read and write data
     *
     * @param inetSocketAddress
     * @return
     */
    public Channel connect(InetSocketAddress inetSocketAddress) {
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        ChannelFuture channelFuture = bootstrap.connect(inetSocketAddress);
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("the client connected {} successful", inetSocketAddress);
                completableFuture.complete(future.channel());
            } else {
                throw new IllegalStateException();
            }
        });
        Channel res = null;
        try {
            res = completableFuture.get();
        } catch (InterruptedException e) {
            log.error("occur interrupt exception when connect");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("occur execution exception when connect");
        }
        return res;
    }


    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        CompletableFuture<RpcResponse<Object>> resultFuture = new CompletableFuture<>();
        // get service address based on rpc request
        InetSocketAddress inetSocketAddress = serviceDiscovery.discoveryService(rpcRequest);
        // get server address connection channel
        Channel channel = getChannel(inetSocketAddress);

        if (channel.isActive()) {
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            RpcMessage rpcMessage = RpcMessage.builder().data(rpcRequest)
                    .serializeType(SerializationTypeEnum.HESSIAN.getCode())
                    .messageType(RpcConstants.REQUEST_TYPE)
                    .compressType(CompressTypeEnum.GZIP.getCode()).build();
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("client send message: {}", rpcMessage);
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                }
            });
        } else {
            throw new IllegalStateException();
        }
        return resultFuture;
    }

    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        Channel channel = channelProvider.get(inetSocketAddress);
        if (channel == null) {
            channel = connect(inetSocketAddress);
            channelProvider.set(inetSocketAddress, channel);
        }
        return channel;
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }
}
