package binzh.wan.comechat.controller;

import binzh.wan.comechat.chat.ContentVo;
import binzh.wan.comechat.chat.Message;
import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.service.UserService;
import binzh.wan.comechat.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.naming.Name;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{name}")
@Controller
@Component
public class WebSocketServerController {
    //存储客户端连接对象，每个客户端连接都会产生一个连接对象
    private static ConcurrentHashMap<String,WebSocketServerController> map=new ConcurrentHashMap();
    //每个连接有个自己的会话
    private Session session;
    private String name;
    private static List<String> names=new ArrayList<>();
    private  static UserServiceImpl userService;
    @OnOpen
    public void open(Session session, @PathParam("name") String name){
        User user = userService.queryUserByUsername(name);
        System.out.println(user);
        names.add(name);
        map.put(name,this);
        System.out.println(name+"连接服务器成功");
        this.session=session;
        this.name=name;

    }
    @OnClose
    public void close(){
        map.remove(name);
        System.out.println(name+"断开了服务器连接");
    }
    @OnError
    public void error(Throwable error){
        error.printStackTrace();
        System.out.println(name+"出现了异常");
    }
    @OnMessage
    public void getMessage(String msg) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ContentVo contentVo = objectMapper.readValue(msg, ContentVo.class);
        System.out.println("ConventVo:"+contentVo);
        System.out.println("收到"+name+":"+msg);
        Message message = new Message();
        message.setDate(new Date().toLocaleString());
        message.setSendMsg(contentVo.getMsg());
        message.setToName(contentVo.getToName());
        message.setFromName(name);
        binzh.wan.comechat.pojo.Message messagePOJO = new binzh.wan.comechat.pojo.Message();
        User user = userService.queryUserByUsername(contentVo.getToName());
        System.out.println(user);

        messagePOJO.setToId(userService.queryUserByUsername(contentVo.getToName()).getId());
        messagePOJO.setFromUsername(name);
        messagePOJO.setMessage(message.getSendMsg());
        messagePOJO.setFromId(userService.queryUserByUsername(name).getId());
        if (contentVo.getType()==1){
            if (names.contains(contentVo.getToName())){
                messagePOJO.setType(1);
                send(map.get(contentVo.getToName()),objectMapper.writeValueAsString(message));
                userService.saveMessage(messagePOJO);
            }else {
                messagePOJO.setType(2);
                userService.saveMessage(messagePOJO);
            }
        }
    }

    public void send(WebSocketServerController webSocketServerController,String message) throws IOException {
            webSocketServerController.session.getBasicRemote().sendText(message);
    }
    @Autowired
    public void setUserService(UserServiceImpl userService){
        this.userService=userService;
    }
}
