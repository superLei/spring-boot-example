package com.example.service.qa.service.userinfo;

import com.example.service.qa.dao.userinfo.SysMenuMapper;
import com.example.service.qa.model.userinfo.SysMenuEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    public List<SysMenuEntity> selectSysMenuByRoleId(Long id) {
        List<SysMenuEntity> sysMenuEntities = sysMenuMapper.selectSysMenuByRoleId(id);
        return sysMenuEntities;

    }
}
