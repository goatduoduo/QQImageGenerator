package com.duoduo.Component;

import com.duoduo.RandomUtil.RandomNumber;
import com.duoduo.User.UserConfig;
import com.duoduo.Util.ConfigUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    /**
     * 无参数调用方法，所有玩家的数据储存在一个json里，公司和员工的关系是1：n，只有两层关系。
     * 所有人最初没有职业，随机5%成为老板，95%员工，而员工若无公司会随机加入一个，暂不考虑失业问题。
     * 保证最少一个老板，否则强行指定一个老板。
     * 但是我最好知道多少人开始了这场聚会，否则无从算起……
     */
    public static void calculateOneDay() {

    }

    /**
     * 十连抽的玩法补充，每个人在挖矿前随机找1名受害者掠夺10%的财产
     */
    public static void lootGameOn(List<String> users) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int size = users.size();
        final float lootPercent = 0.1f;
        for (int i = 0; i < size; i++) {
            int randInt = RandomNumber.getRandomInt(0, size - 1);
            //不允许掠夺自己，所以出现此情况会被跳过
            if (randInt != i) {
                UserConfig looter = ConfigUtil.readUserConfig(users.get(i), UserConfig.class);
                UserConfig victim = ConfigUtil.readUserConfig(users.get(randInt), UserConfig.class);
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
                ConfigUtil.writeConfig(users.get(i),looter);
                ConfigUtil.writeConfig(users.get(randInt),victim);

                System.out.println(looter.getName() + "掠夺了" + victim.getName() + looted + "经验值;");
            }
        }
    }
}
