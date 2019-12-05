package binzh.wan.comechat.controller;

import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.service.UserService;
import binzh.wan.comechat.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

@Controller
public class RegisterController {
    @Autowired
    UserServiceImpl userService;
    @RequestMapping("/register")
    public String register(User user, Model model){
        user.setRole("1");
        System.out.println(user);
        System.out.println("-------------------------");
        model.addAttribute("msg","注册成功！");
        userService.addUserByRegisting(user);
        return "redirect:/index.html";
    }
    @RequestMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(@PathParam("username") String username){
        System.out.println(username);
        User user = userService.queryUserByUsername(username);
        if(user!=null){
            return "true";
        }else{
            return "false";
        }
    }
}
