package com.duoduo.Bean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: goatduoduo
 * @Description: 插入内部图像
 * @Date: Created in 2022/11/13 12:56
 */
public class InnerImageEntity extends ChildEntity {
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 图片宽度
     */
    private int imgWidth = 100;
    /**
     * 图片宽度
     */
    private int imgHeight = 100;


    @Override
    public void render(Graphics2D g2d) {
        //在x y处生成图片
        File file = new File(imagePath);
        try {
            InputStream url = this.getClass().getResourceAsStream(imagePath);
            assert url != null;
            BufferedImage image = (BufferedImage) ImageIO.read(url);

            g2d.drawImage(image, x.intValue(), y.intValue(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 生成图片
     * @param imagePath /images/infinitode/Resource-Scalar.webp
     */
    public InnerImageEntity(String imagePath, Float x,Float y) {
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
