package com.duoduo.component;

import com.duoduo.bean.*;
import com.duoduo.util.random.RandomNumber;
import com.duoduo.config.UserConfig;
import com.duoduo.util.ConfigUtil;
import com.duoduo.component.entity.BackgroundEntity;
import com.duoduo.component.entity.InnerImageEntity;
import com.duoduo.component.entity.PolygonEntity;
import com.duoduo.component.entity.TextEntity;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 准备修改了，换一种随机方式，改用伪洗牌的算法
 * 继续修改：增加一种掠夺玩法，每个人随机掠夺其他人，每次掠夺为当前所有经验值的10%，等级可以提高矿物价值，每1级+20%
 * @Date: Created in 2022/12/4 21:48
 */
public class TenRoundsComponent extends BackgroundEntity {

    private List<RewardBean> rewardBeans = new ArrayList<>();

    private List<RewardBean> rewarded = new ArrayList<>(20);

    private UserConfig user = null;

    LevelBean levelBean = null;

    public TenRoundsComponent(String userName) {
        rewardBeans.add(new RewardBean(300, 4, new Color(74, 174, 82), "标量", "/images/infinitode/Resource-Scalar.png"));
        rewardBeans.add(new RewardBean(150, 9, new Color(90, 105, 198), "矢量", "/images/infinitode/Resource-Vector.png"));
        rewardBeans.add(new RewardBean(100, 16, new Color(173, 69, 189), "矩阵", "/images/infinitode/Resource-Matrix.png"));
        rewardBeans.add(new RewardBean(50, 36, new Color(251, 154, 3), "张量", "/images/infinitode/Resource-Tensor.png"));
        rewardBeans.add(new RewardBean(15, 72, new Color(4, 190, 217), "无量", "/images/infinitode/Resource-Infiar.png"));

        user = ConfigUtil.readUserConfig(userName, UserConfig.class);
        if (user == null) {
            user = new UserConfig(userName);
        }
        levelBean = new LevelBean(user.getCash());

        initWeight();

        long start = System.currentTimeMillis();
        int columns = 5;
        int rows = 3;
        int maxMessage = 5;

        setBackgroundColor(new Color(246, 246, 246));
        setWidth(640);
        setHeight(440);
        gacha(rows * columns);
        init();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                getRound(20 + 80 * j, 76 + 74 * i, i * columns + j);
            }
        }

        for (int i = 0; i < maxMessage; i++) {
            if (user.getMessages().size() - i - 1 >= 0) {
                getMessage(20, 310 + 22 * i, user.getMessages().size() - i - 1);
            }
        }

        getFinalCash();

        ConfigUtil.writeConfig(userName, user);
        System.out.println("十连抽 生成用时:" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 初始化权重
     */
    private void initWeight() {
        try {
            int totalLeft = 0;
            for (int i = 4; i <= 4; i++) {
                totalLeft += user.getItemsLeft().get(i);
            }
            if (totalLeft == 0) {
                //初始化,直接使用上面默认权重即可
                System.out.println("无量用尽，开始重置");
                return;
            }
        } catch (Exception ex) {
            //初始化,直接使用上面默认权重即可
            System.out.println("读取失败，即将使用默认权重");
            user.setItemsLeft(new ArrayList<>(5));
            return;
        }
        for (int i = 0; i < 5; i++) {
            rewardBeans.get(i).setWeight(user.getItemsLeft().get(i));
            //新规则，每次升级增加10%的价值
            rewardBeans.get(i).setCash((int) (rewardBeans.get(i).getCash() * levelBean.getCurMiningRatio()));
        }
    }

    /**
     * 开始抽奖，并将解锁送入rewarded并排序
     *
     * @param rounds
     */
    private void gacha(int rounds) {
        for (int j = 0; j < rounds; j++) {
            int totalWeight = 0;
            for (int i = 0; i < rewardBeans.size(); i++) {
                totalWeight += rewardBeans.get(i).getWeight();
            }
            if (totalWeight == 0) {
                break;
            }
            //加权随机算法
            int rand = RandomNumber.getRandomInt(1, totalWeight);
            for (int i = 0; i < rewardBeans.size(); i++) {
                if (rewardBeans.get(i).getWeight() == 0) {
                    continue;
                }
                rand -= rewardBeans.get(i).getWeight();
                if (rand <= 0) {
                    rewarded.add(rewardBeans.get(i));
                    //此时抽中后将会从奖池里拿出，这是最大的不同点
                    rewardBeans.get(i).setWeight(rewardBeans.get(i).getWeight() - 1);
                    user.setCash(user.getCash() + rewardBeans.get(i).getCash());
                    break;
                }
            }
        }
        Collections.sort(rewarded);
        //写入到config中
        user.getItemsLeft().clear();
        for (int i = 0; i < rewardBeans.size(); i++) {
            user.getItemsLeft().add(rewardBeans.get(i).getWeight());
        }
    }


    /**
     * 开始抽卡，参数为左上角的标记点
     *
     * @param x
     * @param y
     */
    private void getRound(int x, int y, int index) {
        if (rewarded.size() <= index) {
            return;
        }
        TextEntity textTitle = new TextEntity();
        textTitle.setTextContent(rewarded.get(index).getTitle());
        textTitle.setFontSize(16.0f);
        textTitle.setX(x + 18f);
        textTitle.setY(y + 66f);
        textTitle.setFontColor(new Color(138, 138, 138));
        textTitle.setContentWidth(600);

        InnerImageEntity image = new InnerImageEntity(rewarded.get(index).getImagePath(), (float) x, (float) y);

        getChildren().add(image);
        //getChildren().add(textTitle);
    }

    /**
     * 显示消息
     *
     * @param x
     * @param y
     * @param index
     */
    private void getMessage(int x, int y, int index) {
        TextEntity textTitle = new TextEntity();
        textTitle.setTextContent(user.getMessages().get(index));
        textTitle.setFontSize(14.0f);
        textTitle.setX(x + 0f);
        textTitle.setY(y + 0f);
        textTitle.setFontColor(new Color(173, 173, 173, 202));
        textTitle.setContentWidth(600);
        getChildren().add(textTitle);
    }

    private void getFinalCash() {
        levelBean.updateCurrentLevel(user.getCash());
        TextEntity textTitle = new TextEntity();
        textTitle.setTextContent(user.getName() + " Lvl:" + levelBean.getCurLevel() + " Exp:"
                + levelBean.getCurExp() + " / "+levelBean.getCurExpRequired());
        textTitle.setFontSize(18.0f);
        textTitle.setX(20f);
        textTitle.setY(20f);
        textTitle.setFontStyle(1);
        textTitle.setFontColor(new Color(126, 126, 126, 144));
        textTitle.setContentWidth(600);

        TextEntity smallText = new TextEntity();
        smallText.setTextContent("本次收集到了如下矿物：");
        smallText.setX(20f);
        smallText.setY(50.0f);
        smallText.setFontSize(14.0f);
        smallText.setFontStyle(2);
        smallText.setFontColor(new Color(173, 173, 173, 202));

        getChildren().add(textTitle);
        getChildren().add(smallText);
    }

    private void init() {
        int totalWeight = 0;
        final int basePercentY = 64;
        TextEntity title = new TextEntity();
        title.setTextContent("采集矿石以增加经验值统计如下：");
        title.setFontSize(14.0f);
        title.setX(440f);
        title.setY(14f);
        title.setContentWidth(150);
        title.setFontColor(new Color(126, 126, 126, 144));

        for (int i = 0; i < rewarded.size(); i++) {
            totalWeight += rewarded.get(i).getCash();
        }
        TextEntity detail = new TextEntity();
        detail.setTextContent("采矿前随机一个人，夺取其10%的经验值，随机到自己时掠夺无效；抽中后不放回，所有无量被抽出后奖池重置；每次升级增加10%的矿物价值；");
        detail.setFontSize(12.0f);
        detail.setX(440f);
        detail.setY(200f);
        detail.setFontColor(new Color(133, 133, 133, 144));
        detail.setContentWidth(150);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.getMessages().add(sdf.format(new Date()) + ": 你通过采矿获得了" + totalWeight + " 经验值;");

        for (int i = 0; i < rewardBeans.size(); i++) {
            totalWeight += rewardBeans.get(i).getWeight();
        }
        for (int i = 0; i < rewardBeans.size(); i++) {
            TextEntity textTitle = new TextEntity();
            textTitle.setTextContent(rewardBeans.get(i).getTitle() + " 价值:" + rewardBeans.get(i).getCash() + " 剩余:" + rewardBeans.get(i).getWeight());
            textTitle.setFontSize(12.0f);
            textTitle.setX(456f);
            textTitle.setY((float) basePercentY + i * 16);
            textTitle.setFontColor(new Color(138, 138, 138));
            textTitle.setContentWidth(280);

            PolygonEntity polygonEntity = new PolygonEntity(new int[]{440, basePercentY + 3 + i * 16, 440 + 10, basePercentY + 3 + i * 16, 440 + 10,
                    basePercentY + 3 + i * 16 + 10, 440, basePercentY + 3 + i * 16 + 10},
                    rewardBeans.get(i).getColor());

            getChildren().add(textTitle);
            getChildren().add(polygonEntity);
        }
        getChildren().add(detail);
        getChildren().add(title);
    }


}
