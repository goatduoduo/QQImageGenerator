package com.duoduo.Bean;

import java.awt.*;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 19:01
 */
public class PolygonEntity extends ChildEntity {
    /**
     * 按照x1 y1 x2 y2...的顺序排列即可
     */
    private int[] points;

    private Color color = Color.BLACK;

    @Override
    public void render(Graphics2D g2d) {
        int[] x= new int[points.length/2];
        int[] y= new int[points.length/2];
        for(int i=0; i<points.length; i++){
            if(i%2==0){
                x[i/2] = points[i];
            }else{
                y[i/2] = points[i];
            }
        }
        g2d.setColor(color);
        g2d.fillPolygon(x,y,points.length/2);
    }

    public PolygonEntity(int[] points, Color color) {
        this.points = points;
        this.color = color;
    }
}
