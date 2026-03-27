package com.agrifarms.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "worker_group_roles")
public class WorkerGroupRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private WorkerGroup workerGroup;

    private String gender;
    private Integer count;

    @Column(name = "task_name")
    private String taskName;

    public WorkerGroupRole() {}

    public WorkerGroupRole(Integer roleId, WorkerGroup workerGroup, String gender, Integer count, String taskName) {
        this.roleId = roleId;
        this.workerGroup = workerGroup;
        this.gender = gender;
        this.count = count;
        this.taskName = taskName;
    }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public WorkerGroup getWorkerGroup() { return workerGroup; }
    public void setWorkerGroup(WorkerGroup workerGroup) { this.workerGroup = workerGroup; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    @Override
    public String toString() {
        return "WorkerGroupRole{" +
                "roleId=" + roleId +
                ", gender='" + gender + '\'' +
                ", count=" + count +
                ", taskName='" + taskName + '\'' +
                '}';
    }
}
