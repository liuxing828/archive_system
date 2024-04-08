package com.shenmao.archive_management_system.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class History {
    private Integer recordId;
    private Integer archiveId;
    private Integer userId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;
    private String archiveUrl;
    private String reason;
    private String admin;
}
