#springboot快速启动  
##1.配置文件  
###1.1配置druid数据库的连接
```yaml
spring:
  datasource:
    username: root
    password: root
    #?serverTimezone=UTC解决时区的报错
    url: jdbc:mysql://localhost:3306/user?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource

    #Spring Boot 默认是不注入这些属性值的，需要自己绑定
    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters，stat:监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许时报错  java.lang.ClassNotFoundException: org.apache.log4j.Priority
    #则导入 log4j 依赖即可，Maven 地址： https://mvnrepository.com/artifact/log4j/log4j
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
  thymeleaf:
    cache: false
```
###1.1.1引入阿里巴巴数据源的maven坐标
```xml
 <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.12</version>
        </dependency>
```
###1.2整合mybatis
##注意！！！  
###yaml文件中的mybaties顶格写！！！
```yaml
mybatis:
  type-aliases-package: binzh.wan.learn.shiro_springboot.pojo
  mapper-locations: classpath:mapper/*.xml
```
###1.2.1引入springboot整合mybatis的maven坐标
```xml
  <!-- 引入 myBatis，这是 MyBatis官方提供的适配 Spring Boot 的，而不是Spring Boot自己的-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
```
###1.3关闭thymeleaf缓存
```yaml
 thymeleaf:
    cache: false
```
###1.3.1引入thymeleaf的mavne坐标
```xml
 <!--thymeleaf模板-->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
        </dependency>

        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-java8time</artifactId>
        </dependency>
```
##2.创建包  
主包下创建  
*  pojo  **需要添加的注解**
   *  @Data
   *  @NoArgsConstructor
   *  @AllArgsConstructor
    ```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {
        private Integer id;
        private String username;
        private String password;
        private String email;
        private String signature;
        private String tel;
        private String gender;
        private Date birthday;
    }
    ```
*  mapper    **需要添加的注解**  
   *  @Mapper
   *  @Repository
   * **注意这是一个接口**
    ```java
    @Mapper
    @Repository
    public interface UserMapper {
        public User queryUserById(Integer id);
    }
    ```
* /resources下创建mapper
   * 创建配置文件
   * 需引入头部东西
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    ```
    * 注意 如下配置  namespace 为该资源文件路径下所对应的那个mapper接口类
        ```xml
        <mapper namespace="binzh.wan.learn.shiro_springboot.mapper.UserMapper">
        
        </mapper>
        ```
*  service  
   * UserService  接口类
      * 不需要添加的注解
      * 里面定义抽象方法  相当于以前的dao层
        ```java
        public interface UserService {
            public User queryUserById(Integer id);
        }
        ```
   * UserServiceImpl   
       * 需要添加注解
          * @Service
          * 属性 是 mapper包下的接口
             *   @Autowired  
                 UserMapper userMapper;
            ```java
            @Service
            public class UserServiceImpl implements UserService {
                @Autowired
                UserMapper userMapper;
                @Override
                public User queryUserById(Integer id) {
                    return userMapper.queryUserById(id) ;
                }
            }
            ```
*  contoller
   *  HelloController 类
      * 需要添加注解
      * @RestController或@Controller
         * 属性 service包下的实体类 
         *   @Autowired  
             private UserServiceImpl userService;
        ```java
        @RestController
        public class HelloController {
            @Autowired
            private UserServiceImpl userService;
            @GetMapping("/hello1")
            public User hello(){
                User user = userService.queryUserById(2);
                return user;
            }
        }
        ```
#2总结！！！
* 静态资源放在static下面  
* 动态模板放在templates下面
* 静态页面加载不出来在templates中  不用认为是自己错误！！！（我被坑了一天）直接写contoller动态加载测试


#3国际化
##(1)创建文件
* 在resources下创建i18n文件夹  
   *  #注意
      #要先设置编码 全部为 UTF-8
      #file>>setting>>endit>>file encording  
      若是把对properties文件的内容完成再更改代码，则会出现菱形问号乱码
* 创建 ***.properties文件，然后再创建***_zh_CN.properties文件,接着创建***_en_US.properties文件  
     ```xml
        index.content.one=你的过去我来不及参与，你的未来我奉陪到底。
        index.content.three=对于世界而言，你是一个人；但是对于某个人，你是他(她)的整个世界。
        index.content.two=看不见的是不是就等于不存在？也许只是被浓云遮住， 也许刚巧风傻飞入眼帘，我看不见你，却依然感到温暖.
        index.dark=主题
        index.down=我愿意等待你微笑，像一个温暖的太阳在早春的1000万年。大地依然沉重，世界依然在变化，我永远爱你.
        index.go=出发
        index.heroParagraph=不要着急，最好的总会在最不经意的时候出现。
        index.product=我们的产品
        index.section-paragraph=来聊科大是一个在线聊天页面。在这里，你可以遇见命中注定的他或她
        index.section-title=注册使用
        index.title=来聊科大
        index.title.one=未来
        index.title.three=陪伴
        index.title.two=生活
        index.turnMe=转换
     ```
* 在approcation文件里面配置
```yaml
spring:
  messages:
    basename: i18n.index  #后面为文件名
```
##(2)html
* ```html
   <a  th:href="@{/index.html(l='zh_CN')}"  target="_blank">中文</a>


   <a  th:href="@{/index.html(l='en_US')}"  target="_blank">english</a></div>
  ```
* 每句话的内容  th:text="#{  }"
* 注意href里面额路径     /index.html(l="zh_CN)    通过"()"传参
##(3)配置文件
* 创建config包，在该包下创建MyLocaleResolver并实现LocaleResolver
```java
package binzh.wan.comechat.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String l=httpServletRequest.getParameter("l");
        Locale locale=Locale.getDefault();
        if (!StringUtils.isEmpty(l)){
            String[] s = l.split("_");
            locale= new Locale(s[0], s[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
```
* 在config包下创建MyMvcCnfig 实现WebMvcConfiguraion接口。把MyLocaleResolver用set注入(加上Bean注解)
```java
package binzh.wan.comechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("login");
        //  addViewController  相当于@requestMapping("/login.html") 
        //  setViewName        相当于 return " login"
    }
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
```
#4 springsecurity数据库验证登录
##* 在config包下创建SecurityConfig类实现WebSecurityConfigurerAdapter接口   
###该类里面常见的方法   在configure(HttpSecurity http)方法中使用
* loginPage()： 自定义登录页面
* loginProcessingUrl()：将用户名和密码提交到的URL
* defaultSuccessUrl()： 成功登录后跳转的URL。 如果是直接从登录页面登录，会跳转到该URL；如果是从其他页面跳转到登录页面，登录后会跳转到原来页面。可设置true来任何时候到跳转该URL。
* successForwardUrl()：成功登录后重定向的URL
* failureUrl()：登录失败后跳转的URL，指定的路径要能匿名访问
* failureForwardUrl()：登录失败后重定向的URL
```java
package binzh.wan.comechat.config;

import binzh.wan.comechat.config.user.MyUserService;
import binzh.wan.comechat.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserService myUserService;
    @Autowired
    AuthenticationSuccessHandler MyAuthenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler MyAuthenticationFailureHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/","login.html").permitAll();
        http.formLogin()
            .loginPage("/toLogin")
                .successHandler(MyAuthenticationSuccessHandler)
//            .defaultSuccessUrl("/toHandPage");
        .failureHandler(MyAuthenticationFailureHandler);
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().rememberMeParameter("remember");
    }
    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("binzh").password(new BCryptPasswordEncoder().encode("123")).roles("vip2","vip3")
//                .and()
//                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("vip1","vip2","vip3")
//                .and()
//                .withUser("guest").password(new BCryptPasswordEncoder().encode("123")).roles("vip1");
        auth.userDetailsService(myUserService);
    }
}
```
## 在configure(AuthenticationManagerBuilder auth)方法中进行用户的认证
### 可在内存中创建用户名密码权限
```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("binzh").password(new BCryptPasswordEncoder().encode("123")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123")).roles("vip1");
}
```
## 若想查询数据库中的账号密码
### * 先创建MyUserService类继承UserDetailsSerbice
### * 重写该接口里面的loadUserByUsername
```java
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
```
#出错历程！！！
* 1 new出来的 UserDetails后面的GrantedAuthority(授权)类的参数不能为空,所以在下面写一个getAuthorities方法
* 从数据中获取到的密码需要进行BCryptPasswordEncoder类的加密
```java
            Collection<GrantedAuthority> authorities = getAuthorities(userByName);
            //实例化UserDetails对象
            userDetails=new org.springframework.security.core.userdetails.User(s,encode.encode(userByName.getPassword()),authorities);

```
* 并在SecurityConfig类中注入
```java
    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
```
### * 在SecurityConfig类configure(AuthenticationManagerBuilder auth)方法中实现
```java
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService);
    }
```

# 实现自定义登录成功和失败
## *创建MyAuthenticationSuccessHandler类 实现 AuthenticationSuccessHandler接口
## *在里面可以进行登录成功的处理，转发或重定向用原生的response或request来实现
```java
package binzh.wan.comechat.config.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component("MyAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(objectMapper.writeValueAsString(authentication));
//        response.getWriter().write((objectMapper.writeValueAsString(authentication)));
        request.getRequestDispatcher("/toHandPage").forward(request,response);
    }
}
```
## *创建MyAuthenticationFailureHandler类 实现 AuthenticationFailureHandler接口
## *在里面可以进行登录失败的处理，转发或重定向用原生的response或request来实现
```java
package binzh.wan.comechat.config.user;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component("MyAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        request.getRequestDispatcher("/errorToLogin").forward(request,response);
    }
}
```
## 在securityConfig类中的configure(HttpSecurity http)方法中
```java
        .successHandler(MyAuthenticationSuccessHandler)
        .failureHandler(MyAuthenticationFailureHandler);       

```
#4 ajax中遇到的问题
##* jquery循环遍历绑定点击事件
```html
    for (var i=0; i<friendclickname.length; i++) {
                var name=friendclickname[i];
                console.log(name);
                    $("." + name).bind("click", {index:name},clickHandler);
            }
            function clickHandler(event) {

                    $.ajax({
                        url:"clickedFriend",
                        type:"post",
                        contentType:"application/json;charset=UTF-8",
                        data:JSON.stringify({"friendName":event.data.index,"username":username}),
                        datatype:"json",
                        success:function (data) {
                            $(".message-content").html(data);
                        }
                    })
            }
```
## * 前后端数据交互
###* 若往后端传入json格式的字符串，则需在前台指定contentType:"application/json;charset=UTF-8"
###* 后台需要在参数中加入 @requesrbody 注解 
