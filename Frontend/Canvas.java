package Frontend;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Backend.Edge;
import Backend.Node;

import Main.Main;

public class Canvas extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 6556527773896367518L;

    private Color backgroundColor = Color.WHITE;

    // Constructor of class
    public Canvas(int width, int height, Color color) {
        this.setSize(width, height);
        this.backgroundColor = color;
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseHandler());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Main.frame.getSize().width, Main.frame.getSize().height);
        for (Edge edge : Main.graph.edgesList) {
            edge.drawEdge(g);
        }
        for (Node node : Main.graph.nodesList) {
            node.drawNode(g);
        }
    }

}
