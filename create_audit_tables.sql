drop table if exists audit;
drop table if exists book_audit;
drop table if exists client_audit;
drop table if exists purchase_audit;


create table audit(
    id      serial      primary key,
    "table" varchar(50) not null,
    entryid integer     not null,
    time    date        not null
);


create table book_audit(
    id              serial      primary key,
    title_old       varchar(50),
    title_new       varchar(50),
    author_name_old varchar(50),
    author_name_new varchar(50),
    genre_old       varchar(50),
    genre_new       varchar(50),
    price_old       double precision,
    price_new       double precision
);


create table client_audit(
    id        serial        primary key,
    name_old  varchar(50),
    nem_new   varchar(50),
    city_old  varchar(50),
    city_new  varchar(50),
    email_old varchar(50),
    email_new varchar(50)
);

create table purchase_audit(
    id            serial    primary key,
    book_id_old   integer,
    book_id_new   integer,
    client_id_old integer,
    client_id_new integer,
    amount_old    integer   not null,
    amount_new    integer   not null
);
