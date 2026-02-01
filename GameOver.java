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
    JLabel score;
    JLabel scoreMassimo;

    public GameOver(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        this.setLayout(null);
        score = new JLabel();
        scoreMassimo = new JLabel();
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GameOver.this);
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
                score.setLocation(240, 349);
                score.setForeground(new Color(188, 0, 0));
                score.setFont(new Font("Arial", ALLBITS, 32));
                score.setSize(new Dimension(100, 25));
                score.setText(Integer.toString(MyPanel.score));
                scoreMassimo.setText(Integer.toString(MyPanel.scoreMassimo));
                scoreMassimo.setLocation(258, 423);
                scoreMassimo.setForeground(new Color(188, 0, 0));
                scoreMassimo.setFont(new Font("Arial", ALLBITS, 32));
                scoreMassimo.setSize(new Dimension(100, 25));
                MyPanel.score = 0;
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GameOver.this);
                frame.setSize(600, 600);
            }
        });
        this.add(score);
        this.add(scoreMassimo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sfondo, 0, 0, 600, 600, null);
    }
}
