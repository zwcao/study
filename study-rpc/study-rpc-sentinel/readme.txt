一、multicat方式启动时，可能会出现ip地址获取不到的问题。
解决方式：增加启动参数：-Djava.net.preferIPv4Stack=true