/**
 * @file GameLoop.java
 *
 * @author Mohammad Shayan Attari bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief classse che contien un Thread che aggiorna il Panel
 *
 *        la classe estende la classe Thread e ha il compito di chiamare la
 *        funzione repaint ogni 33 millisecondi
 */
public class GameLoop extends Thread {

    /**
     * è il pannello del gioco vero e proprio e viene passato come parametro
     */
    MyPanel m;

    /**
     * @brief costruttore della classe GameLoop
     *
     *        chiede come parametro il pannello su cui fare la repaint
     *
     *        la classe viene usata nella MyPanel.
     *
     * @param m pannello principale del gioco
     */
    public GameLoop(MyPanel m) {
        this.m = m;
        // setto il nome per fare vari test
        this.setName("GameLoop Thread");
    }

    /**
     * @brief Metodo principale della classe Thread ovvero run()
     *
     *        esugue un ciclo infinito che chiama la funzione repaint()
     *        ogni 33 millisecondi per aggiornare la grafica del gioco.
     *
     */
    @Override
    public void run() {
        while (true) {
            // aggiorna il pannello principale del gioco
            m.repaint();

            // disattiva sparo multiplo
            if (m.sparoMultiplo && System.currentTimeMillis() > m.fineSparoMultiplo) {
                m.sparoMultiplo = false;
            }

            // disattiva scudo
            if (m.scudoAttivo && System.currentTimeMillis() > m.fineScudo) {
                m.scudoAttivo = false;
            }

            // disattiva respingi
            if (m.respingiAttivo && System.currentTimeMillis() > m.fineRespingi) {
                m.respingiAttivo = false;
            }

            if (m.rallentaAttivo && System.currentTimeMillis() > m.fineRallenta) {
                m.rallentaAttivo = false;
            }

            try {
                // pausa per mantenere circa 30 frame al secondo
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
