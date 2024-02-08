drop table if exists uploads;

create table uploads (
    id bigint primary key auto_increment,
    upload_directory varchar(255),
    upload_date_time timestamp,
    uploaded_files int,
    uploaded_by bigint
);
