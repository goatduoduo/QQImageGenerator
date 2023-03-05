package com.duoduo.component.entity;

import cn.hutool.core.util.ObjectUtil;
import com.duoduo.bean.TransferableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.duoduo.util.ImageUtil.getG2d;


/**
 * @Author: goatduoduo
 * @Description: 最基础的图片类型，所有图片继承于此，使用组合的设计模式
 * @Date: Created in 2022/11/13 11:55
 */
public class BackgroundEntity {
    /**
     * 宽度
     */
    private Integer width = 700;
    /**
     * 高度
     */
    private Integer height = 300;
    /*=================背景==============*/
    /**
     * 背景颜色
     */
    private Color backgroundColor = Color.WHITE;
    /**
     * 背景是否透明
     */
    private boolean isTransparentBackground = false;
    /**
     * 背景图片
     */
    private BufferedImage backgroundImg;

    private List<ChildEntity> children = new ArrayList<>();

    public List<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildEntity> children) {
        this.children = children;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isTransparentBackground() {
        return isTransparentBackground;
    }

    public void setTransparentBackground(boolean transparentBackground) {
        isTransparentBackground = transparentBackground;
    }

    public BufferedImage getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(BufferedImage backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public BufferedImage render() {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d;
        g2d = getG2d(bufferedImage);
        BufferedImage bgImg = backgroundImg;
        //背景是否透明
        if (ObjectUtil.isNotNull(bgImg)) {
            g2d.drawImage(bgImg.getScaledInstance(width, height, Image.SCALE_DEFAULT), 0, 0, null);
        } else if (isTransparentBackground()) {
            bufferedImage = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d = getG2d(bufferedImage);
        } else {
            g2d.setBackground(getBackgroundColor());
            g2d.clearRect(0, 0, width, height);
            //设置字体透明度，字体透明度只在背景不透明时才生效
            //isFontTransparency = true;
        }

        //画画时传递G2D为参数即可

        for(ChildEntity e : children){
            e.render(g2d);
        }
        g2d.dispose();

        //返回图片，之后将可以保存
        return bufferedImage;
    }

    public void saveImage(BufferedImage image, String path){
        try {
            // 从文件读取一张图片
            BufferedImage bufferedImage = image;

            // 将图片保存到指定位置
            File output = new File(path);
            ImageIO.write(bufferedImage, "jpg", output);

            System.out.println("Image saved to " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
