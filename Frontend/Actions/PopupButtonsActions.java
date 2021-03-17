package Frontend.Actions;

import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.AbstractAction;

import Backend.*;
import Backend.Exceptions.*;
import Backend.Traduction.Traduction;
import Frontend.*;
import Frontend.GraphicalParts.PopupMessage;
import Frontend.GraphicalParts.StepperGUI;

public class PopupButtonsActions {

    public static class FindEulerianPathAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Graph graph;
        private Canvas canvas;
        private int[] position;
        private Traduction traducer;
        private Path eulerianPath;

        public FindEulerianPathAction(Graph graph, Canvas canvas, Traduction traducer, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
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
                    new StepperGUI(graph, canvas, this.eulerianPath, TipoAlgoritmo.EULERIANO, traducer, "Stepper");
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
        private Canvas canvas;
        private Traduction traducer;
        private int[] position;
        private Path hamiltonianPath;

        public FindHamiltonianPathAction(Graph graph, Canvas canvas, Traduction traducer, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.traducer = traducer;
            this.canvas = canvas;
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = graph.getNodeByPosition(position);
            if (startingNode != null) {
                 this.hamiltonianPath = new Path(startingNode, startingNode);
                 this.hamiltonianPath.findHamiltonianPath(graph, graph.nodesList.size());
                if (this.hamiltonianPath.foundHamiltonianPath) {
                    new StepperGUI(graph, canvas, this.hamiltonianPath, TipoAlgoritmo.HAMILTONIANO, traducer, "Stepper");
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
        private Canvas canvas;
        private int xPosition;
        private int yPosition;

        public AddNodeAction(Graph graph, Canvas canvas, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.xPosition = position[0];
            this.yPosition = position[1];
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nodeName = getLeastNumberNamePossible(graph.nodesList.size());
            Node node = new Node(xPosition, yPosition, nodeName);
            graph.addNode(node);
            canvas.paintComponent(canvas.getGraphics());

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
        private Canvas canvas;
        private int[] position;

        public RemoveNodeAction(Graph graph, Canvas canvas, int[] position, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
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
                canvas.repaint();
            }
        }
    }

    public static class ChangeColorOfNodeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 6088549357706659851L;
        private Node node;
        private Canvas canvas;
        private Color color;

        public ChangeColorOfNodeAction(Node node, Canvas canvas, Color color, String buttonName) {
            super(buttonName);
            this.node = node;
            this.canvas = canvas;
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (node != null) {
                node.color = color;
            }
            canvas.repaint();
        }
    }

    public static class ConnectAllSelectedToNode extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 6631150416007845101L;
        private Graph graph;
        private int[] position;
        private Canvas canvas;

        public ConnectAllSelectedToNode(Graph graph, int[] position, Canvas canvas, String name) {
            super(name);
            this.graph = graph;
            this.position = position;
            this.canvas = canvas;
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
            canvas.repaint();
        }
    }
    
}
