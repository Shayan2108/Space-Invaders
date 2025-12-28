public class GameLoop extends Thread {
    MyPanel m;

    public GameLoop(MyPanel m) {
        this.m = m;
    }

    @Override
    public void run() {
        while (true) {
            if (GUI.statoAttuale == "GAME") {
                m.repaint();
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
