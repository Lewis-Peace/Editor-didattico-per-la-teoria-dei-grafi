package Frontend.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Backend.Edge;
import Backend.Graph;
import Backend.Node;
import Backend.MainLoader;
import Backend.Exceptions.NodeDoesNotExitsException;
import Backend.Exceptions.NodesAlreadyConnectedException;
import Backend.Traduction.Traduction;
import Main.Main;
import Frontend.GraphicalParts.InputTextField;
import Frontend.GraphicalParts.PopupMessage;
import Frontend.GraphicalParts.StepperGUI;

public class ButtonsActions {

    public static class Stepper extends AbstractAction {
        private static final long serialVersionUID = -4905679823129595179L;
        private StepperGUI stepperGui;
        public Stepper(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            Main.graph.changeStepperStatus();
            if (Main.graph.stepper) {
                item.setText("Stepper: true");
                //this.stepperGui = new StepperGUI("Stepper");
            } else {
                item.setText("Stepper: false");
                stepperGui.dispatchEvent(new WindowEvent(stepperGui, WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    public static class ResetAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ResetAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.graph.resetGraphToStandardValues();
            Main.canvas.repaint();
        }
        
    }

    public static class ChangeGraphOrientation extends AbstractAction {

        private static final long serialVersionUID = 56753321130683720L;
        private JPanel panel;

        public ChangeGraphOrientation(String name) {
            super(name);
            this.panel = new JPanel();
            this.panel.add(new JLabel(Traduction.translate("graphNotEmpty")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.edgesList.isEmpty()) {
                changeOrientation((JMenuItem) e.getSource());
            } else {
                int result = JOptionPane.showConfirmDialog(Main.frame, this.panel, Traduction.translate("attention"), JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    changeOrientation((JMenuItem) e.getSource());
                }
            }
        }

        private void changeOrientation(JMenuItem item) {
            Main.graph.changeGraphOrientation();
            if (Main.graph.oriented) {
                item.setText(Traduction.translate("gOrientation1"));
            } else {
                item.setText(Traduction.translate("gOrientation2"));
            }
            Main.canvas.repaint();
        }
        
    }
    public static class AddNodeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 5492635494287414457L;
        private int radius;

        public AddNodeAction(String name) {
            super(name);
            this.radius = new Node(0,0,0).diameter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] position = getFreePosition(radius * 2);
            String nodeName = getLeastNumberNamePossible(Main.graph.nodesList.size());
            Node node = new Node(position[0], position[1], nodeName);
            Main.graph.addNode(node);
            Main.canvas.paintComponent(Main.canvas.getGraphics());

        }

        private int[] getFreePosition(int radius) {
            int[] position = new int[2];
            do {
                position[0] = (int) Math.floor(Math.random() * (Main.canvas.getWidth() - radius)) + radius/2;
                position[1] = (int) Math.floor(Math.random() * (Main.canvas.getHeight() - radius)) + radius/2;
            } while (Main.graph.getNodeByPosition(position) != null);
            return position;
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

        public RemoveNodeAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!Main.graph.nodesList.isEmpty()) {
                String name = new InputTextField(Traduction.translate("delMex")).getInput("Action");
                Node toDelete = Main.graph.getNodeByName(name);
                if (toDelete != null) {
                    try {
                        Main.graph.deleteNode(toDelete);
                    } catch (NodeDoesNotExitsException e1) {
                    }
                    Main.canvas.repaint();
                } else {
                    new PopupMessage(Traduction.translate("ndNmNF"), Traduction.translate("help"));
                }
            }
        }
    }

    public static class ClearAllAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 5300657661510139648L;

        public ClearAllAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.graph.nodesList = new ArrayList<Node>();
            Main.graph.edgesList = new ArrayList<Edge>();
            Main.canvas.repaint();
        }

    }

    public static class SaveGraphAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 4641557323777445579L;

        public SaveGraphAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainLoader.save("./myGraph.xml", Main.graph);

        }

    }

    public static class LoadGraphAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2517029647508976026L;

        public LoadGraphAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Graph loadedGraph = MainLoader.load();
            if (loadedGraph != null) {
                Main.frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                Main.frame.dispatchEvent(new WindowEvent(Main.frame, WindowEvent.WINDOW_CLOSING));
                Main.openWindow(loadedGraph, Traduction.language);
            }
        }

    }
    
    public static class LoadSpecificGraphAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2517029647508976026L;

        private String fileLocation;

        public LoadSpecificGraphAction(String filelocation, String name) {
            super(name);
            this.fileLocation = filelocation;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Graph loadedGraph = MainLoader.load(fileLocation);
            if (loadedGraph != null) {
                Main.frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                Main.frame.dispatchEvent(new WindowEvent(Main.frame, WindowEvent.WINDOW_CLOSING));
                Main.openWindow(loadedGraph, Traduction.language);
            }
        }

    }

    public static class ConnectSelectedNodesAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = -6971889892057296923L;
        public ConnectSelectedNodesAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i, j;
            Node node0, node1;
            for (i = 0; i < Main.graph.nodesList.size(); i++) {
                node0 = Main.graph.nodesList.get(i);
                for (j = 0; j < Main.graph.nodesList.size(); j++) {
                    node1 = Main.graph.nodesList.get(j);
                    if (!node0.equals(node1) && node0.selected == node1.selected && node0.selected == true) {
                        try {
                            Main.graph.connectNodes(node0, node1);
                        } catch (NodesAlreadyConnectedException e1) {
                        }
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
