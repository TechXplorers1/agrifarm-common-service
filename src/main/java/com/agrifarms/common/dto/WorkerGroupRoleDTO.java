package com.agrifarms.common.dto;

public class WorkerGroupRoleDTO {
    private Integer roleId;
    private String gender;
    private Integer count;
    private String taskName;

    public WorkerGroupRoleDTO() {}

    public WorkerGroupRoleDTO(Integer roleId, String gender, Integer count, String taskName) {
        this.roleId = roleId;
        this.gender = gender;
        this.count = count;
        this.taskName = taskName;
    }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
}
