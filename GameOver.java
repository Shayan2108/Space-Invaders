
/**
 * @file GameOver.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Pannello della schermata di Game Over.
 *
 *        Il file contiene la classe GameOver che gestisce la schermata
 *        finale del gioco mostrando il punteggio ottenuto, il punteggio massimo
 *        e permettendo di tornare al menu iniziale o uscire dal gioco.
 */

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @class GameOver
 *
 * @brief Pannello della schermata di Game Over.
 *
 *        Mostra il punteggio del giocatore e il punteggio massimo.
 *        Permette di tornare al menu iniziale o uscire dal gioco.
 */
public class GameOver extends JPanel {

    /** layout del pannello principale che permette di cambiare schermata */
    CardLayout cl;

    /** pannello contenitore delle varie schermate */
    JPanel contenitore;

    /** immagine di sfondo della schermata Game Over */
    BufferedImage sfondo;

    /** bottone per tornare al menu iniziale */
    JButton bottoneRitorna;

    /** bottone per uscire dal gioco */
    JButton bottoneQuit;

    /** etichetta che mostra il punteggio ottenuto */
    JLabel score;

    /** etichetta che mostra il punteggio massimo */
    JLabel scoreMassimo;

    /**
     * @brief Costruttore della schermata di Game Over.
     *
     *        Inizializza gli attributi, i pulsanti e carica lo sfondo.
     *        Aggiunge i listener per aggiornare i valori quando la schermata viene
     *        mostrata.
     *
     * @param cl          CardLayout principale del contenitore
     * @param contenitore pannello contenitore delle schermate
     */
    public GameOver(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;

        this.setLayout(null);

        /** creazione etichette per punteggi */
        score = new JLabel();
        scoreMassimo = new JLabel();

        /** creazione bottone ritorna al menu iniziale */
        bottoneRitorna = new JButton();
        bottoneRitorna.setContentAreaFilled(false);
        bottoneRitorna.setBorder(null);
        bottoneRitorna.setBounds(390, 335, 170, 40);
        bottoneRitorna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(contenitore, "START");
            }
        });
        this.add(bottoneRitorna);

        /** creazione bottone per uscire dal gioco */
        bottoneQuit = new JButton();
        bottoneQuit.setContentAreaFilled(false);
        bottoneQuit.setBorder(null);
        bottoneQuit.setBounds(450, 400, 100, 50);
        bottoneQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GameOver.this);
                frame.dispose();
                System.exit(0);
            }
        });
        this.add(bottoneQuit);

        /** caricamento immagine di sfondo */
        try {
            sfondo = ImageIO.read(new File("GameOver.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * listener per aggiornare i valori dei punteggi quando la schermata viene
         * mostrata
         */
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {

                score.setLocation(240, 349);
                score.setSize(100, 25);
                score.setForeground(new Color(188, 0, 0));
                score.setFont(new Font("Arial", Font.BOLD, 32));
                score.setText(Integer.toString(MyPanel.score));

                scoreMassimo.setLocation(258, 423);
                scoreMassimo.setSize(100, 25);
                scoreMassimo.setForeground(new Color(188, 0, 0));
                scoreMassimo.setFont(new Font("Arial", Font.BOLD, 32));
                scoreMassimo.setText(Integer.toString(MyPanel.scoreMassimo));

                MyPanel.score = 0;

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GameOver.this);
                frame.setSize(600, 600);
            }
        });

        this.add(score);
        this.add(scoreMassimo);
    }

    /**
     * @brief Disegna lo sfondo della schermata di Game Over.
     *
     *        Viene richiamato automaticamente da Swing durante il repaint.
     *
     * @param g oggetto Graphics utilizzato per il disegno
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sfondo, 0, 0, 600, 600, null);
    }
}
