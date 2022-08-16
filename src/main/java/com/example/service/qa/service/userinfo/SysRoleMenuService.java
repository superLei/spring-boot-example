package com.example.service.qa.service.userinfo;

import com.example.service.qa.dao.userinfo.SysRoleMenuMapper;
import com.example.service.qa.model.userinfo.SysRoleMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    public void save(SysRoleMenuEntity sysRoleMenuEntity) {
        sysRoleMenuMapper.insertSysRoleMenu(sysRoleMenuEntity);
    }
}
