package org.api;

import lombok.extern.slf4j.Slf4j;
import org.annotation.RpcService;

@Slf4j
@RpcService(group = "test", version = "version")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(Hello hello) {
        log.info("helloServiceImpl receive {}", hello.getMessage());
        String result = "hello description is " + hello.getDescription();
        log.info("helloServiceImpl return {}", result);
        return result;
    }
}
