-- auto-generated definition
create table bot_item
(
  id               serial       not null
    constraint bot_item_pkey
    primary key,
  item_name        varchar(255),
  item_description varchar(255),
  created_at       timestamp(6) not null,
  updated_at       timestamp(6) not null,
  item_type        varchar(255)
);

comment on column bot_item.id
is '记录ID';

comment on column bot_item.item_name
is '物品名';

comment on column bot_item.item_description
is '物品描述';

comment on column bot_item.created_at
is '记录创建时间';

comment on column bot_item.updated_at
is '记录更新时间';

comment on column bot_item.item_type
is '物品的类别';

