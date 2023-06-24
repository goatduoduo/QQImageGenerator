package com.duoduo.config;

import com.alibaba.fastjson2.annotation.JSONField;
import com.duoduo.bean.MinimalUser;
import com.duoduo.util.random.RandomNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 所有人开始共享一个采矿库，现在用户信息也存这里了
 * @Date: Created in 2023/1/22 21:16
 */
public class GlobalConfig {

    /**
     * 操作序列，自动进行
     */
    @JSONField(name = "OperationList")
    private List<String> operationList;

    /**
     * 所有物品的剩余数量
     */
    @JSONField(name = "MineItemsLeft")
    private List<Integer> itemsLeft;

    /**
     * 所有用户的排行榜
     */
    @JSONField(name = "UsersRank")
    private List<MinimalUser> userRank;

    /**
     * 用户信息
     */
    @JSONField(name = "UserConfigs")
    private List<UserConfig> userConfigs;

    /**
     * 全局相关的用户信息，一般会直接加入个人信息进去
     */
    @JSONField(name = "Messages")
    private List<String> globalMessages;

    /**
     * 炼金小游戏已解锁的元素，true代表已解锁，false为未解锁，不允许缺省值
     */
    @JSONField(name = "Alchemist Unlocked Elements")
    private List<Boolean> unlockedElements;


    public List<Integer> getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(List<Integer> itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    public List<MinimalUser> getUserRank() {
        return userRank;
    }

    public void setUsers(List<MinimalUser> users) {
        this.userRank = users;
    }

    public List<String> getGlobalMessages() {
        return globalMessages;
    }

    public void setGlobalMessages(List<String> globalMessages) {
        this.globalMessages = globalMessages;
    }

    public List<String> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<String> operationList) {
        this.operationList = operationList;
    }

    public void setUserRank(List<MinimalUser> userRank) {
        this.userRank = userRank;
    }

    public List<UserConfig> getUserConfigs() {
        return userConfigs;
    }

    public void setUserConfigs(List<UserConfig> userConfigs) {
        this.userConfigs = userConfigs;
    }

    public List<Boolean> getUnlockedElements() {
        return unlockedElements;
    }

    public void setUnlockedElements(List<Boolean> unlockedElements) {
        this.unlockedElements = unlockedElements;
    }

    /**
     * 如果为null则生成一个默认的config
     *
     * @return 默认的
     */
    public static GlobalConfig initialize() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setUsers(new ArrayList<>());
        globalConfig.setItemsLeft(new ArrayList<>());
        globalConfig.setUnlockedElements(new ArrayList<>());
        return globalConfig;
    }

    /**
     * 判断是否需要重置矿脉
     * 是 返回true并重置矿脉
     * 否 返回false
     *
     * @return 是否重置矿脉
     */
    public boolean initMine() {
        //无量在4号位置
        boolean needReset = true;
        try {
            needReset = itemsLeft.get(4) <= 0;
        } catch (Exception e) {
            System.out.println("矿脉信息未初始化");
        }
        if (needReset) {
            itemsLeft = new ArrayList<>(5);
            itemsLeft.add(RandomNumber.getRandomInt(900, 1000));
            itemsLeft.add(RandomNumber.getRandomInt(1400, 1500));
            itemsLeft.add(RandomNumber.getRandomInt(400, 400));
            itemsLeft.add(RandomNumber.getRandomInt(230, 260));
            itemsLeft.add(RandomNumber.getRandomInt(100, 140));
        }
        return needReset;
    }

    public GlobalConfig() {
        operationList = new ArrayList<>();
        itemsLeft = new ArrayList<>();
        userRank = new ArrayList<>();
        userConfigs = new ArrayList<>();
        globalMessages = new ArrayList<>();
    }
    /**
     * 根据userName和qq，生成对象并加入到userConfig数组中
     * （可选）是否替代已存在，默认为否
     *
     * @param userName 用户名
     * @param qq       qq号
     */
    public void generateUser(String userName, String qq) {
        generateUser(userName, qq, false);
    }

    /**
     * 根据userName和qq，生成对象并加入到userConfig数组中
     *
     * @param userName 用户名
     * @param qq       qq号
     * @param replace  是否替代
     */
    public void generateUser(String userName, String qq, Boolean replace) {
        UserConfig temp = new UserConfig(userName, qq);
        for (UserConfig e : userConfigs) {
            if (e.getName().equals(userName)) {
                if (replace) {
                    e = temp;
                    System.out.println(userName + "已存在，开始替代");
                } else {
                    System.out.println(userName + "已存在，操作取消");
                }
                return;
            }
        }
        userConfigs.add(temp);
    }
}


