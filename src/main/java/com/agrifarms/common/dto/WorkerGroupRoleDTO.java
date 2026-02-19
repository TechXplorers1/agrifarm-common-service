package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerGroupRoleDTO {
    private Integer roleId;
    private String gender;
    private Integer count;
    private String taskName;
}
