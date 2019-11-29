package binzh.wan.comechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// 若是添加父路径  则会有 图片资源访问不到     @RequestMapping("/index")
public class IndexController {
    @RequestMapping("/toLogin")
    public String indexToLogin(Model model){
        System.out.println("toLogin");
        return "login";
    }
    @RequestMapping("/errorToLogin")
    public String errorToLogin(Model model){
        model.addAttribute("msg","用户名或密码错误");
        System.out.println("toLogin");
        return "login";
    }
//    @RequestMapping("/toSuccessLogin")
//    public String toSuccessLogin(Model model){
//        model.addAttribute("username","用户名或密码错误");
//        return "/toHandpage";
//    }
}
