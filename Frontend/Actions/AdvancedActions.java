package Frontend.Actions;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Backend.Colore;
import Backend.Edge;
import Backend.Graph;
import Backend.Node;
import Backend.Path;
import Backend.TipoAlgoritmo;
import Backend.Exceptions.NodeNotAdjacentException;
import Backend.Traduction.Traduction;
import Frontend.Canvas;
import Main.Main;
import Frontend.GraphicalParts.InputTextField;
import Frontend.GraphicalParts.PopupMessage;
import Frontend.GraphicalParts.StepperGUI;

public class AdvancedActions {

    public static class FindIfBipartedGraph extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public FindIfBipartedGraph(String name) {
            super(name);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.nodesList.isEmpty()) {
                new PopupMessage(Traduction.translate("emptyGraph"), Traduction.translate("error"));
            } else {
                Main.graph.isBiparted();
                Boolean resutl = Main.graph.isBiparted;
                if (resutl) {
                    for (Node node : Main.graph.nodesList) {
                        if (node.colore == Colore.BIANCO) {
                            node.changeNodeColor(Color.WHITE);
                        } else {
                            node.changeNodeColor(Color.BLACK);
                        }
                    }
                    Main.canvas.repaint();
                } else {
                    Node node0 = Main.graph.sameColorNodesForBiparted.get(0);
                    Node node1 = Main.graph.sameColorNodesForBiparted.get(1);
                    try {
						findRuleException(node0, node1);
					} catch (NodeNotAdjacentException e1) {
					}
                    Main.canvas.repaint();
                    new PopupMessage(Traduction.translate("biErr"), "Alert");
                }
            }
        }

        private void findRuleException(Node node0, Node node1) throws NodeNotAdjacentException {
            Graph clonedGraph = Main.graph.cloneThisGraph();
            Node clonedNode0 = clonedGraph.getNodeByName(node0.name);
            Node clonedNode1 = clonedGraph.getNodeByName(node1.name);
            clonedGraph.deleteConnection(clonedNode0, clonedNode1);
            Path path = new Path(clonedNode0, clonedNode1);
            path.getShortestPath(clonedGraph);
            ArrayList<Edge> edgeList = path.edgePath;
            for (Edge edge : edgeList) {
                Node n0 = Main.graph.getNodeByName(edge.startingNode.name);
                Node n1 = Main.graph.getNodeByName(edge.endingNode.name);
                Main.graph.getNodesConnection(n0, n1).changeColor(Color.RED);
                if (!Main.graph.oriented) {
                    Main.graph.getNodesConnection(n1, n0).changeColor(Color.RED);
                }
            }
            Main.graph.getNodesConnection(node0, node1).changeColor(Color.RED);
            if (!Main.graph.oriented) {
                Main.graph.getNodesConnection(node1, node0).changeColor(Color.RED);
            }
        }
    }

    public static class FindShortestPathFrom2Nodes extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private JPanel panel = new JPanel();
        private JTextField txt1 = new JTextField();
        private JTextField txt2 = new JTextField();

        public FindShortestPathFrom2Nodes(String name) {
            super(name);
            this.panel.add(new JLabel(Traduction.translate("stNdNm")));
            this.panel.add(txt1);
            this.panel.add(new JLabel(Traduction.translate("fnNdNm")));
            this.panel.add(txt2);
            this.panel.setLayout(new GridLayout());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null, this.panel, "Input data", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Node startingNode = Main.graph.getNodeByName(txt1.getText());
                Node finalNode = Main.graph.getNodeByName(txt2.getText());
                if (startingNode == null || finalNode == null) {
                    new PopupMessage(Traduction.translate("nodeNameErrorMul"), Traduction.translate("error"));
                } else {
                    Path path = Main.graph.minPathFromFirstNodeToSecond(startingNode, finalNode);
                    if (finalNode.parent != null) {
                        for (int i = 0; i < path.edgePath.size(); i++) {
                            Edge edge = path.edgePath.get(i);
                            edge = Main.graph.getNodesConnection(edge.startingNode, edge.endingNode);
                            edge.changeColor(Color.RED);
                            if (!Main.graph.oriented) {
                                Edge edge2 = Main.graph.getNodesConnection(edge.endingNode, edge.startingNode);
                                edge2.changeColor(Color.RED);
                            }
                            edge.oriented = true;
                        }
                        Main.canvas.repaint();
                    } else {
                        new PopupMessage(Traduction.translate("noPath1"), "Attention");
                    }
                }
            }
        }
    }
    public static class FindEulerianPathAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Path eulerianPath;

        public FindEulerianPathAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.nodesList.isEmpty()) {
                new PopupMessage(Traduction.translate("emptyGraph"), Traduction.translate("error"));
            } else {
                Node startingNode = Main.graph.nodesList.get(0);
                this.eulerianPath = new Path(startingNode, startingNode);
                this.eulerianPath.findEulerianPath(Main.graph, Main.graph.edgesList.size());
                if (this.eulerianPath.eluerianEnded) {
                    new StepperGUI(Main.graph, this.eulerianPath, TipoAlgoritmo.EULERIANO, "Stepper");
                } else {
                    new PopupMessage(Traduction.translate("noPath2"), Traduction.translate("attention"));
                }
            }
        }

    }
    public static class FindHamiltonianPathAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private Path hamiltonianPath;

        public FindHamiltonianPathAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.nodesList.isEmpty()) {
                new PopupMessage(Traduction.translate("emptyGraph"), Traduction.translate("error"));
            } else {
                Node startingNode = Main.graph.nodesList.get(0);
                this.hamiltonianPath = new Path(startingNode, startingNode);
                this.hamiltonianPath.findHamiltonianPath(Main.graph, Main.graph.nodesList.size());
                if (this.hamiltonianPath.foundHamiltonianPath) {
                    new StepperGUI(Main.graph, this.hamiltonianPath, TipoAlgoritmo.HAMILTONIANO, "Stepper");
                } else {
                    new PopupMessage(Traduction.translate("noPath3"), Traduction.translate("attention"));
                }
            }
        }
        
    }

    public static class AddNodeWithSpecificNameAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 5492635494287414457L;
        private int radius;

        public AddNodeWithSpecificNameAction(String buttonName) {
            super(buttonName);
            this.radius = new Node(0,0,0).diameter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = new InputTextField(Traduction.translate("ndNm")).getInput("Input");
            if (name != null) {
                int[] position = getFreePosition(radius * 2);
                Node node = new Node(position[0], position[1], name);
                Main.graph.addNode(node);
                Main.canvas.repaint();
            }

        }

        private int[] getFreePosition(int radius) {
            int[] position = new int[2];
            do {
                position[0] = (int) Math.floor(Math.random() * (Main.canvas.getWidth() - radius)) + radius/2;
                position[1] = (int) Math.floor(Math.random() * (Main.canvas.getHeight() - radius)) + radius/2;
            } while (Main.graph.getNodeByPosition(position) != null);
            return position;
        }
        
    }

    public static class ColorNodesByDegreeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;

        public ColorNodesByDegreeAction(String buttonName) {
            super(buttonName);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.graph.divideByOddOrEvenDegree();
            Main.canvas.repaint();
        }

    }

    public static class FindArborescenceAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public FindArborescenceAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = Main.graph.getNodeByName(new InputTextField(Traduction.translate("arboQ") + ":").getInput(Traduction.translate("arN")));
            if (startingNode == null) {
                new PopupMessage(Traduction.translate("nodeNameError"), Traduction.translate("error"));
            } else {
                Main.graph.findArborescence(startingNode);
                paintArborescence();
                Main.canvas.repaint();
            }
        }

        private void paintArborescence() {
            for (Node node : Main.graph.nodesList) {
                if (node.parent != node) {
                    Edge edge = Main.graph.getNodesConnection(node.parent, node);
                    edge.changeColor(Color.RED);
                    edge.oriented = true;
                    if (!Main.graph.oriented) {
                        Edge revEdge = Main.graph.getNodesConnection(node, node.parent);
                        revEdge.changeColor(Color.RED);
                    }
                }
            }
        }
        
    }

}
