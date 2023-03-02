import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/2/23 9:56
 */
public class TestSaveImage {
    @Test
    void testSave(){
        int width = 500;
        int height = 500;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, width, height);
        g.drawLine(width, 0, 0, height);

        try {
            File file = new File("myimage.png");
            ImageIO.write(image, "png", file);
            System.out.println("Image saved successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
