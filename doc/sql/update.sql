delete from sys_user where del_flag = 1;
delete from sys_role where del_flag = 1;
delete from sys_menu where del_flag = 1;
delete from sys_dept where del_flag = 1;
delete from gateway_access_conf where del_flag = 1;
delete from gateway_route_conf where del_flag = 1;
delete from sys_error_code where deleted = 1;

alter table sys_error_code add version bigint default 1 not null;
-- comment on column sys_error_code.version is '乐观锁版本号';

alter table sys_dept_relation add create_time datetime default current_timestamp();
-- comment on column sys_dept_relation.create_time is '创建时间';
alter table sys_dept_relation add update_time datetime default current_timestamp();
-- comment on column sys_dept_relation.update_time is '更新时间';

alter table sys_user_role add create_time datetime default current_timestamp();
-- comment on column sys_user_role.create_time is '创建时间';
alter table sys_user_role add update_time datetime default current_timestamp();
-- comment on column sys_user_role.update_time is '更新时间';

alter table sys_role_menu add create_time datetime default current_timestamp();
-- comment on column sys_role_menu.create_time is '创建时间';
alter table sys_role_menu add update_time datetime default current_timestamp();
-- comment on column sys_role_menu.update_time is '更新时间';

alter table sys_user drop column del_flag;
alter table sys_role drop column del_flag;
alter table sys_menu drop column del_flag;
alter table sys_dept drop column del_flag;
alter table gateway_route_conf drop column del_flag;
alter table gateway_access_conf drop column del_flag;

alter table sys_user add creator varchar(64) default 1 not null;
alter table sys_user add updater varchar(64) default 1 not null;
alter table sys_user add deleted char(1) default 0 not null;
alter table sys_user add version bigint default 1 not null;
-- comment on column sys_user.creator is '创建者';
-- comment on column sys_user.updater is '修改者';
-- comment on column sys_user.deleted is '删除标记';
-- comment on column sys_user.version is '乐观锁版本号';

alter table sys_role add creator varchar(64) default 1 not null;
alter table sys_role add updater varchar(64) default 1 not null;
alter table sys_role add deleted char(1) default 0 not null;
alter table sys_role add version bigint default 1 not null;
-- comment on column sys_role.creator is '创建者';
-- comment on column sys_role.updater is '修改者';
-- comment on column sys_role.deleted is '删除标记';
-- comment on column sys_role.version is '乐观锁版本号';

alter table sys_menu add creator varchar(64) default 1 not null;
alter table sys_menu add updater varchar(64) default 1 not null;
alter table sys_menu add deleted char(1) default 0 not null;
alter table sys_menu add version bigint default 1 not null;
-- comment on column sys_menu.creator is '创建者';
-- comment on column sys_menu.updater is '修改者';
-- comment on column sys_menu.deleted is '删除标记';
-- comment on column sys_menu.version is '乐观锁版本号';

alter table sys_dept add creator varchar(64) default 1 not null;
alter table sys_dept add updater varchar(64) default 1 not null;
alter table sys_dept add deleted char(1) default 0 not null;
alter table sys_dept add version bigint default 1 not null;
-- comment on column sys_dept.creator is '创建者';
-- comment on column sys_dept.updater is '修改者';
-- comment on column sys_dept.deleted is '删除标记';
-- comment on column sys_dept.version is '乐观锁版本号';

alter table gateway_route_conf add creator varchar(64) default 1 not null;
alter table gateway_route_conf add updater varchar(64) default 1 not null;
alter table gateway_route_conf add deleted char(1) default 0 not null;
alter table gateway_route_conf add version bigint default 1 not null;
-- comment on column gateway_route_conf.creator is '创建者';
-- comment on column gateway_route_conf.updater is '修改者';
-- comment on column gateway_route_conf.deleted is '删除标记';
-- comment on column gateway_route_conf.version is '乐观锁版本号';

alter table gateway_access_conf add creator varchar(64) default 1 not null;
alter table gateway_access_conf add updater varchar(64) default 1 not null;
alter table gateway_access_conf add deleted char(1) default 0 not null;
alter table gateway_access_conf add version bigint default 1 not null;
-- comment on column gateway_access_conf.creator is '创建者';
-- comment on column gateway_access_conf.updater is '修改者';
-- comment on column gateway_access_conf.deleted is '删除标记';
-- comment on column gateway_access_conf.version is '乐观锁版本号';

alter table sys_dept_relation add creator varchar(64) default 1 not null;
alter table sys_dept_relation add updater varchar(64) default 1 not null;
alter table sys_dept_relation add deleted char(1) default 0 not null;
alter table sys_dept_relation add version bigint default 1 not null;
-- comment on column sys_dept_relation.creator is '创建者';
-- comment on column sys_dept_relation.updater is '修改者';
-- comment on column sys_dept_relation.deleted is '删除标记';
-- comment on column sys_dept_relation.version is '乐观锁版本号';

alter table sys_role_menu add creator varchar(64) default 1 not null;
alter table sys_role_menu add updater varchar(64) default 1 not null;
alter table sys_role_menu add deleted char(1) default 0 not null;
alter table sys_role_menu add version bigint default 1 not null;
-- comment on column sys_role_menu.creator is '创建者';
-- comment on column sys_role_menu.updater is '修改者';
-- comment on column sys_role_menu.deleted is '删除标记';
-- comment on column sys_role_menu.version is '乐观锁版本号';

alter table sys_user_role add creator varchar(64) default 1 not null;
alter table sys_user_role add updater varchar(64) default 1 not null;
alter table sys_user_role add deleted char(1) default 0 not null;
alter table sys_user_role add version bigint default 1 not null;
-- comment on column sys_user_role.creator is '创建者';
-- comment on column sys_user_role.updater is '修改者';
-- comment on column sys_user_role.deleted is '删除标记';
-- comment on column sys_user_role.version is '乐观锁版本号';


insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('11200', '错误码管理', null, '/system/errorCode', '1000', 'el-icon-user-filled', 5, '0', '0', current_timestamp(), current_timestamp(), 'system/errorCode', null, '1', '1', '0', 1);
insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('11201', '错误码新增', 'sys_error_code_add', null, '11200', null, 1, '0', '1', current_timestamp(), current_timestamp(), null, null, '1', '1', '0', 1);
insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('11202', '错误码修改', 'sys_error_code_edit', null, '11200', null, 1, '0', '1', current_timestamp(), current_timestamp(), null, null, '1', '1', '0', 1);
insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('11203', '错误码删除', 'sys_error_code_del', null, '11200', null, 1, '0', '1', current_timestamp(),current_timestamp(), null, null, '1', '1', '0', 1);


insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '11200', '1', '1', '1', 1, current_timestamp(), current_timestamp());
insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '11201', '1', '1', '1', 1, current_timestamp(), current_timestamp());
insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '11202', '1', '1', '1', 1, current_timestamp(), current_timestamp());
insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '11203', '1', '1', '1', 1, current_timestamp(), current_timestamp());

insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('2400', '元数据管理', null, '/gateway/metadata', '2000', 'el-icon-user-filled', 5, '0', '0', current_timestamp(), current_timestamp(), 'gateway/metadata', null, '1', '1', '0', 1);
insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('2401', '元数据新增', 'gateway_metadata_add', null, '2400', null, 1, '0', '1', current_timestamp(), current_timestamp(), null, null, '1', '1', '0', 1);
insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('2402', '元数据修改', 'gateway_metadata_edit', null, '2400', null, 1, '0', '1', current_timestamp(), current_timestamp(), null, null, '1', '1', '0', 1);
insert into sys_menu
(menu_id, name, permission, path, parent_id, icon, sort, keep_alive, type, create_time, update_time, component, tag, creator, updater, deleted, version)
values('2403', '元数据删除', 'gateway_metadata_del', null, '2400', null, 1, '0', '1', current_timestamp(),current_timestamp(), null, null, '1', '1', '0', 1);


insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '2400', '1', '1', '1', 1, current_timestamp(), current_timestamp());
insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '2401', '1', '1', '1', 1, current_timestamp(), current_timestamp());
insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '2402', '1', '1', '1', 1, current_timestamp(), current_timestamp());
insert into sys_role_menu (role_id, menu_id, creator, updater, deleted, version, create_time, update_time)
values('1', '2403', '1', '1', '1', 1, current_timestamp(), current_timestamp());

ALTER TABLE sys_error_code MODIFY COLUMN version bigint(20) DEFAULT 1 NOT NULL;
ALTER TABLE sys_error_code MODIFY COLUMN deleted char(1) DEFAULT 0 NOT NULL;
ALTER TABLE sys_error_code MODIFY COLUMN updater varchar(64) DEFAULT 1 NOT NULL;
ALTER TABLE sys_error_code MODIFY COLUMN creator varchar(64) DEFAULT 1 NOT NULL;
ALTER TABLE sys_error_code MODIFY COLUMN update_time datetime DEFAULT current_timestamp() NOT NULL;
ALTER TABLE sys_error_code MODIFY COLUMN create_time datetime DEFAULT current_timestamp() NOT NULL;

ALTER TABLE sys_user_role ADD id bigint NOT NULL COMMENT 'id';
update sys_user_role set id = concat(user_id,role_id);
ALTER TABLE sys_user_role ADD CONSTRAINT sys_user_role_PK PRIMARY KEY (id);
ALTER TABLE sys_role_menu ADD id bigint NOT NULL COMMENT 'id';
update sys_role_menu set id = concat(role_id,menu_id);
ALTER TABLE sys_role_menu ADD CONSTRAINT sys_role_menu_PK PRIMARY KEY (id);
ALTER TABLE sys_dept_relation ADD id bigint NOT NULL COMMENT 'id';
update sys_dept_relation set id = concat(ancestor,descendant);
ALTER TABLE sys_dept_relation ADD CONSTRAINT sys_dept_relation_PK PRIMARY KEY (id);

ALTER TABLE gateway_access_conf  MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE gateway_route_conf MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_dept MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_dept_relation MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_error_code MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_menu MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_role MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_role_menu MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_user MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_user_role MODIFY COLUMN deleted bigint(20) DEFAULT 0 NOT NULL;

ALTER TABLE sys_user CHANGE user_id id bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_role CHANGE role_id id bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_menu CHANGE menu_id id bigint(20) DEFAULT 0 NOT NULL;
ALTER TABLE sys_dept CHANGE dept_id id bigint(20) DEFAULT 0 NOT NULL;

DELETE from gateway_route_conf where id is null;
ALTER TABLE sys_error_code  ADD CONSTRAINT sys_error_code_PK PRIMARY KEY (id);
ALTER TABLE gateway_access_conf ADD CONSTRAINT gateway_access_conf_PK PRIMARY KEY (id);
ALTER TABLE gateway_route_conf ADD CONSTRAINT gateway_route_conf_PK PRIMARY KEY (id);
ALTER TABLE gateway_log ADD CONSTRAINT gateway_log_PK PRIMARY KEY (id);
