/**
 * @file GUI.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe che gestisce l'interfaccia grafica del gioco
 *
 * La classe GUI estende Thread e si occupa della creazione
 * della finestra principale, del CardLayout e della gestione
 * delle schermate di gioco.
 */
import java.awt.*;
import javax.swing.*;

public class GUI extends Thread {

    /** indica lo stato attuale del gioco */
    static volatile String statoAttuale = "";

    /**
     * @brief metodo principale del thread 
     *
     *        Crea la finestra principale, inizializza il CardLayout,
     *        aggiunge le schermate e imposta le proprietà della finestra.
     */
    @Override
    public void run() {

        // creazione della finestra principale
        JFrame frame = new JFrame();

        // layout che permette di cambiare schermata
        CardLayout cl = new CardLayout();

        // pannello contenitore che gestisce le schermate
        JPanel contenitore = new JPanel(cl);

        // pannello del gioco
        JPanel game = new MyPanel(cl, contenitore);

        // schermata iniziale
        JPanel schermataIniziale = new SchermataIniziale(cl, contenitore);

        // aggiunta dei pannelli al CardLayout
        contenitore.add(game, "GAME");
        contenitore.add(schermataIniziale, "START");

        // mostra la schermata iniziale
        cl.show(contenitore, "START");

        // impostazioni della finestra
        frame.setTitle("Space Invaders");
        frame.setLocation(new Point(100, 100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1259, 718);

        // aggiunta del pannello principale
        frame.add(contenitore);

        // impostazione dell'icona della finestra
        ImageIcon icona = new ImageIcon(getClass().getResource("Logo1.png"));
        frame.setIconImage(icona.getImage());

        // rende visibile la finestra
        frame.setVisible(true);
    }
}
