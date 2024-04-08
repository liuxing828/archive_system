package com.shenmao.archive_management_system.pojo;

import lombok.Data;

@Data
public class Approval {
    private Integer approvalId;
    private String admin;
    private String reason;
    private String userId;
    private String archiveId;
    private String approvalCategory;
}
