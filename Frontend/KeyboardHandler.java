package Frontend;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import Backend.Graph;
import Backend.Node;
import Backend.Exceptions.NodeDoesNotExitsException;

public class KeyboardHandler implements KeyListener {

    Graph graph;
    Canvas canvas;
    int movingPrecision = 1;

    public KeyboardHandler (Graph graph, Canvas canvas) {
        this.graph = graph;
        this.canvas = canvas;
    }

    /**
     * Gets all the nodes with the attribute selected set on true
     * @return Returns an arraylist of those nodes
     */
    private ArrayList<Node> getSelectedNodes() {
        ArrayList<Node> selectedNodes = new ArrayList<Node>();
        for (int i = 0; i < this.graph.nodesList.size(); i++) {
            Node node = this.graph.nodesList.get(i);
            if (node.selected == true) {
                selectedNodes.add(node);
            }
        }
        return selectedNodes;
    }

    /**
     * Deletes all the nodes with the attribute selected
     * set on true
     */
    private void deleteSelectedNodes() {
        ArrayList<Node> nodesToDelete = getSelectedNodes();
        if (!nodesToDelete.isEmpty()) {
            for (Node node: nodesToDelete) {
                try {
                    this.graph.deleteNode(node);
                } catch (NodeDoesNotExitsException e) {
                }
            }
        }
        canvas.repaint();
    }

    /**
     * Adds a node in the current graph chosing from a random free position
     */
    public void addNode() {
        int[] position = getFreePosition((new Node(0,0,0)).diameter * 2);
        String nodeName = getLeastNumberNamePossible(graph.nodesList.size());
        Node node = new Node(position[0], position[1], nodeName);
        this.graph.addNode(node);
        this.canvas.paintComponent(canvas.getGraphics());

    }

    /**
     * Finds a position in the canvas where the isn't any node
     * @param radius How much space will be required
     * @return The coordinates for a free node
     */
    private int[] getFreePosition(int radius) {
        int[] position = new int[2];
        do {
            position[0] = (int) Math.floor(Math.random() * (canvas.getWidth() - radius)) + radius/2;
            position[1] = (int) Math.floor(Math.random() * (canvas.getHeight() - radius)) + radius/2;
        } while (this.graph.getNodeByPosition(position) != null);
        return position;
    }
    
    /**
     * Finds the least free number for naming a node
     * @param maxName The last possible option
     * @return The name of the new node
     */
    private String getLeastNumberNamePossible(int maxName) {
        for (int i = 0; i <= maxName; i++) {
            if (this.graph.getNodeByName(i + "") == null) {
                return i + "";
            }
        }
        return "-1";
    }

    private void moveNodes(int x, int y) {
        ArrayList<Node> nodesToMove = getSelectedNodes();
        for (Node node : nodesToMove) {
            node.changeNodePosition(node.xPos + x, node.yPos + y);
        }
    }

    private void handleArrowKeyEvent(int key) {
        if (key == KeyEvent.VK_UP) {
            moveNodes(0, this.movingPrecision * -1);
        } else if (key == KeyEvent.VK_DOWN) {
            moveNodes(0, this.movingPrecision * 1);
        } else if (key == KeyEvent.VK_LEFT) {
            moveNodes(this.movingPrecision * -1, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            moveNodes(this.movingPrecision * 1, 0);
        }
        this.canvas.repaint();
    }

    private void changeMovingPrecision(char operation) {
        if (operation == '+') {
            this.movingPrecision += 1;
        } else if (operation == '-' && this.movingPrecision != 1) {
            this.movingPrecision -= 1;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        if (keyPressed == KeyEvent.VK_DELETE) {
            this.deleteSelectedNodes();
        } else if (keyPressed == KeyEvent.VK_ADD) {
            this.addNode();
        } else if (keyPressed <= 40 && keyPressed >= 37) { // is an arrow key
            this.handleArrowKeyEvent(keyPressed);
        } else if (keyPressed == KeyEvent.VK_PAGE_UP) {
            this.changeMovingPrecision('+');
        } else if (keyPressed == KeyEvent.VK_PAGE_DOWN) {
            this.changeMovingPrecision('-');
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
