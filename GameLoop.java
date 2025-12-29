public class GameLoop extends Thread {
    MyPanel m;

    public GameLoop(MyPanel m) {
        this.m = m;
        this.setName("GameLoop Thread");
    }

    @Override
    public void run() {
        while (true) {
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
