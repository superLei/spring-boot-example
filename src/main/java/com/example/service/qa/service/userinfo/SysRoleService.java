package com.example.service.qa.service.userinfo;

import com.example.service.qa.dao.userinfo.SysRoleMapper;
import com.example.service.qa.model.userinfo.SysRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public List<SysRoleEntity> selectSysRoleByUserId(Integer id) {
        List<SysRoleEntity> sysRoleEntities = sysRoleMapper.selectSysRoleByUserId(id);
        return sysRoleEntities;
    }
}
