package Frontend;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import Backend.Graph;
import Backend.Node;
import Backend.Traduction.Traduction;
import Frontend.GraphicalParts.EdgesSelectedWindow;

public class MouseHandler extends MouseInputAdapter {

    JFrame frame;
    Graph graph;
    Canvas canvas;
    Traduction traducer;
    EdgesSelectedWindow edgesSelectedWindow;


    public MouseHandler(JFrame frame, Graph graph, Canvas canvas, Traduction traducer) {
        this.frame = frame;
        this.graph = graph;
        this.canvas = canvas;
        this.traducer = traducer;
        this.edgesSelectedWindow = new EdgesSelectedWindow(frame, graph, canvas, traducer);
        frame.add(edgesSelectedWindow);
    }

    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int[] position = {e.getX(), e.getY()};
        if (e.isShiftDown()) {
            Node node = graph.getNodeByPosition(position);
            if (node != null) {
                edgesSelectedWindow.addNodeToNodeList(node);
            }
            edgesSelectedWindow.setVisible(true);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            System.out.println(graph.getNodeByPosition(position));
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            MenuPopUp popup = new MenuPopUp(graph, canvas, traducer, edgesSelectedWindow.getEdgesSelected(), position, edgesSelectedWindow);
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int[] position = {e.getX(), e.getY()};
        Node node = graph.getNodeByPosition(position);
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
            if (node != null) {
                node.changeNodePosition(e.getX(), e.getY());;
            }
            canvas.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int[] position = {e.getX(), e.getY()};
        if (e.getButton() == MouseEvent.BUTTON1) {
            Node node = graph.getNodeByPosition(position);
            if (node != null) {
                node.selected = !node.selected;
            }
            canvas.repaint();
        }
    }
}
