package Frontend.GraphicalParts;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Backend.Edge;
import Backend.Graph;
import Backend.Node;
import Backend.Exceptions.NodeNotAdjacentException;
import Backend.Traduction.Traduction;
import Frontend.Canvas;
import Main.Main;

public class EdgesSelectedWindow extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private Graph graph;
    private Canvas canvas;
    private JFrame frame;
    /**
     * List of selected Edges
     */
    private ArrayList<Edge> edgeList;
    private ArrayList<Node> nodeList;

    JLabel label;
    JPanel panel;

    public EdgesSelectedWindow(JFrame frame, Graph graph, Canvas canvas) {
        super(Traduction.translate("edgeSelection"), true, true);
        this.frame = frame;
        this.canvas = canvas;
        this.graph = graph;
        this.edgeList = new ArrayList<Edge>();
        this.nodeList = new ArrayList<Node>();
        this.setLocation(0, 50);
        label = new JLabel();
        panel = new JPanel();
        this.add(this.menu(), BorderLayout.PAGE_START);
        this.add(panel.add(label));
        this.setSize(400, 100);
        this.setVisible(false);
    }

    private JMenuBar menu() {
        JMenuBar menu = new JMenuBar();
        JMenuItem item;
        item = new JMenuItem(new ClearAction(this, Traduction.translate("clear")));
        menu.add(item);
        item = new JMenuItem(new DeleteAllSelectedConnections(this.edgeList, this, Traduction.translate("delCon")));
        menu.add(item);
        return menu;
    }

    public void updateEdgeListString(String edges) {
        label.setText(edges);
    }

    public void isVisible(Boolean visibility) {
        this.setVisible(visibility);
    }

    /**
     * Adds the given node in the internal list and checks if this addition creates
     * connections or cancellation of predecessor node
     * @param node The node to add in the list
     */
    public void addNodeToNodeList(Node node) {
        this.nodeList.add(node);
        displayPersonalPath(this);
    }

    /**
     * Handles edge addition or removal from selectedEdgeList and updates the window
     * 
     * @param edgesSelectedWindow The window where the selected edges are shown
     */
    private void displayPersonalPath(EdgesSelectedWindow edgesSelectedWindow) {
        if (this.nodeList.size() == 2) {
            Edge newSelectedEdge = this.graph.getNodesConnection(nodeList.get(0), nodeList.get(1));
            if (newSelectedEdge != null) {
                if (this.edgeList.contains(newSelectedEdge)) {
                    this.edgeList.remove(newSelectedEdge);
                } else {
                    this.edgeList.add(newSelectedEdge);
                }
            }
            this.nodeList.clear();
        }
        setedgeListListStringyfied();
    }

    /**
     * Updates the string of selected edges in the window
     */
    private void setedgeListListStringyfied() {
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            if (graph.edgesList.contains(edge)) {
                edge.changeColor(Color.RED);
                if (!edge.oriented) {
                    edge.oriented = true;
                    graph.getNodesConnection(edge.endingNode, edge.startingNode).changeColor(Color.RED);
                }
            } else {
                edgeList.remove(edge);
            }
        }
        String text = edgeList.toString();
        text = text.substring(1, text.length() - 1);
        if (nodeList.size() == 1) {
            text += "(" + nodeList.get(0).name;
        }
        this.updateEdgeListString(text);
        this.frame.repaint();
    }

    /**
     * Returns the list of selected edges from the UI
     * @return The arrayList of selected edges
     */
    public ArrayList<Edge> getEdgesSelected() {
        return this.edgeList;
    }

    /**
     * Clears the list and changes the color for every edge
     * in the graph to the standard one
     */
    public void clearAll() {
        this.nodeList.clear();
        this.edgeList.clear();
        for (Edge edge : graph.edgesList) {
            edge.oriented = graph.oriented;
            edge.changeColor(Color.BLACK);
        }
        this.setedgeListListStringyfied();
    }

    private class ClearAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private EdgesSelectedWindow edgesSelectedWindow;

        public ClearAction(EdgesSelectedWindow edgesSelectedWindow, String name) {
            super(name);
            this.edgesSelectedWindow = edgesSelectedWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            edgesSelectedWindow.clearAll();
        }

        
    }
    
    public static class DeleteAllSelectedConnections extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private EdgesSelectedWindow edgesSelectedWindow;
        private ArrayList<Edge> path;

        public DeleteAllSelectedConnections(ArrayList<Edge> path, EdgesSelectedWindow edgesSelectedWindow, String buttonName) {
            super(buttonName);
            this.path = path;
            this.edgesSelectedWindow = edgesSelectedWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < path.size(); i++) {
                try {
                    Main.graph.deleteConnection(path.get(i).startingNode, path.get(i).endingNode);
                } catch (NodeNotAdjacentException e1) {
                }
                if (!Main.graph.oriented) {
                    try {
                        Main.graph.deleteConnection(path.get(i).endingNode, path.get(i).startingNode);
                    } catch (NodeNotAdjacentException e1) {
                    }
                }
            }
            this.edgesSelectedWindow.clearAll();
            this.edgesSelectedWindow.setVisible(false);
            Main.canvas.repaint();
        }
        
    }
    
}