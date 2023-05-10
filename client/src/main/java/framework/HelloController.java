package framework;

import framework.annotation.RpcReference;
import org.springframework.stereotype.Component;

@Component
public class HelloController {
    @RpcReference(version = "version", group = "test")
    private HelloService helloService;

    public void test() throws InterruptedException {
        String hello = this.helloService.hello(new Hello("111", "222"));
        assert "hello description is 222".equals(hello);
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }
}
