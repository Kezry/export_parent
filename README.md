# 重要说明！





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






```mysql

CREATE DATABASE /*!32312 IF NOT EXISTS*/`saas-export` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `saas-export`;

/*Table structure for table `co_packing_list` */

DROP TABLE IF EXISTS `co_packing_list`;

CREATE TABLE `co_packing_list` (
  `packing_list_id` varchar(40) NOT NULL COMMENT '集装箱编号',
  `export_ids` varchar(200) DEFAULT NULL COMMENT '报运单id集合',
  `export_nos` varchar(200) DEFAULT NULL COMMENT '报运合同号',
  `packing_time` datetime DEFAULT NULL COMMENT '装箱时间',
  `total_volume` decimal(10,0) DEFAULT NULL COMMENT '总体积',
  `net_weights` decimal(10,0) DEFAULT NULL COMMENT '总净重',
  `gross_weights` decimal(10,0) DEFAULT NULL COMMENT '总毛重',
  `marks` varchar(200) DEFAULT NULL COMMENT '唛头',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `packing_money` decimal(10,0) DEFAULT NULL COMMENT '装箱费用',
  `state` int(11) DEFAULT NULL COMMENT '状态 0草稿 1已委托',
  `create_by` varchar(40) DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(40) DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `company_id` varchar(40) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(40) DEFAULT NULL COMMENT '公司名',
  PRIMARY KEY (`packing_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT  INTO `ss_module`(`module_id`,`parent_id`,`parent_name`,`name`,`layer_num`,`is_leaf`,`ico`,`cpermission`,`curl`,`ctype`,`state`,`belong`,`cwhich`,`quote_num`,`remark`,`order_no`) VALUES 

('304','3','统计分析','厂家区域统计',NULL,NULL,NULL,NULL,'stat/toCharts.do?chartsType=factoryArea','1','1','1',NULL,NULL,'stat','16');


INSERT  INTO `ss_module`(`module_id`,`parent_id`,`parent_name`,`name`,`layer_num`,`is_leaf`,`ico`,`cpermission`,`curl`,`ctype`,`state`,`belong`,`cwhich`,`quote_num`,`remark`,`order_no`) VALUES 

('209','2','货运管理','合同审核',NULL,NULL,NULL,'合同审核','/cargo/contract/submit.do','1','1','1',NULL,NULL,'cargo',NULL);



INSERT  INTO `pe_role`(`role_id`,`name`,`remark`,`order_no`,`create_by`,`create_dept`,`create_time`,`update_by`,`update_time`,`company_id`,`company_name`) VALUES 

('4028a1cd4ee2d9d6014ee2df4c6a0009','合同审核','审核并提交合同',12,NULL,NULL,NULL,NULL,NULL,'1','传智播客教育股份有限公司');


INSERT  INTO `pe_role_module`(`role_id`,`module_id`) VALUES 

('4028a1cd4ee2d9d6014ee2df4c6a0009','2'),

('4028a1cd4ee2d9d6014ee2df4c6a0009','209');



// 修复用户管理->权限管理->权限为空时的500的bug

```

