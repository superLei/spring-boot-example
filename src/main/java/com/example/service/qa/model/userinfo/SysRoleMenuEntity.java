package com.example.service.qa.model.userinfo;

import lombok.Data;

/**
 * 角色与权限关系实体
 *
 * */
@Data
public class SysRoleMenuEntity {
    /**
     * ID
     */
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long menuId;
}
