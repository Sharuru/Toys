-- auto-generated definition
create table bot_stock
(
  id         serial       not null
    constraint bot_stock_pkey
    primary key,
  user_id    varchar      not null,
  item_id    integer
    constraint bot_stock_item_id_fkey
    references bot_item,
  item_count numeric(12, 2) default 0,
  created_at timestamp(6) not null,
  updated_at timestamp(6) not null
);

comment on column bot_stock.id
is '记录ID';

comment on column bot_stock.user_id
is '用户ID';

comment on column bot_stock.item_id
is '物品ID';

comment on column bot_stock.item_count
is '物品数量';

comment on column bot_stock.created_at
is '记录创建时间';

comment on column bot_stock.updated_at
is '记录修改时间';

