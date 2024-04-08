package com.shenmao.archive_management_system.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

@Data
public class User {
    @NotNull
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    private String permission;
    private String userUrl;
    private String status;
}
