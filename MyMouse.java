import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouse implements MouseListener {

     MyPanel m;

    public MyMouse(MyPanel m)
    {
        this.m = m;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(m.bulletMassime - m.bullets.size() > 0){
        m.bullets.add(new Bullets(m, m.xNave + m.paddingBullet1, m.yNave, 8));
        m.bullets.add(new Bullets(m, m.xNave + m.paddingBullet2, m.yNave, 8));
        }

        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
