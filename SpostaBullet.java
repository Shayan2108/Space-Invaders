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
                cicloBullet:
                for (int i = 0; i < m.bullets.size(); i++) {
                    for (int j = 0; j < m.nemici.size(); j++) {
                        if (m.bullets.get(i).x > m.nemici.get(j).x
                                && m.bullets.get(i).x < (m.nemici.get(j).x + m.nemici.get(j).grandezzaPianeta)
                                && m.bullets.get(i).y > m.nemici.get(j).y
                                && m.bullets.get(i).y < (m.nemici.get(j).y + m.nemici.get(j).grandezzaPianeta)) {
                            if (m.nemici.get(j).dardiNecessariPerMorte - 1 == 0) {
                                m.esplosioni.add(new Esplosioni(m.bullets.get(i).x - 50, m.bullets.get(i).y - 50,
                                        m.nemici.get(j).velocita));
                                synchronized (m.nemici) {
                                    m.nemici.remove(m.nemici.get(j));
                                }
                            } else {
                                m.esplosioni.add(new Esplosioni(m.bullets.get(i).x - 50, m.bullets.get(i).y -50,
                                        m.nemici.get(j).velocita));
                                m.nemici.get(j).dardiNecessariPerMorte--;
                                m.bullets.remove(m.bullets.get(i));
                                continue cicloBullet;
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
