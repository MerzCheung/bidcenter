drop table if exists sys_user;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(11) NOT NULL COMMENT '账号',
  `phone_num` varchar(11) DEFAULT NULL COMMENT '手机号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `secret_key` varchar(50) NOT NULL COMMENT '秘钥',
  `nick_name` varchar(32) NOT NULL COMMENT '昵称',
  `portrait_uri` varchar(150) NOT NULL COMMENT '头像',
  `balance` int(10) DEFAULT NULL COMMENT '余额',
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
