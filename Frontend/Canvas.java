package Frontend;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JFrame;

import Backend.Edge;
import Backend.Graph;
import Backend.Node;
import Backend.Traduction.Traduction;

public class Canvas extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 6556527773896367518L;

    private Color backgroundColor = Color.WHITE;
    public static Graph graph;
    private JFrame window;

    // Constructor of class
    public Canvas(JFrame window, int width, int height, Color color, Graph graph, Traduction traducer) {
        this.setSize(width, height);
        this.window = window;
        Canvas.graph = graph;
        this.backgroundColor = color;
        this.addMouseListener(new MouseHandler(window, graph, this, traducer));
        this.addMouseMotionListener(new MouseHandler(window, graph, this, traducer));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, window.getSize().width, window.getSize().height);
        for (Edge edge : graph.edgesList) {
            edge.drawEdge(g);
        }
        for (Node node : graph.nodesList) {
            node.drawNode(g);
        }
    }

}
