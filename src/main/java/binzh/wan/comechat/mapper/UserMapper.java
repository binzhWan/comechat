package binzh.wan.comechat.mapper;

import binzh.wan.comechat.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    public User queryUserById(Integer id);
    public User queryUserByUsername(String username);
}
