package com.shenmao.archive_management_system.pojo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Param {
    private Integer pageNum;
    private Integer pageSize;
    private String archiveName;
    private String archiveCategory;
    private LocalDate archiveDate;
}
