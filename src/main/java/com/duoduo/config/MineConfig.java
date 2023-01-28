package com.duoduo.config;

import com.alibaba.fastjson2.annotation.JSONField;
import com.duoduo.bean.MinimalUser;
import com.duoduo.util.random.RandomNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 所有人开始共享一个采矿库
 * @Date: Created in 2023/1/22 21:16
 */
public class MineConfig {
    /**
     * 所有物品的剩余数量
     */
    @JSONField(name = "Mine Items Left")
    private List<Integer> itemsLeft;

    /**
     * 所有用户的排行榜
     */
    @JSONField(name = "Users")
    private List<MinimalUser> users;


    public List<Integer> getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(List<Integer> itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    public List<MinimalUser> getUsers() {
        return users;
    }

    public void setUsers(List<MinimalUser> users) {
        this.users = users;
    }


    /**
     * 如果为null则生成一个默认的config
     *
     * @return 默认的
     */
    public static MineConfig initialize() {
        MineConfig mineConfig = new MineConfig();
        mineConfig.setUsers(new ArrayList<>());
        mineConfig.setItemsLeft(new ArrayList<>());
        return mineConfig;
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
        try{
            needReset = itemsLeft.get(4) <= 0;
        }catch (Exception e){
            System.out.println("矿脉信息未初始化");
        }
        if(needReset){
            itemsLeft = new ArrayList<>(5);
            itemsLeft.add(RandomNumber.getRandomInt(1200,1400));
            itemsLeft.add(RandomNumber.getRandomInt(800,1000));
            itemsLeft.add(RandomNumber.getRandomInt(500,600));
            itemsLeft.add(RandomNumber.getRandomInt(140,200));
            itemsLeft.add(RandomNumber.getRandomInt(50,75));
        }
        return needReset;
    }

    public MineConfig() {

    }
}


