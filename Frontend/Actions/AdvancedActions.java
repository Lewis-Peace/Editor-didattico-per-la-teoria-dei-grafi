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
import Frontend.GraphicalParts.InputTextField;
import Frontend.GraphicalParts.PopupMessage;
import Frontend.GraphicalParts.StepperGUI;

public class AdvancedActions {

    public static class FindIfBipartedGraph extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private Canvas canvas;
        private Traduction traducer;

        public FindIfBipartedGraph(Graph graph, Canvas canvas, Traduction traducer, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.graph.nodesList.isEmpty()) {
                new PopupMessage(this.traducer.translate("emptyGraph"), this.traducer.translate("error"));
            } else {
                graph.isBiparted();
                Boolean resutl = this.graph.isBiparted;
                if (resutl) {
                    for (Node node : graph.nodesList) {
                        if (node.colore == Colore.BIANCO) {
                            node.changeNodeColor(Color.WHITE);
                        } else {
                            node.changeNodeColor(Color.BLACK);
                        }
                    }
                    canvas.repaint();
                } else {
                    Node node0 = graph.sameColorNodesForBiparted.get(0);
                    Node node1 = graph.sameColorNodesForBiparted.get(1);
                    try {
						findRuleException(node0, node1);
					} catch (NodeNotAdjacentException e1) {
					}
                    canvas.repaint();
                    new PopupMessage(traducer.translate("biErr"), "Alert");
                }
            }
        }

        private void findRuleException(Node node0, Node node1) throws NodeNotAdjacentException {
            Graph clonedGraph = this.graph.cloneThisGraph();
            Node clonedNode0 = clonedGraph.getNodeByName(node0.name);
            Node clonedNode1 = clonedGraph.getNodeByName(node1.name);
            clonedGraph.deleteConnection(clonedNode0, clonedNode1);
            Path path = new Path(clonedNode0, clonedNode1);
            path.getShortestPath(clonedGraph);
            ArrayList<Edge> edgeList = path.edgePath;
            for (Edge edge : edgeList) {
                Node n0 = this.graph.getNodeByName(edge.startingNode.name);
                Node n1 = this.graph.getNodeByName(edge.endingNode.name);
                this.graph.getNodesConnection(n0, n1).changeColor(Color.RED);
                if (!this.graph.oriented) {
                    this.graph.getNodesConnection(n1, n0).changeColor(Color.RED);
                }
            }
            this.graph.getNodesConnection(node0, node1).changeColor(Color.RED);
            if (!this.graph.oriented) {
                this.graph.getNodesConnection(node1, node0).changeColor(Color.RED);
            }
        }
    }

    public static class FindShortestPathFrom2Nodes extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private Canvas canvas;
        private JPanel panel = new JPanel();
        private JTextField txt1 = new JTextField();
        private JTextField txt2 = new JTextField();
        private Traduction traducer;

        public FindShortestPathFrom2Nodes(Graph graph, Canvas canvas, Traduction traducer, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
            this.panel.add(new JLabel(traducer.translate("stNdNm")));
            this.panel.add(txt1);
            this.panel.add(new JLabel(traducer.translate("fnNdNm")));
            this.panel.add(txt2);
            this.panel.setLayout(new GridLayout());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null, this.panel, "Input data", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Node startingNode = graph.getNodeByName(txt1.getText());
                Node finalNode = graph.getNodeByName(txt2.getText());
                if (startingNode == null || finalNode == null) {
                    new PopupMessage(this.traducer.translate("nodeNameErrorMul"), this.traducer.translate("error"));
                } else {
                    Path path = graph.minPathFromFirstNodeToSecond(startingNode, finalNode);
                    if (finalNode.parent != null) {
                        for (int i = 0; i < path.edgePath.size(); i++) {
                            Edge edge = path.edgePath.get(i);
                            edge = graph.getNodesConnection(edge.startingNode, edge.endingNode);
                            edge.changeColor(Color.RED);
                            if (!graph.oriented) {
                                Edge edge2 = graph.getNodesConnection(edge.endingNode, edge.startingNode);
                                edge2.changeColor(Color.RED);
                            }
                            edge.oriented = true;
                        }
                        canvas.repaint();
                    } else {
                        new PopupMessage(traducer.translate("noPath1"), "Attention");
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
        private Graph graph;
        private Canvas canvas;
        private Traduction traducer;
        private Path eulerianPath;

        public FindEulerianPathAction(Graph graph, Canvas canvas, Traduction traducer, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (graph.nodesList.isEmpty()) {
                new PopupMessage(this.traducer.translate("emptyGraph"), this.traducer.translate("error"));
            } else {
                Node startingNode = graph.nodesList.get(0);
                this.eulerianPath = new Path(startingNode, startingNode);
                this.eulerianPath.findEulerianPath(graph, graph.edgesList.size());
                if (this.eulerianPath.eluerianEnded) {
                    new StepperGUI(graph, canvas, this.eulerianPath, TipoAlgoritmo.EULERIANO, traducer, "Stepper");
                } else {
                    new PopupMessage(traducer.translate("noPath2"), traducer.translate("attention"));
                }
            }
        }

    }
    public static class FindHamiltonianPathAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private Canvas canvas;
        private Traduction traducer;
        private Path hamiltonianPath;

        public FindHamiltonianPathAction(Graph graph, Canvas canvas, Traduction traducer, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (graph.nodesList.isEmpty()) {
                new PopupMessage(this.traducer.translate("emptyGraph"), this.traducer.translate("error"));
            } else {
                Node startingNode = graph.nodesList.get(0);
                this.hamiltonianPath = new Path(startingNode, startingNode);
                this.hamiltonianPath.findHamiltonianPath(graph, graph.nodesList.size());
                if (this.hamiltonianPath.foundHamiltonianPath) {
                    new StepperGUI(graph, canvas, this.hamiltonianPath, TipoAlgoritmo.HAMILTONIANO, traducer, "Stepper");
                } else {
                    new PopupMessage(traducer.translate("noPath3"), traducer.translate("attention"));
                }
            }
        }
        
    }

    public static class AddNodeWithSpecificNameAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 5492635494287414457L;
        private Graph graph;
        private Canvas canvas;
        private int radius;
        private Traduction traducer;

        public AddNodeWithSpecificNameAction(Graph graph, Canvas canvas, Traduction traducer, String buttonName) {
            super(buttonName);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
            this.radius = new Node(0,0,0).diameter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = new InputTextField(traducer.translate("ndNm")).getInput("Input");
            if (name != null) {
                int[] position = getFreePosition(radius * 2);
                Node node = new Node(position[0], position[1], name);
                graph.addNode(node);
                canvas.repaint();
            }

        }

        private int[] getFreePosition(int radius) {
            int[] position = new int[2];
            do {
                position[0] = (int) Math.floor(Math.random() * (canvas.getWidth() - radius)) + radius/2;
                position[1] = (int) Math.floor(Math.random() * (canvas.getHeight() - radius)) + radius/2;
            } while (graph.getNodeByPosition(position) != null);
            return position;
        }
        
    }

    public static class ColorNodesByDegreeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;
        private Graph graph;
        private Canvas canvas;

        public ColorNodesByDegreeAction(Graph graph, Canvas canvas, String buttonName) {
            super(buttonName);
            this.graph = graph;
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            graph.divideByOddOrEvenDegree();
            canvas.repaint();
        }

    }

    public static class FindArborescenceAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private Canvas canvas;
        private Traduction traducer;

        public FindArborescenceAction(Graph graph, Canvas canvas, Traduction traducer, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = graph.getNodeByName(new InputTextField(traducer.translate("arboQ") + ":").getInput(traducer.translate("arN")));
            if (startingNode == null) {
                new PopupMessage(this.traducer.translate("nodeNameError"), this.traducer.translate("error"));
            } else {
                graph.findArborescence(startingNode);
                paintArborescence();
                canvas.repaint();
            }
        }

        private void paintArborescence() {
            for (Node node : graph.nodesList) {
                if (node.parent != node) {
                    Edge edge = graph.getNodesConnection(node.parent, node);
                    edge.changeColor(Color.RED);
                    edge.oriented = true;
                    if (!graph.oriented) {
                        Edge revEdge = graph.getNodesConnection(node, node.parent);
                        revEdge.changeColor(Color.RED);
                    }
                }
            }
        }
        
    }

}
