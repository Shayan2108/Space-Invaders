public class Sfondo extends Thread {

    public int x;
    public int y;
    private MyPanel m;
    boolean aggiungi;

    public Sfondo(int x, int y, MyPanel m) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.aggiungi = true;
    }

    @Override
    public void run() {
        while (y < m.getHeight()) {
            y += 5;
            if (y > 0 && aggiungi) {
                m.sfondi.add(new Sfondo(0,-800, m));
                m.sfondi.getLast().start();
                aggiungi = false;
            }
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        m.sfondi.remove(this);
    }
}
