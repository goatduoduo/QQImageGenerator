package com.duoduo.bean;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/1/22 22:51
 */
public class MinimalUser{
    @JSONField(name = "User Name")
    private String userName;

    @JSONField(name = "User Exp")
    private Integer exp;

    public MinimalUser(String userName, Integer exp) {
        this.userName = userName;
        this.exp = exp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}