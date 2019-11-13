create table if not exists 
    country (name varchar(150),capital varchar(150),population BIGINT);
create table if not exists 
    currency (code varchar(45), name varchar(150), symbol varchar(5));
create table if not exists 
    border (name varchar(150));
create table if not exists 
    language (iso639_1 varchar(5), iso639_2 varchar(5), name varchar(150), nativeName varchar(150));