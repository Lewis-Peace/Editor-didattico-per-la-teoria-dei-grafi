package Frontend;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import Backend.Node;
import Backend.Traduction.Traduction;
import Frontend.GraphicalParts.EdgesSelectedWindow;

import Main.Main;

public class MouseHandler extends MouseInputAdapter {

    Traduction traducer;
    EdgesSelectedWindow edgesSelectedWindow;


    public MouseHandler(Traduction traducer) {
        this.traducer = traducer;
        this.edgesSelectedWindow = new EdgesSelectedWindow(Main.frame, Main.graph, Main.canvas, traducer);
        Main.frame.add(edgesSelectedWindow);
    }

    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int[] position = {e.getX(), e.getY()};
        if (e.isShiftDown()) { // Vertices selection
            Node node = Main.graph.getNodeByPosition(position);
            if (node != null) {
                edgesSelectedWindow.addNodeToNodeList(node);
            }
            edgesSelectedWindow.setVisible(true);
        } switch (e.getButton()) {
            case MouseEvent.BUTTON2:// Mouse wheel pressed
                System.out.println(Main.graph.getNodeByPosition(position));
                break;
        
            case MouseEvent.BUTTON3: // Rigth click
                MenuPopUp popup = new MenuPopUp(Main.graph, Main.canvas, traducer, edgesSelectedWindow.getEdgesSelected(), position, edgesSelectedWindow);
                popup.show(e.getComponent(), e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int[] position = {e.getX(), e.getY()};
        Node node = Main.graph.getNodeByPosition(position);
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
            if (node != null) {
                node.changeNodePosition(e.getX(), e.getY());;
            }
            Main.canvas.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int[] position = {e.getX(), e.getY()};
        if (e.getButton() == MouseEvent.BUTTON1) {
            Node node = Main.graph.getNodeByPosition(position);
            if (node != null) {
                node.selected = !node.selected;
            }
            Main.canvas.repaint();
        }
    }
}
