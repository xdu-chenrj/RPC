package org;

import org.annotation.RpcScan;
import org.api.HelloService;
import org.api.HelloServiceImpl;
import org.config.RpcServiceConfig;
import org.remote.transport.netty.server.NettyRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackages = {"org"})
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) annotationConfigApplicationContext.getBean("NettyRpcServer");

        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder().group("test2").version("version2").service(helloService).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}