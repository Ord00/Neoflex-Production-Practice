create table orders
(
    id_order uuid primary key,
    id_product varchar(36) not null,
    count integer not null,
    id_user varchar(36) not null,
    date_create timestamp not null default now()
);