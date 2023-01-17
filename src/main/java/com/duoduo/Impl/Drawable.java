package com.duoduo.Impl;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 13:02
 */
public interface Drawable {
    /**
     * 渲染图片的默认接口
     * @return 已经渲染好的图片对象
     */
    void render(Graphics2D g2d);
}
