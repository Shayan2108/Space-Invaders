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
            synchronized (m.bullets) {
                for (int i = m.bullets.size() - 1; i >= 0; i--) {
                    Bullets b = m.bullets.get(i);
                    // b.sposta();

                    // Controllo collisioni con i nemici
                    synchronized (m.nemici) {
                        for (int j = m.nemici.size() - 1; j >= 0; j--) {
                            Nemico n = m.nemici.get(j);

                            // semplice bounding box collision
                            if (b.x > n.x && b.x < n.x + n.grandezzaPianeta && b.y > n.y
                                    && b.y < n.y + n.grandezzaPianeta) {
                                for (int z = 0; z < b.movimento; z++) {
                                    if (n.image.getRGB((b.x - n.x), (b.y - n.y) + z) >>> 24 != 0) // rimuovi nemico e
                                    // proiettile
                                    {
                                        if (n.dardiNecessariPerMorte - 1 == 0) {
                                            m.esplosioni.add(new Esplosioni(b.x, n.y + z, n.velocita));
                                            m.nemici.remove(j);
                                        } else {
                                            m.esplosioni.add(new Esplosioni(b.x, b.y + z, n.velocita));
                                            n.dardiNecessariPerMorte--;
                                        }

                                            m.bullets.remove(i);


                                        break;
                                    }
                                } // il proiettile è sparito, esci dal ciclo dei nemici
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

        }
    }
}
