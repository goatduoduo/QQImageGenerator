import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.duoduo.config.UserConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/12/4 19:39
 */
public class TestJson {
    private List<UserConfig> configList = new ArrayList<>();

    @Test
    public void TestJsonSetup(){
        configList.add(new UserConfig("朵朵","红色"));
        configList.add(new UserConfig("兔兔","蓝色"));

        String jsonOutput = JSON.toJSONString(configList);
        System.out.println(jsonOutput);
    }

    @Test
    public void TestJsonB(){
        String content = "[{\"USER NAME\":\"朵朵\",\"LAST SELECTED COLOR\":\"红色\"},{\"USER NAME\":\"兔兔\",\"LAST SELECTED COLOR\":\"蓝色\"}]";
        JSONObject arr = JSONObject.parseObject(content);
//        String text = "...";
//        JSONObject data = JSON.parseObject(text);
    }
}
