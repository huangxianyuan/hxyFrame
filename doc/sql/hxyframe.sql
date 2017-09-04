/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : hxyframe

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-04 10:38:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------
INSERT INTO `act_ge_bytearray` VALUES ('1ed2f163-9119-11e7-b720-005056c00001', '1', 'source', null, 0x7B227374656E63696C736574223A7B226E616D657370616365223A22687474703A2F2F62336D6E2E6F72672F7374656E63696C7365742F62706D6E322E3023227D7D, null);

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO `act_ge_property` VALUES ('next.dbid', '85001', '35');
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(5.22.0.0)', '1');
INSERT INTO `act_ge_property` VALUES ('schema.version', '5.22.0.0', '1');

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_procdef_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------
INSERT INTO `act_re_model` VALUES ('1ecc61b2-9119-11e7-b720-005056c00001', '2', 'test', null, null, '2017-09-04 10:31:13.500', '2017-09-04 10:31:13.544', '1', '{\"name\":\"test\",\"revision\":1,\"description\":null}', null, '1ed2f163-9119-11e7-b720-005056c00001', null, '');

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------
INSERT INTO `act_re_procdef` VALUES ('leave:10:45069', '1', 'http://www.activiti.org/processdef', '会签分支流程', 'leave', '10', '45066', '45059.bpmn20.xml', '45059.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:11:45079', '1', 'http://www.activiti.org/processdef', '会签分支流程', 'leave', '11', '45076', '45059.bpmn20.xml', '45059.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:12:45083', '1', 'http://www.activiti.org/processdef', '会签分支流程', 'leave', '12', '45080', '45059.bpmn20.xml', '45059.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:13:45087', '1', 'http://www.activiti.org/processdef', '会签分支流程', 'leave', '13', '45084', '45059.bpmn20.xml', '45059.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:14:70005', '1', 'http://www.activiti.org/processdef', '请假', 'leave', '14', '70002', '67501.bpmn20.xml', '67501.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:15:18f469eb-8cc0-11e7-92ce-005056c00001', '1', 'http://www.activiti.org/processdef', '2341', 'leave', '15', '186633a8-8cc0-11e7-92ce-005056c00001', '80007.bpmn20.xml', '80007.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:16:eac61dd3-8d28-11e7-92ce-005056c00001', '1', 'http://www.activiti.org/processdef', '2341', 'leave', '16', 'eaa109a0-8d28-11e7-92ce-005056c00001', '80007.bpmn20.xml', '80007.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:17:a9c4c0c4-8e24-11e7-85a6-005056c00001', '1', 'http://www.activiti.org/processdef', '测试通知', 'leave', '17', 'a999b921-8e24-11e7-85a6-005056c00001', '60aa4e4e-8e24-11e7-85a6-005056c00001.bpmn20.xml', '60aa4e4e-8e24-11e7-85a6-005056c00001.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:18:fc81c888-8e24-11e7-85a6-005056c00001', '1', 'http://www.activiti.org/processdef', '测试通知', 'leave', '18', 'fc6c92d5-8e24-11e7-85a6-005056c00001', '60aa4e4e-8e24-11e7-85a6-005056c00001.bpmn20.xml', '60aa4e4e-8e24-11e7-85a6-005056c00001.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:1:11', '1', 'http://www.activiti.org/processdef', '请假2', 'leave', '1', '8', '5.bpmn20.xml', '5.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:2:16', '1', 'http://www.activiti.org/processdef', '请假1', 'leave', '2', '13', '3.bpmn20.xml', '3.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:3:20', '1', 'http://www.activiti.org/processdef', '请假1', 'leave', '3', '17', '3.bpmn20.xml', '3.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:4:32519', '1', 'http://www.activiti.org/processdef', '条件请假', 'leave', '4', '32516', '3.bpmn20.xml', '3.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:5:32523', '1', 'http://www.activiti.org/processdef', '条件请假', 'leave', '5', '32520', '3.bpmn20.xml', '3.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:6:32533', '1', 'http://www.activiti.org/processdef', '条件请假', 'leave', '6', '32530', '3.bpmn20.xml', '3.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:7:37598', '1', 'http://www.activiti.org/processdef', '会签请假', 'leave', '7', '37595', '5.bpmn20.xml', '5.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:8:45030', '1', 'http://www.activiti.org/processdef', '会签请假', 'leave', '8', '45027', '5.bpmn20.xml', '5.leave.png', null, '0', '1', '1', '');
INSERT INTO `act_re_procdef` VALUES ('leave:9:45065', '1', 'http://www.activiti.org/processdef', '会签分支流程', 'leave', '9', '45062', '45059.bpmn20.xml', '45059.leave.png', null, '0', '1', '1', '');

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Table structure for extend_act_business
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_business`;
CREATE TABLE `extend_act_business` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '业务流程名字',
  `act_key` varchar(50) DEFAULT NULL COMMENT '流程key ',
  `classurl` varchar(150) DEFAULT NULL COMMENT '类路径',
  `type` varchar(1) DEFAULT NULL COMMENT '0=根节点 1=分组 2=业务类 3=回调',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父节点id',
  `sort` varchar(4) DEFAULT NULL COMMENT '同一级节点中的序号',
  `open` varchar(5) DEFAULT NULL COMMENT '是否展开',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建人Id',
  `create_time` datetime DEFAULT NULL,
  `update_id` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `baid` varchar(32) DEFAULT NULL,
  `bapid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程  对应的 业务表';

-- ----------------------------
-- Records of extend_act_business
-- ----------------------------
INSERT INTO `extend_act_business` VALUES ('2dd79ea6652244b789cfeffbece4fec9', '请假', 'leave', 'com.hxy.demo.entity.LeaveEntity', '3', 'dc2c625a139e4b05bd8ee33e1fe38a40', '1', 'true', '请假流程', '026a564bbfd84861ac4b65393644beef', '2017-07-26 15:07:27', '026a564bbfd84861ac4b65393644beef', '2017-07-27 16:24:08', '772a287903f14e01bc4077e5082823cf', '6804cf9cd7eb46148183f16f99d5e6c6');
INSERT INTO `extend_act_business` VALUES ('79cc0fe7bac741d0a6d7f8d72bbee313', '业务树根目录', 'root', '111', '1', '79cc0fe7bac741d0a6d7f8d72bbee313', '1', 'true', '业务树初始化', '026a564bbfd84861ac4b65393644beef', '2017-07-25 21:43:52', '026a564bbfd84861ac4b65393644beef', '2017-07-26 15:14:43', '772a287903f14e01bc4077e5082823cf', '6804cf9cd7eb46148183f16f99d5e6c6');
INSERT INTO `extend_act_business` VALUES ('9146ff7b2d3248df8f89bef6fa5dfbd3', '请假回调', 'leave_1', 'com.hxy.demo.callBack.ActCallBack.leaveBack', '4', '2dd79ea6652244b789cfeffbece4fec9', '1', 'false', '请假回调', '026a564bbfd84861ac4b65393644beef', '2017-07-27 16:30:27', null, null, null, null);
INSERT INTO `extend_act_business` VALUES ('dc2c625a139e4b05bd8ee33e1fe38a40', '人事部门', 'renshi', '', '2', '79cc0fe7bac741d0a6d7f8d72bbee313', '1', 'true', '', '026a564bbfd84861ac4b65393644beef', '2017-07-27 11:22:58', '026a564bbfd84861ac4b65393644beef', '2017-09-04 09:58:06', '772a287903f14e01bc4077e5082823cf', '6804cf9cd7eb46148183f16f99d5e6c6');

-- ----------------------------
-- Table structure for extend_act_flowbus
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_flowbus`;
CREATE TABLE `extend_act_flowbus` (
  `id` varchar(32) DEFAULT NULL,
  `bus_id` varchar(32) DEFAULT NULL COMMENT '业务ID',
  `status` varchar(6) DEFAULT NULL COMMENT '业务流程状态  1=草稿 2=审批中 3=结束',
  `start_time` datetime DEFAULT NULL COMMENT '流程发起时间',
  `instance_id` varchar(64) DEFAULT NULL COMMENT '流程实例id',
  `defid` varchar(64) DEFAULT NULL COMMENT '流程定义id',
  `start_user_Id` varchar(32) DEFAULT NULL COMMENT '流程发起人',
  `code` varchar(128) DEFAULT NULL COMMENT '业务流程单据编号',
  `act_key` varchar(128) DEFAULT NULL COMMENT '流程key',
  `table_name` varchar(255) DEFAULT NULL COMMENT '业务表名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程关系表与activitiBaseEntity中字段一样';

-- ----------------------------
-- Records of extend_act_flowbus
-- ----------------------------

-- ----------------------------
-- Table structure for extend_act_model
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_model`;
CREATE TABLE `extend_act_model` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键ID',
  `act_version` int(11) DEFAULT NULL COMMENT '版本号',
  `extend_act_business_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的 业务表 ID',
  `deployment_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'activiti中的部署表id',
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `model_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'activiti中的模型表id',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `status` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布状态 1:已发布 2：未发布',
  `baid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门(组织)ID【FK】,直接归属的组织ID',
  `bapid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构ID【FK】(上级)',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务流程单据编号',
  `defid` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程定义id',
  `instance_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程实例id',
  `start_time` datetime DEFAULT NULL COMMENT '流程发起时间',
  `remark` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '新增时间',
  `create_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '新增人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程模板扩展表';

-- ----------------------------
-- Records of extend_act_model
-- ----------------------------
INSERT INTO `extend_act_model` VALUES ('4915e6e4fd4a44f6b574f3760c5dcf69', '1', '2dd79ea6652244b789cfeffbece4fec9', null, '', '1ecc61b2-9119-11e7-b720-005056c00001', 'test', '1', '4ec329625a1047ea87a8dfe8dd0750d1', 'a694d140b8e44eb2baa5f26435c6a7f8', null, null, null, null, null, null, null, '2017-09-04 10:31:13', '026a564bbfd84861ac4b65393644beef');

-- ----------------------------
-- Table structure for extend_act_nodefield
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_nodefield`;
CREATE TABLE `extend_act_nodefield` (
  `id` varchar(32) NOT NULL,
  `node_id` varchar(64) DEFAULT NULL COMMENT '节点id',
  `field_name` varchar(64) DEFAULT NULL COMMENT '字段名称',
  `field_type` varchar(2) DEFAULT NULL COMMENT '字段类型  1=可写(可写的也能读) 2=参与连线判断',
  `rule` varchar(10) DEFAULT NULL COMMENT '判断规则 "==",">=","<=",">","<"',
  `field_val` varchar(64) DEFAULT NULL COMMENT '条件值',
  `el_operator` varchar(4) DEFAULT NULL COMMENT 'el表达式 运算符 &&=并且，||=或',
  `sort` varchar(6) DEFAULT NULL COMMENT '同一节点条件排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程节点对应的字段权限表';

-- ----------------------------
-- Records of extend_act_nodefield
-- ----------------------------

-- ----------------------------
-- Table structure for extend_act_nodeset
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_nodeset`;
CREATE TABLE `extend_act_nodeset` (
  `id` varchar(32) NOT NULL,
  `model_id` varchar(64) DEFAULT NULL COMMENT '模型id',
  `defid` varchar(64) DEFAULT NULL COMMENT '流程定义id',
  `node_id` varchar(64) DEFAULT NULL COMMENT ' 流程节点id',
  `node_type` varchar(5) DEFAULT NULL COMMENT '流程节点类型 =开始节点 2=审批节点 3=分支 4=连线 5=结束节点',
  `node_action` varchar(5) DEFAULT NULL COMMENT '节点行为 2 的时候 ,1=审批 2=会签',
  `change_files` varchar(1024) DEFAULT NULL COMMENT '可更改的字段数据，以逗号隔开',
  `call_back` varchar(255) DEFAULT NULL COMMENT '业务回调函数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程节点配置';

-- ----------------------------
-- Records of extend_act_nodeset
-- ----------------------------

-- ----------------------------
-- Table structure for extend_act_nodeuser
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_nodeuser`;
CREATE TABLE `extend_act_nodeuser` (
  `id` varchar(32) NOT NULL,
  `node_id` varchar(64) NOT NULL COMMENT '节点id',
  `user_type` varchar(2) DEFAULT NULL COMMENT '用户类型 1=用户 2=角色 3=组织',
  PRIMARY KEY (`id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点可选人';

-- ----------------------------
-- Records of extend_act_nodeuser
-- ----------------------------

-- ----------------------------
-- Table structure for extend_act_tasklog
-- ----------------------------
DROP TABLE IF EXISTS `extend_act_tasklog`;
CREATE TABLE `extend_act_tasklog` (
  `id` varchar(32) NOT NULL,
  `bus_id` varchar(255) DEFAULT NULL COMMENT '业务id',
  `def_id` varchar(255) DEFAULT NULL COMMENT '流程定义id',
  `instance_id` varchar(255) DEFAULT NULL COMMENT '流程实例Id',
  `task_id` varchar(255) DEFAULT NULL COMMENT '名称',
  `task_Name` varchar(128) DEFAULT NULL COMMENT '流程任务名称',
  `advance_id` varchar(32) DEFAULT NULL COMMENT '预处理人',
  `deal_id` varchar(32) DEFAULT NULL COMMENT '办理人',
  `deal_time` datetime DEFAULT NULL COMMENT '办理时间',
  `agen_id` varchar(32) DEFAULT NULL COMMENT '代理人',
  `app_opinion` varchar(255) DEFAULT NULL COMMENT '审批意见',
  `app_action` varchar(6) DEFAULT NULL COMMENT '审批行为 同意、不同意、驳回、会签 等',
  `is_sign` varchar(6) DEFAULT NULL COMMENT '是否显示签名',
  `columns` varchar(2000) DEFAULT NULL COMMENT '业务表更改的字段记录',
  `create_time` datetime DEFAULT NULL COMMENT '任务创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of extend_act_tasklog
-- ----------------------------

-- ----------------------------
-- Table structure for leaveaply
-- ----------------------------
DROP TABLE IF EXISTS `leaveaply`;
CREATE TABLE `leaveaply` (
  `id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '请假人',
  `title` varchar(255) DEFAULT NULL COMMENT '请假标题',
  `day` int(11) DEFAULT NULL COMMENT '请假天数',
  `leavewhy` varchar(255) DEFAULT NULL COMMENT '原因',
  `status` varchar(6) DEFAULT NULL COMMENT '业务流程状态  1=草稿 2=审批中 3=结束',
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '流程发起时间',
  `instance_id` varchar(64) DEFAULT NULL COMMENT '流程实例id',
  `defid` varchar(64) DEFAULT '' COMMENT '流程定义id',
  `start_user_id` varchar(32) DEFAULT NULL COMMENT '流程发起人',
  `code` varchar(50) DEFAULT NULL COMMENT '业务流程单据编号',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '新建时间',
  `create_id` varchar(32) DEFAULT NULL,
  `update_id` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `act_result` varchar(4) DEFAULT NULL COMMENT '审批结果 1为同意,2为不同意,3为审批中'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假流程测试';

-- ----------------------------
-- Records of leaveaply
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('HxyScheduler', 'TASK_14', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('HxyScheduler', 'TASK_14', 'DEFAULT', null, 'com.hxy.quartz.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720027636F6D2E6878792E71756172747A2E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000015CED5500F07874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000E7400047465737474000672656E72656E74000131737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('HxyScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('HxyScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('HxyScheduler', 'AGOBW-7071507071504491549940', '1504492723510', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('HxyScheduler', 'TASK_14', 'DEFAULT', 'TASK_14', 'DEFAULT', null, '1498636800000', '-1', '5', 'PAUSED', 'CRON', '1498635261000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720027636F6D2E6878792E71756172747A2E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000015CED5500F07874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000E740004746573747400086878794672616D6574000131737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('14', 'testTask', 'test', 'hxyFrame', '0 0/30 * * * ?', '1', '1', '2017-06-28 14:13:10');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code` (
  `id` varchar(32) NOT NULL,
  `mark` varchar(32) DEFAULT '' COMMENT '码值唯一标识, 例如，SEX、SEX_1、SEX_2',
  `name` varchar(128) DEFAULT NULL COMMENT '码值的中文表示， 例如：是、否      、性别',
  `value` varchar(255) DEFAULT NULL COMMENT '码值 的数字表示，例如：1，2，3。。。。。、sex',
  `type` varchar(255) DEFAULT NULL COMMENT '1：目录 2：字典码',
  `parent_id` varchar(32) DEFAULT NULL,
  `sort` varchar(4) DEFAULT NULL COMMENT '在同一级节点中的序号',
  `remark` varchar(255) DEFAULT '' COMMENT '备注，备用字段',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` date DEFAULT NULL,
  `update_id` varchar(32) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `open` varchar(5) DEFAULT NULL COMMENT '是否展开 true是 false否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据字典';

-- ----------------------------
-- Records of sys_code
-- ----------------------------
INSERT INTO `sys_code` VALUES ('008517347a0447a08b7f6faab1fea325', 'act_judg_>', '>', '>', '2', 'c11c36c32e17411c8ec5ba638ad28731', '2', '>', '026a564bbfd84861ac4b65393644beef', '2017-07-28', '026a564bbfd84861ac4b65393644beef', '2017-07-28', 'false');
INSERT INTO `sys_code` VALUES ('03a63d9c56474fe1ae183f31bf3933fc', 'act_judg_>=', '>=', '>=', '2', 'c11c36c32e17411c8ec5ba638ad28731', '3', '>=', '026a564bbfd84861ac4b65393644beef', '2017-07-28', '026a564bbfd84861ac4b65393644beef', '2017-07-28', 'false');
INSERT INTO `sys_code` VALUES ('103df6829961455ca09bb9a2b9987daf', 'act_el_operator', '逻辑运算符', '', '1', 'e8a9e485ff8947718f43706c6de85c60', '1', '逻辑运算符', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('107d1d36e7444f8cb1a3320de7f1ed02', 'YES_NO_1', '否', '1', '2', '2b0281e730ac4923a1f02b4cea3ec03c', '2', '否', '026a564bbfd84861ac4b65393644beef', '2017-07-19', null, null, '0');
INSERT INTO `sys_code` VALUES ('1178fe5df7d64a55ae5b896776ce0684', 'act_task_result_1', '同意', '1', '2', 'c19fd8a436714d8d922726df6d97d5fc', '10', '同意', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('1c4d727d678d44bba18c880f85ad647a', 'act_task_result_2', '反对', '2', '2', 'c19fd8a436714d8d922726df6d97d5fc', '2', '反对', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('24647e27317a4c9d983a0b423d1aca15', 'act_node_type_4', '连线', '4', '2', '8532ec46674b44498ee08f4cefbcf6dd', '4', '连线', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('2611ad193d244c1fb4f50b4a2463f617', 'act_result_1', '同意', '1', '2', '3cc90674a0d5416594cf5afcaca10a6e', '1', '同意', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('2b0281e730ac4923a1f02b4cea3ec03c', 'YES_NO', '是否', null, '1', 'c5e12bdfd1cf4789981406323924854a', '1', '', '026a564bbfd84861ac4b65393644beef', '2017-07-19', null, null, '0');
INSERT INTO `sys_code` VALUES ('2f06861a28ff4fd68e73203ca2757b00', 'act_Bus_type', '业务树类型', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '5', '1=根节点 2=分组 3=业务类 4=回调', '026a564bbfd84861ac4b65393644beef', '2017-07-25', '026a564bbfd84861ac4b65393644beef', '2017-07-25', 'false');
INSERT INTO `sys_code` VALUES ('325d95a84e6c46c0a37b2534815e6d8d', 'act_process_status', '流程状态', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '1', '流程状态', '026a564bbfd84861ac4b65393644beef', '2017-08-14', null, null, 'false');
INSERT INTO `sys_code` VALUES ('327dedbd595e4214807f83e6c72ae858', 'act_node_type_5', '结束节点', '5', '2', '8532ec46674b44498ee08f4cefbcf6dd', '5', '结束节点', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('33d7ab2f4ebd49c2a0201629ff85e3d6', 'act_node_action_2', '会签', '2', '2', '5d477b1523df4fb7811e2648e9bd2086', '2', '会签', '026a564bbfd84861ac4b65393644beef', '2017-07-21', null, null, 'false');
INSERT INTO `sys_code` VALUES ('3515c8053cbd47f0b8bc513b06ca550e', 'act_examine_type_2', '角色', '2', '2', 'fe58d909108f4fbda7ee78e9bde849ea', '2', '', '026a564bbfd84861ac4b65393644beef', '2017-07-24', null, null, 'false');
INSERT INTO `sys_code` VALUES ('3cc90674a0d5416594cf5afcaca10a6e', 'act_result', '整个流程审批结果', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '1', '整个流程审批结果', '026a564bbfd84861ac4b65393644beef', '2017-08-15', '026a564bbfd84861ac4b65393644beef', '2017-08-15', 'false');
INSERT INTO `sys_code` VALUES ('46e6913c00c541319ee005ab4c419ac0', 'act_result_3', '审批中', '3', '2', '3cc90674a0d5416594cf5afcaca10a6e', '3', '审批中', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('46e85988afa24fe4acaa90addec3046a', 'act_user_type', '审批用户类型', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '1', '审批用户类型', '026a564bbfd84861ac4b65393644beef', '2017-07-21', '026a564bbfd84861ac4b65393644beef', '2017-07-21', 'false');
INSERT INTO `sys_code` VALUES ('4df5534dc25f43f5a0d3ae2c2baf9325', '初始化', '字典管理树', null, '1', '0', '1', '字典根据结点', '026a564bbfd84861ac4b65393644beef', '2017-07-19', null, null, 'true');
INSERT INTO `sys_code` VALUES ('53a6e83f9da7431c9733df4ff6d04510', 'act_node_type_3', '分支', '3', '2', '8532ec46674b44498ee08f4cefbcf6dd', '3', '分支', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('5d477b1523df4fb7811e2648e9bd2086', 'act_node_action', '节点行为', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '1', '节点行为', '026a564bbfd84861ac4b65393644beef', '2017-07-21', null, null, 'false');
INSERT INTO `sys_code` VALUES ('68eaec38d48b40d6b7174c56d1d51374', 'is_open_false', '否', 'false', '2', 'cd9212fa11174570a2714d47658e9548', '2', '是', '026a564bbfd84861ac4b65393644beef', '2017-07-26', '026a564bbfd84861ac4b65393644beef', '2017-07-26', 'false');
INSERT INTO `sys_code` VALUES ('6db40ee31e4f4bab8f0fb3740587f13f', 'act_judg_<', '<', '<', '2', 'c11c36c32e17411c8ec5ba638ad28731', '4', '小于', '026a564bbfd84861ac4b65393644beef', '2017-07-28', '026a564bbfd84861ac4b65393644beef', '2017-07-28', 'false');
INSERT INTO `sys_code` VALUES ('6fe9345581964c209e7ff866739d13fb', 'notice_soucre', '通知来源', null, '1', 'e5ce8103550c4044a11bdb285b88fcc0', '1', '通知来源', '026a564bbfd84861ac4b65393644beef', '2017-09-01', '026a564bbfd84861ac4b65393644beef', '2017-08-22', 'false');
INSERT INTO `sys_code` VALUES ('7851704fbf5946bda39cb3f2136f2f8e', 'act_el_operator_||', '或', '||', '2', '103df6829961455ca09bb9a2b9987daf', '2', '或', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('8453516b986948c692e2ceac54521a66', 'act_task_result_3', '弃权', '3', '2', 'c19fd8a436714d8d922726df6d97d5fc', '3', '弃权', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('8532ec46674b44498ee08f4cefbcf6dd', 'act_node_type', '流程节点类型', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '7', '流程节点类型', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('853c64f4c537463eb994929c946d4616', 'act_Bus_type_3', '业务类', '3', '2', '2f06861a28ff4fd68e73203ca2757b00', '3', '业务类', '026a564bbfd84861ac4b65393644beef', '2017-07-25', null, null, 'false');
INSERT INTO `sys_code` VALUES ('88ba5d41c8e9412ea1d6026db65fcbf7', 'act_el_operator_&&', '并且', '&&', '2', '103df6829961455ca09bb9a2b9987daf', '1', '并且', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('88edb13ce29147d59561a6e34f7d7c47', 'act_task_result_4', '驳回到发起人', '4', '2', 'c19fd8a436714d8d922726df6d97d5fc', '4', '驳回', '026a564bbfd84861ac4b65393644beef', '2017-08-15', '026a564bbfd84861ac4b65393644beef', '2017-08-15', 'false');
INSERT INTO `sys_code` VALUES ('9aec9385da7641bc8c283d49d780028f', 'act_process_status_1', '草稿', '1', '2', '325d95a84e6c46c0a37b2534815e6d8d', '1', '草稿', '026a564bbfd84861ac4b65393644beef', '2017-08-14', null, null, 'false');
INSERT INTO `sys_code` VALUES ('a33c03f863674b49b1a40dd8a56a554c', 'is_open_true', '是', 'true', '2', 'cd9212fa11174570a2714d47658e9548', '2', '是', '026a564bbfd84861ac4b65393644beef', '2017-07-26', '026a564bbfd84861ac4b65393644beef', '2017-07-26', 'false');
INSERT INTO `sys_code` VALUES ('a5ff0e83e4ad4e8d90f060bf49118182', 'act_process_status_2', '审批中', '2', '2', '325d95a84e6c46c0a37b2534815e6d8d', '2', '审批中', '026a564bbfd84861ac4b65393644beef', '2017-08-14', null, null, 'false');
INSERT INTO `sys_code` VALUES ('adffbaacc25543129abc86352ecf7b52', 'sys', '系统码', null, '1', '4df5534dc25f43f5a0d3ae2c2baf9325', '1', '系统码', '026a564bbfd84861ac4b65393644beef', '2017-07-19', '026a564bbfd84861ac4b65393644beef', '2017-08-22', 'true');
INSERT INTO `sys_code` VALUES ('aecdbfbb7b0749f0b882ff5c06ad3753', 'YES_NO_0', '是', '0', '2', '2b0281e730ac4923a1f02b4cea3ec03c', '0', '是', '026a564bbfd84861ac4b65393644beef', '2017-07-19', null, null, '0');
INSERT INTO `sys_code` VALUES ('af73bb0b04ea47d9ab8ac0e8435e11d9', 'act_field_type_2', '参与连线判断', '2', '2', 'b8dbfbe5ebc740e2a621aaf8e9f93e14', '2', '参与连线判断', '026a564bbfd84861ac4b65393644beef', '2017-07-21', null, null, 'false');
INSERT INTO `sys_code` VALUES ('b432d1d4a5154c519c8c426cbceb2446', 'act_task_result_5', '转办', null, '1', 'c19fd8a436714d8d922726df6d97d5fc', '5', '转办', '026a564bbfd84861ac4b65393644beef', '2017-09-01', null, null, 'false');
INSERT INTO `sys_code` VALUES ('b8dbfbe5ebc740e2a621aaf8e9f93e14', 'act_field_type', '节点对应的字段类型', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '3', '字段类型  1=可写(可写的也能读) 2=参与连线判断', '026a564bbfd84861ac4b65393644beef', '2017-07-21', null, null, 'false');
INSERT INTO `sys_code` VALUES ('b8e054a4fcc0444ab6907c457511d982', 'notice_soucre_2', '流程通知', null, '1', '6fe9345581964c209e7ff866739d13fb', '2', '流程通知', '026a564bbfd84861ac4b65393644beef', '2017-09-01', '026a564bbfd84861ac4b65393644beef', '2017-08-22', 'false');
INSERT INTO `sys_code` VALUES ('bd3a3edd45db4129a946a3f34f68c186', 'act_node_type_1', '开始节点', '1', '2', '8532ec46674b44498ee08f4cefbcf6dd', '7', '开始节点', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('c06463daea244c2980aa6a68ae0dde0c', 'act_user_type_1', '用户', '1', '2', '46e85988afa24fe4acaa90addec3046a', '1', '用户', '026a564bbfd84861ac4b65393644beef', '2017-07-21', '026a564bbfd84861ac4b65393644beef', '2017-07-21', 'false');
INSERT INTO `sys_code` VALUES ('c11c36c32e17411c8ec5ba638ad28731', 'act_judg', '流程条件运算符', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '6', '流程条件运算符', '026a564bbfd84861ac4b65393644beef', '2017-07-28', '026a564bbfd84861ac4b65393644beef', '2017-07-28', 'false');
INSERT INTO `sys_code` VALUES ('c12863d73db64db98be1a5eff382db70', 'act_examine_type_3', '组织', '3', '2', 'fe58d909108f4fbda7ee78e9bde849ea', '3', '', '026a564bbfd84861ac4b65393644beef', '2017-07-24', null, null, 'false');
INSERT INTO `sys_code` VALUES ('c15231e57f554821a58fd233ccb8af7f', 'act_Bus_type_4', '回调', '4', '2', '2f06861a28ff4fd68e73203ca2757b00', '4', '回调', '026a564bbfd84861ac4b65393644beef', '2017-07-25', null, null, 'false');
INSERT INTO `sys_code` VALUES ('c19fd8a436714d8d922726df6d97d5fc', 'act_task_result', '流程任务审批结果', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '10', '流程任务审批结果', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('c3f2ff1b592c439f8b18fd36413f7f48', 'act_process_status_3', '结束', '3', '2', '325d95a84e6c46c0a37b2534815e6d8d', '3', '结束', '026a564bbfd84861ac4b65393644beef', '2017-08-14', null, null, 'false');
INSERT INTO `sys_code` VALUES ('c5e12bdfd1cf4789981406323924854a', 'public', '公用码', null, '1', '4df5534dc25f43f5a0d3ae2c2baf9325', '2', '公用码', '026a564bbfd84861ac4b65393644beef', '2017-07-19', null, null, '0');
INSERT INTO `sys_code` VALUES ('c695e4c42fb0417d849af2f90af3b4a0', 'act_Bus_type_1', '根节点', '1', '2', '2f06861a28ff4fd68e73203ca2757b00', '1', '根节点', '026a564bbfd84861ac4b65393644beef', '2017-07-25', null, null, 'false');
INSERT INTO `sys_code` VALUES ('c9be0d393d9d4e838a6dc3aa8e788a0d', 'act_result_2', '不同意', '2', '2', '3cc90674a0d5416594cf5afcaca10a6e', '2', '不同意', '026a564bbfd84861ac4b65393644beef', '2017-08-15', null, null, 'false');
INSERT INTO `sys_code` VALUES ('cd9212fa11174570a2714d47658e9548', 'is_open', '是否打开', null, '1', 'c5e12bdfd1cf4789981406323924854a', '2', '', '026a564bbfd84861ac4b65393644beef', '2017-07-26', null, null, 'false');
INSERT INTO `sys_code` VALUES ('ceaa6974c53048938dbef6e8411e9399', 'act_user_type_3', '组织', '3', '2', '46e85988afa24fe4acaa90addec3046a', '3', '组织', '026a564bbfd84861ac4b65393644beef', '2017-07-21', '026a564bbfd84861ac4b65393644beef', '2017-07-21', 'false');
INSERT INTO `sys_code` VALUES ('dd3b72a16c634783814c668a3f5d82ed', 'act_judg_<=', '<=', '<=', '2', 'c11c36c32e17411c8ec5ba638ad28731', '5', '<=', '026a564bbfd84861ac4b65393644beef', '2017-07-28', '026a564bbfd84861ac4b65393644beef', '2017-07-28', 'false');
INSERT INTO `sys_code` VALUES ('dd8c2aadb7aa4d9d824ab85988a4670d', 'act_Bus_type_2', '分组', '2', '2', '2f06861a28ff4fd68e73203ca2757b00', '2', '分组', '026a564bbfd84861ac4b65393644beef', '2017-07-25', null, null, 'false');
INSERT INTO `sys_code` VALUES ('e0e88d6f79ff41d5958e21205dc93e28', 'act_user_type_2', '角色', '2', '2', '46e85988afa24fe4acaa90addec3046a', '2', '角色', '026a564bbfd84861ac4b65393644beef', '2017-07-21', '026a564bbfd84861ac4b65393644beef', '2017-07-21', 'false');
INSERT INTO `sys_code` VALUES ('e23bb5e7bf6b495b835b9d25f73676d7', 'act_node_action_1', '审批', '1', '2', '5d477b1523df4fb7811e2648e9bd2086', '1', '审批', '026a564bbfd84861ac4b65393644beef', '2017-07-21', null, null, 'true');
INSERT INTO `sys_code` VALUES ('e5ce8103550c4044a11bdb285b88fcc0', 'notice', '通知', null, '1', 'adffbaacc25543129abc86352ecf7b52', '2', '通知', '026a564bbfd84861ac4b65393644beef', '2017-09-01', '026a564bbfd84861ac4b65393644beef', '2017-09-01', 'false');
INSERT INTO `sys_code` VALUES ('e8a9e485ff8947718f43706c6de85c60', 'activiti', '工作流', null, '1', 'adffbaacc25543129abc86352ecf7b52', '1', '工作流', '026a564bbfd84861ac4b65393644beef', '2017-07-21', '026a564bbfd84861ac4b65393644beef', '2017-08-24', 'true');
INSERT INTO `sys_code` VALUES ('f053291c0c654237b280c44580581a3e', 'notice_soucre_1', '普通通知（人工发起）', null, '1', '6fe9345581964c209e7ff866739d13fb', '1', '普通通知', '026a564bbfd84861ac4b65393644beef', '2017-09-01', '026a564bbfd84861ac4b65393644beef', '2017-09-01', 'false');
INSERT INTO `sys_code` VALUES ('f2196b74abc84786843f57f1c5d0da13', 'act_judg_==', '==', '==', '2', 'c11c36c32e17411c8ec5ba638ad28731', '1', '==', '026a564bbfd84861ac4b65393644beef', '2017-07-28', '026a564bbfd84861ac4b65393644beef', '2017-07-28', 'false');
INSERT INTO `sys_code` VALUES ('f37cdb5ece184b249543ef2f521b8b55', 'act_examine_type_1', '用户', '1', '2', 'fe58d909108f4fbda7ee78e9bde849ea', '1', '', '026a564bbfd84861ac4b65393644beef', '2017-07-24', null, null, 'false');
INSERT INTO `sys_code` VALUES ('f6df754090bc4db3846ca51c84df4563', 'act_node_type_2', '审批节点', '2', '2', '8532ec46674b44498ee08f4cefbcf6dd', '2', '审批节点', '026a564bbfd84861ac4b65393644beef', '2017-08-09', null, null, 'false');
INSERT INTO `sys_code` VALUES ('f9a61a2364ed400e94f1acb88e271360', 'act_field_type_1', '可写', '1', '2', 'b8dbfbe5ebc740e2a621aaf8e9f93e14', '1', '可写(可写的也能读)', '026a564bbfd84861ac4b65393644beef', '2017-07-21', null, null, 'false');
INSERT INTO `sys_code` VALUES ('fe58d909108f4fbda7ee78e9bde849ea', 'act_examine_type', '审批者类型', null, '1', 'e8a9e485ff8947718f43706c6de85c60', '4', '流程审批者类型', '026a564bbfd84861ac4b65393644beef', '2017-07-24', null, null, 'false');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('2', 'CLOUD_STORAGE_CONFIG_KEY', '{\"qiniuAccessKey\":\"DkJTbNsm0L9ZVIHEUe1MzwuLcDjBoReYVh53JC3x\",\"qiniuBucketName\":\"hxyframe\",\"qiniuDomain\":\"http://osaowv4s0.bkt.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"shYrg6C685eAueqVTg8yvFSaX4PghNAVrA2Lmb8i\",\"type\":1}', '0', null);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=417 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父菜单id',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(1000) DEFAULT NULL COMMENT '菜单链接',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` varchar(6) DEFAULT NULL COMMENT '状态（0显示，1隐藏)',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `update_id` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `open` varchar(5) DEFAULT NULL COMMENT '是否展开 true 是 false 否',
  `bapid` varchar(32) DEFAULT NULL COMMENT '机构',
  `baid` varchar(32) DEFAULT NULL COMMENT '部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0', null, null, '根目录', null, '', '0', null, null, null, '2017-06-29 17:29:00', null, '026a564bbfd84861ac4b65393644beef', '2017-07-20 18:20:50', '0', 'true', null, null);
INSERT INTO `sys_menu` VALUES ('0c410538dff54d35a1e19c7524c20e47', '1eabb55ae2fa4f5e890c7739c1678e44', '0', '删除', '', null, '17', '1', 'sys:config:delete', null, '2017-08-29 22:03:44', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:36:06', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('0c7df2638a464cd5b165547e07dd5409', 'f4f6af97585d401f918afedd3ca223e9', '0', '日志列表', '', null, '14', '1', 'sys:schedule:log', null, '2017-08-29 22:06:44', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:32:31', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('0e6213b719bd424a9c794ee87f64333e', '1e7867f5ef3748bbaa2060b0192b8ff0', '0', '生成代码', '', null, '20', '1', 'sys:generator:code', null, '2017-08-29 22:00:22', '44b66661d5954f36a26465d96e837cf7', '026a564bbfd84861ac4b65393644beef', '2017-06-26 15:10:40', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('1', '0', '0', '系统管理', '23', 'fa fa-cog', '1', '1', '32', null, null, null, '026a564bbfd84861ac4b65393644beef', '2017-08-24 13:18:09', '0', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('14c58e44d6754c92806e1b97614c49b9', 'b7ab517a15b74dea812ee7ef32847a48', '0', '所有权限', '', null, '1', '1', 'sys:organ:all', null, '2017-08-29 22:11:25', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:30:16', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('1515daa136ba41f783e82318f851d343', '1', '0', '字典管理', 'sys/code.html', 'fa fa-bell-o', '6', '1', '', null, '2017-07-14 15:14:04', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:21:10', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('15d2074a502f443cb760f613a40df598', '8b1f46f8ba6e455790a515c32e0329c5', null, '保存/更新', null, null, '0', '1', 'sys:user:update', null, '2017-05-18 19:31:05', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:08:39', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('1e7867f5ef3748bbaa2060b0192b8ff0', '1', '0', '代码生成', 'sys/generator.html', 'fa fa-check-circle', '20', '1', '', null, '2017-06-28 17:35:34', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-26 15:10:40', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('1eabb55ae2fa4f5e890c7739c1678e44', '1', '0', '系统参数', 'sys/config.html', 'fa fa-cog', '17', '1', 'sys:config:list', null, '2017-06-29 11:33:47', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:36:06', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('1f936af47d064ab4989aadf6373e6502', '1515daa136ba41f783e82318f851d343', '0', '保存/更新', '', null, '6', '1', 'sys:code:update', null, '2017-07-24 09:46:51', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 21:54:24', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('2', '1', '1', '菜单', 'sys/menu.html', 'fa fa-bullhorn', '12', '1', '', null, null, null, '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:18:47', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('4', '2', '2', '保存/更新', null, null, null, '1', 'sys:menu:update', null, null, null, '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:04:39', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('4565ee01959c478d96c4071d3bb2a36f', 'f4f6af97585d401f918afedd3ca223e9', '0', '立即执行', 'sys/schedule.html', null, '11', '1', 'sys:schedule:run', null, '2017-06-26 15:57:14', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:05:58', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('4e053e35bfed492fa6248e4888addd67', '1515daa136ba41f783e82318f851d343', '0', '查看信息', '', null, '6', '1', 'sys:code:info', null, '2017-07-24 09:46:36', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:32:36', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('6', '2', '2', '删除', null, null, null, '1', 'sys:menu:delete', null, null, null, '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:05:01', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('624b686dd7884be48f41fcb6bf86c272', 'e700f061dae448e58cd81a68e17b3439', '0', '我的待办', 'act/deal/myUpcoming', 'fa fa-comment-o', '6', '1', '', null, '2017-08-07 13:21:36', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-09 10:05:01', '1', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('62bdcab5fe5f4e5fa7ab79fcd8cb47c4', '8b1f46f8ba6e455790a515c32e0329c5', null, '删除', null, null, '0', '1', 'sys:user:delete', null, '2017-05-18 19:31:29', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:08:56', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('690b7470fe394a6a8d9d0fa1340f18dc', 'f4f6af97585d401f918afedd3ca223e9', '0', '暂停定时', 'sys/schedule.html', null, '11', '1', 'sys:schedule:pause', null, '2017-06-26 15:57:30', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:06:04', '2', null, null, null);
INSERT INTO `sys_menu` VALUES ('695388220b704cdaa72539c5f82fb254', '1eabb55ae2fa4f5e890c7739c1678e44', '0', '保存/更新', '', null, '17', '1', 'sys:config:update', null, '2017-08-29 22:03:32', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:36:06', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('7', '2', '2', '查看信息', null, null, null, null, 'sys:menu:info', null, null, null, '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:05:10', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('80030434d10548968beaaeed79c3408b', '8b1f46f8ba6e455790a515c32e0329c5', null, '查看信息', '', null, '1', '1', 'sys:user:info', null, '2017-08-29 22:09:38', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:33:35', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('866aa98844be494bbe669d29a76aff7b', '8ae83f604d394671ae78d763c3f41ded', null, '收到通知', 'sys/notice/myList', 'fa fa-comment-o', '1', '1', 'sys:notice:list', null, '2017-09-01 11:38:55', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-09-01 13:40:32', '1', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('8ae83f604d394671ae78d763c3f41ded', '0', null, '通知管理', '', 'fa fa-bullhorn', '4', '1', '', null, '2017-09-01 11:35:46', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-09-01 11:39:33', '0', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('8b1f46f8ba6e455790a515c32e0329c5', 'feb235067fd7400090b0aa5451e4a5a4', null, '系统用户', 'sys/user.html', 'fa fa-bar-chart-o', '1', '1', null, null, '2017-05-10 14:01:31', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:33:35', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('90ca98befe9f4a67a07d5ab5fb2f3de3', '1515daa136ba41f783e82318f851d343', '0', '删除', '', null, '6', '1', 'sys:code:delete', null, '2017-07-24 09:47:19', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:32:36', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('9181aafab10044d99267598e54f0057b', 'e700f061dae448e58cd81a68e17b3439', '0', '请假流程测试', 'demo/leave/list', 'fa fa-archive', '3', '1', '', null, '2017-07-31 14:09:32', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-07-12 15:20:23', '1', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('9d22510a1b1a40459619c5b2fdddfdc2', '9e8dc02fe5614580bdf5f6ca9a852b70', null, '删除', null, null, '0', '1', 'sys:role:delete', null, '2017-05-18 21:35:21', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:09:53', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('9e8dc02fe5614580bdf5f6ca9a852b70', 'feb235067fd7400090b0aa5451e4a5a4', null, '系统角色', 'sys/role.html', 'fa fa-rouble', '0', '1', null, null, '2017-05-12 09:31:31', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:32:59', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('a73be9e0371848e18135652f632a117f', 'e700f061dae448e58cd81a68e17b3439', '0', '业务树管理', 'act/bus/busTree', 'fa fa-archive', '2', '1', '', null, '2017-07-25 19:57:31', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-07-12 15:20:23', '1', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('b7ab517a15b74dea812ee7ef32847a48', 'feb235067fd7400090b0aa5451e4a5a4', '0', '组织机构', 'sys/organ.html', 'fa fa-cog', '1', '1', '', null, '2017-07-19 19:42:23', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:30:16', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('bbe03cab3b4a45c2a4dd972c80ef4224', 'e700f061dae448e58cd81a68e17b3439', '0', '流程设计', 'act/model/list', 'fa fa-archive', '1', '1', '', null, '2017-07-12 15:19:56', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-07-25 19:58:34', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('bc82a59fa9034622a66e13fd830def06', 'e700f061dae448e58cd81a68e17b3439', '0', '我的已办', 'act/deal/myDoneList', 'fa fa-puzzle-piece', '5', '1', '', null, '2017-08-14 11:23:37', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-14 11:24:27', '1', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('c799ed2f2b1945ee875734b658cbbf10', '1515daa136ba41f783e82318f851d343', '0', '查询码值树', '', null, '6', '1', 'sys:code:codeTree', null, '2017-07-24 09:46:04', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 21:55:00', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('c898d73ff1fb49dc89909ed13452103a', '9e8dc02fe5614580bdf5f6ca9a852b70', null, '保存/更新', null, null, '0', '1', 'sys:role:update', null, '2017-05-18 20:37:18', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:10:19', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('ce4a2b7afad24616abd283f4741fe3e0', '9e8dc02fe5614580bdf5f6ca9a852b70', null, '查看信息', '', null, null, '1', 'sys:role:info', null, '2017-05-18 20:34:07', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:10:47', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('cefab951f1474646b9419828db47c362', 'f4f6af97585d401f918afedd3ca223e9', '0', '保存/更新', '', null, '14', '1', 'sys:schedule:update', null, '2017-08-25 09:29:35', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:05:48', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('e700f061dae448e58cd81a68e17b3439', '0', '0', '工作流管理', 'test/testjsp', 'fa fa-archive', '3', '1', '', null, '2017-07-10 14:10:15', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-09-01 11:36:24', '0', null, null, null);
INSERT INTO `sys_menu` VALUES ('f161e717976340e89c68b93abb87419d', 'f8524c36e2584ff5a2e374c3727c39c5', '0', '所有权限', '', null, '16', '1', 'sys:oss:all', null, '2017-08-29 22:07:42', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:35:45', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('f4d418f7f9174338acc595151daa020f', 'f4f6af97585d401f918afedd3ca223e9', '0', '恢复定时', 'sys/schedule.html', null, '11', '1', 'sys:schedule:resume', null, '2017-06-26 15:57:44', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:06:12', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('f4f6af97585d401f918afedd3ca223e9', '1', '0', '定时任务', 'sys/schedule.html', 'fa fa-rub', '14', '1', 'sys:schedule:list', null, '2017-06-26 11:42:14', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:32:31', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('f7a850cb503c4b2cb5dc27081aca3325', 'f4f6af97585d401f918afedd3ca223e9', '0', '删除', 'sys/schedule.html', null, '11', '1', 'sys:schedule:delete', null, '2017-06-26 15:56:53', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-08-29 22:06:16', '2', 'false', null, null);
INSERT INTO `sys_menu` VALUES ('f8524c36e2584ff5a2e374c3727c39c5', '1', '0', '云文件上传', 'sys/oss.html', 'fa fa-camera', '16', '1', 'sys:oss:all', null, '2017-06-29 11:35:30', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 11:35:45', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('fdd70e08ae994de18009bc95f4f51fff', '1', '0', '系统日志', 'sys/log.html', 'fa fa-fighter-jet', '30', '1', '', null, '2017-06-29 21:47:49', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:32:36', '1', null, null, null);
INSERT INTO `sys_menu` VALUES ('feb235067fd7400090b0aa5451e4a5a4', '0', null, '权限管理', null, 'fa fa-certificate', '2', '1', null, null, '2017-06-29 17:31:31', '026a564bbfd84861ac4b65393644beef', '026a564bbfd84861ac4b65393644beef', '2017-06-29 17:34:18', '0', null, null, null);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` varchar(32) NOT NULL,
  `context` varchar(512) DEFAULT NULL COMMENT '通知内容',
  `title` varchar(255) DEFAULT NULL COMMENT '通知标题',
  `soucre` varchar(3) DEFAULT NULL COMMENT '通知来源 1=普通通知（人工发起） 2=流程通知',
  `status` varchar(3) DEFAULT NULL COMMENT '通知状态 0=已发布 1=草稿 ',
  `is_urgent` varchar(3) DEFAULT NULL COMMENT '是否紧急 0是1否',
  `release_timee` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `update_id` varchar(32) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_notice_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_user`;
CREATE TABLE `sys_notice_user` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `notice_id` varchar(32) DEFAULT NULL,
  `status` varchar(3) DEFAULT NULL COMMENT '0已读 1未读',
  `remark` varchar(255) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知和用户关系表';

-- ----------------------------
-- Records of sys_notice_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organ
-- ----------------------------
DROP TABLE IF EXISTS `sys_organ`;
CREATE TABLE `sys_organ` (
  `id` varchar(32) NOT NULL,
  `bapid` varchar(32) DEFAULT NULL COMMENT '该部门的归属机构ID ，只有当是部门的时候才生效',
  `type` varchar(1) DEFAULT NULL COMMENT '结点类型：0=根节点 ，1=机构，2=部门',
  `code` varchar(64) DEFAULT NULL COMMENT '编号',
  `name` varchar(64) DEFAULT NULL COMMENT '节点的名字',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '节点的父节点',
  `is_del` varchar(1) DEFAULT NULL COMMENT '是否删除 0 是 1 否',
  `sysmark` varchar(1024) DEFAULT NULL COMMENT '系统标识，32*10+9 最多支持10级节点。用户具体一批数据的控制',
  `sort` varchar(4) DEFAULT NULL COMMENT '在同一级节点中的序号',
  `open` varchar(5) DEFAULT NULL COMMENT '是否展开 true 是 false 否',
  `remark` varchar(255) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织表';

-- ----------------------------
-- Records of sys_organ
-- ----------------------------
INSERT INTO `sys_organ` VALUES ('0eec919538bd4cd7bf3b1aebd54808f5', '7180f5a0c3624f4bb6fb758ab2c3bda6', '2', '101', 'IT信息中心', '7180f5a0c3624f4bb6fb758ab2c3bda6', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'true', 'IT信息中心', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:27:39', '026a564bbfd84861ac4b65393644beef', '2017-08-17 15:08:03');
INSERT INTO `sys_organ` VALUES ('40ffec9b300c431e890c17beccf8e65c', '7180f5a0c3624f4bb6fb758ab2c3bda6', '2', '102', '人事中心', '7180f5a0c3624f4bb6fb758ab2c3bda6', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '2', 'true', '人事中心', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:28:03', '026a564bbfd84861ac4b65393644beef', '2017-08-16 15:19:01');
INSERT INTO `sys_organ` VALUES ('4ec329625a1047ea87a8dfe8dd0750d1', 'a694d140b8e44eb2baa5f26435c6a7f8', '2', '201', '风控部门', 'a694d140b8e44eb2baa5f26435c6a7f8', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'true', '风控部门', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:28:49', null, null);
INSERT INTO `sys_organ` VALUES ('7180f5a0c3624f4bb6fb758ab2c3bda6', '0', '1', '001', '成都部门', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'true', '成都总部', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:25:43', '026a564bbfd84861ac4b65393644beef', '2017-09-04 10:30:12');
INSERT INTO `sys_organ` VALUES ('8a726eabdc8d4bff880b7d6f6ed59d52', 'a694d140b8e44eb2baa5f26435c6a7f8', '2', '2', '2', 'ff6d5335b1c7456e9a2e7734062820b2', '0', 'ff6d5335b1c7456e9a2e7734062820b2', '2', 'true', '', '026a564bbfd84861ac4b65393644beef', '2017-09-04 10:30:37', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:41:27');
INSERT INTO `sys_organ` VALUES ('988fed2319e14ebbb1ecf6ddc74e594c', 'a694d140b8e44eb2baa5f26435c6a7f8', '1', '1', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '0', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'true', '1', '026a564bbfd84861ac4b65393644beef', '2017-09-04 10:30:27', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:41:27');
INSERT INTO `sys_organ` VALUES ('9e6a3c71bcd84d45807aa6641ab1a642', 'a694d140b8e44eb2baa5f26435c6a7f8', '2', '202', '测试部门', 'a694d140b8e44eb2baa5f26435c6a7f8', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '2', 'true', '测试部门', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:29:10', null, null);
INSERT INTO `sys_organ` VALUES ('a694d140b8e44eb2baa5f26435c6a7f8', '0', '1', '002', '北京分公司', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '2', 'true', '宜宾分公司', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:26:16', '026a564bbfd84861ac4b65393644beef', '2017-09-04 10:07:17');
INSERT INTO `sys_organ` VALUES ('b416c33a10674690b2fbf77c639aeb5d', 'a694d140b8e44eb2baa5f26435c6a7f8', '1', '320', 'test', 'ff6d5335b1c7456e9a2e7734062820b2', '0', 'ff6d5335b1c7456e9a2e7734062820b2', '3', 'true', '', '026a564bbfd84861ac4b65393644beef', '2017-09-04 10:08:01', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:41:27');
INSERT INTO `sys_organ` VALUES ('d3a2d739512040a6a7608de76baeef97', '0', '1', '23', '23', 'ff6d5335b1c7456e9a2e7734062820b2', '0', 'ff6d5335b1c7456e9a2e7734062820b2', '23', 'true', '32', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:41:19', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:36:12');
INSERT INTO `sys_organ` VALUES ('ff6d5335b1c7456e9a2e7734062820b2', '0', '0', '0', '组织机构树', '0', '1', 'ff6d5335b1c7456e9a2e7734062820b2', '1', 'true', '根节点', '026a564bbfd84861ac4b65393644beef', '2017-08-16 11:24:51', '026a564bbfd84861ac4b65393644beef', '2017-08-22 18:41:27');

-- ----------------------------
-- Table structure for sys_organ_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_organ_role`;
CREATE TABLE `sys_organ_role` (
  `organ_id` varchar(32) NOT NULL COMMENT '组织id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`organ_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织角色关系表';

-- ----------------------------
-- Records of sys_organ_role
-- ----------------------------
INSERT INTO `sys_organ_role` VALUES ('0eec919538bd4cd7bf3b1aebd54808f5', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');
INSERT INTO `sys_organ_role` VALUES ('40ffec9b300c431e890c17beccf8e65c', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');
INSERT INTO `sys_organ_role` VALUES ('4ec329625a1047ea87a8dfe8dd0750d1', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');
INSERT INTO `sys_organ_role` VALUES ('7180f5a0c3624f4bb6fb758ab2c3bda6', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');
INSERT INTO `sys_organ_role` VALUES ('9e6a3c71bcd84d45807aa6641ab1a642', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');
INSERT INTO `sys_organ_role` VALUES ('a694d140b8e44eb2baa5f26435c6a7f8', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');
INSERT INTO `sys_organ_role` VALUES ('ff6d5335b1c7456e9a2e7734062820b2', 'f6e67f1c5d9d4ac29fd8d6aab81c3aab');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '角色id',
  `bapid` varchar(32) DEFAULT NULL COMMENT '机构id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(64) DEFAULT NULL COMMENT '角色代码',
  `status` varchar(6) DEFAULT NULL COMMENT '角色状态(0正常 1禁用）',
  `role_type` varchar(6) DEFAULT NULL COMMENT '角色类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_id` varchar(32) DEFAULT NULL COMMENT '新增人',
  `baid` varchar(255) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0c3245b565c84f639ab9e05a6d1eedfd', null, '北京分部', null, '0', null, '北京分部', '2017-09-04 10:36:32', '2017-08-16 13:51:21', null, '026a564bbfd84861ac4b65393644beef', null);
INSERT INTO `sys_role` VALUES ('695a97ae34b94d57899833a3ebcd656a', null, '成都总部', null, '0', null, '成都总部', '2017-08-31 20:12:00', '2017-07-20 11:49:42', null, '026a564bbfd84861ac4b65393644beef', null);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`menu_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限角色表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('0c3245b565c84f639ab9e05a6d1eedfd', '0');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', '0');
INSERT INTO `sys_role_menu` VALUES ('0c3245b565c84f639ab9e05a6d1eedfd', '1');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', '624b686dd7884be48f41fcb6bf86c272');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', '9181aafab10044d99267598e54f0057b');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', 'a73be9e0371848e18135652f632a117f');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', 'bbe03cab3b4a45c2a4dd972c80ef4224');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', 'bc82a59fa9034622a66e13fd830def06');
INSERT INTO `sys_role_menu` VALUES ('695a97ae34b94d57899833a3ebcd656a', 'e700f061dae448e58cd81a68e17b3439');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT 'id主键',
  `bapid` varchar(32) NOT NULL COMMENT '机构id',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `login_name` varchar(64) NOT NULL COMMENT '登陆帐户',
  `pass_word` varchar(128) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '新建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(6) DEFAULT NULL COMMENT '状态(0正常 -1禁用)',
  `phone` varchar(64) DEFAULT NULL COMMENT '电话',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `update_id` varchar(32) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `baid` varchar(32) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('026a564bbfd84861ac4b65393644beef', 'a694d140b8e44eb2baa5f26435c6a7f8', '超级管理员', 'hxy', '0cc175b9c0f1b6a831c399e269772661', '2017-04-27 21:51:49', '2017-09-04 10:04:52', '0', null, null, null, null, '026a564bbfd84861ac4b65393644beef', null, '4ec329625a1047ea87a8dfe8dd0750d1');
INSERT INTO `sys_user` VALUES ('1890471a658f4fbebbc67960eea6cfaa', '7180f5a0c3624f4bb6fb758ab2c3bda6', '小王', 'xw', '0cc175b9c0f1b6a831c399e269772661', null, '2017-09-04 10:04:01', '0', null, null, null, null, '026a564bbfd84861ac4b65393644beef', null, '40ffec9b300c431e890c17beccf8e65c');
INSERT INTO `sys_user` VALUES ('44b66661d5954f36a26465d96e837cf7', '7180f5a0c3624f4bb6fb758ab2c3bda6', '小明', 'xm', '0cc175b9c0f1b6a831c399e269772661', null, '2017-09-04 10:04:09', '0', null, null, null, null, '026a564bbfd84861ac4b65393644beef', null, '0eec919538bd4cd7bf3b1aebd54808f5');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('026a564bbfd84861ac4b65393644beef', '0c3245b565c84f639ab9e05a6d1eedfd');
INSERT INTO `sys_user_role` VALUES ('1890471a658f4fbebbc67960eea6cfaa', '695a97ae34b94d57899833a3ebcd656a');
INSERT INTO `sys_user_role` VALUES ('44b66661d5954f36a26465d96e837cf7', '695a97ae34b94d57899833a3ebcd656a');
