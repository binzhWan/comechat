package binzh.wan.comechat.controller;

import binzh.wan.comechat.pojo.Message;
import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.pojo.img;
import binzh.wan.comechat.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @PostMapping("/changeMessage")
    @ResponseBody
    public img changeMessage(@RequestParam("username") String username,@RequestParam("file")
    MultipartFile file,@RequestParam("email") String email,@RequestParam("tel") String tel,
    @RequestParam("signature")String signature) {
        String imgUrl=null;
        img img = new img();
        User user = userService.queryUserByUsername(username);
        System.out.println("--------------------");
        if (file.isEmpty()){
            System.out.println("文件为空，请选择文件");
        }else {
            String originalFilename = file.getOriginalFilename();
            String filepath="src/main/resources/static/upload/img/";
//            String filepath="C:\\Users\\12590\\Desktop\\comechat\\src\\main\\resources\\static\\upload\\img\\";
            String filename= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + originalFilename;
            System.out.println("filename="+filename);
            imgUrl="/img/"+filename;
            File file1 = new File(filepath+filename);
            System.out.println("..."+filepath+filename);
            System.out.println("parent"+file1.getParentFile());
            if (!file1.getParentFile().exists()){
                file1.getParentFile().mkdirs();
                System.out.println("创建了");
            }
            try {
                String delFileStr = userService.queryImgUrlByUserId(user.getId());
                File delFile = new File(delFileStr);
                if (delFile.delete()){
                    System.out.println("文件删除成功");
                }else {
                    System.out.println("文件删除失败");
                }
                file.transferTo(file1.getAbsoluteFile());
                userService.updataPortrait(user.getId(),imgUrl);
                img.setImgUrl(imgUrl);
                img.setUsername(username);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("上传失败");
            }
        }
        return img;
    }

}
