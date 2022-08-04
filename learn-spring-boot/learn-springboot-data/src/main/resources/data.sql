INSERT INTO `item` (`id`, `name`, `category`) VALUES (1, 'IPhone 6S', 'Mobile');
INSERT INTO `item` (`id`, `name`, `category`) VALUES (2, 'Samsung Galaxy', 'Mobile');
INSERT INTO `item` (`id`, `name`, `category`) VALUES (3, 'Lenovo', 'Laptop');
INSERT INTO `item` (`id`, `name`, `category`) VALUES (4, 'LG', 'Telivision');

INSERT INTO `customer` (`customer_code`,`customer_name`,`customer_phone`,`is_member`,`create_time`) VALUES ('XF00001', '网易科技有限公司', '0571-34234567', 1, '2023-07-12 00:00:00');
INSERT INTO `customer` (`customer_code`,`customer_name`,`customer_phone`,`is_member`,`create_time`) VALUES ('XF00002', '阿里巴巴科技有限公司', '0571-23454222', 1, '2023-07-10 00:00:00');

INSERT INTO `mall_order` VALUES ('1000', '写字楼专用空气净化器', '2022-07-12 09:23:58', '00001', 'Tom', 'XF00001', '网易科技有限公司');
INSERT INTO `mall_order` VALUES ('1001', '商务办公楼咖啡机', '2022-07-12 09:27:00', '00001', 'Tom', 'XF00001', '网易科技有限公司');
INSERT INTO `mall_order` VALUES ('1002', '办公室摄像头', '2022-07-12 09:39:43', '00002', 'Ketty', 'XF00002', '阿里巴巴科技有限公司');