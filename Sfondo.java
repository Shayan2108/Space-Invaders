/**
 * @file Sfondo.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * Classe Sfondo
 *
 * Questa classe gestisce lo sfondo del gioco.
 * Lo sfondo scende dall’alto verso il basso.
 * Quando arriva a un certo punto,
 * viene creato un nuovo sfondo sopra.
 *
 * La classe usa un thread per muovere
 * lo sfondo in modo continuo.
 */

public class Sfondo extends Thread {

    // posizione x dello sfondo
    public int x;

    // posizione y dello sfondo
    public int y;

    // riferimento al pannello di gioco
    private MyPanel m;

    // serve per aggiungere lo sfondo solo una volta
    boolean aggiungi;

    /**
     * Costruttore della classe Sfondo
     *
     * @param x posizione x iniziale
     * @param y posizione y iniziale
     * @param m riferimento al pannello di gioco
     */
    public Sfondo(int x, int y, MyPanel m) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.aggiungi = true;
    }

    /**
     * Metodo run del thread.
     *
     * Fa scendere lo sfondo.
     * Controlla se il gioco è in pausa.
     * Aggiunge un nuovo sfondo quando serve.
     * Alla fine rimuove lo sfondo dalla lista.
     */
    @Override
    public void run() {
        while (y < m.getHeight()) {

            // se il gioco è in pausa
            if (m.isPaused) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                continue;
            }

            synchronized (m.sfondi) {
                y += 5;

                // aggiunge un nuovo sfondo sopra
                if (y > 0 && aggiungi) {
                    m.sfondi.add(new Sfondo(0, -800, m));
                    m.sfondi.getLast().start();
                    aggiungi = false;
                }
            }

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // rimozione dello sfondo dalla lista
        synchronized (m.sfondi) {
            m.sfondi.remove(this);
        }
    }
}
