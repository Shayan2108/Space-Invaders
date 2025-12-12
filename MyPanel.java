import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MyPanel extends JPanel {
    volatile ArrayList<Stella> stelle = new ArrayList<>();
    Random r;
    Sfondo sfondo;

    public MyPanel() {
        this.setFocusable(true);
        this.setBackground(Color.black);
        r = new Random();
        sfondo = new Sfondo(this);
        sfondo.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        stampaStelle(g);
    }

    private void stampaStelle(Graphics g) {
        synchronized (stelle) {
            for (Stella stella : stelle) {
                g.setColor(Color.WHITE);
                g.fillOval(stella.x, stella.y, 2, 2);
            }
        }
    }
}
    class Sfondo extends Thread {
        MyPanel m;
        public Sfondo(MyPanel m)
        {
            this.m = m;
        }
        @Override
        public void run() {
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int i = 0; i < m.getWidth(); i++) {
                m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), m.r.nextInt(0, m.getHeight()), 8, m));
            }
            while (true) {
                synchronized (m.stelle) {
                    for (int i = 0; i < m.getWidth() / 75; i++) {
                        m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), 0, 8,m));
                    }
                    for (int i = 0; i < m.stelle.size(); i++) {
                        m.stelle.get(i).sposta();
                        if (m.stelle.get(i).isFinita()) {
                            m.stelle.remove(i);
                        }
                    }
                }
                m.repaint();
                try {
                    sleep(33);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

