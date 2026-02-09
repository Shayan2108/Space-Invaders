
/**
 * @author  Statella Giuseppe Salvatore, statella.giuseppe01@gmail.com
 * @version 1.0
 * @file HelpDialog.java
 * 
 * @brief Dialog modale per visualizzare i comandi del gioco.
 *
 *        Mostra le istruzioni principali per giocare a Space Invaders.
 *        Contiene un titolo, l’elenco dei comandi e un pulsante per chiudere il dialog.
 */

import javax.swing.*;
import java.awt.*;

/**
 * @class HelpDialog
 *
 * @brief Dialog dei comandi di gioco.
 * 
 *        Dialog modale che appare al centro dello schermo con le istruzioni
 *        del gioco e un bottone per chiudere la finestra.
 */
public class HelpDialog extends JDialog {

    /** Larghezza della finestra */
    private static final int WIDTH = 400;

    /** Altezza della finestra */
    private static final int HEIGHT = 400;

    /**
     * @brief Costruisce il dialog Help
     *
     *        Inizializza il layout, titolo, area comandi e bottone chiudi.
     *        La finestra è centrata rispetto alla finestra proprietaria.
     * 
     * @param owner finestra proprietaria del dialog
     */
    public HelpDialog(JFrame owner) {
        super(owner, "HELP", ModalityType.APPLICATION_MODAL);

        // Dimensioni e posizione
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(owner);
        setLayout(null);
        setUndecorated(true);

        // Sfondo semi-trasparente
        getContentPane().setBackground(new Color(0, 0, 0, 220));

        // Titolo
        JLabel title = new JLabel("COMANDI DEL GIOCO", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 22));
        title.setForeground(new Color(255, 180, 255));
        title.setBounds(0, 20, WIDTH, 30);
        add(title);

        // Area comandi testuale
        JTextArea commands = new JTextArea(
                "W: Muovi su\n" +
                        "A: Muovi sinistra\n" +
                        "S: Muovi giù\n" +
                        "D: Muovi destra\n" +
                        "P / ESC: Apri menu pausa\n" +
                        "Click sinistro del mouse: Spara");
        commands.setFont(new Font("Monospaced", Font.PLAIN, 16));
        commands.setForeground(Color.WHITE);
        commands.setBackground(new Color(0, 0, 0, 0)); // trasparente
        commands.setEditable(false);

        // Posizionamento centrato
        int commandsHeight = 200;
        commands.setBounds(25, 80, WIDTH - 50, commandsHeight);
        add(commands);

        // Bottone Chiudi
        JButton closeBtn = new JButton("Chiudi");
        closeBtn.setBounds(WIDTH / 2 - 50, HEIGHT - 80, 100, 30);
        closeBtn.setFocusPainted(false);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(80, 0, 120));
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);
    }
}
