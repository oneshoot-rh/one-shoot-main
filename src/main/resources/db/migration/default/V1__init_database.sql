drop table if exists  tenants;
drop table if exists  users;

create table tenants (
    id bigint primary key auto_increment,
    organization_name varchar(255),
    requestor_role varchar(255),
    requestor_professional_email varchar(255)
);


create table users (
   id bigint primary key auto_increment,
   username varchar(255),
   name varchar(255),
   password varchar(255)
);


