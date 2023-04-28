package framework;

import framework.annotation.RpcScan;
import framework.config.RpcServiceConfig;
import framework.remote.transport.netty.server.NettyRpcServer;
import framework.serviceImpl.HelloServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackages = "framework")
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) annotationConfigApplicationContext.getBean("nettyRpcServer");

        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder().group("test2").version("version2").service(helloService).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}