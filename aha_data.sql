/*
 Navicat Premium Data Transfer

 Source Server         : 智栋-阿里云
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 47.93.224.217:3306
 Source Schema         : aha_data

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 02/04/2021 20:08:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `act_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `act_creator_user_id` int(11) NOT NULL COMMENT '创建者用户id',
  `act_title` varchar(255) NOT NULL COMMENT '活动标题',
  `act_intro` text NOT NULL COMMENT '活动介绍',
  `act_start_time` datetime NOT NULL COMMENT '活动开始时间',
  `act_end_time` datetime NOT NULL COMMENT '活动结束时间',
  `act_create_time` datetime NOT NULL COMMENT '活动创建时间',
  `act_exchange_aha_point` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '兑换aha点数额',
  `act_exchange_aha_credit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '兑换aha币数额',
  `act_code_sum` int(11) NOT NULL DEFAULT '0' COMMENT '活动最大允许兑换券数量',
  PRIMARY KEY (`act_id`) USING BTREE,
  KEY `act_creator_user_id` (`act_creator_user_id`) USING BTREE,
  CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`act_creator_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for activity_code_exchange_log
-- ----------------------------
DROP TABLE IF EXISTS `activity_code_exchange_log`;
CREATE TABLE `activity_code_exchange_log` (
  `acel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `acel_user_id` int(11) NOT NULL COMMENT '兑换者id',
  `acel_activity_id` int(11) NOT NULL COMMENT '活动id',
  `acel_code` varchar(255) NOT NULL COMMENT '兑换码',
  `acel_exchange_time` datetime NOT NULL COMMENT '兑换时间',
  PRIMARY KEY (`acel_id`) USING BTREE,
  KEY `acel_user_id` (`acel_user_id`) USING BTREE,
  KEY `acel_activity_id` (`acel_activity_id`) USING BTREE,
  CONSTRAINT `activity_code_exchange_log_ibfk_1` FOREIGN KEY (`acel_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_code_exchange_log_ibfk_2` FOREIGN KEY (`acel_activity_id`) REFERENCES `activity` (`act_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition` (
  `co_id` int(11) NOT NULL AUTO_INCREMENT,
  `co_type_id` int(11) DEFAULT NULL COMMENT '竞赛类别id(外键)',
  `co_level` int(10) DEFAULT NULL COMMENT '竞赛级别',
  `co_name` varchar(255) DEFAULT NULL COMMENT '赛事名称',
  `co_intro` longtext COMMENT '赛事简介',
  `co_pic_url` int(11) DEFAULT NULL COMMENT '赛事图片保存路径',
  PRIMARY KEY (`co_id`) USING BTREE,
  KEY `co_type_id` (`co_type_id`) USING BTREE,
  CONSTRAINT `competition_ibfk_1` FOREIGN KEY (`co_type_id`) REFERENCES `competition_type` (`cot_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for competition_type
-- ----------------------------
DROP TABLE IF EXISTS `competition_type`;
CREATE TABLE `competition_type` (
  `cot_id` int(11) NOT NULL AUTO_INCREMENT,
  `cot_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cot_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `con_id` int(11) NOT NULL AUTO_INCREMENT,
  `con_user_id` int(11) NOT NULL COMMENT '用户id(外键)',
  `con_name` varchar(255) NOT NULL COMMENT '联系人',
  `con_signature_filename` varchar(255) NOT NULL COMMENT '签名文件名',
  `con_sign_time` datetime DEFAULT NULL COMMENT '合同签名时间',
  PRIMARY KEY (`con_id`) USING BTREE,
  UNIQUE KEY `con_user_id` (`con_user_id`) USING BTREE,
  KEY `user_id` (`con_user_id`) USING BTREE,
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`con_user_id`) REFERENCES `user` (`u_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for contrib_point_log
-- ----------------------------
DROP TABLE IF EXISTS `contrib_point_log`;
CREATE TABLE `contrib_point_log` (
  `cl_id` int(11) NOT NULL AUTO_INCREMENT,
  `cl_user_id` int(11) NOT NULL COMMENT '用户id',
  `cl_type` int(11) NOT NULL COMMENT '类型',
  `cl_external_id` int(11) DEFAULT NULL COMMENT '业务外键',
  `cl_aha_credit_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Aha币总额',
  `cl_aha_point_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Aha点总额',
  `cl_time` datetime NOT NULL COMMENT '发生时间',
  PRIMARY KEY (`cl_id`) USING BTREE,
  KEY `cl_user_id` (`cl_user_id`) USING BTREE,
  CONSTRAINT `contrib_point_log_ibfk_1` FOREIGN KEY (`cl_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=634 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for contrib_point_order
-- ----------------------------
DROP TABLE IF EXISTS `contrib_point_order`;
CREATE TABLE `contrib_point_order` (
  `cor_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单号',
  `cor_user_id` int(11) DEFAULT NULL COMMENT '用户id(外键)',
  `cor_project_id` int(11) DEFAULT NULL COMMENT '项目id(外键)',
  `cor_total_cost` decimal(10,2) DEFAULT NULL COMMENT '订单总价',
  `cor_status` int(2) DEFAULT NULL COMMENT '订单状态',
  `cor_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `cor_pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  `cor_charged_aha_credit` decimal(10,2) DEFAULT NULL COMMENT '订单实际支付aha币',
  `cor_charged_aha_point` decimal(10,2) DEFAULT NULL COMMENT '订单实际支付aha点',
  PRIMARY KEY (`cor_id`) USING BTREE,
  KEY `cor_user_id` (`cor_user_id`) USING BTREE,
  KEY `cor_project_id` (`cor_project_id`) USING BTREE,
  CONSTRAINT `contrib_point_order_ibfk_1` FOREIGN KEY (`cor_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contrib_point_order_ibfk_2` FOREIGN KEY (`cor_project_id`) REFERENCES `project` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `fe_id` int(11) NOT NULL AUTO_INCREMENT,
  `fe_user_id` int(11) DEFAULT NULL COMMENT '反馈用户id',
  `fe_time` datetime DEFAULT NULL COMMENT '反馈时间',
  `fe_type` int(10) DEFAULT NULL COMMENT '反馈类型',
  `fe_content` longtext COMMENT '反馈内容',
  `fe_status` int(10) DEFAULT NULL COMMENT '反馈状态',
  `fe_reply` longtext COMMENT '反馈回复',
  `fe_reply_time` datetime DEFAULT NULL COMMENT '反馈回复时间',
  `fe_level` int(10) DEFAULT NULL COMMENT '反馈问题级别',
  PRIMARY KEY (`fe_id`) USING BTREE,
  KEY `fe_user_id` (`fe_user_id`) USING BTREE,
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`fe_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for financial_order
-- ----------------------------
DROP TABLE IF EXISTS `financial_order`;
CREATE TABLE `financial_order` (
  `fo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '财务订单号',
  `fo_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `fo_pay_channel` tinyint(4) DEFAULT NULL COMMENT '支付渠道',
  `fo_pay_number` varchar(255) DEFAULT NULL COMMENT '第三方支付流水号',
  `fo_discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `fo_price` decimal(10,2) DEFAULT NULL COMMENT '订单总价',
  `fo_status` tinyint(2) DEFAULT NULL COMMENT '订单状态',
  `fo_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `fo_pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  PRIMARY KEY (`fo_id`) USING BTREE,
  KEY `financial_order_ibfk_1` (`fo_user_id`) USING BTREE,
  CONSTRAINT `financial_order_ibfk_1` FOREIGN KEY (`fo_user_id`) REFERENCES `user` (`u_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT,
  `m_receiver_user_id` int(11) NOT NULL COMMENT '收件人id',
  `m_text_id` int(11) NOT NULL COMMENT '信件内容id(外键)',
  `m_status` int(2) NOT NULL DEFAULT '0' COMMENT '阅读状态',
  `m_receive_date` datetime DEFAULT NULL COMMENT '收件时间',
  PRIMARY KEY (`m_id`) USING BTREE,
  KEY `m_text_id` (`m_text_id`) USING BTREE,
  KEY `m_receiver_user_id` (`m_receiver_user_id`) USING BTREE,
  CONSTRAINT `message_ibfk_3` FOREIGN KEY (`m_text_id`) REFERENCES `message_text` (`mt_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `message_ibfk_4` FOREIGN KEY (`m_receiver_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for message_text
-- ----------------------------
DROP TABLE IF EXISTS `message_text`;
CREATE TABLE `message_text` (
  `mt_id` int(11) NOT NULL AUTO_INCREMENT,
  `mt_sender_user_id` int(11) DEFAULT NULL COMMENT '发送者id',
  `mt_title` varchar(255) DEFAULT NULL COMMENT '信件标题',
  `mt_content` text NOT NULL COMMENT '信件内容',
  `mt_type` int(1) NOT NULL COMMENT '类型',
  `mt_post_date` datetime DEFAULT NULL COMMENT '发件日期',
  PRIMARY KEY (`mt_id`) USING BTREE,
  KEY `mt_sender_user_id` (`mt_sender_user_id`) USING BTREE,
  CONSTRAINT `message_text_ibfk_1` FOREIGN KEY (`mt_sender_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `no_id` int(11) NOT NULL AUTO_INCREMENT,
  `no_title` varchar(255) DEFAULT NULL COMMENT '公告标题',
  `no_content` varchar(255) DEFAULT NULL COMMENT '公告内容',
  `no_create_time` datetime DEFAULT NULL COMMENT '公告开始日期',
  `no_putting_start_time` datetime DEFAULT NULL COMMENT '公告投放开始日期',
  `no_putting_end_time` datetime DEFAULT NULL COMMENT '公告投放结束日期',
  `no_enable` tinyint(1) DEFAULT NULL COMMENT '公告是否启用',
  PRIMARY KEY (`no_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth
-- ----------------------------
DROP TABLE IF EXISTS `oauth`;
CREATE TABLE `oauth` (
  `oa_user_id` int(11) NOT NULL COMMENT '被授权用户id',
  `oa_oauth_type` varchar(255) NOT NULL COMMENT '授权类型',
  `oa_oauth_id` varchar(255) NOT NULL COMMENT '授权码(手机号，微信号等)',
  `oa_unionid` varchar(255) DEFAULT NULL COMMENT '微信unionid(预留字段)',
  `oa_credential` varchar(255) DEFAULT NULL COMMENT '授权秘钥(预留字段)',
  PRIMARY KEY (`oa_user_id`,`oa_oauth_type`) USING BTREE,
  KEY `user_id` (`oa_user_id`) USING BTREE,
  CONSTRAINT `oauth_ibfk_1` FOREIGN KEY (`oa_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order_project_resource
-- ----------------------------
DROP TABLE IF EXISTS `order_project_resource`;
CREATE TABLE `order_project_resource` (
  `opr_order_id` int(11) NOT NULL COMMENT '订单号',
  `opr_resource_id` int(11) NOT NULL COMMENT '项目资源id',
  `opr_discount` decimal(10,2) DEFAULT NULL COMMENT '资源折扣',
  `opr_price` decimal(10,2) DEFAULT NULL COMMENT '贡献点小计',
  PRIMARY KEY (`opr_order_id`,`opr_resource_id`) USING BTREE,
  KEY `opr_resource_id` (`opr_resource_id`) USING BTREE,
  CONSTRAINT `order_project_resource_ibfk_1` FOREIGN KEY (`opr_order_id`) REFERENCES `contrib_point_order` (`cor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_project_resource_ibfk_2` FOREIGN KEY (`opr_resource_id`) REFERENCES `project_resource` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_creator_user_id` int(11) DEFAULT NULL COMMENT '项目创建者id',
  `p_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `p_avatar_url` varchar(255) DEFAULT NULL COMMENT '项目头像url',
  `p_tags` varchar(255) DEFAULT NULL COMMENT '项目标签',
  `p_intro` longtext COMMENT '项目介绍(富文本)',
  `p_read` int(11) NOT NULL DEFAULT '0' COMMENT '点击率',
  `p_collect` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数量',
  `p_comp_id` int(11) DEFAULT NULL COMMENT '赛事id(外键)',
  `p_comp_name` varchar(255) DEFAULT NULL COMMENT '比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)',
  `p_award_level` int(10) DEFAULT NULL COMMENT '项目获奖级别',
  `p_award_time` datetime DEFAULT NULL COMMENT '项目获奖时间',
  `p_award_prove_url` varchar(255) DEFAULT NULL COMMENT '获奖证明文件url',
  `p_meaning` decimal(10,2) DEFAULT NULL COMMENT '项目资源完整程度，决定贡献点',
  `p_passed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通过审核',
  PRIMARY KEY (`p_id`) USING BTREE,
  KEY `t_creator_phone` (`p_creator_user_id`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`p_creator_user_id`) REFERENCES `user` (`u_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COMMENT='团队表';

-- ----------------------------
-- Table structure for project_member
-- ----------------------------
DROP TABLE IF EXISTS `project_member`;
CREATE TABLE `project_member` (
  `pm_project_id` int(11) NOT NULL,
  `pm_member_user_id` int(11) NOT NULL COMMENT '项目成员id',
  `pm_rank` int(5) DEFAULT NULL COMMENT '项目成员顺位(决定显示顺序，1为队长)',
  `pm_job` varchar(255) DEFAULT NULL COMMENT '项目成员职务',
  `pm_editable` tinyint(1) DEFAULT NULL COMMENT '成员是否可编辑项目信息',
  PRIMARY KEY (`pm_project_id`,`pm_member_user_id`) USING BTREE,
  KEY `tm_member_role_id` (`pm_editable`) USING BTREE,
  KEY `pm_member_phone` (`pm_member_user_id`) USING BTREE,
  CONSTRAINT `project_member_ibfk_1` FOREIGN KEY (`pm_project_id`) REFERENCES `project` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_member_ibfk_2` FOREIGN KEY (`pm_member_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队成员表，一个团队对应多个成员，包括成员的手机号、顺位、角色、职位等信息';

-- ----------------------------
-- Table structure for project_resource
-- ----------------------------
DROP TABLE IF EXISTS `project_resource`;
CREATE TABLE `project_resource` (
  `pr_id` int(11) NOT NULL AUTO_INCREMENT,
  `pr_project_id` int(11) NOT NULL COMMENT '项目id(外键)',
  `pr_name` varchar(255) DEFAULT NULL COMMENT '资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)',
  `pr_file_type` int(11) DEFAULT NULL COMMENT '资源类型',
  `pr_type_id` int(11) NOT NULL COMMENT '资源类型id(外键)',
  `pr_filename` varchar(255) DEFAULT NULL COMMENT '保存在oss里的资源文件名(包括前缀)',
  `pr_preview_url` varchar(255) DEFAULT NULL COMMENT '保存在oss里的预览文件地址',
  `pr_download` int(11) NOT NULL DEFAULT '0' COMMENT '资源文件下载量',
  `pr_score` decimal(10,2) DEFAULT NULL COMMENT '资源平均分',
  `pr_score_count` int(11) DEFAULT NULL COMMENT '评分人数',
  `pr_price` decimal(10,2) DEFAULT '0.00' COMMENT '资源价格',
  `pr_discount` decimal(10,2) DEFAULT '0.00' COMMENT '资源折扣',
  `pr_passed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通过审核',
  PRIMARY KEY (`pr_id`) USING BTREE,
  KEY `pr_project_id` (`pr_project_id`) USING BTREE,
  KEY `project_resource_ibfk_2` (`pr_type_id`),
  CONSTRAINT `project_resource_ibfk_1` FOREIGN KEY (`pr_project_id`) REFERENCES `project` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_resource_ibfk_2` FOREIGN KEY (`pr_type_id`) REFERENCES `project_resource_type` (`prt_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=298 DEFAULT CHARSET=utf8mb4 COMMENT='团队资源表，一个团队对应多个资源，资源表包括了资源文件名称，类别，阅读量等';

-- ----------------------------
-- Table structure for project_resource_check_log
-- ----------------------------
DROP TABLE IF EXISTS `project_resource_check_log`;
CREATE TABLE `project_resource_check_log` (
  `prcl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prcl_resource_id` int(11) NOT NULL COMMENT '被审核资源id',
  `prcl_operate_user_id` int(11) NOT NULL COMMENT '操作者用户id',
  `prcl_check_status` int(11) NOT NULL COMMENT '审核结果',
  `prcl_operate_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`prcl_id`),
  KEY `prcl_resource_id` (`prcl_resource_id`),
  CONSTRAINT `project_resource_check_log_ibfk_1` FOREIGN KEY (`prcl_resource_id`) REFERENCES `project_resource` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_resource_financial_scheme
-- ----------------------------
DROP TABLE IF EXISTS `project_resource_financial_scheme`;
CREATE TABLE `project_resource_financial_scheme` (
  `prfs_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prfs_award_level` int(11) NOT NULL COMMENT '获奖等级',
  `prfs_price_upper_limit` decimal(10,2) NOT NULL COMMENT '资源定价上限',
  `prfs_price_lower_limit` decimal(10,2) NOT NULL COMMENT '资源定价下限',
  PRIMARY KEY (`prfs_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_resource_score
-- ----------------------------
DROP TABLE IF EXISTS `project_resource_score`;
CREATE TABLE `project_resource_score` (
  `prs_user_id` int(11) NOT NULL COMMENT '用户id',
  `prs_resource_id` int(11) NOT NULL COMMENT '项目资源id',
  `prs_score` decimal(10,1) DEFAULT NULL COMMENT '资源评分',
  `prs_comment` text COMMENT '评论',
  `prs_time` datetime DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`prs_user_id`,`prs_resource_id`) USING BTREE,
  KEY `prs_resource_id` (`prs_resource_id`) USING BTREE,
  CONSTRAINT `project_resource_score_ibfk_1` FOREIGN KEY (`prs_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_resource_score_ibfk_2` FOREIGN KEY (`prs_resource_id`) REFERENCES `project_resource` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_resource_type
-- ----------------------------
DROP TABLE IF EXISTS `project_resource_type`;
CREATE TABLE `project_resource_type` (
  `prt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prt_name` varchar(255) DEFAULT NULL COMMENT '资源类型名称',
  `prt_price_coefficient` decimal(10,2) DEFAULT NULL COMMENT '资源价格系数',
  PRIMARY KEY (`prt_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for purchased_resource
-- ----------------------------
DROP TABLE IF EXISTS `purchased_resource`;
CREATE TABLE `purchased_resource` (
  `pur_user_id` int(11) NOT NULL COMMENT '用户id',
  `pur_resource_id` int(11) NOT NULL COMMENT '项目资源id',
  `pur_order_id` int(11) NOT NULL COMMENT '订单表id',
  `pur_purchase_time` datetime DEFAULT NULL COMMENT '购买时间',
  PRIMARY KEY (`pur_user_id`,`pur_resource_id`) USING BTREE,
  KEY `pr_resource_id` (`pur_resource_id`) USING BTREE,
  KEY `pur_order_id` (`pur_order_id`) USING BTREE,
  CONSTRAINT `purchased_resource_ibfk_1` FOREIGN KEY (`pur_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchased_resource_ibfk_2` FOREIGN KEY (`pur_resource_id`) REFERENCES `project_resource` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchased_resource_ibfk_3` FOREIGN KEY (`pur_order_id`) REFERENCES `contrib_point_order` (`cor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for real_name_authentication
-- ----------------------------
DROP TABLE IF EXISTS `real_name_authentication`;
CREATE TABLE `real_name_authentication` (
  `rna_user_id` int(11) NOT NULL COMMENT '用户id',
  `rna_true_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `rna_type` int(11) DEFAULT NULL COMMENT '认证类型(学生or社会人士)',
  `rna_student_card_filename` varchar(255) DEFAULT NULL COMMENT '学生证图片文件名',
  `rna_id_card_front_filename` varchar(255) DEFAULT NULL COMMENT '身份证正面文件名',
  `rna_id_card_back_filename` varchar(255) DEFAULT NULL COMMENT '身份证背面文件名',
  `rna_status` int(11) NOT NULL DEFAULT '0' COMMENT '认证审核状态',
  `rna_upload_time` datetime DEFAULT NULL COMMENT '上传日期',
  `rna_pass_time` datetime DEFAULT NULL COMMENT '审核通过日期',
  PRIMARY KEY (`rna_user_id`) USING BTREE,
  CONSTRAINT `real_name_authentication_ibfk_1` FOREIGN KEY (`rna_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reward_financial_scheme
-- ----------------------------
DROP TABLE IF EXISTS `reward_financial_scheme`;
CREATE TABLE `reward_financial_scheme` (
  `rfs_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rfs_name` varchar(255) DEFAULT NULL COMMENT '奖励计划名称',
  `rfs_aha_point_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '奖励aha点数量',
  `rfs_aha_credit_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '奖励aha币数量',
  `rfs_upload_time` datetime NOT NULL COMMENT '计划上传时间',
  PRIMARY KEY (`rfs_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for slide_show_resource
-- ----------------------------
DROP TABLE IF EXISTS `slide_show_resource`;
CREATE TABLE `slide_show_resource` (
  `ssr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ssr_picture_url` varchar(255) DEFAULT NULL COMMENT '轮播图图片url',
  `ssr_link_type` int(11) DEFAULT NULL COMMENT '轮播图链接类型',
  `ssr_link_url` varchar(255) DEFAULT NULL COMMENT '轮播图链接路径',
  `ssr_enabled` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `ssr_upload_time` datetime DEFAULT NULL COMMENT '轮播图上传时间',
  PRIMARY KEY (`ssr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `u_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '账户首次登录时间',
  `u_student_rec_filename` varchar(255) DEFAULT NULL COMMENT '学生证图片保存路径',
  `u_aha_credit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'aha币数量',
  `u_aha_point` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'aha点数量',
  `u_signed_notice` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否签署服务协议',
  `u_signed_contract` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否签署合同',
  `u_authenticated` int(11) NOT NULL DEFAULT '0' COMMENT '是否通过身份认证',
  `u_role_id` int(11) DEFAULT NULL COMMENT '角色id(外键)',
  PRIMARY KEY (`u_id`) USING BTREE,
  KEY `role_id` (`u_role_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`u_role_id`) REFERENCES `role` (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100408 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection` (
  `coll_user_id` int(11) NOT NULL COMMENT '收藏用户id',
  `coll_project_id` int(11) NOT NULL COMMENT '收藏资源id',
  `coll_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '收藏时间戳',
  PRIMARY KEY (`coll_user_id`,`coll_project_id`) USING BTREE,
  KEY `coll_project_id` (`coll_project_id`) USING BTREE,
  CONSTRAINT `user_collection_ibfk_2` FOREIGN KEY (`coll_project_id`) REFERENCES `project` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_collection_ibfk_3` FOREIGN KEY (`coll_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `ui_user_id` int(11) NOT NULL COMMENT '用户id(外键)',
  `ui_nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `ui_gender` tinyint(1) DEFAULT NULL COMMENT '用户性别',
  `ui_birthday` date DEFAULT NULL COMMENT '用户出生日期',
  `ui_type_id` int(11) DEFAULT NULL COMMENT '用户类别',
  `ui_signature` varchar(255) DEFAULT NULL COMMENT '用户个性签名',
  `ui_avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像文件保存路径',
  `ui_school` varchar(255) DEFAULT NULL COMMENT '用户学校',
  `ui_academy` varchar(255) DEFAULT NULL COMMENT '用户学院',
  `ui_major` varchar(255) DEFAULT NULL COMMENT '用户主修专业',
  `ui_grade` int(11) DEFAULT NULL COMMENT '用户当前年级',
  `ui_intro` varchar(255) DEFAULT NULL COMMENT '用户自我介绍',
  `ui_specialty_tags` varchar(255) DEFAULT NULL COMMENT '用户特长标签',
  `ui_true_name` varchar(255) DEFAULT NULL COMMENT '用户真实姓名',
  `ui_comp_tags` varchar(255) DEFAULT NULL COMMENT '用户参与过比赛标签',
  `ui_vip_level_id` int(11) DEFAULT NULL COMMENT '用户VIP等级id(外键)',
  PRIMARY KEY (`ui_user_id`) USING BTREE,
  KEY `user_info_vip_level_vip_id_fk` (`ui_vip_level_id`) USING BTREE,
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`ui_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_info_vip_level_vip_id_fk` FOREIGN KEY (`ui_vip_level_id`) REFERENCES `vip_level` (`vip_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_statistics
-- ----------------------------
DROP TABLE IF EXISTS `user_statistics`;
CREATE TABLE `user_statistics` (
  `ust_user_id` int(11) NOT NULL COMMENT '主键',
  `ust_total_contrib_point` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '累计获取贡献点',
  `ust_total_project` int(11) NOT NULL DEFAULT '0' COMMENT '累计参与项目数',
  `ust_total_received_collection` int(11) NOT NULL DEFAULT '0' COMMENT '累计获得的收藏',
  `ust_total_purchased_count` int(11) NOT NULL DEFAULT '0' COMMENT '累计被购买次数',
  PRIMARY KEY (`ust_user_id`) USING BTREE,
  CONSTRAINT `user_statistics_ibfk_1` FOREIGN KEY (`ust_user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for vip_level
-- ----------------------------
DROP TABLE IF EXISTS `vip_level`;
CREATE TABLE `vip_level` (
  `vip_id` int(11) NOT NULL AUTO_INCREMENT,
  `vip_name` varchar(255) DEFAULT NULL COMMENT 'VIP等级名称',
  `vip_weekly_contrib_point` decimal(10,2) DEFAULT NULL COMMENT '每周发放贡献点',
  PRIMARY KEY (`vip_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
