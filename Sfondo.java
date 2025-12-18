class Sfondo extends Thread {
    MyPanel m;
    Long timerAsteroidi;
    int frequenzaAsteroidiSpawn;
    public Sfondo(MyPanel m)
    {
        this.m = m;
        frequenzaAsteroidiSpawn = 1000;
        timerAsteroidi = System.currentTimeMillis() + frequenzaAsteroidiSpawn;
    }
        @Override
        public void run() {
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            synchronized (m.stelle) {
                for (int i = 0; i < m.getWidth(); i++) {
                    m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), m.r.nextInt(0, m.getHeight()), 7,m));
                }
            }
            while (true) {
                if (m.isPressed) {
                    if (m.xNave + m.paddingX + m.movimento <= m.getWidth() && m.xNave + m.movimento >= 0)
                        m.xNave += m.movimento;
                    else if (m.xNave + m.paddingX + m.movimento > m.getWidth()) {
                        m.xNave = m.getWidth() - m.paddingX + 1;
                    } else if (m.xNave + m.movimento < 0) {
                        m.xNave = 0;
                    }
                } else {
                    m.yNave = m.getHeight() - m.paddingY;
                }
                try {
                    sleep(33);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
                if (System.currentTimeMillis() >= m.timer) {
                    m.pianeti.add(new Pianeti(m.r.nextInt(0, m.getWidth()), 0, 0,m,
                    m.immaginiPianeti.get(m.r.nextInt(1, m.NPianeti))));
                    m.timer = System.currentTimeMillis() + m.r.nextLong(m.frequezaminimaPianeti, m.frequezaMassimaPianeti);
                }
                synchronized (m.stelle) {
                    for (int i = 0; i < m.getWidth() / 75; i++) {
                        m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), 0, 7, m));
                    }
                    for (int i = 0; i < m.stelle.size(); i++) {
                        m.stelle.get(i).sposta();
                        if (m.stelle.get(i).isFinita()) {
                            m.stelle.remove(i);
                        }
                    }
                }
                    if (System.currentTimeMillis() >= timerAsteroidi) {
                    m.dettagli.add(new Dettagli(m.r.nextInt(0, m.getWidth()), 0, 0,m,
                    m.immaginiDettagli.get(m.r.nextInt(0, m.NpngPerDettagliImmagini))));
                    timerAsteroidi = System.currentTimeMillis() + frequenzaAsteroidiSpawn;
                }
            }
        }
    }