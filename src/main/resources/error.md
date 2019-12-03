* Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.  
原因: 在初始化springbooot的时候勾选了mybatis的整合包，必须在yaml文件中进行数据库的配置    
    
* org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): binzh.wan.comechat.  
原因:  在确定自己映射没错误的情况下  test时还是报错，可能是缓存的问题，可以关掉重新打卡爱或者删掉一个包，再重新引入  

* 国际化中文菱形问号中文乱码。  
原因: 在进行Bundle配置文件之前，先在file>>setting>>endit>>file encording页面中的编码全部变为UTF-8
* 国际化出现??_zh_cn??的乱码
原因 在.yaml配置文件中没有进行bundle配置文件的指定。故需在yaml文件中加上 spring.messages.basename: i18n.***
* thymeleaf中获取后端存入的list可以
th:text="${NewMsgNumbers.get(__${friendStat.index}__)}  这个__得加上