/*
 Navicat Premium Dump SQL

 Source Server         : localhos
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : meals

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 06/05/2025 15:01:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '收货人',
  `sex` tinyint NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '地址管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (1417414526093082626, 1417012167126876162, '小明', 1, '13812345678', NULL, NULL, NULL, NULL, NULL, NULL, '昌平区金燕龙办公楼', '公司', 1, '2021-07-20 17:22:12', '2021-07-20 17:26:33', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1417414926166769666, 1417012167126876162, '小李', 1, '13512345678', NULL, NULL, NULL, NULL, NULL, NULL, '测试', '家', 0, '2021-07-20 17:23:47', '2021-07-20 17:23:47', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1854706487860436994, 1, '111', 1, '15082513825', NULL, NULL, NULL, NULL, NULL, NULL, 'rediaz', '家', 1, '2024-11-08 10:04:20', '2024-11-08 10:04:22', 1, 1, 0);
INSERT INTO `address_book` VALUES (1854709985045950465, 1854706422592872449, '杨自', 1, '15082513825', NULL, NULL, NULL, NULL, NULL, NULL, '13215', '公司', 1, '2024-11-08 10:18:14', '2025-04-17 21:43:26', 1854706422592872449, 1854706422592872449, 0);
INSERT INTO `address_book` VALUES (1892195820466774017, 1854881414601351170, 'lijufei', 1, '18828807576', NULL, NULL, NULL, NULL, NULL, NULL, 'sjdhbd', '家', 1, '2025-02-19 20:53:34', '2025-02-19 20:53:39', 1854881414601351170, 1854881414601351170, 0);
INSERT INTO `address_book` VALUES (1907039152416227330, 1907036803580821505, '辰基', 1, '15082513825', NULL, NULL, NULL, NULL, NULL, NULL, '攀枝花学院', '学校', 1, '2025-04-01 19:55:40', '2025-04-01 19:55:43', 1907036803580821505, 1907036803580821505, 0);
INSERT INTO `address_book` VALUES (1911052516918415361, 1854706422592872449, '雷迪亚兹', 1, '15082513825', NULL, NULL, NULL, NULL, NULL, NULL, '机场路十号攀枝花学院', '学校', 0, '2025-04-12 21:43:20', '2025-04-17 21:43:25', 1854706422592872449, 1854706422592872449, 0);
INSERT INTO `address_book` VALUES (1912865943395622914, 1854706422592872449, '辰基', 1, '15390315625', NULL, NULL, NULL, NULL, NULL, NULL, '机场路十号', '学校', 0, '2025-04-17 21:49:15', '2025-04-17 21:49:15', 1854706422592872449, 1854706422592872449, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL COMMENT '主键',
  `type` int NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `merchant_id` bigint NOT NULL COMMENT '关联商家表',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1397844263642378242, 1, '湘菜', 1, '2021-05-27 09:16:58', '2021-07-15 20:25:23', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1397844303408574465, 1, '川菜', 2, '2021-05-27 09:17:07', '2021-06-02 14:27:22', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1397844391040167938, 1, '粤菜', 3, '2021-05-27 09:17:28', '2021-07-09 14:37:13', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1413341197421846529, 1, '饮品', 11, '2021-07-09 11:36:15', '2021-07-09 14:39:15', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1413342269393674242, 2, '商务套餐', 5, '2021-07-09 11:40:30', '2021-07-09 14:43:45', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1413384954989060097, 1, '主食', 12, '2021-07-09 14:30:07', '2021-07-09 14:39:19', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1413386191767674881, 2, '儿童套餐', 6, '2021-07-09 14:35:02', '2021-07-09 14:39:05', 1, 1, 1890985306738790121);
INSERT INTO `category` VALUES (1908884642917322754, 1, '火锅', 1, '2025-04-06 22:08:59', '2025-04-06 22:08:59', 1890985306738790125, 1890985306738790125, 1890985306738790125);
INSERT INTO `category` VALUES (1908889412172283905, 2, '疯狂星期五', 0, '2025-04-06 22:27:56', '2025-04-06 22:27:56', 1890985306738790125, 1890985306738790125, 1890985306738790125);
INSERT INTO `category` VALUES (1912503455751704578, 1, '串串', 2, '2025-04-16 21:48:51', '2025-04-16 21:48:51', 1890985306738790125, 1890985306738790125, 1890985306738790125);

-- ----------------------------
-- Table structure for certification
-- ----------------------------
DROP TABLE IF EXISTS `certification`;
CREATE TABLE `certification`  (
  `id` bigint NOT NULL COMMENT 'id',
  `merchant_id` bigint NULL DEFAULT NULL COMMENT '商家id',
  `certification_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证的类型，如营业执照认证、食品经营许可证认证等',
  `certification_file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证文件的存储路径',
  `certification_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证状态，如待审核、已通过、未通过',
  `certification_time` datetime NULL DEFAULT NULL COMMENT '认证操作的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of certification
-- ----------------------------
INSERT INTO `certification` VALUES (1890985306873008130, 1, '营业执照认证', '/path/to/file', '已通过', '2025-02-16 12:43:25');
INSERT INTO `certification` VALUES (1891064488424562689, 1, '营业执照认证', '/path/to/file', '已通过', '2025-02-16 17:58:03');
INSERT INTO `certification` VALUES (1896093412504494082, 1, '营业执照认证', '/path/to/file', '已通过', '2025-03-02 15:01:12');

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint`  (
  `id` bigint NOT NULL COMMENT '主键',
  `complaint_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投诉或建议的类型，如菜品问题、服务问题等',
  `complaint_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '具体的投诉或建议内容',
  `user_id` bigint NULL DEFAULT NULL COMMENT '提出投诉或建议的用户 ID',
  `handling_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理状态，如待处理、已处理、待审核、未通过等',
  `handling_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '处理的结果说明',
  `handling_time` datetime NULL DEFAULT NULL COMMENT '处理操作的时间',
  `complant_object_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价对象的id',
  `merchant_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complaint
-- ----------------------------
INSERT INTO `complaint` VALUES (2342, '服务问题', '商家辱骂顾客', 1854706422592872449, '已处理', '马上处理相关人员', '2025-03-06 11:47:53', '1397849739276890114', 1890985306738790121);
INSERT INTO `complaint` VALUES (1890985305908318210, '菜品问题', '菜品不新鲜', 1854706422592872449, '已处理', '无', '2025-02-16 12:43:24', '1397849739276890114', 1890985306738790121);
INSERT INTO `complaint` VALUES (1891064488604917761, '菜品问题', '菜品不新鲜', 1854706422592872449, '已处理', '123', '2025-02-16 17:58:03', '1397849739276890114', 1890985306738790121);
INSERT INTO `complaint` VALUES (1896093411388809218, '套餐问题', '菜品不新鲜', 1854706422592872449, '已处理', '无', '2025-03-02 15:01:12', '1397849739276890114', 1890985306738790121);
INSERT INTO `complaint` VALUES (1911069787417477120, '其他', '存在卫生问题', 1854706422592872449, '已处理', '严肃处理', '2025-04-16 22:54:08', NULL, 1890985306738790121);
INSERT INTO `complaint` VALUES (1911069820011413504, '其他', '存在卫生问题', 1854706422592872449, '已处理', '马上进行卫生整改', '2025-04-17 22:30:40', NULL, 1890985306738790121);
INSERT INTO `complaint` VALUES (1919382815707758592, '订单问题', '外卖配送异常', 1919382726172569602, '已处理', '已经严肃处理', '2025-05-05 21:44:52', NULL, NULL);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  `merchant_id` bigint NOT NULL COMMENT '关联商家表',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dish_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '菜品管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (1397849739276890114, '辣子鸡', 1397844263642378242, 7800.00, '222222222', 'f966a38e-0780-40be-bb52-5699d13cb3d9.jpg', '来自鲜嫩美味的小鸡，值得一尝', 1, 0, '2021-05-27 09:38:43', '2021-05-27 09:38:43', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397850140982161409, '毛氏红烧肉', 1397844263642378242, 6800.00, '123412341234', '0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg', '毛氏红烧肉毛氏红烧肉，确定不来一份？', 1, 0, '2021-05-27 09:40:19', '2021-05-27 09:40:19', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397850392090947585, '组庵鱼翅', 1397844263642378242, 4800.00, '123412341234', '740c79ce-af29-41b8-b78d-5f49c96e38c4.jpg', '组庵鱼翅，看图足以表明好吃程度', 1, 0, '2021-05-27 09:41:19', '2021-05-27 09:41:19', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397850851245600769, '霸王别姬', 1397844263642378242, 12800.00, '123412341234', '057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg', '还有什么比霸王别姬更美味的呢？', 1, 0, '2021-05-27 09:43:08', '2021-05-27 09:43:08', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397851099502260226, '全家福', 1397844263642378242, 11800.00, '23412341234', 'a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg', '别光吃肉啦，来份全家福吧，让你长寿又美味', 1, 0, '2021-05-27 09:44:08', '2021-05-27 09:44:08', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397851370462687234, '邵阳猪血丸子', 1397844263642378242, 13800.00, '1246812345678', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', '看，美味不？来嘛来嘛，这才是最爱吖', 1, 0, '2021-05-27 09:45:12', '2021-05-27 09:45:12', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397851668262465537, '口味蛇', 1397844263642378242, 16800.00, '1234567812345678', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', '爬行界的扛把子，东兴-口味蛇，让你欲罢不能', 1, 0, '2021-05-27 09:46:23', '2021-05-27 09:46:23', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397852391150759938, '辣子鸡丁', 1397844303408574465, 8800.00, '2346812468', 'ef2b73f2-75d1-4d3a-beea-22da0e1421bd.jpg', '辣子鸡丁，辣子鸡丁，永远的魂', 1, 0, '2021-05-27 09:49:16', '2021-05-27 09:49:16', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397853183287013378, '麻辣兔头', 1397844303408574465, 19800.00, '123456787654321', '2a2e9d66-b41d-4645-87bd-95f2cfeed218.jpg', '麻辣兔头的详细制作，麻辣鲜香，色泽红润，回味悠长', 1, 0, '2021-05-27 09:52:24', '2021-05-27 09:52:24', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397853709101740034, '蒜泥白肉', 1397844303408574465, 9800.00, '1234321234321', 'd2f61d70-ac85-4529-9b74-6d9a2255c6d7.jpg', '多么的有食欲啊', 1, 0, '2021-05-27 09:54:30', '2021-05-27 09:54:30', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397853890262118402, '鱼香肉丝', 1397844303408574465, 3800.00, '1234212321234', '8dcfda14-5712-4d28-82f7-ae905b3c2308.jpg', '鱼香肉丝简直就是我们童年回忆的一道经典菜，上学的时候点个鱼香肉丝盖饭坐在宿舍床上看着肥皂剧，绝了！现在完美复刻一下上学的时候感觉', 1, 0, '2021-05-27 09:55:13', '2021-05-27 09:55:13', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397854652581064706, '麻辣水煮鱼', 1397844303408574465, 14800.00, '2345312·345321', '1fdbfbf3-1d86-4b29-a3fc-46345852f2f8.jpg', '鱼片是买的切好的鱼片，放几个虾，增加味道', 1, 0, '2021-05-27 09:58:15', '2021-05-27 09:58:15', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397854865672679425, '鱼香炒鸡蛋', 1397844303408574465, 2000.00, '23456431·23456', '0f252364-a561-4e8d-8065-9a6797a6b1d3.jpg', '鱼香菜也是川味的特色。里面没有鱼却鱼香味', 1, 0, '2021-05-27 09:59:06', '2021-05-27 09:59:06', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397860242057375745, '脆皮烧鹅', 1397844391040167938, 12800.00, '123456786543213456', 'e476f679-5c15-436b-87fa-8c4e9644bf33.jpeg', '“广东烤鸭美而香，却胜烧鹅说古冈（今新会），燕瘦环肥各佳妙，君休偏重便宜坊”，可见烧鹅与烧鸭在粤菜之中已早负盛名。作为广州最普遍和最受欢迎的烧烤肉食，以它的“色泽金红，皮脆肉嫩，味香可口”的特色，在省城各大街小巷的烧卤店随处可见。', 1, 0, '2021-05-27 10:20:27', '2021-05-27 10:20:27', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397860578738352129, '白切鸡', 1397844391040167938, 6600.00, '12345678654', '9ec6fc2d-50d2-422e-b954-de87dcd04198.jpeg', '白切鸡是一道色香味俱全的特色传统名肴，又叫白斩鸡，是粤菜系鸡肴中的一种，始于清代的民间。白切鸡通常选用细骨农家鸡与沙姜、蒜茸等食材，慢火煮浸白切鸡皮爽肉滑，清淡鲜美。著名的泮溪酒家白切鸡，曾获商业部优质产品金鼎奖。湛江白切鸡更是驰名粤港澳。粤菜厨坛中，鸡的菜式有200余款之多，而最为人常食不厌的正是白切鸡，深受食家青睐。', 1, 0, '2021-05-27 10:21:48', '2021-05-27 10:21:48', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397860792492666881, '烤乳猪', 1397844391040167938, 38800.00, '213456432123456', '2e96a7e3-affb-438e-b7c3-e1430df425c9.jpeg', '广式烧乳猪主料是小乳猪，辅料是蒜，调料是五香粉、芝麻酱、八角粉等，本菜品主要通过将食材放入炭火中烧烤而成。烤乳猪是广州最著名的特色菜，并且是“满汉全席”中的主打菜肴之一。烤乳猪也是许多年来广东人祭祖的祭品之一，是家家都少不了的应节之物，用乳猪祭完先人后，亲戚们再聚餐食用。', 1, 0, '2021-05-27 10:22:39', '2021-05-27 10:22:39', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397860963880316929, '脆皮乳鸽', 1397844391040167938, 10800.00, '1234563212345', '3fabb83a-1c09-4fd9-892b-4ef7457daafa.jpeg', '“脆皮乳鸽”是广东菜中的一道传统名菜，属于粤菜系，具有皮脆肉嫩、色泽红亮、鲜香味美的特点，常吃可使身体强健，清肺顺气。随着菜品制作工艺的不断发展，逐渐形成了熟炸法、生炸法和烤制法三种制作方法。无论那种制作方法，都是在鸽子经过一系列的加工，挂脆皮水后再加工而成，正宗的“脆皮乳鸽皮脆肉嫩、色泽红亮、鲜香味美、香气馥郁。这三种方法的制作过程都不算复杂，但想达到理想的效果并不容易。', 1, 0, '2021-05-27 10:23:19', '2021-05-27 10:23:19', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397861683434139649, '清蒸河鲜海鲜', 1397844391040167938, 38800.00, '1234567876543213456', '1405081e-f545-42e1-86a2-f7559ae2e276.jpeg', '新鲜的海鲜，清蒸是最好的处理方式。鲜，体会为什么叫海鲜。清蒸是广州最经典的烹饪手法，过去岭南地区由于峻山大岭阻隔，交通不便，经济发展起步慢，自家打的鱼放在锅里煮了就吃，没有太多的讲究，但却发现这清淡的煮法能使鱼的鲜甜跃然舌尖。', 1, 0, '2021-05-27 10:26:11', '2021-05-27 10:26:11', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397862198033297410, '老火靓汤', 1397844391040167938, 49800.00, '123456786532455', '583df4b7-a159-4cfc-9543-4f666120b25f.jpeg', '老火靓汤又称广府汤，是广府人传承数千年的食补养生秘方，慢火煲煮的中华老火靓汤，火候足，时间长，既取药补之效，又取入口之甘甜。 广府老火汤种类繁多，可以用各种汤料和烹调方法，烹制出各种不同口味、不同功效的汤来。', 1, 0, '2021-05-27 10:28:14', '2021-05-27 10:28:14', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1397862477831122945, '上汤焗龙虾', 1397844391040167938, 108800.00, '1234567865432', '5b8d2da3-3744-4bb3-acdc-329056b8259d.jpeg', '上汤焗龙虾是一道色香味俱全的传统名菜，属于粤菜系。此菜以龙虾为主料，配以高汤制成的一道海鲜美食。本品肉质洁白细嫩，味道鲜美，蛋白质含量高，脂肪含量低，营养丰富。是色香味俱全的传统名菜。', 1, 0, '2021-05-27 10:29:20', '2025-03-18 15:36:07', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1413342036832100354, '北冰洋', 1413341197421846529, 500.00, '', 'c99e0aab-3cb7-4eaa-80fd-f47d4ffea694.png', '', 1, 0, '2021-07-09 11:39:35', '2021-07-09 15:12:18', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1413384757047271425, '王老吉', 1413341197421846529, 500.00, '', '00874a5e-0df2-446b-8f69-a30eb7d88ee8.png', '', 1, 0, '2021-07-09 14:29:20', '2021-07-12 09:09:16', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1413385247889891330, '米饭', 1413384954989060097, 200.00, '', 'ee04a05a-1230-46b6-8ad5-1a95b140fff3.png', '', 1, 0, '2021-07-09 14:31:17', '2021-07-11 16:35:26', 1, 1, 0, 1890985306738790121);
INSERT INTO `dish` VALUES (1908887714632564738, '鲜毛肚', 1908884642917322754, 4500.00, '', 'e8afdc7b-ec79-4e5f-8841-3ea9bafaefae.jpg', '', 1, 0, '2025-04-06 22:21:11', '2025-04-06 22:21:11', 1890985306738790125, 1890985306738790125, 0, 1890985306738790125);
INSERT INTO `dish` VALUES (1912500086114979841, '美洲大虾', 1908884642917322754, 12000.00, '', '2105e370-4902-4a37-9584-e86bb299cb93.jpg', '源自美洲温暖海域的珍馐 —— 美洲大虾，个大肥美，堪称虾中 “巨人”。其外壳泛着晶莹光泽，虾肉紧实鲜嫩。入口瞬间，富有弹性的口感带来十足嚼劲，鲜甜醇厚的滋味尽显大海本味。无论是白灼保留原汁原味，蒜蓉清蒸让蒜香与虾鲜交融，还是油焖赋予浓郁风味，每一口都令人陶醉，为您呈上无与伦比的味觉盛宴。', 1, 0, '2025-04-16 21:35:28', '2025-04-16 21:35:28', 1890985306738790125, 1890985306738790125, 0, 1890985306738790125);
INSERT INTO `dish` VALUES (1913945192038780930, '菜品1', 1397844303408574465, 1200.00, '', '363b8a1f-5d2c-49d8-8da1-f4bb0bfbb76b.jpg', 'test', 1, 0, '2025-04-20 21:17:48', '2025-04-20 21:17:48', 1890985306738790121, 1890985306738790121, 0, 1890985306738790121);

-- ----------------------------
-- Table structure for dish_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `dish_evaluation`;
CREATE TABLE `dish_evaluation`  (
  `id` bigint NOT NULL,
  `dish_id` bigint NULL DEFAULT NULL,
  `evaluator_id` bigint NULL DEFAULT NULL,
  `evaluation_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `evaluation_time` datetime NULL DEFAULT NULL,
  `score` decimal(3, 1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_evaluation
-- ----------------------------
INSERT INTO `dish_evaluation` VALUES (1890985306940116995, 1, 1, '味道不错', '2025-02-16 12:43:25', 4.8);
INSERT INTO `dish_evaluation` VALUES (1891064487409541121, 1, 1, '味道不错', '2025-02-16 17:58:03', 4.8);
INSERT INTO `dish_evaluation` VALUES (1896093412571602947, 1, 1, '味道不错', '2025-03-02 15:01:12', 4.8);

-- ----------------------------
-- Table structure for dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor`  (
  `id` bigint NOT NULL COMMENT '主键',
  `dish_id` bigint NOT NULL COMMENT '菜品',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '菜品口味关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES (1397849417888346113, 1397849417854791681, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:37:27', '2021-05-27 09:37:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849739297861633, 1397849739276890114, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:38:43', '2021-05-27 09:38:43', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849739323027458, 1397849739276890114, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:38:43', '2021-05-27 09:38:43', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849936421761025, 1397849936404983809, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:39:30', '2021-05-27 09:39:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849936438538241, 1397849936404983809, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:39:30', '2021-05-27 09:39:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850141015715841, 1397850140982161409, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:40:19', '2021-05-27 09:40:19', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850141040881665, 1397850140982161409, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:40:19', '2021-05-27 09:40:19', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850392120307713, 1397850392090947585, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:41:19', '2021-05-27 09:41:19', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850392137084929, 1397850392090947585, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:41:19', '2021-05-27 09:41:19', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850630734262274, 1397850630700707841, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:42:16', '2021-05-27 09:42:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850630755233794, 1397850630700707841, '辣度', '[\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:42:16', '2021-05-27 09:42:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850851274960898, 1397850851245600769, '忌口', '[\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:43:08', '2021-05-27 09:43:08', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850851283349505, 1397850851245600769, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:43:08', '2021-05-27 09:43:08', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397851099523231745, 1397851099502260226, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:44:08', '2021-05-27 09:44:08', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397851099527426050, 1397851099502260226, '辣度', '[\"不辣\",\"微辣\",\"中辣\"]', '2021-05-27 09:44:08', '2021-05-27 09:44:08', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397851370483658754, 1397851370462687234, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-27 09:45:12', '2021-05-27 09:45:12', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397851370483658755, 1397851370462687234, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:45:12', '2021-05-27 09:45:12', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397851370483658756, 1397851370462687234, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:45:12', '2021-05-27 09:45:12', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397851668283437058, 1397851668262465537, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-27 09:46:23', '2021-05-27 09:46:23', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397852391180120065, 1397852391150759938, '忌口', '[\"不要葱\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:49:16', '2021-05-27 09:49:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397852391196897281, 1397852391150759938, '辣度', '[\"不辣\",\"微辣\",\"重辣\"]', '2021-05-27 09:49:16', '2021-05-27 09:49:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397853183307984898, 1397853183287013378, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:52:24', '2021-05-27 09:52:24', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397853423486414850, 1397853423461249026, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:53:22', '2021-05-27 09:53:22', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397853709126905857, 1397853709101740034, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:54:30', '2021-05-27 09:54:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397853890283089922, 1397853890262118402, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:55:13', '2021-05-27 09:55:13', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854133632413697, 1397854133603053569, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-27 09:56:11', '2021-05-27 09:56:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854652623007745, 1397854652581064706, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:58:15', '2021-05-27 09:58:15', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854652635590658, 1397854652581064706, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:58:15', '2021-05-27 09:58:15', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854865735593986, 1397854865672679425, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:59:06', '2021-05-27 09:59:06', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397855742303186946, 1397855742273826817, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:02:35', '2021-05-27 10:02:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397855906497605633, 1397855906468245506, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:03:14', '2021-05-27 10:03:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397856190573621250, 1397856190540066818, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:04:21', '2021-05-27 10:04:21', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859056709316609, 1397859056684150785, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:15:45', '2021-05-27 10:15:45', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859277837217794, 1397859277812051969, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:16:37', '2021-05-27 10:16:37', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859487502086146, 1397859487476920321, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:17:27', '2021-05-27 10:17:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859757061615618, 1397859757036449794, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:18:32', '2021-05-27 10:18:32', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397860242086735874, 1397860242057375745, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:20:27', '2021-05-27 10:20:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397860963918065665, 1397860963880316929, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:23:19', '2021-05-27 10:23:19', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861135754506242, 1397861135733534722, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:24:00', '2021-05-27 10:24:00', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861370035744769, 1397861370010578945, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:24:56', '2021-05-27 10:24:56', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861683459305474, 1397861683434139649, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:26:11', '2021-05-27 10:26:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861898467717121, 1397861898438356993, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:27:02', '2021-05-27 10:27:02', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397862198054268929, 1397862198033297410, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:28:14', '2021-05-27 10:28:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397862477835317250, 1397862477831122945, '辣度', '[\"不辣\",\"微辣\",\"中辣\"]', '2021-05-27 10:29:20', '2021-05-27 10:29:20', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089545865015297, 1398089545676271617, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:31:38', '2021-05-28 01:31:38', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089782323097601, 1398089782285348866, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:32:34', '2021-05-28 01:32:34', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090003262255106, 1398090003228700673, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:33:27', '2021-05-28 01:33:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090264554811394, 1398090264517062657, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:34:29', '2021-05-28 01:34:29', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090455399837698, 1398090455324340225, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:35:14', '2021-05-28 01:35:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090685449023490, 1398090685419663362, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:36:09', '2021-05-28 01:36:09', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090825358422017, 1398090825329061889, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:36:43', '2021-05-28 01:36:43', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091007051476993, 1398091007017922561, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:37:26', '2021-05-28 01:37:26', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091296164851713, 1398091296131297281, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:38:35', '2021-05-28 01:38:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091546531246081, 1398091546480914433, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:39:35', '2021-05-28 01:39:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091729809747969, 1398091729788776450, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:40:18', '2021-05-28 01:40:18', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091889499484161, 1398091889449152513, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:40:56', '2021-05-28 01:40:56', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398092095179763713, 1398092095142014978, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:41:45', '2021-05-28 01:41:45', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398092283877306370, 1398092283847946241, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:42:30', '2021-05-28 01:42:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398094018939236354, 1398094018893099009, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:49:24', '2021-05-28 01:49:24', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398094391494094850, 1398094391456346113, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:50:53', '2021-05-28 01:50:53', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1399574026165727233, 1399305325713600514, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-06-01 03:50:25', '2021-06-01 03:50:25', 1399309715396669441, 1399309715396669441, 0);
INSERT INTO `dish_flavor` VALUES (1413389540592263169, 1413384757047271425, '温度', '[\"常温\",\"冷藏\"]', '2021-07-12 09:09:16', '2021-07-12 09:09:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1413389684020682754, 1413342036832100354, '温度', '[\"常温\",\"冷藏\"]', '2021-07-09 15:12:18', '2021-07-09 15:12:18', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1908887714632564739, 1908887714632564738, '分量', '[\"大份\",\"中份\",\"小份\"]', '2025-04-06 22:21:11', '2025-04-06 22:21:11', 1890985306738790125, 1890985306738790125, 0);
INSERT INTO `dish_flavor` VALUES (1912500086182088706, 1912500086114979841, '分量', '[\"大份\",\"中份\",\"小份\"]', '2025-04-16 21:35:28', '2025-04-16 21:35:28', 1890985306738790125, 1890985306738790125, 0);
INSERT INTO `dish_flavor` VALUES (1913945192038780931, 1913945192038780930, '分量', '[\"大份\",\"中份\",\"小份\"]', '2025-04-20 21:17:48', '2025-04-20 21:17:48', 1890985306738790121, 1890985306738790121, 0);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint NOT NULL COMMENT '主键',
  `merchant_id` bigint NULL DEFAULT NULL COMMENT '商家ID',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 1890985306738790121, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (1854702901009096705, 1890985306738790121, '杨自强', 'rediaz', 'e10adc3949ba59abbe56e057f20f883e', '15082513825', '1', '513223200206156467', 1, '2024-11-08 09:50:05', '2025-03-26 20:31:26', 1, 1);
INSERT INTO `employee` VALUES (1904888979267334145, 1890985306738790122, '自强', 'yangziqiang', 'e10adc3949ba59abbe56e057f20f883e', '15085215112', '0', '513223200206562535', 1, '2025-03-26 21:31:38', '2025-03-26 21:31:38', 1890985306738790122, 1890985306738790122);
INSERT INTO `employee` VALUES (1908877718742216705, 1890985306738790125, '菜鸡蛋', 'caijidan', 'e10adc3949ba59abbe56e057f20f883e', '15082513825', '1', '513223200206561546', 0, '2025-04-06 21:41:28', '2025-04-17 22:43:10', 1890985306738790125, 1890985306738790125);
INSERT INTO `employee` VALUES (1912513189120700417, 1890985306738790125, '小米', 'xiaomi', 'e10adc3949ba59abbe56e057f20f883e', '15082513825', '0', '513223200203561546', 0, '2025-04-16 22:27:32', '2025-04-17 22:43:05', 1890985306738790125, 1890985306738790125);

-- ----------------------------
-- Table structure for evaluation
-- ----------------------------
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`  (
  `id` bigint NOT NULL COMMENT '主键',
  `evaluation_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户的评价内容',
  `user_id` bigint NULL DEFAULT NULL COMMENT '给出评价的用户 ID',
  `evaluated_object_id` bigint NULL DEFAULT NULL COMMENT '评价的对象 ID，如商家 ID 或菜品 ID',
  `evaluated_object_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价对象的类型，如商家、菜品',
  `evaluation_time` datetime NULL DEFAULT NULL COMMENT '评价的时间',
  `score` tinyint NULL DEFAULT NULL COMMENT '评分（共1~5分）',
  `merchant_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluation
-- ----------------------------
INSERT INTO `evaluation` VALUES (1908784998501683201, '这如此用餐让我浏览往返', 1854706422592872449, 1890978541821378562, '订单', '2025-04-06 15:33:02', 5, 1890985306738790121);
INSERT INTO `evaluation` VALUES (1908833719855624194, '不是特别好吃，但味道独特', 1854706422592872449, 1854710014502547457, '订单', '2025-04-06 18:46:38', 3, 1890985306738790121);
INSERT INTO `evaluation` VALUES (1912470396717236225, '超级好吃', 1854706422592872449, 1908888705356562433, '外卖订单', '2025-04-16 19:37:29', 5, 1890985306738790121);
INSERT INTO `evaluation` VALUES (1912873495034454017, '666超级好吃', 1854706422592872449, 1912160038047809538, '外卖订单', '2025-04-17 22:19:15', 5, NULL);

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant`  (
  `id` bigint NOT NULL COMMENT '主键',
  `merchant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商家名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '商家审核状态 0未审核 1启用 2禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of merchant
-- ----------------------------
INSERT INTO `merchant` VALUES (1890985306738790121, '老成都火锅', '2000-05-16 01:27:55', '2010-09-09 00:40:27', 'username', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1890985306738790122, '农家老火锅', '2008-07-16 01:52:41', '2011-01-08 07:25:05', 'username1', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1890985306738790123, '麻辣香锅', '2025-03-19 15:37:59', '2025-03-19 15:38:02', 'username2', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1890985306738790124, '肯德基', '2017-11-11 02:04:42', '2019-06-14 15:50:50', 'username3', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1890985306738790125, '乐山钵钵鸡', '2012-08-02 02:45:10', '2025-05-05 20:56:04', 'username4', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1890985306738790128, '鲜嫩兔头', '2010-11-24 04:51:31', '2010-08-21 23:29:48', 'username5', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1904525941309751297, '隆江猪脚饭', '2025-03-25 21:29:03', '2025-03-25 21:29:03', 'username6', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1904531382730752000, 'AAA老王家火锅1', '2025-03-25 21:50:41', '2025-04-12 16:30:16', 'username7', 'e10adc3949ba59abbe56e057f20f883e', NULL);
INSERT INTO `merchant` VALUES (1913937809652056064, '饭来了', '2025-04-20 20:48:28', '2025-04-20 20:48:28', 're', '827ccb0eea8a706c4c34a16891f84e7b', NULL);
INSERT INTO `merchant` VALUES (1913943673813336064, '饭来了', '2025-04-20 21:11:46', '2025-04-20 21:11:46', NULL, '827ccb0eea8a706c4c34a16891f84e7b', NULL);

-- ----------------------------
-- Table structure for merchant_audit
-- ----------------------------
DROP TABLE IF EXISTS `merchant_audit`;
CREATE TABLE `merchant_audit`  (
  `id` bigint NOT NULL COMMENT '主键',
  `merchant_id` bigint NULL DEFAULT NULL COMMENT '关联商家表',
  `audit_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '审核状态, 如待审核、已通过、未通过',
  `audit_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '审核人员给出的意见',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核操作的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of merchant_audit
-- ----------------------------
INSERT INTO `merchant_audit` VALUES (1890985123456738791, 1890985306738790122, '已通过 ', '同意', NULL);
INSERT INTO `merchant_audit` VALUES (1890985306734590124, 1890985306738790124, '已通过', '同意审批', NULL);
INSERT INTO `merchant_audit` VALUES (1890985306738790122, 1890985306738790121, '已通过', '12', NULL);
INSERT INTO `merchant_audit` VALUES (1890985306940116994, 1890985306738790123, '已通过', '123', '2025-02-16 12:43:25');
INSERT INTO `merchant_audit` VALUES (1891064488542003202, 1890985306738790125, '已通过', '12', '2025-02-16 17:58:03');
INSERT INTO `merchant_audit` VALUES (1896093412571602946, 1890985306738790128, '已通过', '12', '2025-03-02 15:01:12');
INSERT INTO `merchant_audit` VALUES (1904525941309751291, 1904525941309751297, '已通过', '同意', NULL);
INSERT INTO `merchant_audit` VALUES (1904531386010697728, 1904531382730752000, '已通过', '同意审批', '2025-04-12 12:52:07');
INSERT INTO `merchant_audit` VALUES (1913937809710776320, 1913937809652056064, '未审核', '', NULL);
INSERT INTO `merchant_audit` VALUES (1913943673821724672, 1913943673813336064, '未审核', '', NULL);

-- ----------------------------
-- Table structure for merchant_details
-- ----------------------------
DROP TABLE IF EXISTS `merchant_details`;
CREATE TABLE `merchant_details`  (
  `id` bigint NOT NULL,
  `merchant_id` bigint NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `id_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `business_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of merchant_details
-- ----------------------------
INSERT INTO `merchant_details` VALUES (1904531385989726208, 1904531382730752000, '杨自强', '15015211212', '女', '513263526526665912', '/common/download?name=c78d78dd-bb6f-40e3-9d5e-8e91decaae56.jpg', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726211, 1890985306738790121, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726212, 1890985306738790125, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726213, 1890985306738790122, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726214, 1890985306738790123, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726215, 1890985306738790124, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726216, 1890985306738790128, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1904531385989726217, 1904525941309751297, '杨自强', '15015211212', '男', '513263526526665965', '/common/download?name=604046f7-b6ce-4844-8289-2584de7a2fb1.png', '机场路十号攀枝花学院');
INSERT INTO `merchant_details` VALUES (1913937809681416192, 1913937809652056064, '杨自强', '15082513825', '男', '513222222222013105', '/common/download?name=99c1745f-f757-454d-8187-277d456e89da.jpg', '攀枝花学院');
INSERT INTO `merchant_details` VALUES (1913943673821724672, 1913943673813336064, 'yzq', '15082513825', '男', '531551312541313131', '/common/download?name=6a9c87c4-8fa2-424c-820c-5d57af53a56c.jpg', '攀枝花学院');

-- ----------------------------
-- Table structure for order_complaint
-- ----------------------------
DROP TABLE IF EXISTS `order_complaint`;
CREATE TABLE `order_complaint`  (
  `id` bigint NOT NULL,
  `complaint_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `complaint_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `order_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `submit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_complaint
-- ----------------------------
INSERT INTO `order_complaint` VALUES (1890985306738790402, '服务问题', '服务员态度极差', 1, 1, '2025-02-16 12:43:25');
INSERT INTO `order_complaint` VALUES (1891064488479088641, '服务问题', '服务员态度极差', 1, 1, '2025-02-16 17:58:03');
INSERT INTO `order_complaint` VALUES (1896093412403830786, '服务问题', '服务员态度极差', 1, 1, '2025-03-02 15:01:12');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `dish_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1854710014502547458, '邵阳猪血丸子', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', 1854710014502547457, 1397851370462687234, NULL, '去冰,不要香菜,重辣', 1, 138.00);
INSERT INTO `order_detail` VALUES (1890978541821378563, '口味蛇', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', 1890978541821378562, 1397851668262465537, NULL, '去冰', 1, 168.00);
INSERT INTO `order_detail` VALUES (1892195901215514625, '鱼香炒鸡蛋', '0f252364-a561-4e8d-8065-9a6797a6b1d3.jpg', 1892195901148405762, 1397854865672679425, NULL, '不辣', 1, 20.00);
INSERT INTO `order_detail` VALUES (1905236593351446531, '全家福', 'a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg', 1905236593351446530, 1397851099502260226, NULL, '不要香菜,中辣', 1, 118.00);
INSERT INTO `order_detail` VALUES (1907039276743786497, '全家福', 'a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg', 1907039276676677633, 1397851099502260226, NULL, '不要蒜,微辣', 1, 118.00);
INSERT INTO `order_detail` VALUES (1907041499380973570, '邵阳猪血丸子', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', 1907041499380973569, 1397851370462687234, NULL, '常温,不要蒜,中辣', 1, 138.00);
INSERT INTO `order_detail` VALUES (1907042341148426243, '毛氏红烧肉', '0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg', 1907042341148426242, 1397850140982161409, NULL, '不要蒜,微辣', 1, 68.00);
INSERT INTO `order_detail` VALUES (1907042341148426244, '辣子鸡', 'f966a38e-0780-40be-bb52-5699d13cb3d9.jpg', 1907042341148426242, 1397849739276890114, NULL, '不要蒜,中辣', 1, 78.00);
INSERT INTO `order_detail` VALUES (1907043019388461058, '邵阳猪血丸子', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', 1907043019388461057, 1397851370462687234, NULL, '去冰,不要蒜,中辣', 1, 138.00);
INSERT INTO `order_detail` VALUES (1908888705423671297, '鲜毛肚', 'e8afdc7b-ec79-4e5f-8841-3ea9bafaefae.jpg', 1908888705356562433, 1908887714632564738, NULL, '小份', 1, 45.00);
INSERT INTO `order_detail` VALUES (1910897888960565249, '口味蛇', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', 1910897888893456386, 1397851668262465537, NULL, '去冰', 1, 168.00);
INSERT INTO `order_detail` VALUES (1910898362770116611, '口味蛇', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', 1910898362770116610, 1397851668262465537, NULL, '去冰', 1, 168.00);
INSERT INTO `order_detail` VALUES (1912160038127501313, '口味蛇', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', 1912160038047809538, 1397851668262465537, NULL, '少冰', 1, 168.00);
INSERT INTO `order_detail` VALUES (1912160038127501314, '邵阳猪血丸子', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', 1912160038047809538, 1397851370462687234, NULL, '常温,不要蒜,中辣', 2, 138.00);
INSERT INTO `order_detail` VALUES (1912160672432095234, '邵阳猪血丸子', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', 1912160672432095233, 1397851370462687234, NULL, '去冰,不要蒜,中辣', 1, 138.00);
INSERT INTO `order_detail` VALUES (1912869214822719490, '霸王别姬', '057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg', 1912869214822719489, 1397850851245600769, NULL, '不要香菜,中辣', 1, 128.00);
INSERT INTO `order_detail` VALUES (1913946362140545026, '123', '92ce8631-d6fc-4320-9580-2444bfd2c73a.jpg', 1913946362140545025, NULL, 1913945392736227329, NULL, 3, 12.00);
INSERT INTO `order_detail` VALUES (1913946362140545027, '上汤焗龙虾', '5b8d2da3-3744-4bb3-acdc-329056b8259d.jpeg', 1913946362140545025, 1397862477831122945, NULL, '中辣', 1, 1088.00);
INSERT INTO `order_detail` VALUES (1913946362140545028, '菜品1', '363b8a1f-5d2c-49d8-8da1-f4bb0bfbb76b.jpg', 1913946362140545025, 1913945192038780930, NULL, '小份', 1, 12.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NOT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `merchant_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1854710014502547457, '1854710014502547457', 4, 1854706422592872449, 1854709985045950465, '2024-11-08 10:18:21', '2024-11-08 10:18:21', 2, 138.00, '', '15082513825', '13215', NULL, '杨自', 1890985306738790121);
INSERT INTO `orders` VALUES (1890978541821378562, '1890978541821378562', 4, 1854706422592872449, 1854709985045950465, '2025-02-16 12:16:32', '2025-02-16 12:16:32', 2, 168.00, '', '15082513825', '13215', NULL, '杨自', 1890985306738790121);
INSERT INTO `orders` VALUES (1892195901148405762, '1892195901148405762', 4, 1854881414601351170, 1892195820466774017, '2025-02-19 20:53:53', '2025-02-19 20:53:53', 1, 20.00, '', '18828807576', 'sjdhbd', '雷迪亚兹', 'lijufei', 1890985306738790121);
INSERT INTO `orders` VALUES (1905236593351446530, '1905236593351446530', 4, 1854881414601351170, 1892195820466774017, '2025-03-27 20:32:56', '2025-03-27 20:32:56', 2, 118.00, '', '18828807576', 'sjdhbd', '雷迪亚兹', 'lijufei', 1890985306738790121);
INSERT INTO `orders` VALUES (1907039276676677633, '1907039276676677633', 4, 1907036803580821505, 1907039152416227330, '2025-04-01 19:56:09', '2025-04-01 19:56:09', 2, 118.00, '备注信息', '15082513825', '攀枝花学院', '辰基', '辰基', 1890985306738790121);
INSERT INTO `orders` VALUES (1907041499380973569, '1907041499380973569', 4, 1907036803580821505, 1907039152416227330, '2025-04-01 20:04:59', '2025-04-01 20:04:59', 2, 138.00, '', '15082513825', '攀枝花学院', '辰基', '辰基', 1890985306738790121);
INSERT INTO `orders` VALUES (1907042341148426242, '1907042341148426242', 4, 1907036803580821505, 1907039152416227330, '2025-04-01 20:08:20', '2025-04-01 20:08:20', 2, 146.00, '', '15082513825', '攀枝花学院', '辰基', '辰基', 1890985306738790121);
INSERT INTO `orders` VALUES (1907043019388461057, '1907043019388461057', 4, 1907036803580821505, 1907039152416227330, '2025-04-01 20:11:02', '2025-04-01 20:11:02', 2, 138.00, '', '15082513825', '攀枝花学院', '辰基', '辰基', 1890985306738790121);
INSERT INTO `orders` VALUES (1908888705356562433, '1908888705356562433', 4, 1854706422592872449, 1854709985045950465, '2025-04-06 22:25:07', '2025-04-06 22:25:07', 2, 45.00, '', '15082513825', '13215', 'rediaz', '杨自', 1890985306738790121);
INSERT INTO `orders` VALUES (1910897888893456386, '1910897888893456386', 2, 1907036803580821505, 1907039152416227330, '2025-04-12 11:28:54', '2025-04-12 11:28:54', 2, 168.00, '', '15082513825', '攀枝花学院', '辰基', '辰基', 1890985306738790121);
INSERT INTO `orders` VALUES (1910898362770116610, '1910898362770116610', 4, 1907036803580821505, 1907039152416227330, '2025-04-12 11:30:47', '2025-04-12 11:30:47', 2, 168.00, '', '15082513825', '攀枝花学院', '辰基', '辰基', 1890985306738790121);
INSERT INTO `orders` VALUES (1912160038047809538, '1912160038047809538', 4, 1854706422592872449, 1854709985045950465, '2025-04-15 23:04:14', '2025-04-15 23:04:14', 2, 444.00, '', '15082513825', '13215', 'rediaz', '杨自', 1890985306738790121);
INSERT INTO `orders` VALUES (1912160672432095233, '1912160672432095233', 3, 1854706422592872449, 1854709985045950465, '2025-04-15 23:06:45', '2025-04-15 23:06:45', 2, 138.00, '', '15082513825', '13215', 'rediaz', '杨自', 1890985306738790121);
INSERT INTO `orders` VALUES (1912869214822719489, '1912869214822719489', 3, 1854706422592872449, 1854709985045950465, '2025-04-17 22:02:15', '2025-04-17 22:02:15', 2, 128.00, '', '15082513825', '13215', 'rediaz', '杨自', NULL);
INSERT INTO `orders` VALUES (1913946362140545025, '1913946362140545025', 3, 1854706422592872449, 1854709985045950465, '2025-04-20 21:22:27', '2025-04-20 21:22:27', 2, 1136.00, '', '15082513825', '13215', 'rediaz', '杨自', NULL);

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
  `id` bigint NOT NULL COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
  `status` int NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  `merchant_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (1415580119015145474, 1413386191767674881, '儿童套餐A计划', 4000.00, 1, '', '', '61d20592-b37f-4d72-a864-07ad5bb8f3bb.jpg', '2021-07-15 15:52:55', '2025-02-16 11:29:09', 1415576781934608386, 1, 0, 1890985306738790121);
INSERT INTO `setmeal` VALUES (1908901466660691970, 1908889412172283905, '超值套餐', 4300.00, 1, '', '超值套餐', 'd5b3389f-abf4-4262-a17b-5777ff0a74af.jpg', '2025-04-06 23:15:50', '2025-04-06 23:15:50', 1890985306738790125, 1890985306738790125, 0, 1890985306738790125);
INSERT INTO `setmeal` VALUES (1912507375924383745, 1908889412172283905, '帝京軒中式二人套餐', 36800.00, 1, '', '在帝京轩，我们精心为您准备了中式二人套餐，邀您与挚爱共享一场舌尖上的中式风味之旅。套餐内菜品丰富多样，每一道皆为精心烹制。首先是鲜嫩多汁的招牌宫保鸡丁，鸡肉滑嫩，花生米香脆，甜辣滋味恰到好处。搭配清爽可口的清炒时蔬，新鲜时蔬在大厨妙手下，保留了自然的清甜与爽脆口感。主食是喷香软糯的扬州炒饭，丰富食材与米饭完美融合，颗颗饱满，香气四溢。用餐间，典雅的中式环境、周到的服务，让您沉浸式享受惬意用餐时光。', 'e20f0483-2fab-45df-b6c5-653a004996a7.jpg', '2025-04-16 22:04:26', '2025-04-16 22:04:26', 1890985306738790125, 1890985306738790125, 0, 1890985306738790125);
INSERT INTO `setmeal` VALUES (1913945392736227329, 1413342269393674242, '123', 1200.00, 1, '', '1', '92ce8631-d6fc-4320-9580-2444bfd2c73a.jpg', '2025-04-20 21:18:36', '2025-04-20 21:18:36', 1890985306738790121, 1890985306738790121, 0, 1890985306738790121);

-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`  (
  `id` bigint NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int NOT NULL COMMENT '份数',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '套餐菜品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES (1415580119052894209, '1415580119015145474', '1397862198033297410', '老火靓汤', 49800.00, 1, 0, '2021-07-15 15:52:55', '2021-07-15 15:52:55', 1415576781934608386, 1415576781934608386, 0);
INSERT INTO `setmeal_dish` VALUES (1415580119061282817, '1415580119015145474', '1413342036832100354', '北冰洋', 500.00, 1, 0, '2021-07-15 15:52:55', '2021-07-15 15:52:55', 1415576781934608386, 1415576781934608386, 0);
INSERT INTO `setmeal_dish` VALUES (1415580119069671426, '1415580119015145474', '1413385247889891330', '米饭', 200.00, 1, 0, '2021-07-15 15:52:55', '2021-07-15 15:52:55', 1415576781934608386, 1415576781934608386, 0);
INSERT INTO `setmeal_dish` VALUES (1908901466673274881, '1908901466660691970', '1908887714632564738', '鲜毛肚', 4500.00, 1, 0, '2025-04-06 23:15:50', '2025-04-06 23:15:50', 1890985306738790125, 1890985306738790125, 0);
INSERT INTO `setmeal_dish` VALUES (1912507375924383746, '1912507375924383745', '1912500086114979841', '美洲大虾', 12000.00, 1, 0, '2025-04-16 22:04:26', '2025-04-16 22:04:26', 1890985306738790125, 1890985306738790125, 0);
INSERT INTO `setmeal_dish` VALUES (1912507375924383747, '1912507375924383745', '1908887714632564738', '鲜毛肚', 4500.00, 1, 0, '2025-04-16 22:04:26', '2025-04-16 22:04:26', 1890985306738790125, 1890985306738790125, 0);
INSERT INTO `setmeal_dish` VALUES (1913945392736227330, '1913945392736227329', '1397850851245600769', '霸王别姬', 12800.00, 1, 0, '2025-04-20 21:18:36', '2025-04-20 21:18:36', 1890985306738790121, 1890985306738790121, 0);
INSERT INTO `setmeal_dish` VALUES (1913945392736227331, '1913945392736227329', '1397850392090947585', '组庵鱼翅', 4800.00, 1, 0, '2025-04-20 21:18:36', '2025-04-20 21:18:36', 1890985306738790121, 1890985306738790121, 0);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名称',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `dish_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for super_admin
-- ----------------------------
DROP TABLE IF EXISTS `super_admin`;
CREATE TABLE `super_admin`  (
  `id` bigint NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of super_admin
-- ----------------------------
INSERT INTO `super_admin` VALUES (1891089501919817730, 'admin', '123456');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '头像',
  `status` int NULL DEFAULT 0 COMMENT '状态 0:禁用，1:正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1854706422592872449, 'rediaz', '15082513825', '1', NULL, 'd8912aef-5259-45f0-bb0c-8eb0a10319ef.jpg', 1);
INSERT INTO `user` VALUES (1854881414601351170, '雷迪亚兹', '18784363129', '1', NULL, 'c08e1e79-e45c-4c98-a916-25b26b0b571a.jpg', 1);
INSERT INTO `user` VALUES (1906703634319138818, NULL, '150825138925', '1', NULL, '87c728c8-8522-45f3-a6a6-ee7f8502ce5b.png', 1);
INSERT INTO `user` VALUES (1907036803580821505, '辰基', '15082538251', '0', NULL, '20169ae9-7db6-4ec8-8e1d-ff83b7b592dd.jpg', 1);
INSERT INTO `user` VALUES (1908034759192748033, '0033', '17265896590', '0', NULL, '87c728c8-8522-45f3-a6a6-ee7f8502ce5b.png', 1);
INSERT INTO `user` VALUES (1912466180900675585, NULL, '15082513826', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1912482482549788673, NULL, '150852513825', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1919382726172569602, NULL, '15082513821', NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
