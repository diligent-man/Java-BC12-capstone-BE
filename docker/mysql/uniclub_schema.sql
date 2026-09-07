DROP DATABASE IF EXISTS uniclub;
CREATE DATABASE uniclub;

USE uniclub;


CREATE TABLE color
(
    id   int auto_increment primary key,
    name varchar(20) NOT NULL,

    CONSTRAINT UQ_color_name UNIQUE (name)
);


CREATE TABLE size
(
    id   int auto_increment primary key,
    name varchar(20) NOT NULL,

    CONSTRAINT UQ_size_name UNIQUE (name)
);


CREATE TABLE variant
(
    sku         bigint auto_increment primary key,
    id_product  bigint    NOT NULL,
    id_color    int       NOT NULL,
    id_size     int       NOT NULL,
    images      text,
    quantity    int       NOT NULL,
    price       decimal(11, 2),
    create_date timestamp NOT NULL default now()
);


CREATE TABLE order_variant
(
    id_order    bigint,
    sku_variant bigint,
    quantity    int,
    price       decimal(11, 2),

    primary key (id_order, sku_variant)
);


CREATE TABLE orders
(
    id          bigint auto_increment primary key,
    total       decimal(11, 2) not null,
    note        text,
    id_payment  int            not null,
    id_user     bigint         not null,
    create_date timestamp default now()
);


CREATE TABLE billing_details
(
    id           bigint auto_increment primary key,
    first_name   varchar(50)  not null,
    last_name    varchar(50)  not null,
    company_name varchar(50)  not null,
    id_country   int          not null,
    id_order     bigint       not null,
    address      varchar(255) not null,
    town         varchar(50)  not null,
    state        varchar(50)  not null,
    zip_code     varchar(50)  not null,
    phone        varchar(12)  not null,
    email        varchar(255) not null,
    create_date  timestamp default now(),

    CONSTRAINT billing_details_id_order UNIQUE (id_order)
);


CREATE TABLE payment_method
(
    id          int auto_increment primary key,
    name        varchar(50) not null,
    description text,

    CONSTRAINT UQ_payment_method_name UNIQUE (name)
);


CREATE TABLE wishlist
(
    id_product bigint,
    id_user    bigint,

    primary key (id_product, id_user)
);


CREATE TABLE user
(
    id        bigint auto_increment primary key,
    email     varchar(50)  not null,
    password  varchar(255) not null,
    full_name varchar(255) not null,
    role_id   int          not null,
    status    varchar(20)  not null default 'ACTIVE',

    CONSTRAINT UQ_user_email UNIQUE (email)
);


CREATE TABLE role
(
    id   int auto_increment primary key,
    name varchar(100) not null,

    CONSTRAINT UQ_role_name UNIQUE (name)
);


CREATE TABLE review
(
    id          bigint auto_increment primary key,
    id_product  bigint,
    id_user     bigint,
    star        int,
    content     text,
    create_date timestamp default now(),
    images      text
);


CREATE TABLE comment
(
    id          bigint auto_increment primary key,
    id_user     bigint,
    id_post     int,
    id_reply    int,
    content     text,
    create_date timestamp default now()
);


CREATE TABLE post
(
    id          int auto_increment primary key,
    content     text,
    create_date timestamp default now()
);


CREATE TABLE product
(
    id          bigint auto_increment primary key,
    name        varchar(255)   NOT NULL,
    description text,
    information text,
    price       decimal(11, 2) NOT NULL,
    id_brand    int            NOT NULL,
    create_date timestamp default now()
);


CREATE TABLE brand
(
    id   int auto_increment primary key,
    name varchar(50) not null,

    CONSTRAINT UQ_brand_name UNIQUE (name)
);


CREATE TABLE tag
(
    id   int auto_increment primary key,
    name varchar(50) not null,

    CONSTRAINT UQ_tag_name UNIQUE (name)
);


CREATE TABLE category
(
    id   int auto_increment primary key,
    name varchar(50) not null,

    CONSTRAINT UQ_category_name UNIQUE (name)
);


CREATE TABLE product_tag
(
    id_tag     int,
    id_product bigint,

    primary key (id_tag, id_product)
);


CREATE TABLE product_category
(
    id_category int,
    id_product  bigint,

    primary key (id_category, id_product)
);


CREATE TABLE post_category
(
    id_category int,
    id_post     int,

    primary key (id_category, id_post)
);


CREATE TABLE IF NOT EXISTS country
(
    id         int(11)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    iso        char(2)     NOT NULL,
    name       varchar(80) NOT NULL,
    nice_name  varchar(80) NOT NULL,
    iso3       char(3)     DEFAULT NULL,
    num_code   smallint(6) DEFAULT NULL,
    phone_code int(5)      NOT NULL
) DEFAULT CHARSET = utf8mb4;


ALTER TABLE variant ADD CONSTRAINT FK_id_product_variant FOREIGN KEY (id_product) REFERENCES product (id);
ALTER TABLE variant ADD CONSTRAINT FK_id_color_variant FOREIGN KEY (id_color) REFERENCES color (id);
ALTER TABLE variant ADD CONSTRAINT FK_id_size_variant FOREIGN KEY (id_size) REFERENCES size (id);


ALTER TABLE order_variant ADD CONSTRAINT FK_id_order_order_variant FOREIGN KEY (id_order) REFERENCES orders (id);
ALTER TABLE order_variant ADD CONSTRAINT FK_sku_variant_order_variant FOREIGN KEY (sku_variant) REFERENCES variant (sku);


ALTER TABLE orders ADD CONSTRAINT FK_id_payment_order FOREIGN KEY (id_payment) REFERENCES payment_method (id);
ALTER TABLE orders ADD CONSTRAINT FK_id_user_order FOREIGN KEY (id_user) REFERENCES user (id);


ALTER TABLE billing_details ADD CONSTRAINT FK_id_country_billing_details FOREIGN KEY (id_country) REFERENCES country (id);
ALTER TABLE billing_details ADD CONSTRAINT FK_id_orders_billing_details FOREIGN KEY (id_order) REFERENCES orders (id);


ALTER TABLE wishlist ADD CONSTRAINT FK_id_product_wishlist FOREIGN KEY (id_product) REFERENCES product (id);
ALTER TABLE wishlist ADD CONSTRAINT FK_id_user_wishlist FOREIGN KEY (id_user) REFERENCES user (id);


ALTER TABLE review ADD CONSTRAINT FK_id_product_review FOREIGN KEY (id_product) REFERENCES product (id);
ALTER TABLE review ADD CONSTRAINT FK_id_user_review FOREIGN KEY (id_user) REFERENCES user (id);


ALTER TABLE comment ADD CONSTRAINT FK_id_user_comment FOREIGN KEY (id_user) REFERENCES user (id);
ALTER TABLE comment ADD CONSTRAINT FK_id_post_comment FOREIGN KEY (id_post) REFERENCES post (id);
ALTER TABLE comment ADD CONSTRAINT FK_id_reply_comment FOREIGN KEY (id_reply) REFERENCES post (id);


ALTER TABLE product ADD CONSTRAINT FK_id_brand_product FOREIGN KEY (id_brand) REFERENCES brand (id);


ALTER TABLE post_category ADD CONSTRAINT FK_id_post_post_category FOREIGN KEY (id_post) REFERENCES post (id);
ALTER TABLE post_category ADD CONSTRAINT FK_id_category_category FOREIGN KEY (id_category) REFERENCES category (id);


ALTER TABLE product_category ADD CONSTRAINT FK_id_product_product_category FOREIGN KEY (id_product) REFERENCES product (id);
ALTER TABLE product_category ADD CONSTRAINT FK_id_category_product_category FOREIGN KEY (id_category) REFERENCES category (id);


ALTER TABLE product_tag ADD CONSTRAINT FK_id_product_product_tag FOREIGN KEY (id_product) REFERENCES product (id);
ALTER TABLE product_tag ADD CONSTRAINT FK_id_category_product_tag FOREIGN KEY (id_tag) REFERENCES tag (id);


ALTER TABLE user ADD CONSTRAINT FK_role_id_user_role FOREIGN KEY (role_id) REFERENCES role (id);
