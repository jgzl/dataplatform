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

CREATE TABLE sys_error_code
(
    id               bigint  NOT NULL AUTO_INCREMENT,
    type             int     NOT NULL DEFAULT 0,
    application_name varchar NOT NULL DEFAULT '',
    code             int     NOT NULL DEFAULT 0,
    message          varchar NOT NULL DEFAULT '',
    memo             varchar          DEFAULT '',
    creator          varchar          DEFAULT '',
    create_time      datetime,
    updater          varchar          DEFAULT '',
    update_time      datetime,
    deleted          bit              DEFAULT 0,
    constraint SYS_ERROR_CODE_PK
        primary key (id)
);
