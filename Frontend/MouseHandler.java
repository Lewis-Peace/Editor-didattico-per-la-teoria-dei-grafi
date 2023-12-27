package Frontend;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import Backend.Node;
import Frontend.GraphicalParts.EdgesSelectedWindow;

import Main.Main;

public class MouseHandler extends MouseInputAdapter {

    EdgesSelectedWindow edgesSelectedWindow;


    public MouseHandler() {
        this.edgesSelectedWindow = new EdgesSelectedWindow();
        Main.frame.add(edgesSelectedWindow);
    }

    public static int[] startingPosition = new int[2];
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        startingPosition[0] = e.getX();
        startingPosition[1] = e.getY();
        if (e.isShiftDown()) { // Vertices selection
            Node node = Main.graph.getNodeByPosition(startingPosition);
            if (node != null) {
                edgesSelectedWindow.addNodeToNodeList(node);
            }
            edgesSelectedWindow.setVisible(true);
        } switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                draggedNode = Main.graph.getNodeByPosition(startingPosition);
                if (draggedNode == null) {
                    Canvas.deselectAllNodes();
                    mouseAction = MouseActions.Selecting;
                } else {
                    mouseAction = MouseActions.Moving;
                }
                
                break;

            case MouseEvent.BUTTON2:// Mouse wheel pressed
                System.out.println(Main.graph.getNodeByPosition(startingPosition));
                break;
        
            case MouseEvent.BUTTON3: // Rigth click
                MenuPopUp popup = new MenuPopUp(Main.graph, Main.canvas, edgesSelectedWindow.getEdgesSelected(), startingPosition, edgesSelectedWindow);
                popup.show(e.getComponent(), e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    public static Node draggedNode = null;
    public static MouseActions mouseAction = MouseActions.Undefined;

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK && mouseAction == MouseActions.Moving) {
            draggedNode.changeNodePosition(e.getX(), e.getY());;
        } else if (mouseAction == MouseActions.Selecting || mouseAction == MouseActions.SelectingMultiple) {
            mouseAction = MouseActions.SelectingMultiple;
            this.drawSelectionRectangle(Main.canvas.getGraphics(), startingPosition[0], startingPosition[1], e.getX(), e.getY());
        }
        Main.canvas.repaint();
    }

    private void drawSelectionRectangle(Graphics g, int x, int y, int x2, int y2) {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw = Math.abs(x-x2);
        int ph = Math.abs(y-y2);
        Canvas.selectionArea[0] = px;
        Canvas.selectionArea[1] = py;
        Canvas.selectionArea[2] = pw;
        Canvas.selectionArea[3] = ph;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        draggedNode = null;
        if (mouseAction == MouseActions.SelectingMultiple) {
            Canvas.selectNodesByMultipleSelection();
            Canvas.selectionArea[0] = -1;
        }
        Main.canvas.repaint();
        mouseAction = MouseActions.Undefined;
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
