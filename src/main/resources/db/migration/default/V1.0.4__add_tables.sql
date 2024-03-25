


create table if not exists free_trial_tenant (
    id bigint primary key auto_increment,
    tenant_id bigint not null,
    organizationName varchar(255) not null,
    start_date timestamp  null,
    end_date timestamp  null,
    constraint fk_tenant_id foreign key (tenant_id) references tenants(id)
);


create table if not exists subscription_payment (
    id bigint primary key auto_increment,
    reference varchar(255) not null,
    tenant_id bigint not null,
    organizationName varchar(255) not null,
    payment_delay timestamp not null,
    amount double precision not null,
    is_paid boolean not null default false,
    created_at timestamp not null default current_timestamp,
    constraint fk_tenant_payment foreign key (tenant_id) references tenants(id)
);