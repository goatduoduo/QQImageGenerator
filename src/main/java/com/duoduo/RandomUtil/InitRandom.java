package com.duoduo.RandomUtil;

import com.duoduo.Bean.ColorBean;
import com.duoduo.Util.CsvUtil;

import java.awt.*;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/20 21:16
 */
public class InitRandom {
    /**
     * 读取csv文件以初始化
     */
    public void initCsvs(){
        long start = System.currentTimeMillis();
        InputStream url=this.getClass().getResourceAsStream("/data/colors.csv");
        List<String[]> list = CsvUtil.parseCsv(url);
        for (String[] e:list){
            //System.out.println(Arrays.asList(list.get(i)));
            ColorBean temp = new ColorBean(new Color(Integer.parseInt(e[1]),Integer.parseInt(e[2]),Integer.parseInt(e[3])),e[0],e[4],Integer.parseInt(e[5]));
            RandomColor.COLORS.add(temp);
        }


        System.out.println("初始化用时："+ (System.currentTimeMillis()-start)+"ms");
    }
}
