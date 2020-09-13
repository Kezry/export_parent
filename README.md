# 重要说明！



克隆完成代码之后，请在IDEA底部的Terminal中运行下面两行代码

```
git rm -r --cached .
git add .
```



------

请修改下面对应文件的配置信息：
	
	

------

export_cargo_service ->src/main/resources/spring/applicationContext-dubbo.xml

	address="zookeeper://__________

​	

------


export_company_service ->src/main/resources/spring/applicationContext-dubbo.xml

	address="zookeeper://__________

​	

------

export_dao ->src/main/resources/properties/db.properties



```
jdbc.password=_________
```




​	

------

export_mq_consumer ->src/main/resources/applicationContext-rabbitmq-consumer.xml

	<rabbit:connection-factory id="connectionFactory" host="192.168.20.128" port="5672" username="ztl" password="123"

​	



------

export_stat_service ->src/main/resources/spring/applicationContext-dubbo.xml



```
	zookeeper://___________
```



------

export_web_manager ->src/main/resources/qiniu.properties

```
	qiniu.accessKey=
	qiniu.secretKey=
	qiniu.bucket=
	qiniu.rtValue=
```



------

export_web_manager ->src/main/resources/spring/applicationContext-rabbitmq-producer.xml

```
	<rabbit:connection-factory id="connectionFactory" host="192.168.20.128" port="5672" username="ztl" password="123" virtual-host="/itcast"/>
```



------

export_web_manager ->src/main/resources/spring/springmvc.xml

```
	zookeeper://______________
```



