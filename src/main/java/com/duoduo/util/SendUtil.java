package com.duoduo.util;

import cn.hutool.core.swing.clipboard.ImageSelection;
import com.duoduo.config.UserConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;

/**
 * @Author: goatduoduo
 * @Description: 将信息发送给指定用户的工具类
 * @Date: Created in 2023/3/4 19:59
 */
public class SendUtil {
    /**
     * 配置文件的路径
     */
    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "\\";

    /**
     * 将已生成的图片发送给QQ用户
     * @param user 用户
     */
    public static void sendImageToQq(UserConfig user) {
        System.out.println("正在发送："+user.getName());
        try {
            // 读取图片文件
            File file = new File(CONFIG_FILE_PATH + "\\users\\" + user.getName() + ".jpg");
            Image image = ImageIO.read(file);

            // 将图片复制到剪贴板
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            ImageSelection selection = new ImageSelection(image);
            clipboard.setContents(selection, null);

            //打开qq
            String programPath = "tencent://message/?uin=" + user.getQq();
            // 使用Desktop类打开程序
            Desktop.getDesktop().browse(new URI(programPath));
            Thread.sleep(300);

            // 模拟键盘操作进行粘贴
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            //发送消息
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_S);
            Thread.sleep(200);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_F4);
            Thread.sleep(40);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
