/**
 * @file Sfondo.java
 *
 * @author Shayan
 * @version 1.0
 *
 * @brief Thread che gestisce lo sfondo e gli elementi dinamici del gioco.
 *
 * il file contiene la classe che si occupa di controllare tutti gli oggetti che devono essere messi a video 
 */
class Sfondo extends Thread {

    /**
     * Riferimento al pannello principale del gioco.Contiene lo stato globale della partita e le strutture dati condivise.
     */
    MyPanel m;

    /**
     * Timer utilizzato per determinare il momento di spawn degli asteroidi.
     */
    Long timerDettagli;

    /**
     * Frequenza di spawn degli asteroidi espressa in millisecondi.
     */
    int frequenzaAsteroidiSpawn;

    /**
     * @brief Costruttore della classe Sfondo.
     *
     * inizializza la classe passando il pannello su cui mettere i oggetti
     *
     * @param m pannello principale del gioco
     */
    public Sfondo(MyPanel m) {
        this.m = m;
        frequenzaAsteroidiSpawn = 1000;
        timerDettagli = System.currentTimeMillis() + frequenzaAsteroidiSpawn;
        this.setName("Sfondo Thread");
    }

    /**
     * @brief Metodo principale della classe Thread ovvero run()
     *
     * Gestisce il ciclo di aggiornamento del gioco. Dopo aver atteso
     * l’inizializzazione del pannello, genera le stelle iniziali e
     * avvia un ciclo infinito che:
     * - aggiorna il movimento della nave
     * - gestisce lo spawn dei pianeti
     * - aggiorna e rimuove le stelle
     * - gestisce lo spawn degli Dettagli
     *
     */
    @Override
    public void run() {

        // Attesa inizializzazione pannello
        while (m.getWidth() == 0 || m.getHeight() == 0) {
            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Generazione iniziale delle stelle
        synchronized (m.stelle) {
            for (int i = 0; i < m.getWidth(); i++) {
                m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), m.r.nextInt(0, m.getHeight()), 7, m));
            }
        }

        // Ciclo principale del gioco
        while (true) {

            // Gestione movimento nave
            if (m.isPressed) {
                if (m.xNave + m.paddingX + m.movimento <= m.getWidth() && m.xNave + m.movimento >= 0)
                    m.xNave += m.movimento;
                else if (m.xNave + m.paddingX + m.movimento > m.getWidth())
                    m.xNave = m.getWidth() - m.paddingX + 1;
                else if (m.xNave + m.movimento < 0)
                    m.xNave = 0;
            } else {
                m.yNave = m.getHeight() - m.paddingY;
            }

            // Spawn pianeti
            if (System.currentTimeMillis() >= m.timer) {
                m.pianeti.add(new Pianeti(m.r.nextInt(0, m.getWidth()), 0, 0, m, m.immaginiPianeti.get(m.r.nextInt(1, m.NPianeti))));
                m.timer = System.currentTimeMillis() + m.r.nextLong(m.frequezaminimaPianeti, m.frequezaMassimaPianeti);
            }

            // Aggiornamento stelle
            synchronized (m.stelle) {
                for (int i = 0; i < m.getWidth() / 75; i++)
                    m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), 0, 7, m));

                for (int i = 0; i < m.stelle.size(); i++) {
                    m.stelle.get(i).sposta();
                    if (m.stelle.get(i).isFinita())
                        m.stelle.remove(i);
                }
            }

            // Spawn Dettagli
            if (System.currentTimeMillis() >= timerDettagli) {
                m.dettagli.add(new Dettagli(m.r.nextInt(0, m.getWidth()), 0, 0, m, m.immaginiDettagli.get(m.r.nextInt(0, m.NpngPerDettagliImmagini))));
                timerDettagli = System.currentTimeMillis() + frequenzaAsteroidiSpawn;
            }

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
