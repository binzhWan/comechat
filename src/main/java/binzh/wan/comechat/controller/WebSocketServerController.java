package binzh.wan.comechat.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
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
    public void open(Session session){
//        map.put(name,this);
        System.out.println(name+"连接服务器成功");
        System.out.println("客户端连接个数:"+getConnetNum());

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
    public void getMessage(String message) throws IOException {
        System.out.println("收到"+name+":"+message);
        System.out.println("客户端连接个数:"+getConnetNum());

        Set<Map.Entry<String, WebSocketServerController>> entries = map.entrySet();
        for (Map.Entry<String, WebSocketServerController> entry : entries) {
            if(!entry.getKey().equals(name)){//将消息转发到其他非自身客户端
                entry.getValue().send(message);

            }
        }
    }

    public void send(String message) throws IOException {
        if(session.isOpen()){
            session.getBasicRemote().sendText(message);
        }
    }

    public int  getConnetNum(){
        return map.size();
    }

}
