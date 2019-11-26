package binzh.wan.comechat.service;

import binzh.wan.comechat.mapper.UserMapper;
import binzh.wan.comechat.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
