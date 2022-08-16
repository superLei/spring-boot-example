package com.example.service.qa.dao.userinfo;

import com.example.service.qa.model.userinfo.CrmClearUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
*  @author sulei
*/

@Mapper
public interface CrmClearUserMapper {
    int insertCrmClearUser(CrmClearUser object);

    int updateCrmClearUser(CrmClearUser object);

    List<CrmClearUser> queryCrmClearUser(CrmClearUser object);

}