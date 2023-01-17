package com.duoduo.Util;

import cn.hutool.core.util.ArrayUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 13:09
 */
public class ImageUtil {
    public static Graphics2D getG2d(BufferedImage bf) {
        Graphics2D g2d = bf.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2d;
    }
    /**
     * 获取字体分行后list
     * todo 增加对换行符的支持
     * @param textContent 文本内容
     * @param contentWith 文本宽度
     * @param fontSpace   字体间距
     * @param fontSize    字体尺寸
     * @return java.util.List<java.lang.String>
     */
    public static java.util.List<String> getLines(String textContent, Integer contentWith, Integer fontSpace, float fontSize) {
        //每一行的字体个数
        int lineCont = (int) (contentWith / ((1 + fontSpace) * fontSize));
        char[] chars = textContent.toCharArray();
        List<String> lines = new ArrayList<>();
        char[] charArrayTemp = new char[lineCont];
        int indexTemp = 0;
        for (char c : chars) {
            if (indexTemp == lineCont) {
                //一行数据已满，开始新的一行
                lines.add(new String(charArrayTemp));
                indexTemp = 0;
                charArrayTemp = new char[lineCont];
            }
            charArrayTemp[indexTemp] = c;
            indexTemp++;
        }
        //最后一行
        if (ArrayUtil.isNotEmpty(charArrayTemp)) {
            lines.add(new String(charArrayTemp));
        }
        return lines;
    }
}
