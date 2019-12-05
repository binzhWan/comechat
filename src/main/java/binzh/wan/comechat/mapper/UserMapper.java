package binzh.wan.comechat.mapper;

import binzh.wan.comechat.pojo.Message;
import binzh.wan.comechat.pojo.friend;
import binzh.wan.comechat.pojo.img;
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
    public void saveMessage(Message message);
    public Integer countOneUserNewMegByType(Integer type,Integer fromId,Integer toId);
    public void updataPortrait(Integer userId, String imgUrl);
    public String queryImgUrlByUserId(Integer userId);
    public void addImgUrlIfPortraitIsNULL(Integer userId,String imgUrl);
    public void addUser(Integer userId,Integer friendId);
    public friend checkUserIsExist(Integer userId, Integer friendId);

}
