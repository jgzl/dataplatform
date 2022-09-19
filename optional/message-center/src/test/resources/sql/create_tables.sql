create table gateway_access_conf
(
    id          bigint auto_increment,
    api_key     varchar,
    api_secret  varchar,
    system      varchar,
    status      char default 1,
    remark      clob,
    create_time datetime,
    update_time datetime,
    deleted    char,
    constraint GATEWAY_ACCESS_CONF_PK
        primary key (id)
);

comment on table gateway_access_conf is '网关访问';

create index INDEX_GATEWAY_ACCESS_CONF_API_KEY
    on gateway_access_conf (api_key);