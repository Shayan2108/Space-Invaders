
/**
 * @file Main.java
 *
 * @author Mohammad Shayan Attri Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief classe con il punto di entrata del programma
 *
 * Questa classe contiene il metodo main.
 * che associa a swing il suo Thread ovvero GUI
 * usando il thread corretto di Swing.
 */
import javax.swing.SwingUtilities;

public class Main {

    /**
     * @brief metodo di avvio del programma
     *
     *        Il metodo main crea e avvia il thread della GUI
     *        tramite SwingUtilities.invokeLater
     *
     * @param args argomenti
     */
    public static void main(String[] args) {

        // avvio della GUI nel thread grafico di Swing
        SwingUtilities.invokeLater(new GUI());
    }
}
