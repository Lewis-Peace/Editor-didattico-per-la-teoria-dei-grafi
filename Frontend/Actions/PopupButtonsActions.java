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
        private int[] position;
        private Path eulerianPath;

        public FindEulerianPathAction(int[] position, String name) {
            super(name);
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = Main.graph.getNodeByPosition(position);
            if (startingNode != null) {
                this.eulerianPath = new Path(startingNode, startingNode);
                this.eulerianPath.findEulerianPath(Main.graph, Main.graph.edgesList.size());
                if (this.eulerianPath.eluerianEnded) {
                    new StepperGUI(Main.graph, this.eulerianPath, TipoAlgoritmo.EULERIANO, "Stepper");
                } else {
                    new PopupMessage(Traduction.translate("noPath2"), Traduction.translate("attention"));
                }
            } else {
                new PopupMessage(Traduction.translate("noNodeSelected"), Traduction.translate("error"));
            }
        }
    }

    public static class FindHamiltonianPathAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private int[] position;
        private Path hamiltonianPath;

        public FindHamiltonianPathAction(int[] position, String name) {
            super(name);
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node startingNode = Main.graph.getNodeByPosition(position);
            if (startingNode != null) {
                 this.hamiltonianPath = new Path(startingNode, startingNode);
                 this.hamiltonianPath.findHamiltonianPath(Main.graph, Main.graph.nodesList.size());
                if (this.hamiltonianPath.foundHamiltonianPath) {
                    new StepperGUI(Main.graph, this.hamiltonianPath, TipoAlgoritmo.HAMILTONIANO, "Stepper");
                } else {
                    new PopupMessage(Traduction.translate("noPath3"), Traduction.translate("attention"));
                }
            } else {
                new PopupMessage(Traduction.translate("noNodeSelected"), Traduction.translate("error"));
            }
        }
    }

    public static class AddNodeAction extends AbstractAction {

        private static final long serialVersionUID = 5492635494287414457L;
        private int xPosition;
        private int yPosition;

        public AddNodeAction(int[] position, String name) {
            super(name);
            this.xPosition = position[0];
            this.yPosition = position[1];
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nodeName = getLeastNumberNamePossible(Main.graph.nodesList.size());
            Node node = new Node(xPosition, yPosition, nodeName);
            Main.graph.addNode(node);
            Main.canvas.paintComponent(Main.canvas.getGraphics());

        }

        private String getLeastNumberNamePossible(int maxName) {
            for (int i = 0; i <= maxName; i++) {
                if (Main.graph.getNodeByName(i + "") == null) {
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
        private int[] position;

        public RemoveNodeAction(int[] position, String name) {
            super(name);
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!Main.graph.nodesList.isEmpty()) {
                Node toDelete = Main.graph.getNodeByPosition(position);
                try {
                    Main.graph.deleteNode(toDelete);
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
        private int[] position;

        public ConnectAllSelectedToNode(int[] position, String name) {
            super(name);
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i;
            Node node = Main.graph.getNodeByPosition(position);
            for (i = 0; i < Main.graph.nodesList.size(); i++) {
                Node node1 = Main.graph.nodesList.get(i);
                if (node1.selected && !node1.equals(node)) {
                    try {
                        Main.graph.connectNodes(node1, node);
                    } catch (NodesAlreadyConnectedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            for (i = 0; i < Main.graph.nodesList.size(); i++) {
                Main.graph.nodesList.get(i).selected = false;
            }
            Main.canvas.repaint();
        }
    }
    
}
