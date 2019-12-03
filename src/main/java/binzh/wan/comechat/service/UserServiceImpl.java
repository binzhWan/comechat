package binzh.wan.comechat.service;

import binzh.wan.comechat.mapper.UserMapper;
import binzh.wan.comechat.pojo.Message;
import binzh.wan.comechat.pojo.img;
import binzh.wan.comechat.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserById(Integer id) {
        return userMapper.queryUserById(id) ;
    }

    @Override
    public User queryUserByUsername(String username) {
        return userMapper.queryUserByUsername(username);
    }

    @Override
    public void addUserByRegisting(User user) {
        userMapper.addUserByRegisting(user);
    }

    @Override
    public List<Integer> queryFriendsIdById(Integer id) {
        return userMapper.queryFriendsIdById(id);
    }

    @Override
    public List<User> queryFriendsByFriendId(List<Integer> friendId) {
        return userMapper.queryFriendsByFriendId(friendId);
    }

    @Override
    public List<Message> queryHistoryMsgByFromIDAndToId(Integer fromId, Integer toId) {
        return userMapper.queryHistoryMsgByFromIDAndToId(fromId,toId);
    }

    @Override
    public void saveMessage(Message message) {
        userMapper.saveMessage(message);
    }

    @Override
    public Integer countOneUserNewMegByType(Integer type, Integer fromId, Integer toId) {
        return userMapper.countOneUserNewMegByType(type, fromId, toId);
    }

    @Override
    public void updataPortrait(Integer userId,  String imgUrl) {
        userMapper.updataPortrait(userId,imgUrl);
    }

    @Override
    public String queryImgUrlByUserId(Integer userId) {
        return userMapper.queryImgUrlByUserId(userId);
    }

    @Override
    public void addImgUrlIfPortraitIsNULL(Integer userId, String imgUrl) {
        userMapper.addImgUrlIfPortraitIsNULL(userId,imgUrl);
    }
}
