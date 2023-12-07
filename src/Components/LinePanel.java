package Components;

import javax.swing.*;
import java.awt.*;

public class LinePanel extends JPanel {

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(startX, startY, endX, endY);
    };

    public LinePanel(){
        super();
    }

    public LinePanel(int startX, int startY, int endX, int endY){
        super();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }
}
