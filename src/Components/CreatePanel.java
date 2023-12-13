package Components;

import javax.swing.*;
import java.awt.*;

public class CreatePanel extends JPanel {
    protected void paintComponent(Graphics g) {
        //Adding  super.paintComponent....
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    }
}
