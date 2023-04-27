package framework.remote.handler;

import framework.exception.RpcException;
import framework.remote.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import framework.factory.SingletonFactory;
import framework.provider.ServiceProvider;
import framework.provider.impl.ZKServiceProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RpcRequest processor
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZKServiceProvider.class);
    }


    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    public Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service {} invoke  method {}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }


}
