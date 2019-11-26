package binzh.wan.comechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandPageController {
    @RequestMapping("/toHandPage")
    public String toHandPage(){
        System.out.println("toHandPage");
        return "handpage/index";
    }
}
