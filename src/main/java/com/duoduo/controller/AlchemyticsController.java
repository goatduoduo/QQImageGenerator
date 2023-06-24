package com.duoduo.controller;

import com.alibaba.fastjson2.JSON;
import com.duoduo.bean.alchemytics.AlchemyticsVO;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/6/24 12:41
 */
public class AlchemyticsController implements InitData{
    public AnchorPane newEleImg;
    public Label unlockedNum;
    public Label unlockedDetail;
    public HBox myEleImg;
    public Label myName;
    public Label myEleName;
    public HBox targetEleImg;
    public Label targetName;
    public Label targetEleName;
    public Label message;
    public Label newEleMerge;
    public Label globalMessage;

    @Override
    public void initData(String json) {
        AlchemyticsVO vo= JSON.parseObject(json, AlchemyticsVO.class);
        unlockedDetail.setText(vo.getUnlockedDetail());
        unlockedNum.setText(vo.getUnlockedNum());
        message.setText(vo.getMessage());
        globalMessage.setText(vo.globalMessage);

        myName.setText(vo.getMyName());
        myEleImg.setStyle(getImageStyle(vo.getMyEleImg()));
        myEleName.setText(vo.getMyEleName());

        targetName.setText(vo.getTargetName());
        targetEleImg.setStyle(getImageStyle(vo.getTargetEleImg()));
        targetEleName.setText(vo.getTargetEleName());

        if(vo.getNewEleImg()!=null){
            newEleImg.setStyle(getImageStyle(vo.getNewEleImg()));
            newEleMerge.setText(vo.getNewEleMerge());
        }else{
            newEleMerge.setText("这两者无法合成！");
        }
    }

    private String getImageStyle(String imageId){
        return "-fx-background-image: url(\"alchemytics/img/"+ imageId +".png\"); -fx-background-repeat: no-repeat; -fx-background-position: center;";
    }
}
