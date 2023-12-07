package Components;

import javax.swing.*;
import java.awt.*;

public class LinePanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,0, 20, 35);
    };
}
