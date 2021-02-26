insert into sys_user (id, account, password, created_time) values (1, 'dyg_admin', 'e10adc3949ba59abbe56e057f20f883e', NOW());
insert into sys_roles (id, role, description, created_time) values (1, 'ADMIN', '管理员', NOW());
insert into sys_roles (id, role, description, created_time) values (2, 'SUPPLIER', '供应商', NOW());
insert into sys_roles (id, role, description, created_time) values (3, 'BUYER', '采购员', NOW());
insert into sys_roles (id, role, description, created_time) values (4, 'AUDITOR', '审计员', NOW());
insert into sys_roles (id, role, description, created_time) values (5, 'BUYER_ADMIN', '采购管理员', NOW());
insert into sys_roles (id, role, description, created_time) values (6, 'BUYER_CHIEF', '采购科长', NOW());
insert into sys_users_roles (user_id, role_id, created_time) values (1, 1, NOW());


insert into sys_dict (type, code, name, created_time) values ('company', 'SYGS', '实业公司', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'YYGF', '药业股份', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'DCXC', '冬虫夏草', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'CJYY', '长江药业', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'HCB', '化成箔', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'HLFD', '火力发电', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'YDS', '胰岛素', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'CXZY', '创新制药', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'HCFC', '合成分厂', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'GCGS', '工程公司', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'JXC', '机械厂', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'MMC', '面膜厂', NOW());
insert into sys_dict (type, code, name, created_time) values ('company', 'DFD', '大饭店', NOW());

insert into sys_dict (type, code, name, created_time) values ('role', 'AUDITOR', '审计员', NOW());
insert into sys_dict (type, code, name, created_time) values ('role', 'BUYER_ADMIN', '采购管理员', NOW());
insert into sys_dict (type, code, name, created_time) values ('role', 'BUYER_CHIEF', '采购科长', NOW());
insert into sys_dict (type, code, name, created_time) values ('role', 'BUYER', '采购员', NOW());
