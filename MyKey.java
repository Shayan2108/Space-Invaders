import java.awt.event.*;

public class MyKey implements KeyListener {

    MyPanel m;
    boolean isA, isD;

    public MyKey(MyPanel m) {
        this.m = m;
        isA = false;
        isD = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            m.isPressed = true;
            m.movimento = 3;
            isD = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            m.isPressed = true;
            m.movimento = -3;
            isA = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) isD = false;
        if (e.getKeyCode() == KeyEvent.VK_A) isA = false;
        if (!isA && !isD) m.isPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
