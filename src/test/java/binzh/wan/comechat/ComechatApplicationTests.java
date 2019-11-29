package binzh.wan.comechat;

import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ComechatApplicationTests {

    @Autowired
    UserServiceImpl userService;
    @Test
    void contextLoads() {
        List<String> strings = userService.queryHistoryMsgByFromIDAndToId(2, 3);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
