package binzh.wan.comechat.controller;

import binzh.wan.comechat.chat.ContentVo;
import binzh.wan.comechat.chat.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.naming.Name;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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
    @OnOpen
    public void open(Session session, @PathParam("name") String name){
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
        if (contentVo.getType()==1){
                    send(map.get(contentVo.getToName()),objectMapper.writeValueAsString(message));
        }
    }

    public void send(WebSocketServerController webSocketServerController,String message) throws IOException {
        if(webSocketServerController.session.isOpen()){
            webSocketServerController.session.getBasicRemote().sendText(message);
        }
    }

    public int  getConnetNum(){
        return map.size();
    }

}
