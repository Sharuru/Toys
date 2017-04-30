/*
Navicat SQLite Data Transfer

Source Server         : SQLite
Source Server Version : 31300
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 31300
File Encoding         : 65001

Date: 2017-04-30 20:05:00
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for fish_record
-- ----------------------------
DROP TABLE IF EXISTS "main"."fish_record";
CREATE TABLE "fish_record" (
"id"  INTEGER NOT NULL,
"user"  TEXT NOT NULL,
"check_time"  INTEGER,
"input_time"  INTEGER,
PRIMARY KEY ("id" ASC)
);

-- ----------------------------
-- Records of fish_record
-- ----------------------------
INSERT INTO "main"."fish_record" VALUES (1, 'thisIsTestUserName', 1493429100, 1493429100);
INSERT INTO "main"."fish_record" VALUES (2, 'thisIsTestUserName', 1493514000, 1493526300);
INSERT INTO "main"."fish_record" VALUES (3, 'thisIsTestUserName1', 1493512980, 1493512980);
PRAGMA foreign_keys = ON;
