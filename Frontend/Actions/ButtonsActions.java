package Frontend.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
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
import Frontend.Canvas;
import Frontend.MainGraphical;
import Frontend.GraphicalParts.InputTextField;
import Frontend.GraphicalParts.PopupMessage;
import Frontend.GraphicalParts.StepperGUI;

public class ButtonsActions {

    public static class Stepper extends AbstractAction {
        private static final long serialVersionUID = -4905679823129595179L;
        private Graph graph;
        private StepperGUI stepperGui;
        public Stepper(String name, Graph graph) {
            super(name);
            this.graph = graph;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            this.graph.changeStepperStatus();
            if (graph.stepper) {
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
        private Graph graph;
        private Canvas canvas;

        public ResetAction(Graph graph, Canvas canvas, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            graph.resetGraphToStandardValues();
            canvas.repaint();
        }
        
    }

    public static class ChangeGraphOrientation extends AbstractAction {

        private static final long serialVersionUID = 56753321130683720L;
        private Graph graph;
        private Canvas canvas;
        private Traduction traducer;
        private JPanel panel;
        private JFrame frame;

        public ChangeGraphOrientation(JFrame frame, Graph graph, Canvas canvas, String name, Traduction traducer) {
            super(name);
            this.frame = frame;
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
            this.panel = new JPanel();
            this.panel.add(new JLabel(traducer.translate("graphNotEmpty")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.graph.edgesList.isEmpty()) {
                changeOrientation((JMenuItem) e.getSource());
            } else {
                int result = JOptionPane.showConfirmDialog(this.frame, this.panel, traducer.translate("attention"), JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    changeOrientation((JMenuItem) e.getSource());
                }
            }
        }

        private void changeOrientation(JMenuItem item) {
            this.graph.changeGraphOrientation();
            if (this.graph.oriented) {
                item.setText(traducer.translate("gOrientation1"));
            } else {
                item.setText(traducer.translate("gOrientation2"));
            }
            canvas.repaint();
        }
        
    }
    public static class AddNodeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 5492635494287414457L;
        private Graph graph;
        private Canvas canvas;
        private int radius;

        public AddNodeAction(Graph graph, Canvas canvas, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.radius = new Node(0,0,0).diameter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] position = getFreePosition(radius * 2);
            String nodeName = getLeastNumberNamePossible(graph.nodesList.size());
            Node node = new Node(position[0], position[1], nodeName);
            graph.addNode(node);
            canvas.paintComponent(canvas.getGraphics());

        }

        private int[] getFreePosition(int radius) {
            int[] position = new int[2];
            do {
                position[0] = (int) Math.floor(Math.random() * (canvas.getWidth() - radius)) + radius/2;
                position[1] = (int) Math.floor(Math.random() * (canvas.getHeight() - radius)) + radius/2;
            } while (graph.getNodeByPosition(position) != null);
            return position;
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
        private Traduction traducer;

        public RemoveNodeAction(Graph graph, Canvas canvas, Traduction traducer, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!graph.nodesList.isEmpty()) {
                String name = new InputTextField(traducer.translate("delMex")).getInput("Action");
                Node toDelete = graph.getNodeByName(name);
                if (toDelete != null) {
                    try {
                        graph.deleteNode(toDelete);
                    } catch (NodeDoesNotExitsException e1) {
                    }
                    canvas.repaint();
                } else {
                    new PopupMessage(traducer.translate("ndNmNF"), traducer.translate("help"));
                }
            }
        }
    }

    public static class ClearAllAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 5300657661510139648L;
        private Graph graph;
        private Canvas canvas;

        public ClearAllAction(Graph graph, Canvas canvas, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            graph.nodesList = new ArrayList<Node>();
            graph.edgesList = new ArrayList<Edge>();
            canvas.repaint();
        }

    }

    public static class SaveGraphAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 4641557323777445579L;
        private Graph graph;

        public SaveGraphAction(Graph graph, String name) {
            super(name);
            this.graph = graph;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainLoader.save("./myGraph.xml", graph);

        }

    }

    public static class LoadGraphAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2517029647508976026L;

        private JFrame window;
        private Traduction traducer;

        public LoadGraphAction(JFrame window, Traduction traducer, String name) {
            super(name);
            this.window = window;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Graph loadedGraph = MainLoader.load();
            if (loadedGraph != null) {
                window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                MainGraphical.openWindow(loadedGraph, traducer.language);
            }
        }

    }
    
    public static class LoadSpecificGraphAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2517029647508976026L;

        private JFrame window;
        private Traduction traducer;
        private String fileLocation;

        public LoadSpecificGraphAction(JFrame window, Traduction traducer, String filelocation, String name) {
            super(name);
            this.window = window;
            this.traducer = traducer;
            this.fileLocation = filelocation;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Graph loadedGraph = MainLoader.load(fileLocation);
            if (loadedGraph != null) {
                window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                MainGraphical.openWindow(loadedGraph, traducer.language);
            }
        }

    }

    public static class ConnectSelectedNodesAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = -6971889892057296923L;
        private Graph graph;
        private Canvas canvas;

        public ConnectSelectedNodesAction(Graph graph, Canvas canvas, String name) {
            super(name);
            this.graph = graph;
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i, j;
            Node node0, node1;
            for (i = 0; i < graph.nodesList.size(); i++) {
                node0 = graph.nodesList.get(i);
                for (j = 0; j < graph.nodesList.size(); j++) {
                    node1 = graph.nodesList.get(j);
                    if (!node0.equals(node1) && node0.selected == node1.selected && node0.selected == true) {
                        try {
                            graph.connectNodes(node0, node1);
                        } catch (NodesAlreadyConnectedException e1) {
                        }
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
