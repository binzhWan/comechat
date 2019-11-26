package binzh.wan.comechat;

import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComechatApplicationTests {

    @Autowired
    UserServiceImpl userService;
    @Test
    void contextLoads() {
        User user = userService.queryUserById(2);
        System.out.println(user);
    }
}
