package com.duoduo.Bean;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.duoduo.Util.ImageUtil;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: goatduoduo
 * @Description: 插入文本内容
 * @Date: Created in 2022/11/13 12:55
 */
public class TextEntity extends ChildEntity {
    /*=====================文本内容===================*/
    /**
     * 文本内容
     */
    private String textContent;
    /**
     * 字体名称
     */
    private String fontName = "微软雅黑";
    /**
     * 字体文件路径
     */
    private String fontFilePath = "";
    /**
     * 字体尺寸
     */
    private float fontSize = 20f;
    /**
     * 字体风格
     */
    private int fontStyle = Font.PLAIN;
    /**
     * 字体颜色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 字体间距，默认值为零，与当前字体尺寸相关
     */
    private Integer fontSpace = 0;
    /**
     * 行距
     */
    private Integer linePadding = 10;
    /**
     * 文本透明度：值从0-1.0，依次变得不透明
     */
    private float textTransparency = 1.0f;
    /**
     * 左边距
     */
    private Integer textLeftPadding = 0;
    /**
     * 右边距
     */
    private Integer textRightPadding = 0;
    /**
     * 每行居中
     */
    private boolean isCenterLine;
    /**
     * 每行限制长度
     */
    private Integer contentWidth = 600;

    @Override
    public void render(Graphics2D g2d) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        boolean contains = ArrayUtil.contains(ge.getAvailableFontFamilyNames(), fontName);
        Font font = null;
        if (contains) {
            font = new Font(fontName, fontStyle, (int) fontSize);
        } else {
            boolean isOk = true;
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFilePath)).deriveFont(fontStyle).deriveFont(fontSize);
                ge.registerFont(font);
            } catch (FontFormatException e) {
                //log.error("加载字体失败！", e);
                isOk = false;
            } catch (IOException e) {
                isOk = false;
                //log.error("加载字体文件失败！", e);
            }
            if (!isOk) {
                return;
            }
        }
        //设置字体透明度，字体透明度只在背景不透明时才生效
        if (textTransparency <= 1.0f) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, textTransparency));
        }
        //字体信息
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, fontSpace);
        font = font.deriveFont(attributes);
        g2d.setFont(font);
        g2d.setColor(fontColor);
        //对文本进行处理
        int contentWidth = this.contentWidth - textLeftPadding - textRightPadding;
        List<String> lines = ImageUtil.getLines(textContent, contentWidth, fontSpace, fontSize);
        float lineWidth;
        float paddingAdd = 0;
        //逐行渲染
        //当前行文字的长度
        if (CollectionUtil.isNotEmpty(lines)) {
            String contentLine;
            for (int i = 0; i < lines.size(); i++) {
                contentLine = lines.get(i).trim();
                //设置每行居中
                if (isCenterLine) {
                    lineWidth = ((1 + fontSpace) * fontSize) * contentLine.trim().length();
                    paddingAdd = (contentWidth - lineWidth) / 2;
                    x = paddingAdd + textLeftPadding;
                }
                g2d.drawString(contentLine, x, y + fontSize + (i * (fontSize + linePadding)));
            }
        }
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontFilePath() {
        return fontFilePath;
    }

    public void setFontFilePath(String fontFilePath) {
        this.fontFilePath = fontFilePath;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Integer getFontSpace() {
        return fontSpace;
    }

    public void setFontSpace(Integer fontSpace) {
        this.fontSpace = fontSpace;
    }

    public Integer getLinePadding() {
        return linePadding;
    }

    public void setLinePadding(Integer linePadding) {
        this.linePadding = linePadding;
    }

    public float getTextTransparency() {
        return textTransparency;
    }

    public void setTextTransparency(float textTransparency) {
        this.textTransparency = textTransparency;
    }

    public Integer getTextLeftPadding() {
        return textLeftPadding;
    }

    public void setTextLeftPadding(Integer textLeftPadding) {
        this.textLeftPadding = textLeftPadding;
    }

    public Integer getTextRightPadding() {
        return textRightPadding;
    }

    public void setTextRightPadding(Integer textRightPadding) {
        this.textRightPadding = textRightPadding;
    }

    public boolean isCenterLine() {
        return isCenterLine;
    }

    public void setCenterLine(boolean centerLine) {
        isCenterLine = centerLine;
    }

    public Integer getContentWidth() {
        return contentWidth;
    }

    public void setContentWidth(Integer contentWidth) {
        this.contentWidth = contentWidth;
    }

    public void setX(Float x){
        this.x= x;
    }
    public void setY(Float y){
        this.y= y;
    }
}
