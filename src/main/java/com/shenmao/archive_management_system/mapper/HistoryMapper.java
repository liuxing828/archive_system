package com.shenmao.archive_management_system.mapper;

import com.shenmao.archive_management_system.pojo.History;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HistoryMapper {
    @Insert("insert into history (archiveId,userId,borrowDate,returnDate,archiveUrl) " +
            "values (#{archiveId},#{userId},#{borrowDate},#{returnDate},#{archiveUrl})")
    void borrow(History history);

    @Select("select * from history where status = #{status}")
    List<History> listA(String status);

    @Select("select * from history where status = #{status} and userId = #{userId}")
    List<History> list(Integer userId, String status);


    @Update("update history set status = #{status}, reason = #{reason}, admin = #{admin} where recordId = #{recordId}")
    void approval(Integer recordId, String status, String reason, String admin);

    @Delete("delete from history where recordId = #{recordId}")
    void deleteA(Integer recordId);

    @Delete("delete from history where recordId = #{recordId} and userId = #{userId}")
    void delete(Integer recordId, Integer userId);

    @Select("select * from history where archiveId = #{archiveId} and userId = #{userId}")
    History getReason(Integer archiveId, Integer userId);
}
