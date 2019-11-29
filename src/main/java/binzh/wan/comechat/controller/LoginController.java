package binzh.wan.comechat.controller;

import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserServiceImpl userService;
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }
    @RequestMapping("/toHandPage")
    public String toHandPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username",username);
        User user = userService.queryUserByUsername(username);
        List<User> friends = userService.queryFriendsByFriendId(userService.queryFriendsIdById(user.getId()));
        model.addAttribute("friends",friends);
        return "handpage/index";
    }
}
