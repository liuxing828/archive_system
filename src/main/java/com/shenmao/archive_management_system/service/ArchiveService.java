package com.shenmao.archive_management_system.service;

import com.shenmao.archive_management_system.pojo.Archive;
import com.shenmao.archive_management_system.pojo.Category;
import com.shenmao.archive_management_system.pojo.PageBean;
import com.shenmao.archive_management_system.pojo.Param;


import java.time.LocalDate;
import java.util.List;

public interface ArchiveService {
    void add(Archive archive);

    void delete(Integer archiveId);

    PageBean<Archive> list(Integer pageNum, Integer pageSize, String archiveName, String archiveCategory, LocalDate archiveDate, String status);

    List<Archive> itself(String status);

    List<Integer> countYear(String archiveDate);

    List<Integer> countMonth(String year, String month);

    List<Category> countCategory();

    Integer countName(String username);

    void updateStatus(Integer archiveId, String status, Integer secrecy,Integer userId);

    void updateSecrecy(Integer archiveId, Integer secrecy);
}
