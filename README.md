## 认证服务器

相关说明：
1. 生成rsa证书。
```shell
keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
```
记住生成时的密码，在程序中要进行配置。

2. 安装redis。

3. 本程序使用了jpa，启动时会在demo数据库中生成user表。请根据实际情况修改数据库名。如果要自动生成role和userrole表，请根据user类的配置修改role和userrole类。