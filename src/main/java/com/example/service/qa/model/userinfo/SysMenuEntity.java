package com.example.service.qa.model.userinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 权限实体
 * */
@Data
public class SysMenuEntity implements Serializable {

    /**
     * 权限ID
     */
    private Long menuId;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限标识
     */
    private String perms;
}
