package Frontend.Actions;

import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.AbstractAction;

import Backend.*;
import Backend.Exceptions.*;
import Backend.Traduction.Traduction;

import Main.Main;

import Frontend.GraphicalParts.PopupMessage;
import Frontend.GraphicalParts.StepperGUI;

public class PopupButtonsActions {

    public static class FindEulerianPathAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private int[] position;
        private Traduction traducer;
        private Path eulerianPath;

        public FindEulerianPathAction(Graph graph, Traduction traducer, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.traducer = traducer;
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = graph.getNodeByPosition(position);
            if (startingNode != null) {
                this.eulerianPath = new Path(startingNode, startingNode);
                this.eulerianPath.findEulerianPath(graph, graph.edgesList.size());
                if (this.eulerianPath.eluerianEnded) {
                    new StepperGUI(graph, this.eulerianPath, TipoAlgoritmo.EULERIANO, traducer, "Stepper");
                } else {
                    new PopupMessage(this.traducer.translate("noPath2"), this.traducer.translate("attention"));
                }
            } else {
                new PopupMessage(this.traducer.translate("noNodeSelected"), this.traducer.translate("error"));
            }
        }
    }

    public static class FindHamiltonianPathAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private Traduction traducer;
        private int[] position;
        private Path hamiltonianPath;

        public FindHamiltonianPathAction(Graph graph, Traduction traducer, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.traducer = traducer;
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = graph.getNodeByPosition(position);
            if (startingNode != null) {
                 this.hamiltonianPath = new Path(startingNode, startingNode);
                 this.hamiltonianPath.findHamiltonianPath(graph, graph.nodesList.size());
                if (this.hamiltonianPath.foundHamiltonianPath) {
                    new StepperGUI(graph, this.hamiltonianPath, TipoAlgoritmo.HAMILTONIANO, traducer, "Stepper");
                } else {
                    new PopupMessage(this.traducer.translate("noPath3"), this.traducer.translate("attention"));
                }
            } else {
                new PopupMessage(this.traducer.translate("noNodeSelected"), this.traducer.translate("error"));
            }
        }
    }

    public static class AddNodeAction extends AbstractAction {

        private static final long serialVersionUID = 5492635494287414457L;
        private Graph graph;
        private int xPosition;
        private int yPosition;

        public AddNodeAction(Graph graph, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.xPosition = position[0];
            this.yPosition = position[1];
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nodeName = getLeastNumberNamePossible(graph.nodesList.size());
            Node node = new Node(xPosition, yPosition, nodeName);
            graph.addNode(node);
            Main.canvas.paintComponent(Main.canvas.getGraphics());

        }

        private String getLeastNumberNamePossible(int maxName) {
            for (int i = 0; i <= maxName; i++) {
                if (graph.getNodeByName(i + "") == null) {
                    return i + "";
                }
            }
            return "-1";
        }

    }

    public static class RemoveNodeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7851390276908254776L;
        private Graph graph;
        private int[] position;

        public RemoveNodeAction(Graph graph, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!graph.nodesList.isEmpty()) {
                Node toDelete = graph.getNodeByPosition(position);
                try {
                    graph.deleteNode(toDelete);
                } catch (NodeDoesNotExitsException e1) {
                    System.err.println(e1.toString());
                }
                Main.canvas.repaint();
            }
        }
    }

    public static class ChangeColorOfNodeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 6088549357706659851L;
        private Node node;
        private Color color;

        public ChangeColorOfNodeAction(Node node, Color color, String buttonName) {
            super(buttonName);
            this.node = node;
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (node != null) {
                node.color = color;
            }
            Main.canvas.repaint();
        }
    }

    public static class ConnectAllSelectedToNode extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 6631150416007845101L;
        private Graph graph;
        private int[] position;

        public ConnectAllSelectedToNode(Graph graph, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i;
            Node node = graph.getNodeByPosition(position);
            for (i = 0; i < graph.nodesList.size(); i++) {
                Node node1 = graph.nodesList.get(i);
                if (node1.selected && !node1.equals(node)) {
                    try {
                        graph.connectNodes(node1, node);
                    } catch (NodesAlreadyConnectedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            for (i = 0; i < graph.nodesList.size(); i++) {
                graph.nodesList.get(i).selected = false;
            }
            Main.canvas.repaint();
        }
    }
    
}
