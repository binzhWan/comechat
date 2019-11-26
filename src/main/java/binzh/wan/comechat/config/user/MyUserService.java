package binzh.wan.comechat.config.user;

import binzh.wan.comechat.pojo.User;
import binzh.wan.comechat.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Component
public class MyUserService implements UserDetailsService {
    @Autowired
    private UserServiceImpl userService;
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //从数据库根据用户名获取用户信息
        System.out.println(s);
        User userByName = userService.queryUserByUsername(s);
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        System.out.println(userByName);
        //创建一个新的UserDetails对象，最后验证登陆的需要
        UserDetails userDetails=null;
        if(userByName!=null){
            System.out.println(encode.encode(userByName.getPassword()));
//            //创建一个集合来存放权限+
            Collection<GrantedAuthority> authorities = getAuthorities(userByName);
            //实例化UserDetails对象
            userDetails=new org.springframework.security.core.userdetails.User(s,encode.encode(userByName.getPassword()),authorities);
        }
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //注意：这里每个权限前面都要加ROLE_。否在最后验证不会通过
        authList.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return authList;
    }
}
