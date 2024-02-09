drop table if exists subcriptions;

create table subcriptions(
    id bigint primary key auto_increment,
    subscription_id varchar(255),
    subscription_type varchar(10),
    organization_name varchar(255),
    tenant_id bigint,
    start_date timestamp default current_timestamp,
    end_date timestamp default current_timestamp,
    foreign key (tenant_id) references tenants(id)
);