package com.shenmao.archive_management_system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Archive {
    //@NotNull(groups = Update.class)
    private Integer archiveId;
    //@NotEmpty
    private String archiveName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate archiveDate;
    //@NotEmpty
    private String archiveCategory;
    //@NotEmpty @URL
    private String archiveUrl;
    private String createUser;
    private String status;
    private Integer secrecy;

}
