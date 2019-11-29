package binzh.wan.comechat.controller;

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
        HashMap<String, List<String>> map = new HashMap<>();
        HashMap<String, String> noMap = new HashMap<>();
        List<String> messages1=null;
        List<String> messages2=null;
        try {
            messages1  = userService.queryHistoryMsgByFromIDAndToId(userService.queryUserByUsername(username).getId(),
                    userService.queryUserByUsername(friendName).getId());
            messages2  = userService.queryHistoryMsgByFromIDAndToId(userService.queryUserByUsername(friendName).getId(),
                    userService.queryUserByUsername(username).getId());
            map.put("Mymessage", messages1);
            map.put("OpsiteMessage", messages2);
            for (String s : messages1) {
                System.out.println(s);
            }
            for (String s : messages2) {
                System.out.println(s);
            }
            return map;
        }catch (Exception e ){
            if (messages1!=null||messages2!=null) {
                map.put("Mymessage", messages1);
                map.put("OpsiteMessage", messages2);
                return map;
            }else {
                noMap.put("Mymessage","NoMessage");
                noMap.put("OpsiteMessage","NoMessage");
                return noMap;
            }
        }






    }
}
