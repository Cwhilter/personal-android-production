# personal-android-production
基于安卓平台实现从本地文件夹或者摄像头获取图片并通过简单socket通信上传至服务器
主要技术难点在于socket传输大容量数据时数据包发送接收问题，以及不同平台（本项目为安卓和C#平台）下socket套接字不同封装问题
