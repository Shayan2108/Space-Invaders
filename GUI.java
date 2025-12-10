import javax.swing.*;
import java.awt.*;

public class GUI extends Thread {

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setSize(400, 800);
        frame.setVisible(true);
        frame.setLocation(new Point(100, 100));
        frame.setTitle("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new MyPanel());
    }
    
}
