create database expance;
use expance;
create table management(
purched_id int auto_increment primary key,
price int,
item varchar(250),
purched_date timestamp default current_timestamp
);