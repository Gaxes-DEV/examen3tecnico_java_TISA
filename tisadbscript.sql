create database tisadb;
use tisadb;

create table customer
(
    id               int auto_increment
        primary key,
    name             varchar(50) not null,
    last_Name        varchar(50) not null,
    housing_Address  varchar(75) not null,
    cashing_Address  varchar(75) not null,
    credit_Card      int         not null,
    expiration_Month int         not null,
    expiration_Year  int         not null
);

create table corder
(
    id          int auto_increment
        primary key,
    product     varchar(10) not null,
    quantity    int         not null,
    total_Price int         not null,
    image       varchar(20) not null,
    status      varchar(6)  not null,
    customer_id int         not null,
    constraint fk1_toc
        foreign key (customer_id) references customer (id)
);