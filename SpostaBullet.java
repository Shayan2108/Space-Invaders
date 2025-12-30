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

            if (!m.inGioco) { // se il gioco non è partito, non muovere proiettili
                try {
                    sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            synchronized (m.bullets) {
                for (int i = m.bullets.size() - 1; i >= 0; i--) {
                    Bullets b = m.bullets.get(i);
                    b.sposta();

                    // Controllo collisioni con i nemici
                    synchronized (m.nemici) {
                        for (int j = m.nemici.size() - 1; j >= 0; j--) {
                            Nemico n = m.nemici.get(j);

                            // semplice bounding box collision
                            if (b.x + b.image.getWidth() > n.x &&
                                    b.x < n.x + n.larghezza &&
                                    b.y + b.image.getHeight() > n.y &&
                                    b.y < n.y + n.altezza) {

                                // rimuovi nemico e proiettile
                                m.nemici.remove(j);
                                m.bullets.remove(i);

                                // TODO: aggiungere animazione esplosione qui
                                new Thread(() -> {
                                    // placeholder: semplice repaint per animazione
                                    // in seguito potremo aggiungere immagini esplosione
                                    try {
                                        Thread.sleep(100); // durata esplosione
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }).start();

                                break; // il proiettile è sparito, esci dal ciclo dei nemici
                            }
                        }
                    }
                }
            }

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // ridisegna il pannello
            m.repaint();
        }
    }
}
