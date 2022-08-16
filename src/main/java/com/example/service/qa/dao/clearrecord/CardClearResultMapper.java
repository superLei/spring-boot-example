package com.example.service.qa.dao.clearrecord;

import com.example.service.qa.model.BaseContext;
import com.example.service.qa.model.clearrecord.ClearAllDataRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author maxsu
 */
@Mapper
public interface CardClearResultMapper {

    /**
     * 会员清除记录表
     */
    int insertClearRecord(ClearAllDataRecord record);

    /**
     * 查询清除记录
     */
    List<ClearAllDataRecord> queryClearRecord(@Param("groupID") Long groupID);

    /**
     * 分页查询清除记录
     */
    List<ClearAllDataRecord> queryClearRecordByPage(@Param("pageOffset") Integer pageOffset, @Param("pageSize") Integer pageSize ,@Param("groupID") Long groupID);

    /**
     * 查询总数
     * */
    int queryRecordCount(@Param("groupID") Long groupID);

    /**
     * 更新清理记录
     *
     * @param itemId
     * @param result
     * @param actionStamp
     * @return
     */
    int updateClearRecordStatus(@Param("itemId") Long itemId, @Param("result") Integer result, @Param("actionStamp") Timestamp actionStamp);

    int updateClearRecord(@Param("itemId") Long itemId, @Param("actionStamp") Timestamp actionStamp, BaseContext context);

    /**
     * 更新操作记录结果
     *
     * @return
     */
    int updateRecordResult(ClearAllDataRecord record);
}
