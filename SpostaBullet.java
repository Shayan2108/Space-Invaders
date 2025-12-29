/**
 * @file SpostaBullet.java
 * 
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 * 
 * @brief Thread per aggiornare continuamente la posizione dei proiettili
 *
 *        La classe gestisce il movimento di tutti i proiettili presenti
 *        nel pannello ogni 33ms
 */
class SpostaBullet extends Thread {

    /**
     * pannello principale del gioco, usato per accedere alla lista dei proiettili
     */
    MyPanel m;

    /**
     * @brief costruttore del thread
     * @param m pannello principale del gioco
     */
    SpostaBullet(MyPanel m) {
        this.m = m;
    }

    /**
     * @brief metodo principale del thread Ovvero run()
     *
     *        Per ogni proiettile nella lista, chiama il metodo sposta().
     *        Il thread dorme per 33ms.
     */
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < m.bullets.size(); i++) {
                m.bullets.get(i).sposta();
            }
            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}