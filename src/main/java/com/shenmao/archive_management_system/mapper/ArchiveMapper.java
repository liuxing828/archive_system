package com.shenmao.archive_management_system.mapper;

import com.shenmao.archive_management_system.pojo.Archive;
import com.shenmao.archive_management_system.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;


@Mapper
public interface ArchiveMapper {
    @Insert("insert into archive (archiveName, archiveDate, archiveCategory, archiveUrl, createUser) values " +
            "(#{archiveName}, #{archiveDate}, #{archiveCategory}, #{archiveUrl}, #{createUser})")
    void add(Archive archive);

    @Select("select * from archive where status = #{status} and createUser = #{createUser}")
    List<Archive> itself(String createUser, String status);

    @Delete("delete from archive where archiveId = #{archiveId} and createUser = #{createUser}")
    void delete(Integer archiveId, String createUser);

    @Delete("delete from archive where archiveId = #{archiveId}")
    void deleteA(Integer archiveId);

    List<Archive> listA(String archiveName, String archiveCategory, LocalDate archiveDate, String status);

    List<Archive> list(String archiveName, String archiveCategory, LocalDate archiveDate, String status, Integer secrecy);


    @Select("select count(*) from archive where year(archiveDate) = #{archiveDate} GROUP BY month(archiveDate)")
    List<Integer> countYear(String archiveDate);
    @Select("select count(*) from archive where year(archiveDate) = #{year} and month(archiveDate) = #{month} GROUP BY day(archiveDate)")
    List<Integer> countMonth(String year, String month);

    @Select("select archiveCategory from archive group by archiveCategory")
    List<String> countCategory1();
    @Select("select count(*) from archive group by archiveCategory")
    List<Integer> countCategory2();
//    @Select("select archiveCategory, count(*) from archive group by archiveCategory")
//    List<Category> countCategory();

    @Select("select count(*) from archive where createUser = #{createUser}")
    Integer countName(String createUser);

    @Update("update archive set status = #{status}, secrecy = #{secrecy} where archiveId = #{archiveId}")
    void updateStatus(Integer archiveId, String status, Integer secrecy);

    @Update("update archive set secrecy = #{secrecy} where archiveId = #{archiveId}")
    void updateSecrecy(Integer archiveId, Integer secrecy);
}
