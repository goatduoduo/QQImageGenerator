package com.duoduo.component;

import com.duoduo.bean.*;
import com.duoduo.config.MineConfig;
import com.duoduo.util.random.RandomNumber;
import com.duoduo.config.UserConfig;
import com.duoduo.util.ConfigUtil;
import com.duoduo.component.entity.BackgroundEntity;
import com.duoduo.component.entity.InnerImageEntity;
import com.duoduo.component.entity.PolygonEntity;
import com.duoduo.component.entity.TextEntity;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description: 准备修改了，换一种随机方式，改用伪洗牌的算法
 * 继续修改：增加一种掠夺玩法，每个人随机掠夺其他人，每次掠夺为当前所有经验值的10%，等级可以提高矿物价值，每1级+20%
 * @Date: Created in 2022/12/4 21:48
 */
public class TenRoundsComponent extends BackgroundEntity {

    private List<RewardBean> rewardBeans = new ArrayList<>();

    private UserConfig user = null;

    private MineConfig mineConfig = null;

    LevelBean levelBean = null;

    public TenRoundsComponent(String userName) {
        //既然公用矿脉，那么weight不再被需要了
        rewardBeans.add(new RewardBean(1, 4, new Color(74, 174, 82), "标量", "/images/infinitode/Resource-Scalar.png"));
        rewardBeans.add(new RewardBean(1, 9, new Color(90, 105, 198), "矢量", "/images/infinitode/Resource-Vector.png"));
        rewardBeans.add(new RewardBean(1, 16, new Color(173, 69, 189), "矩阵", "/images/infinitode/Resource-Matrix.png"));
        rewardBeans.add(new RewardBean(1, 36, new Color(251, 154, 3), "张量", "/images/infinitode/Resource-Tensor.png"));
        rewardBeans.add(new RewardBean(1, 72, new Color(4, 190, 217), "无量", "/images/infinitode/Resource-Infiar.png"));

        user = ConfigUtil.readUserConfig(userName, "users", UserConfig.class);
        if (user == null) {
            user = new UserConfig(userName);
        }
        levelBean = new LevelBean(user.getCash());

        mineConfig = ConfigUtil.readUserConfig("mine", "globe", MineConfig.class);

        for (String e : mineConfig.getGlobalMessages()) {
            user.getMessages().add(e);
        }

        initValue();

        long start = System.currentTimeMillis();
        int columns = 5;
        int rows = 3;
        int maxMessage = 10;

        setBackgroundColor(new Color(246, 246, 246));
        setWidth(640);
        setHeight(545);
        init();
        //显示挖矿结果
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                getRound(20 + 80 * j, 76 + 74 * i, i * columns + j);
            }
        }
        //显示信息
        for (int i = 0; i < maxMessage; i++) {
            if (user.getMessages().size() - i - 1 >= 0) {
                getMessage(20, 310 + 22 * i, user.getMessages().size() - i - 1);
            }
        }

        getFinalCash();

        ConfigUtil.writeConfig(userName, "users", user);
        System.out.println("十连抽 生成用时:" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 初始化
     */
    private void initValue() {
        for (int i = 0; i < 5; i++) {
            //每次升级增加价值
            rewardBeans.get(i).setCash((int) (rewardBeans.get(i).getCash() * levelBean.getCurMiningRatio()));
        }
    }

    /**
     * 开始抽卡，参数为左上角的标记点
     *
     * @param x
     * @param y
     */
    private void getRound(int x, int y, int index) {
        if (user.getNormalMineIndexList().size() <= index) {
            return;
        }
        InnerImageEntity image = new InnerImageEntity(rewardBeans.get(user.getNormalMineIndexList().get(index)).
                getImagePath(), (float) x, (float) y);
        getChildren().add(image);
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
        textTitle.setFontSize(12.0f);
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
                + levelBean.getCurExp() + " / " + levelBean.getCurExpRequired());
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
        final int basePercentY = 20;

        for (int i = 0; i < user.getNormalMineIndexList().size(); i++) {
            totalWeight += rewardBeans.get(user.getNormalMineIndexList().get(i)).getCash();
        }
        TextEntity detail = new TextEntity();
        detail.setTextContent("注意：所有人共享同一矿脉！");
        detail.setFontSize(12.0f);
        detail.setX(440f);
        detail.setY(110f);
        detail.setFontStyle(1);
        detail.setFontColor(new Color(252, 133, 14, 184));
        detail.setContentWidth(180);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.getMessages().add(sdf.format(new Date()) + ": 你通过采矿获得了" + totalWeight + " 经验值;");

        for (RewardBean rewardBean : rewardBeans) {
            totalWeight += rewardBean.getWeight();
        }
        //显示矿物价值和数量
        for (int i = 0; i < rewardBeans.size(); i++) {
            TextEntity textTitle = new TextEntity();
            textTitle.setTextContent(rewardBeans.get(i).getTitle() + " 价值:" + rewardBeans.get(i).getCash() + " 剩余:" + mineConfig.getItemsLeft().get(i));
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
        //排行榜模块
        TextEntity rankingTitle = new TextEntity();
        rankingTitle.setTextContent("排行榜");
        rankingTitle.setFontStyle(1);
        rankingTitle.setFontSize(12.0f);
        rankingTitle.setX(440f);
        rankingTitle.setY(160f);
        rankingTitle.setFontColor(new Color(126, 126, 126, 144));
        rankingTitle.setFontSize(16);
        for (int i = 0; i < mineConfig.getUsers().size() && i < 5; i++) {
            TextEntity textTitle = new TextEntity();
            LevelBean temp = new LevelBean(mineConfig.getUsers().get(i).getExp());
            textTitle.setTextContent("#" + (i + 1) + " " + mineConfig.getUsers().get(i).getUserName());
            textTitle.setFontSize(12.0f);
            textTitle.setX(440f);
            textTitle.setY((float) 186 + i * 16);
            textTitle.setFontColor(new Color(138, 138, 138));
            textTitle.setContentWidth(360);

            TextEntity expTitle = new TextEntity();
            expTitle.setTextContent( "Lvl:" + temp.getCurLevel() + " Exp:" + temp.getCurExp());
            expTitle.setFontSize(12.0f);
            expTitle.setX(514f);
            expTitle.setY((float) 186 + i * 16);
            expTitle.setFontColor(new Color(138, 138, 138));
            expTitle.setContentWidth(360);

            getChildren().add(textTitle);
            getChildren().add(expTitle);
        }

        int userRankIndex = 0;
        for (int i = 0; i < mineConfig.getUsers().size(); i++) {
            if (Objects.equals(mineConfig.getUsers().get(i).getUserName(), user.getName())) {
                userRankIndex = i + 1;
                break;
            }
        }
        TextEntity rankCurrent = new TextEntity();
        rankCurrent.setTextContent("你的位置：" + userRankIndex + "/" + mineConfig.getUsers().size());
        rankCurrent.setFontColor(new Color(126, 126, 126, 144));
        rankCurrent.setX(440f);
        rankCurrent.setY(280f);
        rankCurrent.setFontSize(14.0f);
        //显示排行榜
        getChildren().add(rankCurrent);
        getChildren().add(detail);
        getChildren().add(rankingTitle);
    }


}
