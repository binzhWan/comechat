package binzh.wan.comechat.mapper;

import binzh.wan.comechat.pojo.Message;
import binzh.wan.comechat.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    public User queryUserById(Integer id);
    public User queryUserByUsername(String username);
    public void addUserByRegisting(User user);
    public List<Integer> queryFriendsIdById(Integer id);
    public List<User> queryFriendsByFriendId(List<Integer> friendId);
    public List<Message> queryHistoryMsgByFromIDAndToId(Integer fromId, Integer toId);
}
