import java.awt.image.BufferedImage;

public class Bullets {
    MyPanel m;
    BufferedImage image;
    int x;
    int y;
    int movimento;

    public Bullets(MyPanel m, int x, int y, int movimento) {
        this.m = m;
        this.x = x;
        this.y = y;
        this.movimento = movimento;
        this.image = m.immaginiBullet.get(m.r.nextInt(1, 66));
    }

    public void sposta() {
        if (y - movimento > 0) {
            y -= movimento;
        } else {
            m.bullets.remove(this);
        }
    }
}

class SpostaBullet extends Thread {
    MyPanel m;

    SpostaBullet(MyPanel m) {
        this.m = m;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < m.bullets.size(); i++) {
                m.bullets.get(i).sposta();
            }
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
