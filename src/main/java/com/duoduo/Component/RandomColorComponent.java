package com.duoduo.Component;

import com.duoduo.Bean.BackgroundEntity;
import com.duoduo.Bean.ColorBean;
import com.duoduo.Bean.PolygonEntity;
import com.duoduo.Bean.TextEntity;
import com.duoduo.RandomUtil.RandomColor;
import com.duoduo.RandomUtil.RandomNumber;
import com.duoduo.RandomUtil.RandomPercent;
import com.duoduo.Util.UserConfigUtil;

import java.awt.*;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 15:07
 */
public class RandomColorComponent extends BackgroundEntity {
    public RandomColorComponent(String userName) {
        long start = System.currentTimeMillis();
        setBackgroundColor(new Color(246, 246, 246));
        setWidth(560);

        ColorBean colorBean = RandomColor.randomColor();

        TextEntity textTitle = new TextEntity();
        textTitle.setTextContent(userName + "选中的幸运颜色是" + colorBean.getTitle());
        textTitle.setFontSize(20.0f);
        textTitle.setX(20F);
        textTitle.setY(32f);
        textTitle.setFontColor(colorBean.getColor());
        textTitle.setContentWidth(400);
        TextEntity detailTitle = new TextEntity();

        if(UserConfigUtil.userConfig.getLastSelectedColor() != null){
            TextEntity lastColor = new TextEntity();
            lastColor.setTextContent(userName + "上一次抽中了" + UserConfigUtil.userConfig.getLastSelectedColor());
            lastColor.setFontSize(12.0f);
            lastColor.setX(20f);
            lastColor.setY(242f);
            lastColor.setFontColor(new Color(168, 168, 168));
            getChildren().add(lastColor);
        }


        detailTitle.setTextContent(colorBean.getDetail().replace("<name>", userName));
        detailTitle.setFontSize(12.0f);
        detailTitle.setFontColor(new Color(180, 180, 180));
        detailTitle.setX(20F);
        detailTitle.setY(80f);
        detailTitle.setContentWidth(400);

        PolygonEntity polygonEntity = new PolygonEntity(new int[]{560, 0, 560, 300, 340, 300, 460, 0},
                colorBean.getColor());

        TextEntity luckyNumber = new TextEntity();
        final int complexNumberPercent = 25;
        String luckyNumberString = "";
        if (RandomPercent.randomTrue(complexNumberPercent)) {
            luckyNumberString = RandomNumber.getRandomInt(0, 100) + "+" + RandomNumber.getRandomInt(0, 100) + "i";
            luckyNumber.setTextContent("与此同时，抽到了幸运复数为： " + luckyNumberString);
        } else {
            luckyNumberString = RandomNumber.getRandomInt(0, 100) + "";
            luckyNumber.setTextContent("与此同时，抽到了幸运数字为： " + luckyNumberString);
        }
        luckyNumber.setFontColor(new Color(168, 168, 168));
        luckyNumber.setX(20F);
        luckyNumber.setY(260F);
        luckyNumber.setFontSize(12.0f);

        getChildren().add(polygonEntity);
        getChildren().add(textTitle);
        getChildren().add(detailTitle);
        getChildren().add(luckyNumber);

        //对于浅色，使用深色模式
        if(colorBean.isLight() && !colorBean.getColor().equals(Color.WHITE)){
            //setBackgroundColor(Color.darkGray);
            textTitle.setFontColor(Color.BLACK);
        }

        UserConfigUtil.userConfig.setLastSelectedColor(colorBean.getTitle());
        System.out.println("随机颜色摘要：" + colorBean.getTitle() + " "+luckyNumberString +" 生成用时:" +(System.currentTimeMillis()-start)+"ms");
    }
}
