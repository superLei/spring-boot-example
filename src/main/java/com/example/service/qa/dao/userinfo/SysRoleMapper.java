package com.example.service.qa.dao.userinfo;

import com.example.service.qa.model.userinfo.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    /**
     * 通过用户ID查询角色集合
     *
     * */
    List<SysRoleEntity> selectSysRoleByUserId(@Param("userId") Integer userId);
}
