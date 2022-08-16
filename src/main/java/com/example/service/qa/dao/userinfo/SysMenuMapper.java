package com.example.service.qa.dao.userinfo;

import com.example.service.qa.model.userinfo.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    /**
     * 根据角色查询用户权限
     *
     * */
    List<SysMenuEntity> selectSysMenuByRoleId(@Param("roleId") Long roleId);
}
