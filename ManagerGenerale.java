/**
 * @file Sfondo.java
 *
 * @author Shayan
 * @version 1.0
 *
 * @brief Thread che gestisce lo sfondo e gli elementi dinamici del gioco.
 *
 *        il file contiene la classe che si occupa di controllare tutti gli
 *        oggetti che devono essere messi a video
 */
class ManagerGenerale extends Thread {

    /**
     * Riferimento al pannello principale del gioco.Contiene lo stato globale della
     * partita e le strutture dati condivise.
     */
    MyPanel m;

    /**
     * Timer utilizzato per determinare il momento di spawn degli asteroidi.
     */
    Long timerDettagli;
    /**
     * Timer utilizzato per determinare il momento di spawn dei nemici.
     */
    Long timerNemici;

    /**
     * Frequenza di spawn degli asteroidi espressa in millisecondi.
     */
    int frequenzaDettagliSpawn;

    public volatile boolean running = true;

    /**
     * @brief Costruttore della classe Sfondo.
     *
     *        inizializza la classe passando il pannello su cui mettere i oggetti
     *
     * @param m pannello principale del gioco
     */
    public ManagerGenerale(MyPanel m) {
        this.m = m;
        frequenzaDettagliSpawn = 4000;
        timerDettagli = System.currentTimeMillis() + frequenzaDettagliSpawn;
        timerNemici = System.currentTimeMillis()
                + m.r.nextInt(Nemico.frequenzaAggiuntaNemicoMinima, Nemico.frequenzaAggiuntaNemicoMassima);
        this.setName("Sfondo Thread");
    }

    /**
     * @brief Metodo principale della classe Thread ovvero run()
     *
     *        Gestisce il ciclo di aggiornamento del gioco. Dopo aver atteso
     *        l’inizializzazione del pannello, genera le stelle iniziali e
     *        avvia un ciclo infinito che:
     *        - aggiorna il movimento della nave
     *        - gestisce lo spawn dei pianeti
     *        - aggiorna e rimuove le stelle
     *        - gestisce lo spawn degli Dettagli
     *
     */
    @Override
    public void run() {

        // Attesa inizializzazione pannello
        // while (m.getWidth() == 0 || m.getHeight() == 0) {
        // try {
        // sleep(33);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }

        // Generazione iniziale delle stelle
        // synchronized (m.stelle) {
        // for (int i = 0; i < m.getWidth(); i++) {
        // m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), m.r.nextInt(0,
        // m.getHeight()), 7, m));
        // }
        // }

        // Ciclo principale del gioco
        while (running == true && !m.gameOver) {

            if (m.isPaused) {
                try {
                    Thread.sleep(33); // blocca loop ma non CPU
                } catch (InterruptedException e) {
                }
                continue;
            }
            // Gestione movimento nave
            if (m.isPressed) {
                if (m.xNave + m.paddingX + m.movimento <= m.getWidth() && m.xNave + m.movimento >= 0) {
                    m.xNave += m.movimento;
                    m.hitboxNave.x += m.movimento;
                    m.hitBoxScudo.x += m.movimento;
                } else if (m.xNave + m.paddingX + m.movimento > m.getWidth()) {
                    m.xNave = m.getWidth() - m.paddingX + 1;
                    m.hitboxNave.x = m.getWidth() - m.paddingX + 1;
                    m.hitBoxScudo.x = m.getWidth() - m.paddingX + 1 - 10;
                } else if (m.xNave + m.movimento < 0) {
                    m.hitboxNave.x = 0;
                    m.xNave = 0;
                    m.hitBoxScudo.x = 0 - 10;
                }
            } else {
                m.yNave = m.getHeight() - m.paddingY;
                m.hitboxNave.y = m.getHeight() - m.paddingY;
                m.hitBoxScudo.y = m.getHeight() - m.paddingY - 10;
            }
            for (int i = 0; i < m.powerUps.size(); i++) {
                if (m.powerUps.get(i).hitbox.intersects(m.hitboxNave)) {
                    synchronized (m.powerUps) {
                        if (!m.powerUps.get(i).iniziatoUnaVolta)
                            m.powerUps.get(i).effettoIniziato = true;
                        m.powerUps.get(i).isDisegnare = false;
                    }
                }
            }
            // Spawn pianeti
            if (System.currentTimeMillis() >= m.timer) {
                m.pianeti.add(new Pianeti(m.r.nextInt(0, m.getWidth()), 0, 0, m,
                        m.immaginiPianeti.get(m.r.nextInt(1, m.NPianeti))));
                m.timer = System.currentTimeMillis()
                        + m.r.nextLong(m.frequezaminimaPianeti, m.frequezaMassimaPianeti);
            }
            long elapsedSeconds = (System.currentTimeMillis() - MyPanel.startTime) / 1000;

            // Imposta la difficoltà in base al tempo
            int nemiciDaSpawnare = 1; // base
            int velocitaVerticale = 5; // base
            int frequenzaSpawn = 2000; // base in ms

            // Dopo 30 secondi: spawn +1 nemico
            if (elapsedSeconds >= 30) {
                nemiciDaSpawnare = 2;
            }
            // Dopo 60 secondi: aumenta velocità verticale nemici
            if (elapsedSeconds >= 60) {
                velocitaVerticale = 8;
            }
            // Dopo 90 secondi: spawn +1 nemico e frequenza maggiore
            if (elapsedSeconds >= 90) {
                nemiciDaSpawnare = 3;
                frequenzaSpawn = 1500; // spawn più frequente
            }

            // SPAWN NEMICI SOLO SE È PASSATO IL TIMER
            if (System.currentTimeMillis() >= timerNemici) {
                for (int i = 0; i < nemiciDaSpawnare; i++) {
                    m.nemici.add(new Nemico(
                            m.r.nextInt(0, m.getWidth()),
                            0,
                            velocitaVerticale,
                            m,
                            m.immaginiNemici));
                }
                timerNemici = System.currentTimeMillis() + frequenzaSpawn;
            }
            // Spawn Dettagli
            if (System.currentTimeMillis() >= timerDettagli) {
                m.dettagli.add(new Dettagli(m.r.nextInt(0, m.getWidth()), 0, 0, m,
                        m.immaginiDettagli.get(m.r.nextInt(0, m.NpngPerDettagliImmagini))));
                timerDettagli = System.currentTimeMillis() + frequenzaDettagliSpawn;
            }

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
