CREATE TABLE IF NOT EXISTS statutes
(
    id   serial not null,
    name text   not null,
    constraint statutes_pk primary key (id)
);

CREATE TABLE IF NOT EXISTS payments
(
    id   serial not null,
    name text   not null,
    constraint payments_pk primary key (id)
);

CREATE TABLE IF NOT EXISTS customers
(
    id                serial not null,
    first_name        text   not null,
    last_name         text   not null,
    billing_address   text   not null,
    billing_city      text   not null,
    billing_country   text   not null,
    billing_zip_code  text   not null,
    shipping_address  text   not null,
    shipping_city     text   not null,
    shipping_country  text   not null,
    shipping_zip_code text   not null,
    phone             text   not null,
    email             text   not null,
    password          text   not null,
    constraint customers_pk primary key (id)
);

CREATE TABLE IF NOT EXISTS departments
(
    id   serial not null,
    name text   not null,
    constraint departments_pk primary key (id)
);

CREATE TABLE IF NOT EXISTS categories
(
    id            serial  not null,
    name          text    not null,
    department_id integer not null,
    constraint categories_pk primary key (id),
    constraint fk_categories_department_id foreign key (department_id) references departments
);

CREATE TABLE IF NOT EXISTS suppliers
(
    id   serial not null,
    name text   not null,
    constraint suppliers_pk primary key (id)
);

CREATE TABLE IF NOT EXISTS products
(
    id          serial  not null,
    name        text    not null,
    description text    not null,
    price       decimal not null,
    currency    text    not null,
    category_id integer not null,
    supplier_id integer not null,
    constraint products_pk primary key (id),
    constraint fk_products_category_id foreign key (category_id) references categories,
    constraint fk_products_supplier_id foreign key (supplier_id) references suppliers
);

CREATE TABLE IF NOT EXISTS orders
(
    id          serial    not null,
    data        timestamp not null,
    customer_id integer   not null,
    payment_id  integer,
    total_price decimal,
    status_id   integer   not null default 1,
    constraint orders_pk primary key (id),
    constraint fk_orders_customer_id foreign key (customer_id) references customers,
    constraint fk_orders_payment_id foreign key (payment_id) references payments,
    constraint fk_orders_status_id foreign key (status_id) references statutes
);

CREATE TABLE IF NOT EXISTS line_items
(
    id         serial  not null,
    product_id integer not null,
    quantity   integer not null,
    orders_id  integer not null,
    constraint line_items_pk primary key (id),
    constraint fk_line_items_product_id foreign key (product_id) references products,
    constraint fk_line_items_orders_id foreign key (orders_id) references orders
);



