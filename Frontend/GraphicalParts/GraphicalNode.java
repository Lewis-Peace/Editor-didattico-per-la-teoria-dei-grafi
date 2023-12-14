package Frontend.GraphicalParts;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public abstract class GraphicalNode extends JComponent {
    public int diameter = 35;
    public int xPos;
    public int yPos;
    /**
     *  The color wich the node is visualized
    */
    public Color color = Color.BLACK;
    public Boolean selected;
    public String name;

    /**
     * Moves the node in a new position
     * @param x The value in the x axis
     * @param y The value in the y axis
     */
    public void changeNodePosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void changeDiameter(int diameter) {
        this.diameter = diameter;
    }

    /**
     * Changes color of this node
     * @param color The new color for the node
     */
    public void changeNodeColor(Color color) {
        this.color = color;
    }

    /**
     * Draws the node in the canvas
     */
    public void drawNode(Graphics g) {
        int[] centerPosition = {(xPos - (diameter / 2)), (yPos - (diameter / 2))};

        if (selected) {
            int sqdist = 0/*6*/;
            g.setColor(Color.DARK_GRAY);
            g.drawRect(centerPosition[0] - sqdist, centerPosition[1] - sqdist, diameter + sqdist * 2, diameter + sqdist * 2);
        }
        g.setColor(color);
        g.fillOval(centerPosition[0], centerPosition[1], diameter, diameter);
        g.setColor(Color.BLACK);
        g.drawOval(centerPosition[0], centerPosition[1], diameter - 1, diameter - 1);
        g.drawString(name, centerPosition[0], centerPosition[1]);
    }

}
