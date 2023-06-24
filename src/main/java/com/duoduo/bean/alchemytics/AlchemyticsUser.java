package com.duoduo.bean.alchemytics;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/6/23 22:12
 */
public class AlchemyticsUser {
    @JSONField(name = "User Name")
    private String userName;

    @JSONField(name = "User Exp")
    private Integer exp;

    /**
     * 当前拥有的元素Id
     */
    private Integer currentElementId;

    /**
     * 选择结合的目标玩家的index
     */
    private Integer targetUserIndex;

    /**
     * 选择玩家所拥有的元素Id
     */
    private Integer targetElementId;

    /**
     * 如合成成功，则为新的元素Id，null代表合成失败
     */
    private Integer newElementId;

    /**
     * 游戏相关信息
     */
    private String message;

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

    public Integer getCurrentElementId() {
        return currentElementId;
    }

    public void setCurrentElementId(Integer currentElementId) {
        this.currentElementId = currentElementId;
    }

    public Integer getTargetUserIndex() {
        return targetUserIndex;
    }

    public void setTargetUserIndex(Integer targetUserIndex) {
        this.targetUserIndex = targetUserIndex;
    }

    public Integer getNewElementId() {
        return newElementId;
    }

    public void setNewElementId(Integer newElementId) {
        this.newElementId = newElementId;
    }

    public Integer getTargetElementId() {
        return targetElementId;
    }

    public void setTargetElementId(Integer targetElementId) {
        this.targetElementId = targetElementId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
