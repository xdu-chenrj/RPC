package framework;

import framework.config.RpcServiceConfig;
import framework.proxy.RpcClientProxy;
import framework.remote.transport.RpcRequestTransport;
import framework.remote.transport.socket.client.SocketRpcClient;

public class SocketClientMain {
    public static void main(String[] args) {
        RpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceConfig);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("hello", "world"));
        System.out.println(hello);
    }
}
