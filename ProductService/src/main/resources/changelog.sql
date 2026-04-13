create table products
(
    id_product uuid primary key,
    name varchar(255) not null,
    count integer not null,
    date_time_last_change timestamp not null default now()
);

create or replace function update_date_time_last_change()
returns trigger as $$
begin
    new.date_time_last_change = now();
return new;
end;
$$ language plpgsql;

create trigger trigger_update_products_last_change
    before update on products
    for each row
    execute function update_date_time_last_change();

insert into products(id_product, name, count)
values (gen_random_uuid(), 'pineapple', 25),
       (gen_random_uuid(), 'cucumber', 3),
       (gen_random_uuid(), 'pen', 5),
       (gen_random_uuid(), 'keyboard', 9),
       (gen_random_uuid(), 'owl', 1);
