package com.shenmao.archive_management_system.service;

import com.shenmao.archive_management_system.pojo.History;
import com.shenmao.archive_management_system.pojo.PageBean;

public interface HistoryService {
    void borrow(Integer archiveId, String archiveUrl);

    PageBean<History> list(Integer pageNum, Integer pageSize, String status);


    void approval(History history);

    void delete(Integer recordId);

    History getReason(Integer archiveId);
}
