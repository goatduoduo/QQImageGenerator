package com.duoduo.config;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 公司和员工之间的关系确实有些……复杂
 * @Date: Created in 2023/1/15 20:45
 */
public class IncConfig {
    /**
     * 公司名字
     */
    @JSONField(name = "Inc Name")
    private String inc_Name;

    /**
     * 公司老板（通常只有一位）
     */
    @JSONField(name = "Inc Boss")
    private String boss;

    /**
     * 员工数组
     */
    @JSONField(name = "Inc Employees")
    private List<String> employees;

    public IncConfig(String inc_Name, String boss) {
        this.inc_Name = inc_Name;
        this.boss = boss;
    }

    public String getInc_Name() {
        return inc_Name;
    }

    public void setInc_Name(String inc_Name) {
        this.inc_Name = inc_Name;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }
}
