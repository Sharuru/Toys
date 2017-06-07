/*
Navicat PGSQL Data Transfer

Source Server         : PostgreSQL@127.0.0.1(LOCAL)
Source Server Version : 90601
Source Host           : 127.0.0.1:5432
Source Database       : bot
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90601
File Encoding         : 65001

Date: 2017-06-07 19:08:09
*/


-- ----------------------------
-- Table structure for fish_time_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."fish_time_record";
CREATE TABLE "public"."fish_time_record" (
"id" int4 DEFAULT nextval('fish_time_record_id_seq'::regclass) NOT NULL,
"username" varchar(255) COLLATE "default" NOT NULL,
"checkin_time" timestamptz(6),
"created_at" timestamp(6) NOT NULL,
"updated_at" timestamp(6) NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."fish_time_record"."id" IS '记录ID';
COMMENT ON COLUMN "public"."fish_time_record"."username" IS '用户名';
COMMENT ON COLUMN "public"."fish_time_record"."checkin_time" IS '出勤时间';
COMMENT ON COLUMN "public"."fish_time_record"."created_at" IS '记录创建时间';
COMMENT ON COLUMN "public"."fish_time_record"."updated_at" IS '记录更新时间';

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table fish_time_record
-- ----------------------------
ALTER TABLE "public"."fish_time_record" ADD PRIMARY KEY ("id");
