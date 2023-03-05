import com.duoduo.component.MiningIncCalculation;
import com.duoduo.config.GlobalConfig;
import com.duoduo.util.ConfigUtil;
import com.duoduo.util.Operations;
import org.junit.Test;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/3/3 9:47
 */
public class TestQQBot {
    @Test
    public void testAddUser() {
        GlobalConfig globalConfig = ConfigUtil.readUserConfig("mine", "globe", GlobalConfig.class);
        if (globalConfig == null) {
            globalConfig = new GlobalConfig();
        }
        globalConfig.generateUser("焗猫", "734****90");
        ConfigUtil.writeConfig("mine", "globe", globalConfig);
    }

    @Test
    public void testAddOperation(){
        GlobalConfig globalConfig = ConfigUtil.readUserConfig("mine", "globe", GlobalConfig.class);
        if (globalConfig == null) {
            globalConfig = new GlobalConfig();
        }
        globalConfig.getOperationList().clear();
        globalConfig.getOperationList().add(Operations.INCREMENT.getSymbol());
        globalConfig.getOperationList().add(Operations.LOOTING.getSymbol());
        globalConfig.getOperationList().add(Operations.MINING.getSymbol());
        for(String e:globalConfig.getOperationList()){
            System.out.println(Operations.fromString(e));
        }
        ConfigUtil.writeConfig("mine", "globe", globalConfig);
    }

    @Test
    public void testGenerateSend(){
        GlobalConfig globalConfig = ConfigUtil.readUserConfig("mine", "globe", GlobalConfig.class);
        MiningIncCalculation.expIncrease(globalConfig);
        MiningIncCalculation.lootGameOn(globalConfig);
        MiningIncCalculation.mineGameOn(globalConfig);
        MiningIncCalculation.showMine(globalConfig);
        ConfigUtil.writeConfig("mine", "globe", globalConfig);
    }
}
