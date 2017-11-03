-- 菜单SQL
INSERT INTO `sys_menu` (`id`,`parent_id`, `name`, `url`, `permission`, `type`, `icon`, `sort`)
    VALUES ('${id}','1', '${comments}', '${module}/${pathName}.html', '${pathName}:list', '1', 'fa fa-file-code-o', '6');

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`id`,`parent_id`, `name`, `url`, `permission`, `type`, `icon`, `sort`)
    SELECT '${rid}','${id}', '查看', null, '${pathName}:info', '2', null, '6';
INSERT INTO `sys_menu` (`id`,`parent_id`, `name`, `url`, `permission`, `type`, `icon`, `sort`)
    SELECT '${cid}','${id}', '新增', null, '${pathName}:save', '2', null, '6';
INSERT INTO `sys_menu` (`id`,`parent_id`, `name`, `url`, `permission`, `type`, `icon`, `sort`)
    SELECT '${uid}','${id}', '修改', null, '${pathName}:update', '2', null, '6';
INSERT INTO `sys_menu` (`id`,`parent_id`, `name`, `url`, `permission`, `type`, `icon`, `sort`)
    SELECT '${did}','${id}', '删除', null, '${pathName}:delete', '2', null, '6';
