import com.duoduo.util.random.RandomColor;
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

}
