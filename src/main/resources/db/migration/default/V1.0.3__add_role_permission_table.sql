


alter table users add column role varchar(255) not null default 'USER';


create table if not exists permissions (
    id bigint primary key auto_increment,
    permission varchar(255) not null
);

create table if not exists user_permissions (
    id bigint primary key auto_increment,
    user_id bigint not null,
    permission_id bigint not null,
    foreign key (user_id) references users(id),
    foreign key (permission_id) references permissions(id)
);