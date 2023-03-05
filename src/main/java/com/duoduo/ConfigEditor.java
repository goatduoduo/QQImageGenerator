package com.duoduo;

import com.duoduo.config.GlobalConfig;
import com.duoduo.config.UserConfig;
import com.duoduo.util.ConfigUtil;
import com.duoduo.util.Operations;

/**
 * @Author: goatduoduo
 * @Description: 用户配置工具，将在之后版本设置为可视化窗口
 * @Date: Created in 2023/3/5 15:45
 */
public class ConfigEditor {
    public static void addUser(GlobalConfig config, String userName, String qq, Boolean replace) {
        UserConfig temp = new UserConfig(userName, qq);
        for (UserConfig e : config.getUserConfigs()) {
            if (e.getName().equals(userName)) {
                if (replace) {
                    config.getUserConfigs().remove(e);
                    System.out.println(userName + "已存在，开始替代");
                } else {
                    System.out.println(userName + "已存在，操作取消");
                    return;
                }
            }
        }
        config.getUserConfigs().add(temp);
    }

    public static void removeUser(GlobalConfig config, String userName) {
        for (UserConfig e : config.getUserConfigs()) {
            if (e.getName().equals(userName)) {
                config.getUserConfigs().remove(e);
                System.out.println(userName + "已存在，开始移除");
                return;
            }
        }
        System.out.println(userName + "不存在，无法删除");
    }

    public static void clearOperation(GlobalConfig config) {
        config.getOperationList().clear();
        System.out.println("所有操作序列已清理");
    }

    public static void addOperation(GlobalConfig config, String operation) {
        Operations operations = Operations.fromString(operation);
        System.out.println(operations);
        if (operations != null) {
            config.getOperationList().add(operations.getSymbol());
            System.out.println("已添加" + operation);
        } else {
            System.out.println("无效的操作" + operation);
        }

    }
}
