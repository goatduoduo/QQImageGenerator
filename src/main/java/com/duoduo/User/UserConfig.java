package com.duoduo.User;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 用户配置Json文件
 * @Date: Created in 2022/12/4 19:16
 */
public class UserConfig {

    @JSONField(name = "USER NAME", ordinal = 1)
    private String name;

    @JSONField(name = "LAST SELECTED COLOR", ordinal = 2)
    private String lastSelectedColor;

    /**
     * 剩余资金
     */
    @JSONField(name = "Lottery Cash", ordinal = 3)
    private Integer cash;

    /**
     * 动态概率
     */
    @JSONField(name = "Lottery Dynamic Percentage", ordinal = 4)
    private Integer dynamicPercentage;

    /**
     * 所有物品的剩余数量
     */
    @JSONField(name = "Lottery Items Left", ordinal = 5)
    private List<Integer> itemsLeft;

    /**
     * 消息列表
     */
    @JSONField(name = "Messages", ordinal = 6)
    private List<String> messages;

    public UserConfig(String name, String lastSelectedColor) {
        this.name = name;
        this.lastSelectedColor = lastSelectedColor;
    }

    public UserConfig(String name) {
        this.name = name;
        this.cash = 100;
        this.dynamicPercentage = 0;
        this.itemsLeft = new ArrayList<>(5);
        this.messages = new ArrayList<>(0);
        for (int i = 0; i < 5; i++) {
            this.itemsLeft.add(0);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSelectedColor() {
        return lastSelectedColor;
    }

    public void setLastSelectedColor(String lastSelectedColor) {
        this.lastSelectedColor = lastSelectedColor;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getDynamicPercentage() {
        return dynamicPercentage;
    }

    public void setDynamicPercentage(Integer dynamicPercentage) {
        this.dynamicPercentage = dynamicPercentage;
    }

    public List<Integer> getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(List<Integer> itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public int getCurrentLvl() {
        return cash / 650 + 1;
    }

    public int getCurrentExp() {
        return cash % 650;
    }
}
