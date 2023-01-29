import com.duoduo.util.random.RandomColor;
import com.duoduo.util.random.RandomNumber;
import com.duoduo.util.random.RandomPercent;
import org.junit.Test;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/20 17:23
 */
public class TestRandom {
    @Test
    public void testRandomColor(){
        for (int i=0;i<1000;i++){
            if(RandomColor.randomColor().getTitle().equals("天谴色")){
                System.out.println("命中天谴");
            }

        }
    }

    @Test
    public void getCsv(){
        RandomColor randomColor=new RandomColor();
        randomColor.test();
    }

    @Test
    public void testOnePercent(){
        for(int i=0;i<1000;i++){
            if(RandomPercent.randomTrue(1)){
                System.out.println("幸运啊");
            }
        }
    }

}
