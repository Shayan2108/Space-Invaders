public class GameLoop extends Thread {
    MyPanel m;

    public GameLoop(MyPanel m) {
        this.m = m;
    }

    @Override
    public void run() {
        while (true) {
            m.repaint();
            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
    