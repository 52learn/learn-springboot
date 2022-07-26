CREATE TABLE IF NOT EXISTS `item` (
  `id` int NOT NULL,
  `name` VARCHAR(50)  NULL DEFAULT NULL,
  `category` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `customer`  (
  `customer_code` varchar(32) NOT NULL DEFAULT '' COMMENT ' 企业客户编码',
  `customer_name` varchar(255) NOT NULL  DEFAULT '' COMMENT ' 企业客户名称',
  `customer_phone` varchar(50) NOT NULL  DEFAULT '' ,
  `is_member` int NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`customer_code`)
) ;

truncate table item;
truncate table customer;