package com.example.service.qa.service.userinfo;


import com.example.service.qa.dao.userinfo.CrmClearUserMapper;
import com.example.service.qa.model.userinfo.CrmClearUser;
import com.example.service.qa.utils.CommonUtils;
import com.example.service.qa.utils.SHA256Util;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private static final String BASE = "OU=hualala";

    private static final String FILETR_FORMAT = "(sAMAccountname={0})";

    //query超时时间设置
    private static final int TIME_LIMIT = 10000;

    @Autowired
    CrmClearUserMapper crmClearUserMapper;


    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * 判断用户是否存在
     */
    public boolean userExist(CrmClearUser clearUser) {
        // 查询数据库
        List<CrmClearUser> crmClearUsers = crmClearUserMapper.queryCrmClearUser(clearUser);
        return !CommonUtils.isEmpty(crmClearUsers);
    }

    /**
     * 新增用户
     */
    public void save(CrmClearUser user) {
        crmClearUserMapper.insertCrmClearUser(user);
    }

    /**
     * 判断用户是否存在
     */
    public CrmClearUser queryClearUser(CrmClearUser clearUser) {
        // 查询数据库
        List<CrmClearUser> crmClearUsers = crmClearUserMapper.queryCrmClearUser(clearUser);
        return crmClearUsers.size() > 0 ? crmClearUsers.get(0) : null;
    }

    /**
     * 更新密码
     */
    public void updatePassword(CrmClearUser user) {
        String password = user.getPassword();
        String pwdSha = SHA256Util.sha256(password);

    }

    /***
     * 验证用户名+密码
     * @param ldapName
     * @param pwd
     * @param userInDb
     * @return
     */
    public boolean verifyAndUpdateInfoByLdap(String ldapName, String pwd, CrmClearUser userInDb) {
        boolean result = false;
        return result;
    }

    public boolean verifyAndUpdateInfoByLdap2(String ldapName, String pwd, CrmClearUser userInDb) {
        return true;
    }

    @Data
    static class FillWithUserInfo {

        private Integer id;
        private CrmClearUser originUser;

        FillWithUserInfo(CrmClearUser user) {
            this.id = user.getId();
            this.originUser = user;
        }

        private boolean isLackOfUserInfo() {
            if (CommonUtils.isNull(this.originUser.getRealName())) {
                return true;
            }
            return CommonUtils.isNull(this.originUser.getEmail());
        }

        public CrmClearUser buildEntity(CrmClearUser user) {
            final CrmClearUser crmClearUser = new CrmClearUser();
            crmClearUser.setId(this.id);
            boolean changed = false;
            if (needChange(originUser.getEmail(), user.getEmail())) {
                crmClearUser.setEmail(user.getEmail());
                this.originUser.setEmail(user.getEmail());
                changed = true;
            }
            if (needChange(originUser.getRealName(), user.getRealName())) {
                crmClearUser.setRealName(user.getRealName());
                this.originUser.setRealName(user.getRealName());
                changed = true;
            }

            return changed ? crmClearUser : null;
        }

        private boolean needChange(String source, String target) {
            return CommonUtils.isBlank(source) && CommonUtils.notNull(target);
        }
    }
}
