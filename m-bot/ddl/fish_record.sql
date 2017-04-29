/*
Navicat SQLite Data Transfer

Source Server         : SQLite
Source Server Version : 31300
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 31300
File Encoding         : 65001

Date: 2017-04-29 21:36:37
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
INSERT INTO "main"."fish_record" VALUES (1, 'thisIsTestUserName', 1493427600, 1493429700);
PRAGMA foreign_keys = ON;
