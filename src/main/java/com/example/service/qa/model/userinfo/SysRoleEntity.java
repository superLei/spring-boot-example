package com.example.service.qa.model.userinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 角色实体
 * @author su
 * */
@Data
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
}
