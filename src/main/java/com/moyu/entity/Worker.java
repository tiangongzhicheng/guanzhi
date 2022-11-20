package com.moyu.entity;

public class Worker {

    private Integer id;
    private Integer depId;
    private Integer leader;
    private Boolean header;

    public Worker(){};

    public Worker(Integer id, Integer depId, Integer leader, Boolean header) {
        this.id = id;
        this.depId = depId;
        this.leader = leader;
        this.header = header;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }
}
