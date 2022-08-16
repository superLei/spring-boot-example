package com.example.service.qa.dao.userinfo;

import com.example.service.qa.model.userinfo.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMenuMapper {

    int insertSysRoleMenu(SysRoleMenuEntity sysRoleMenuEntity);
}
