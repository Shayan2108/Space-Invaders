import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOver extends JPanel {
    CardLayout cl;
    JPanel contenitore;
    BufferedImage sfondo;
    JButton bottoneRitorna;
    JButton bottoneQuit;

    public GameOver(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        this.setLayout(null);
        bottoneRitorna = new JButton();
        bottoneRitorna.setContentAreaFilled(false);
        bottoneRitorna.setBounds(390, 335, 170, 40);
        bottoneRitorna.setBorder(null);
        bottoneRitorna.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(contenitore, "START");
            }

        });
        this.add(bottoneRitorna);
        bottoneQuit = new JButton();
        bottoneQuit.setContentAreaFilled(false);
        bottoneQuit.setBounds(450, 400, 100, 50);
        bottoneQuit.setBorder(null);
        bottoneQuit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(GameOver.this);
                frame.dispose();
                System.exit(0);
            }
            
        });
        this.add(bottoneQuit);
        try {
            sfondo = ImageIO.read(new File("GameOver.jpeg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GameOver.this);
                frame.setSize(600, 600);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sfondo, 0, 0, 600, 600, null);
    }
}
