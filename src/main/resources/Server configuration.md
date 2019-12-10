# Server configuration(linux-mysql)
## 
* 解压mysql压缩文件 #  tar -xvf mysql-8.0.18-linux-glibc2.12-x86_64.tar.xz
* 重命名并移动到/usr/local/mysql   #  mv mysql-8.0.18-linux-glibc2.12-x86_64 /usr/local/mysql
* 在/usr/local/mysql下创建 data文件夹     #  mkdir data
* 初始化数据库 # bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data
 在此保存密码
* ps：中间遇到一个坑，初始化数据库时候报了错 具体错误为：bin/mysqld: error while loading shared libraries: libaio.so.1: cannot open shared object file: No such file or directory
  ......................解决方法为：yum install libaio-devel.x86_64   原因是缺少了libaio这个东西 安装就完事了
* 修改/usr/local/mysql 当前目录的用户
    *  ####   #  chown -R root:root ./
    *  ####   # chown -R mysql:mysql data
* 复制my-default.cnf到/etc/my.cnf里面
    *   #### # cd support-files/
    *   #### #  touch my-default.cnf  
    *   #### #  chmod 777 ./my-default.cnf 
    *   #### #  cd ../
    *   #### #  cp support-files/my-default.cnf /etc/my.cnf
* 配置my.cnf   vim /etc/my.cnf
```ini
[mysqld]
 
# Remove leading # and set to the amount of RAM for the most important data
# cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
# innodb_buffer_pool_size = 128M
 
# Remove leading # to turn on a very important data integrity option: logging
# changes to the binary log between backups.
# log_bin
 
# These are commonly set, remove the # and set as required.
basedir = /usr/local/mysql
datadir = /usr/local/mysql/data
socket = /tmp/mysql.sock
log-error = /usr/local/mysql/data/error.log
pid-file = /usr/local/mysql/data/mysql.pid
tmpdir = /tmp
port = 5186
#lower_case_table_names = 1
# server_id = .....
# socket = .....
#lower_case_table_names = 1
max_allowed_packet=32M
default-authentication-plugin = mysql_native_password
#lower_case_file_system = on
#lower_case_table_names = 1
log_bin_trust_function_creators = ON
# Remove leading # to set options mainly useful for reporting servers.
# The server defaults are faster for transactions and fast SELECTs.
# Adjust sizes as needed, experiment to find the optimal values.
# join_buffer_size = 128M
# sort_buffer_size = 2M
# read_rnd_buffer_size = 2M 
 
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
```
如果后期mysql运行报错，可以直接到log-error = /usr/local/mysql/data/error.log目录下直接查看错误日志.....命令:cat /usr/local/mysql/data/error.log
* 开机自启，进入/usr/local/mysql/support-files进行设置
    * #### # cd support-files/
    * #### # cp mysql.server /etc/init.d/mysql 
    * #### # chmod +x /etc/init.d/mysql
* 注册服务  # chkconfig --add mysql
* /etc/ld.so.conf要配置路径，不然报错  
    * ####  #vim /etc/ld.so.conf
    * ####  里面添加   /usr/local/mysql/lib
* 配置环境变量
    * #### # vim /etc/profile
    * #### 添加 
               #MYSQL ENVIRONMENT
               export PATH=$PATH:/usr/local/mysql/bin:/usr/local/mysql/lib
    * #### # source /etc/profile
* 登录 修改密码
    * #### 启动mysql 服务   #service mysql start
    * #### mysql -uroot -p 登录
    * #### 修改密码   alter user 'root'@'localhost' identified by 'root';
#远程连接
####  2003错误  linux防火墙设置开通3306端口  服务器安全组设置开通3306端口
####  1130错误 没有给远程连接用户权限
        mysql -u root -p  //第一句是以权限用户root登录
        
        mysql>use mysql;  //第二句：选择mysql库
        
        mysql>select 'host' from user where user='root'; //第三句：查看mysql库中的user表的host值（即可进行连接访问的主机/IP名称）
        
        mysql>update user set host = '%' where user ='root'; //第四句：修改host值（以通配符%的内容增加主机/IP地址），当然也可以直接增加IP地址
         
        mysql>flush privileges;  //第五句：刷新MySQL的系统权限相关表
        
        mysql>select 'host'   from user where user='root';  //第六句：再重新查看user表时，有修改。。
####  2058  解决方法：linux 登录 mysql -u root -p 登录你的 mysql 数据库，然后 执行这条SQL： ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
          
          