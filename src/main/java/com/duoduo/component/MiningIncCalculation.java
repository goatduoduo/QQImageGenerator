package com.duoduo.component;

import com.duoduo.bean.MinimalUser;
import com.duoduo.config.MineConfig;
import com.duoduo.util.random.RandomNumber;
import com.duoduo.config.UserConfig;
import com.duoduo.util.ConfigUtil;
import com.duoduo.util.random.RandomPercent;
import com.duoduo.util.random.RandomUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 所有人分为两大职业：老板和矿工，老板会开设公司，而员工会负责挖矿。
 * 挖到的矿物会被公司转化为价值直接回馈给老板，但需要付给员工工资，而工资的组成取决于不同的公司，主要为基本工资+提成。
 * 员工的收入来源只有老板发的工资
 * 每个人需要付出200的日供，如果欠款则会收到政府低保以保持生存。
 * 后期会加入随机事件
 * @Date: Created in 2023/1/15 20:05
 */
public class MiningIncCalculation {

    private static MineConfig mineConfig = null;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 无参数调用方法，所有玩家的数据储存在一个json里，公司和员工的关系是1：n，只有两层关系。
     * 所有人最初没有职业，随机5%成为老板，95%员工，而员工若无公司会随机加入一个，暂不考虑失业问题。
     * 保证最少一个老板，否则强行指定一个老板。
     * 但是我最好知道多少人开始了这场聚会，否则无从算起……
     */
    public static void calculateOneDay() {

    }


    /**
     * 十连抽的玩法补充，每个人在挖矿前随机找1名受害者掠夺6%的经验值
     * 经验值越高的玩家越有可能成为目标
     */
    public static void lootGameOn(List<String> users) {
        mineConfig = ConfigUtil.readUserConfig("mine", "globe", MineConfig.class);
        if (mineConfig == null) {
            mineConfig = MineConfig.initialize();
        }

        int size = users.size();
        final float lootPercent = 0.06f;
        //将文件读入到随机数组中以启用随机指定玩家的功能
        for (String e : users) {
            RandomUser.userConfigs.add(ConfigUtil.readUserConfig(e, "users", UserConfig.class));
        }
        for (int i = 0; i < size; i++) {
            int randInt = RandomNumber.getRandomInt(0, size - 1);
            //不允许掠夺自己，所以出现此情况会被跳过
            if (randInt != i) {
                UserConfig looter = RandomUser.userConfigs.get(i);
                UserConfig victim = RandomUser.userConfigs.get(randInt);
                int looted = (int) (victim.getCash() * lootPercent);
                //掠夺游戏开始
                looter.setCash(looter.getCash() + looted);
                if (looter.getMessages() == null) {
                    looter.setMessages(new ArrayList<>());
                }
                looter.getMessages().add(sdf.format(new Date()) + ": " + "你掠夺了" + victim.getName() + " " + looted + " 经验值;");
                victim.setCash(victim.getCash() - looted);
                if (victim.getMessages() == null) {
                    victim.setMessages(new ArrayList<>());
                }
                victim.getMessages().add(sdf.format(new Date()) + ": " + looter.getName() + "掠夺了你 " + looted + " 经验值;");
                //掠夺结束了
                ConfigUtil.writeConfig(users.get(i), "users", looter);
                ConfigUtil.writeConfig(users.get(randInt), "users", victim);

                System.out.println(looter.getName() + "掠夺了" + victim.getName() + looted + "经验值;");
            }
        }
        RandomUser.sortUserByMostExp();
        mineConfig.setUsers(new ArrayList<>());
        for (UserConfig e : RandomUser.userConfigs) {
            mineConfig.getUsers().add(new MinimalUser(e.getName(), e.getCash()));
        }
        ConfigUtil.writeConfig("mine", "globe", mineConfig);
    }

    /**
     * 挖矿算法在此
     *
     * @param users
     */
    public static void mineGameOn(List<String> users) {
        //一名玩家的挖矿数量
        int rounds = 15;
        //重置矿脉的费用
        int resetCost = 2000;
        //洗牌算法打乱玩家顺序
        for (int i = 0; i < users.size(); i++) {
            int userA = RandomNumber.getRandomInt(0, users.size() - 1);
            int userB = RandomNumber.getRandomInt(0, users.size() - 1);
            if (userA != userB) {
                String temp = users.get(userB);
                users.set(userB, users.get(userA));
                users.set(userA, temp);
            }
        }
        //初始化以防null
        mineConfig = ConfigUtil.readUserConfig("mine", "globe", MineConfig.class);
        if (mineConfig == null) {
            mineConfig = MineConfig.initialize();
        }
        mineConfig.initMine();
        //全局信息每一次运行都会被重置，因为之后，每个玩家在生成图片时都会把这些信息插入其中
        mineConfig.setGlobalMessages(new ArrayList<>());

        for (String e : users) {
            UserConfig user = ConfigUtil.readUserConfig(e, "users", UserConfig.class);
            if (user == null) {
                user = new UserConfig(e);
            }
            //开始挖矿
            user.setNormalMineIndexList(new ArrayList<>());
            for (int j = 0; j < rounds; j++) {
                //1/300抽命中稀有物品
                if(RandomPercent.randomTrueDenominator(300)){
                    mineConfig.getGlobalMessages().add(sdf.format(new Date()) + ": " + user.getName() + "真的幸运极了，抽中了"+""+"稀有物品——九转大肠");
                    System.out.println(user.getName() + "真的幸运极了，抽中了"+""+"稀有物品");
                }
                //平平无奇的抽奖流程
                int totalWeight = 0;
                for (int i = 0; i < mineConfig.getItemsLeft().size(); i++) {
                    totalWeight += mineConfig.getItemsLeft().get(i);
                }
                if (totalWeight == 0) {
                    break;
                }
                //加权随机算法
                int rand = RandomNumber.getRandomInt(1, totalWeight);
                for (int i = 0; i < mineConfig.getItemsLeft().size(); i++) {
                    if (mineConfig.getItemsLeft().get(i) == 0) {
                        continue;
                    }
                    rand -= mineConfig.getItemsLeft().get(i);
                    if (rand <= 0) {
                        user.getNormalMineIndexList().add(i);
                        //此时抽中后将会从奖池里拿出，这是最大的不同点
                        mineConfig.getItemsLeft().set(i, mineConfig.getItemsLeft().get(i) - 1);
                        break;
                    }
                }
            }
            //将用户采集到的矿物从高到低排序
            user.getNormalMineIndexList().sort((o1, o2) -> {
                if (o1 > o2) {
                    return -1;
                } else if (o1 < o2) {
                    return 1;
                }
                return 0;
            });
            //真是倒霉鬼，最后一个挖完矿物的要去重置了
            if (mineConfig.initMine()) {
                user.setCash(user.getCash() - resetCost);
                mineConfig.getGlobalMessages().add(sdf.format(new Date()) + ": " + user.getName() + "真是个倒霉鬼，花费了"+resetCost+"经验值重置矿脉");
                System.out.println(user.getName() + "真是个倒霉鬼，花费了"+resetCost+"经验值重置矿脉");
            }
            ConfigUtil.writeConfig(e, "users", user);
        }
        ConfigUtil.writeConfig("mine", "globe", mineConfig);
        System.out.println("采矿游戏结束");
    }
}
