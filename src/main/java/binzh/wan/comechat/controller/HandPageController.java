package binzh.wan.comechat.controller;

import binzh.wan.comechat.pojo.Message;
import binzh.wan.comechat.service.UserService;
import binzh.wan.comechat.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HandPageController {
    @Autowired
    UserServiceImpl userService;
    @PostMapping(value = "clickedFriend")
    @ResponseBody
    public Object clickedFriend(@RequestBody Map<String,Object> beJson){
        String username = (String)beJson.get("username");
        System.out.println(username);
        String friendName = (String)beJson.get("friendName");
        System.out.println(friendName);
        HashMap<String, List<Message>> map = new HashMap<>();
        HashMap<String, Message> noMap = new HashMap<>();
        List<Message> messages=null;
        try {
            messages  = userService.queryHistoryMsgByFromIDAndToId(userService.queryUserByUsername(username).getId(),
                    userService.queryUserByUsername(friendName).getId());
            map.put("message", messages);
            for (Message s : messages) {
                System.out.println(s);
            }
            return map;
        }catch (Exception e ){
                map.put("message", messages);
                return map;
        }
    }
}
