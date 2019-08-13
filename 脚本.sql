
--创建数据库(即分区),测试创建4个库(即4个分区)，其中supermarket_00为主分区，所有的小表都在主分区上面，大表分布在4个分区上
CREATE DATABASE supermarket_00;
CREATE DATABASE supermarket_01;
CREATE DATABASE supermarket_02;
CREATE DATABASE supermarket_03;

--查看当前正在使用的数据库
select DATABASE();

--查看当前库中存在哪些表
show tables;

--切换到主分区 supermarket_00,在主分区上创建表
USE supermarket_00;

CREATE TABLE zto_enter_stock_0000 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0001 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0002 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0003 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--切换到分区 supermarket_01
USE supermarket_01;

CREATE TABLE zto_enter_stock_0000 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0001 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0002 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0003 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--切换到分区 supermarket_02
USE supermarket_02;

CREATE TABLE zto_enter_stock_0000 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0001 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0002 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0003 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





--切换到分区 supermarket_03
USE supermarket_03;

CREATE TABLE zto_enter_stock_0000 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0001 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0002 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE zto_enter_stock_0003 (
  id bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  bill_code varchar(32) NOT NULL COMMENT '单号：时间戳+用户ID后4位+4位随机数',
  depot_code varchar(16) NOT NULL COMMENT '形象店编号',
  take_code varchar(12) NOT NULL COMMENT '取件码',
  upload_date datetime NOT NULL COMMENT '入库时间',
  express_company_code varchar(16) NOT NULL COMMENT '快递公司编号',
  express_type int(10) NOT NULL DEFAULT '6' COMMENT '快件类型',
  receive_man varchar(64) DEFAULT NULL COMMENT '收件人姓名',
  receive_man_mobile varchar(64) DEFAULT NULL COMMENT '收件人联系方式',
  receive_man_addr varchar(1024) DEFAULT NULL COMMENT '收件人地址',
  PRIMARY KEY (id),
  UNIQUE KEY UNIQ_BillCode (bill_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

