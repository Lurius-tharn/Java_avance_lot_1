create table user
(
    username varchar(100) not null
        primary key,
    password varchar(45)  null,
    role     int          null,
    mail     varchar(200) null
);

