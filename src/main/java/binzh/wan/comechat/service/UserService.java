package binzh.wan.comechat.service;

import binzh.wan.comechat.pojo.User;

public interface UserService {
    public User queryUserById(Integer id);
    public User queryUserByUsername(String username);
}
