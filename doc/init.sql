drop table if exists sys_user;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(11) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

drop table if exists sys_roles;
create table sys_roles (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  role varchar(50) NOT NULL comment '角色名称',
  description varchar(100) DEFAULT NULL comment '角色描述',
  pid int(11) DEFAULT NULL comment '父节点',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色';

drop table if exists sys_permissions;
create table sys_permissions (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  permission varchar(100) NOT NULL comment '权限编号',
  description varchar(100) DEFAULT NULL comment '权限描述',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限';

drop table if exists sys_users_roles;
create table sys_users_roles (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-角色';

drop table if exists sys_roles_permissions;
create table sys_roles_permissions (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  role_id int(11) NOT NULL comment '角色编号',
  permission_id int(11) NOT NULL comment '权限编号',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色-权限';


drop table if exists sys_supplier;
create table sys_supplier (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  user_id int(11) NOT NULL comment '用户主键',
  company_name varchar (100) NOT NULL comment '公司名称',
  principal varchar (100) DEFAULT NULL comment '负责人',
  phone_number varchar (20) DEFAULT NULL comment '联系电话',
  address varchar (300) DEFAULT NULL comment '联系地址',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商';

drop table if exists sys_buyer;
create table sys_buyer (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  user_id int(11) NOT NULL comment '用户主键',
  username varchar (100) DEFAULT NULL comment '姓名',
  phone_number varchar (20) DEFAULT NULL comment '联系电话',
  department varchar (300) DEFAULT NULL comment '任职部门',
  position varchar (300) DEFAULT NULL comment '职位',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购员';

drop table if exists sys_bid;
create table sys_bid (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  number varchar (100) DEFAULT NULL comment '标书编号',
  name varchar (100) DEFAULT NULL comment '标名',
  time datetime DEFAULT NULL comment '开标时间',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标书';

drop table if exists sys_bid_document;
create table sys_bid_document (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  bid_id int(11) NOT NULL comment '标书主键',
  supplier_id int(11) NOT NULL comment '供应商主键',
  file_name varchar (100) NOT NULL comment '文件名',
  file_url varchar (300) NOT NULL comment '文件地址',
  time datetime NOT NULL comment '投标时间',
  `is_valid` int(1) DEFAULT 1 COMMENT '是否有效 1：有效 0：无效',
  `created_time` datetime NOT NULL,
  `modified_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标书管理';
