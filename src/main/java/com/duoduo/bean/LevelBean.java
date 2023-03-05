package com.duoduo.bean;

import com.duoduo.util.CsvUtil;
import com.duoduo.util.random.RandomColor;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: duoduo
 * Created Date: 2023/1/17 15:52
 **/
public class LevelBean {
    static List<Integer> expList = null;
    static List<Float> miningRatio = null;

    int curLevel = 0;
    int curExp = 0;
    int curExpRequired = 0;
    float curMiningRatio = 1;

    /**
     * 读取经验值表格到总表中
     */
    public void initLevelCsv() {
        if (expList != null && miningRatio != null) {
            return;
        }
        expList = new ArrayList<>(25);
        miningRatio = new ArrayList<>(25);
        long start = System.currentTimeMillis();
        InputStream url = this.getClass().getResourceAsStream("/data/levels.csv");
        List<String[]> list = CsvUtil.parseCsv(url);
        for (String[] e : list) {
            expList.add(Integer.parseInt(e[1]));
            miningRatio.add(Float.parseFloat(e[2]));
        }
        System.out.println("初始化用时：" + (System.currentTimeMillis() - start) + "ms");
    }

    public LevelBean(Integer totalExp) {
        updateCurrentLevel(totalExp);
    }

    public LevelBean() {
        initLevelCsv();
    }

    /**
     * 更新用户等级信息
     *  level从1开始，但是数组从0开始
     * @param totalExp 总经验值
     */
    public void updateCurrentLevel(int totalExp) {
        for (int i = 0; i < expList.size(); i++) {
            if (totalExp < expList.get(i)) {
                curLevel = i + 1;
                curExp = totalExp;
                curExpRequired = expList.get(i);
                curMiningRatio = miningRatio.get(i);
                return;
            } else {
                totalExp -= expList.get(i);
            }
        }
        curLevel = expList.size();
        curExp = totalExp;
        curExpRequired = Integer.MAX_VALUE;
        curMiningRatio = miningRatio.get(curLevel - 1);
    }

    public int getCurLevel() {
        return curLevel;
    }

    public int getCurExp() {
        return curExp;
    }

    public int getCurExpRequired() {
        return curExpRequired;
    }

    public float getCurMiningRatio() {
        return curMiningRatio;
    }
}
