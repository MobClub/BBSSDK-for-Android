# BBSSDK-for-Android
<br>
欢迎查看Android端BBSSDK的Sample工程。<br><br>
这个工程演示了如何使用BBSSDK快速地为论坛网站构建出全新的APP。    
<br><br>


[点击下载BBSSDK](http://www.mob.com/downloadDetail/BBSSDK/android)  

[点击查看BBSSDK集成指南](http://wiki.mob.com/快速集成/)

<br>

**工程说明:**<br><br>
app工程编译出的安装包大小是20M左右，其中<br>
- 为了支持附件打开pdf文件功能，工程里面引入了 [AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer)：
    ```
compile 'com.github.barteksc:android-pdf-viewer:2.4.0'
```
   这个库会让安装包增加16M左右。

- 为了支持附件打开微软的office文档功能，工程里面引入了 [Apache poi](https://github.com/apache/poi) 和 [Apache xmlbeans](https://github.com/apache/xmlbeans)：
   ```
poi-*.jar
poi-ooxml-*.jar
poi-scrachpad-*.jar
```
   ```
xmlbeans-*.jar
```
   这些库会让安装包增加3M左右。