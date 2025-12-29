import java.awt.*;
import javax.swing.*;

public class GUI extends Thread{
    static volatile String statoAttuale = "";
    @Override
    public void run() {
        JFrame frame = new JFrame();
        CardLayout cl = new CardLayout();
        JPanel contenitore = new JPanel(cl);
        JPanel game = new MyPanel(cl,contenitore);
        JPanel schermataIniziale = new SchermataIniziale(cl, contenitore);
        contenitore.add(game,"GAME");
        contenitore.add(schermataIniziale,"START");
        cl.show(contenitore, "START");
        frame.setVisible(true);
        frame.setLocation(new Point(100, 100));
        frame.setTitle("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(contenitore);
        frame.setSize(1259, 718);
        ImageIcon icona = new ImageIcon();
        icona = new ImageIcon(getClass().getResource("Logo.jpeg"));
        frame.setIconImage(icona.getImage());


    }
}