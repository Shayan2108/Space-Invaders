
/**
 * @file SchermataIniziale.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Gestisce la schermata iniziale del gioco
 *
 * Questo file contiene la classe SchermataIniziale che mostra
 * l'immagine iniziale e il bottone per avviare il gioco.
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SchermataIniziale extends JPanel {

    /**
     * @brief layout per il cambio schermata
     *
     *        Serve per passare dalla schermata iniziale al gioco.
     */
    CardLayout cl;

    /**
     * @brief pannello contenitore principale
     *
     *        Contiene tutte le schermate del gioco.
     */
    JPanel contenitore;

    /**
     * @brief bottone di avvio del gioco
     *
     *        Quando viene premuto fa partire il gioco.
     */
    JButton bottone;

    /**
     * @brief immagine di sfondo della schermata iniziale
     */
    BufferedImage image;

    /**
     * @brief costruttore della schermata iniziale
     *
     *        Inizializza lo sfondo, il bottone start e collega
     *        il pannello al CardLayout.
     *
     * @param cl          layout per il cambio schermata
     * @param contenitore pannello che contiene le schermate
     */
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
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                frame.setSize(1259, 718);
            }
        });

        this.add(bottone);

        bottone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(contenitore, "GAME");
                GUI.statoAttuale = "GAME";
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                frame.setSize(new Dimension(400, 800));
            }
        });
    }

    /**
     * @brief disegno della schermata iniziale
     *
     *        Disegna l'immagine di sfondo del menu iniziale.
     *
     * @param g oggetto Graphics per il disegno
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
