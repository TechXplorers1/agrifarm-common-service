package com.agrifarms.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "worker_group_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerGroupRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    private WorkerGroup workerGroup;

    private String gender;
    private Integer count;

    @Column(name = "task_name")
    private String taskName;
}
