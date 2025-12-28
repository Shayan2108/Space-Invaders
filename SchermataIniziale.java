import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SchermataIniziale  extends JPanel {
    CardLayout cl;
    JPanel contenitore;
    JButton bottone;
    BufferedImage image;
    public SchermataIniziale(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        this.setLayout(null);
        try {
            image = ImageIO.read(new File("alt f4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(getClass().getResource("Start_button.png"));
        bottone = new JButton(icon);
        bottone.setBounds(920, 62, bottone.getPreferredSize().width, bottone.getPreferredSize().height);  
        bottone.setBackground(Color.BLACK);
        bottone.setBorderPainted(false);
        bottone.setOpaque(false);
        bottone.setContentAreaFilled(false);
        bottone.setFocusPainted(false);

        this.add(bottone);
        
        bottone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(contenitore, "GAME");
                GUI.statoAttuale = "GAME";
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                frame.setSize(new Dimension(400 , 800));
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}

