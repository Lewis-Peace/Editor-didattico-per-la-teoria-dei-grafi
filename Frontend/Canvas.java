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

    public static int[] selectionArea = new int[4];

    // Constructor of class
    public Canvas(int width, int height, Color color) {
        this.setSize(width, height);
        this.backgroundColor = color;
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseHandler());
    }

    public static void deselectAllNodes() {
        Main.graph.deselectAllNodes();
    }

    public static void selectNodesByMultipleSelection() {
        Canvas.deselectAllNodes();
        int x, y, h, w;
        x = selectionArea[0];
        y = selectionArea[1];
        w = selectionArea[2];
        h = selectionArea[3];
        for (Node n : Main.graph.nodesList) {
            if (
                x <= n.xPos &&
                w + x >= n.xPos &&
                h + y >= n.yPos &&
                y <= n.yPos
                ) {
                    n.selected = true;
                }
        }
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
        if (selectionArea[0] != -1) {
            g.drawRect(selectionArea[0], selectionArea[1], selectionArea[2], selectionArea[3]);
        }
    }

}
