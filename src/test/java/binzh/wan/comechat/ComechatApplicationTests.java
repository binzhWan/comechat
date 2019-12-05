package binzh.wan.comechat;

import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.pojo.friend;
import binzh.wan.comechat.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ComechatApplicationTests {

    @Autowired
    UserServiceImpl userService;
    @Test
    void contextLoads() {
        friend friend = userService.checkUserIsExist(12, 13);
        System.out.println(friend);
        userService.addUser(12,13);
        friend = userService.checkUserIsExist(12, 13);
        System.out.println(friend);
    }
}
