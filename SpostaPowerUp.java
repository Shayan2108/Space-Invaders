import java.awt.Rectangle;

class SpostaPowerUp extends Thread {
    MyPanel m;

    public SpostaPowerUp(MyPanel m) {
        this.m = m;
    }

    @Override
    public void run() {
        while (!m.gameOver) {
            synchronized (m.powerUps) {
                for (int i = 0; i < m.powerUps.size(); i++) {
                    PowerUp p = m.powerUps.get(i);

                    // muovi il power-up
                    p.y += p.velocita;

                    // collisione con la nave
                    Rectangle navBox = new Rectangle(m.xNave, m.yNave, m.larghezzaNave, m.altezzaNave);
                    Rectangle powerBox = new Rectangle(p.x, p.y, 50, 50); // dimensione fissa

                    if (navBox.intersects(powerBox)) {
                        applicaEffetto(p);
                        m.powerUps.remove(i);
                        i--;
                        continue;
                    }

                    // rimuovi se fuori schermo
                    if (p.y > m.getHeight()) {
                        m.powerUps.remove(i);
                        i--;
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

    private void applicaEffetto(PowerUp p) {
        switch (p.tipo) {
            case MULTI -> m.attivaSparoMultiplo();
            case SCUDO -> m.attivaScudo();
            case RESPINGI -> m.attivaRespingi();
            case RALLENTA -> m.attivaRallenta();
            case PENETRANTE -> m.attivaProiettiliPenetranti();
        }
    }
}
