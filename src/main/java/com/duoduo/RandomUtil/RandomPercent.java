package com.duoduo.RandomUtil;

import java.util.Random;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 19:42
 */
public class RandomPercent {
    static Random random = new Random();

    /**
     * 根据百分比计算真假值
     *
     * @param percent 结果为真的百分比
     * @return 真假值
     */
    public static boolean randomTrue(int percent) {
        int ans = random.nextInt(100);
        return ans < percent;
    }
}