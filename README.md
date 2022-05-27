# GoogleCSE
 谷歌CSE自定义搜索

> 如何Google自定义搜索 + 阿里云函数计算 实现完美使用Google搜索  
> 优点：  
> 自定义搜索结果   
> 国内网络无障碍访问   
> 无广告   

# 获取代码   
``` 
// github 仓库地址
https://github.com/dsttl3/GoogleCSE
// Gitee（码云） 仓库地址
https://gitee.com/dsttl3/GoogleCSE
```
需修改`\src\main\resources`目录下的`config.setting`配置文件，如下   
``` 
[cse]
# 搜索引擎ID (如果需要自己定制，可以在 https://cse.google.com 申请)
cx = 007765593562555407508:qxj7yrxd00a 
# url 主域名 （改成自己的域名）
url = https://google.dsttl3.cn/
# css链接
cssurl = https://dsttl3.cn/css/st.css
# logo图片
logourl = https://dsttl3.cn/img/dstt.png
# 百度统计ID （改成自己的）
bdid = a177f39aa76e5026c3a549f48d7b8a0e
```
# 如何获取自己的搜索引擎ID   
 https://cse.google.com/   自定义自己的搜索引擎（访问该网站需要能正常访问Google的网络环境）  
 添加一个自己的搜索引擎    
![cse1.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/440a87b202f2628951e5067fab0caec2cee23c.png)
 获取自己的搜索引擎 ID    
![cse2.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/466f712221f25fa1c5a9524b0f292837dc80db.png)

# 创建阿里函数计算  
登录到阿里云函数计算fc控制台 https://fcnext.console.aliyun.com/overview    
创建服务一个新服务，地区要选择香港或者能正常访问Google的地区     
![alifc1.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/f71b8092600f13e18854302d22522403463b40.png)
日志功能是需要付费的，这里需要关闭掉    
![alifc2.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/75caedf19a19939894134928f24b13bd314d02.png) 
创建服务一个函数，运行环境选择Java8或者Java11    
![alifc3.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/d867408394a26a075cc919ac7d36bbc15c0eba.png) 
上传代码，选择自己编译好的jar包    
![alifc4.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/22c93cd27e8ff74eb8926270664dc9955a874e.png)
配置函数    
![alifc5.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/16126c4726b3fe3eba3412403c04abb370515a.png)
配置 请求处理程序（函数入口） `GetHtml::handleRequest` 如果需要返回json格式，填写 `GetJson::handleRequest`   
![alifc6.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/b713a4c19c670d2c0340440d53b7034158108f.png)
配置好以后就可以配置自定义域名了   

# 配置自定义域名   
> https://fcnext.console.aliyun.com/cn-hongkong/domains   
这里地区同样是选择香港   
![alifc7.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/85d8a7b2631c8e4ec0e2221e6fcc59c77bc18f.png)
路由配置根据刚才创建的函数选择   
![alifc8.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/9855abf47e03753f53127116edc0db07a4e4ec.png)
配置完成后通过`http://域名/?sou=关键词`访问,例`https://google.dsttl3.cn/?sou=dsttl3`   

# 演示   
通过`https://google.dsttl3.cn/?sou=搜索的关键词`进行搜索，示例`https://google.dsttl3.cn/?sou=dsttl3`   
![image.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/3532bb79948a9be84c94360d8fbb105592f038.png)

# 配置Chrome地址栏搜索和右键快捷搜索   
首先打开chrome的设置，找到搜索引擎选项   

![image.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/84181f305f5302280820301821c73df90e872d.png)

选择`管理搜索引擎和网站搜索`   

![image.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/c5d90d940b9f4e139e156846671e387d8172fb.png)

在`网站搜索`模块下添加   

![image.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/b62337102c40cb41baa012e119ccde6836ea57.png)   

![image.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/852f61e145d4b2e5e14920d85b0c6138078ac7.png)

最后设置成默认搜索   

![image.png](https://harmonyos.oss-cn-beijing.aliyuncs.com/images/202205/3139a25991ed97f1d26394c80fdd64f52fd595.png)
