/*
Navicat SQLite Data Transfer

Source Server         : SQLite
Source Server Version : 31300
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 31300
File Encoding         : 65001

Date: 2017-05-27 14:40:26
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for roll_record
-- ----------------------------
DROP TABLE IF EXISTS "main"."roll_record";
CREATE TABLE "roll_record" (
"id"  INTEGER NOT NULL,
"uid"  TEXT,
"nickname"  TEXT,
"amount"  DECIMAL,
"stone"  INTEGER DEFAULT 0,
PRIMARY KEY ("id" ASC)
);
PRAGMA foreign_keys = ON;
