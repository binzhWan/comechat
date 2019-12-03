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

import java.util.ArrayList;
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
        model.addAttribute("user",user);
        List<User> friends=new ArrayList<>();
        List<Integer> NewMsgNumbers=new ArrayList<>();
        ArrayList<String> friendsImgUrl = new ArrayList<>();
        try {
            friends = userService.queryFriendsByFriendId(userService.queryFriendsIdById(user.getId()));
            System.out.println("friend没查到但没异常");
        }catch (Exception e){
            friends=null;
            System.out.println("friends没查到报异常");
        }
        model.addAttribute("friends",friends);
        if (friends!=null){
            for (User friend : friends) {
                String friendImgUrl = userService.queryImgUrlByUserId(friend.getId());
                friendsImgUrl.add(friendImgUrl);
                try {
                    Integer number = userService.countOneUserNewMegByType(1, user.getId(), friend.getId());
                    NewMsgNumbers.add(number);
                }catch (Exception e){
                    System.out.println("消息为0报异常了");
                    NewMsgNumbers.add(0);
                }
            }
            model.addAttribute("friendsImgUrl",friendsImgUrl);
            model.addAttribute("NewMsgNumbers",NewMsgNumbers);
        }
        for (Integer integer : NewMsgNumbers) {
            System.out.println(integer);
        }
        String imgUrl = userService.queryImgUrlByUserId(user.getId());
        if (imgUrl==null){
            System.out.println("imgUrl为空");
            userService.addImgUrlIfPortraitIsNULL(user.getId(),"/hdist/media/img/man_avatar3.jpg");
            imgUrl="/hdist/media/img/man_avatar3.jpg";
            model.addAttribute("userPortrait",imgUrl);
        }else {
            model.addAttribute("userPortrait",imgUrl);
        }
        return "handpage/index";
    }
}
