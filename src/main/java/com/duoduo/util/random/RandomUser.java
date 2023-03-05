package com.duoduo.util.random;

import com.duoduo.config.UserConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/1/22 21:55
 */
public class RandomUser {
    public static List<UserConfig> userConfigs = new ArrayList<>(20);

    /**
     * 加权随机选择最高经验值的目标
     *
     * @return 随机到的受害者
     */
    public static UserConfig randomUserByMostExp() {
        int totalWeight = 0;
        for (UserConfig e : userConfigs) {
            totalWeight += e.getExperience();
        }
        int rand = RandomNumber.getRandomInt(1, totalWeight);
        for (UserConfig e : userConfigs) {
            rand -= e.getExperience();
            if (rand <= 0) {
                return e;
            }
        }
        return userConfigs.get(0);
    }

    /**
     * 根据最高经验值进行排序
     */
    public static void sortUserByMostExp(List<UserConfig> userConfigs) {
        userConfigs.sort((o1, o2) -> {
            if (o1.getExperience() > o2.getExperience()) {
                return -1;
            } else if (o1.getExperience() < o2.getExperience()) {
                return 1;
            }
            return 0;
        });
    }

    /**
     * 按照名字进行排序，这是默认规则
     * @param userConfigs
     */
    public static void sortUserByDefault(List<UserConfig> userConfigs){
        userConfigs.sort(Comparator.comparing(UserConfig::getName));
    }
}
