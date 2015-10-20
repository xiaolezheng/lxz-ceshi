package com.lxz.pg;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Garry on 15/6/10.
 */
public class FormData {
    private String id;
    private String dataId;
    private String tenantId;
    private String formId;
    private Long userId;
    private String userName;
    private Date updateTime;
    private int status;

    private Map<String, Object> fields;

    public static FormData builder(){
        return new FormData();
    }

    private FormData(){
        this.dataId = "d_" + UUID.randomUUID();
    }

    public void generateId() {
        this.dataId = "d_" + UUID.randomUUID();
    }

    public FormData setId(String id) {
        this.id = id;
        return this;
    }

    public FormData setDataId(String dataId) {
        this.dataId = dataId;

        return this;
    }

    public FormData setFormId(String formId) {
        this.formId = formId;

        return this;
    }

    public FormData setUserId(Long userId) {
        this.userId = userId;

        return this;
    }

    public FormData setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;

        return this;
    }

    public FormData setFields(Map<String, Object> fields) {
        this.fields = fields;

        return this;
    }

    public FormData setStatus(int status) {
        this.status = status;

        return this;
    }

    public String getId() {

        return id;
    }

    public String getDataId() {
        return dataId;
    }

    public String getFormId() {
        return formId;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public int getStatus() {

        return status;
    }

    public FormData setUserName(String userName) {
        this.userName = userName;

        return this;
    }

    public String getUserName() {
        return userName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public FormData setTenantId(String tenantId) {
        this.tenantId = tenantId;

        return this;
    }
}
