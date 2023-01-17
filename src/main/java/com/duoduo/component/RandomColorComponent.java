package com.duoduo.component;

import com.duoduo.util.CsvUtil;
import com.duoduo.component.entity.BackgroundEntity;
import com.duoduo.bean.ColorBean;
import com.duoduo.component.entity.PolygonEntity;
import com.duoduo.component.entity.TextEntity;
import com.duoduo.util.random.RandomColor;
import com.duoduo.util.random.RandomNumber;
import com.duoduo.util.random.RandomPercent;
import com.duoduo.config.UserConfig;
import com.duoduo.util.ConfigUtil;

import java.awt.*;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 15:07
 */
public class RandomColorComponent extends BackgroundEntity {
    public RandomColorComponent(String userName) {
        initColorCsv();
        long start = System.currentTimeMillis();
        setBackgroundColor(new Color(246, 246, 246));
        setWidth(560);
        //读取用户信息的json文件，如果是新用户则会创建一个新的默认文件
        UserConfig user = ConfigUtil.readUserConfig(userName,UserConfig.class);
        if(user == null){
            user = new UserConfig(userName);
        }

        ColorBean colorBean = RandomColor.randomColor();
        //颜色大标题
        TextEntity textTitle = new TextEntity();
        textTitle.setTextContent(userName + "选中的幸运颜色是" + colorBean.getTitle());
        textTitle.setFontSize(20.0f);
        textTitle.setX(20F);
        textTitle.setY(32f);
        textTitle.setFontColor(colorBean.getColor());
        textTitle.setContentWidth(400);
        TextEntity detailTitle = new TextEntity();

        //上一次选中的颜色
        if(user.getLastSelectedColor() != null){
            TextEntity lastColor = new TextEntity();
            lastColor.setTextContent(userName + "上一次抽中了" + user.getLastSelectedColor());
            lastColor.setFontSize(12.0f);
            lastColor.setX(20f);
            lastColor.setY(242f);
            lastColor.setFontColor(new Color(168, 168, 168));
            getChildren().add(lastColor);
        }

        //颜色介绍小字
        detailTitle.setTextContent(colorBean.getDetail().replace("<name>", userName));
        detailTitle.setFontSize(12.0f);
        detailTitle.setFontColor(new Color(180, 180, 180));
        detailTitle.setX(20F);
        detailTitle.setY(80f);
        detailTitle.setContentWidth(400);

        //右侧颜色梯形
        PolygonEntity polygonEntity = new PolygonEntity(new int[]{560, 0, 560, 300, 340, 300, 460, 0},
                colorBean.getColor());

        //幸运数字
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

        user.setLastSelectedColor(colorBean.getTitle());
        ConfigUtil.writeConfig(userName,user);
        System.out.println("随机颜色摘要：" + colorBean.getTitle() + " "+luckyNumberString +" 生成用时:" +(System.currentTimeMillis()-start)+"ms");
    }

    /**
     * 读取csv文件以初始化
     */
    private void initColorCsv(){
        long start = System.currentTimeMillis();
        InputStream url=this.getClass().getResourceAsStream("/data/colors.csv");
        List<String[]> list = CsvUtil.parseCsv(url);
        for (String[] e:list){
            ColorBean temp = new ColorBean(new Color(Integer.parseInt(e[1]),Integer.parseInt(e[2]),Integer.parseInt(e[3])),e[0],e[4],Integer.parseInt(e[5]));
            RandomColor.COLORS.add(temp);
        }
        System.out.println("初始化用时："+ (System.currentTimeMillis()-start)+"ms");
    }
}
