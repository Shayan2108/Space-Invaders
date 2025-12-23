import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SchermataIniziale  extends JPanel {
    CardLayout cl;
    JPanel contenitore;
    JButton bottone;
    public SchermataIniziale(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        bottone = new JButton();
        this.add(bottone);
        
        bottone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(contenitore, "GAME");
                GUI.statoAttuale = "GAME";
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                frame.setSize(new Dimension(400 , 800));
            }
        });
    }
}

