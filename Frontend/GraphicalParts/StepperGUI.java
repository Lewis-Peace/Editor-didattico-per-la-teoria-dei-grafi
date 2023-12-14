package Frontend.GraphicalParts;

import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import Backend.Edge;
import Backend.Graph;
import Backend.Node;
import Backend.Path;
import Backend.TipoAlgoritmo;
import Backend.Traduction.Traduction;

import Main.Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class StepperGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    int counter;

    public StepperGUI(Graph graph, Path path, TipoAlgoritmo algo, String name) {
        super(name);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JMenuBar menubar = new JMenuBar();
        JButton forwardButton = new JButton(new MakeAStepAction(graph, algo, path));
        JButton undoButton = new JButton(new UndoAStepAction(graph, algo, path));
        menubar.add(undoButton);
        menubar.add(new JLabel(Traduction.translate("stepperText")));
        menubar.add(forwardButton);
        this.add(menubar, BorderLayout.NORTH);
        this.add(new JButton(new DoAllStepsAction(graph, algo, path)), BorderLayout.SOUTH);
        this.pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 4 * 3);
        this.setVisible(true);
    }

    private class DoAllStepsAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        Graph graph;
        Path path;
        TipoAlgoritmo algo;
        
        public DoAllStepsAction(Graph graph, TipoAlgoritmo algo, Path path) {
            super(Traduction.translate("doAllSteps"));
            this.graph = graph;
            this.algo = algo;
            this.path = path;
            counter = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            counter = 0;
            if (algo == TipoAlgoritmo.EULERIANO) {
                colorEdgesEulerian(-1);
            } else if (algo == TipoAlgoritmo.HAMILTONIANO) {
                colorEdgesHamiltonian(-1);
            }
            Main.canvas.repaint();
        }
        

        private void colorEdgesEulerian(int steps) {
            if (steps == -1) {
                steps = this.path.edgePath.size();
            }
            for (int i = 0; i < steps; i++) {
                Edge edge = this.path.edgePath.get(i);
                Node node0 = graph.getNodeByName(edge.startingNode.name);
                Node node1 = graph.getNodeByName(edge.endingNode.name);
                edge = graph.getNodesConnection(node0, node1);
                edge.changeColor(Color.RED);
                if (!graph.oriented) {
                    Edge edge2 = graph.getNodesConnection(node1, node0);
                    edge2.changeColor(Color.RED);
                }
                edge.oriented = true;
            }
            Main.canvas.repaint();
        }
        
        private void colorEdgesHamiltonian(int steps) {
            if (steps == -1) {
                steps = this.path.nodePath.size();
            }
            for (int i = 0; i < steps; i++) {
                Node node0 = this.path.nodePath.get(i);
                Node node1;
                if (i < this.path.nodePath.size() - 1) {
                    node1 = this.path.nodePath.get(i + 1);
                } else {
                    node1 = this.path.nodePath.get(0);
                }
                graph.getNodesConnection(node0, node1).changeColor(Color.RED);
                graph.getNodesConnection(node0, node1).oriented = true;
                if (!graph.oriented) {
                    graph.getNodesConnection(node1, node0).changeColor(Color.RED);
                }
            }
            Main.canvas.repaint();
        }
    }

    private class MakeAStepAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        Graph graph;
        Path path;
        TipoAlgoritmo algo;

        public MakeAStepAction(Graph graph, TipoAlgoritmo algo, Path path) {
            super(">|");
            this.graph = graph;
            this.algo = algo;
            this.path = path;
            counter = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (algo == TipoAlgoritmo.EULERIANO) {
                if (path.edgePath.size() == counter) {

                } else {
                    counter++;
                }
            } else {
                if (path.nodePath.size() == counter) {

                } else {
                    counter++;
                }
            }
            if (algo == TipoAlgoritmo.EULERIANO) {
                colorEdgesEulerian(counter);
            } else if (algo == TipoAlgoritmo.HAMILTONIANO) {
                colorEdgesHamiltonian(counter);
            }
            Main.canvas.repaint();
        }
        

        private void colorEdgesEulerian(int steps) {
            if (steps == -1) {
                steps = this.path.edgePath.size();
            }
            for (int i = 0; i < steps; i++) {
                Edge edge = this.path.edgePath.get(i);
                Node node0 = graph.getNodeByName(edge.startingNode.name);
                Node node1 = graph.getNodeByName(edge.endingNode.name);
                edge = graph.getNodesConnection(node0, node1);
                edge.changeColor(Color.RED);
                if (!graph.oriented) {
                    Edge edge2 = graph.getNodesConnection(node1, node0);
                    edge2.changeColor(Color.RED);
                }
                edge.oriented = true;
            }
            Main.canvas.repaint();
        }
        
        private void colorEdgesHamiltonian(int steps) {
            if (steps == -1) {
                steps = this.path.nodePath.size();
            }
            for (int i = 0; i < steps; i++) {
                Node node0 = this.path.nodePath.get(i);
                Node node1;
                if (i < this.path.nodePath.size() - 1) {
                    node1 = this.path.nodePath.get(i + 1);
                } else {
                    node1 = this.path.nodePath.get(0);
                }
                graph.getNodesConnection(node0, node1).changeColor(Color.RED);
                graph.getNodesConnection(node0, node1).oriented = true;
                if (!graph.oriented) {
                    graph.getNodesConnection(node1, node0).changeColor(Color.RED);
                }
            }
            Main.canvas.repaint();
        }
        
    }
    private class UndoAStepAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        Graph graph;
        Path path;
        TipoAlgoritmo algo;

        public UndoAStepAction(Graph graph, TipoAlgoritmo algo, Path path) {
            super("|<");
            this.graph = graph;
            this.algo = algo;
            this.path = path;
            counter = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (counter < 1) {
                counter = 0;
            } else {
                counter--;
            }
            this.graph.resetGraphToStandardValues();
            if (algo == TipoAlgoritmo.EULERIANO) {
                colorEdgesEulerian(counter);
            } else if (algo == TipoAlgoritmo.HAMILTONIANO) {
                colorEdgesHamiltonian(counter);
            }
            Main.canvas.repaint();
        }
        
        private void colorEdgesEulerian(int steps) {
            if (steps == -1) {
                steps = this.path.edgePath.size();
            }
            for (int i = 0; i < steps; i++) {
                Edge edge = this.path.edgePath.get(i);
                Node node0 = graph.getNodeByName(edge.startingNode.name);
                Node node1 = graph.getNodeByName(edge.endingNode.name);
                edge = graph.getNodesConnection(node0, node1);
                edge.changeColor(Color.RED);
                if (!graph.oriented) {
                    Edge edge2 = graph.getNodesConnection(node1, node0);
                    edge2.changeColor(Color.RED);
                }
                edge.oriented = true;
            }
            Main.canvas.repaint();
        }

        private void colorEdgesHamiltonian(int steps) {
            if (steps == -1) {
                steps = this.path.nodePath.size();
            }
            for (int i = 0; i < steps; i++) {
                Node node0 = this.path.nodePath.get(i);
                Node node1;
                if (i < this.path.nodePath.size() - 1) {
                    node1 = this.path.nodePath.get(i + 1);
                } else {
                    node1 = this.path.nodePath.get(0);
                }
                graph.getNodesConnection(node0, node1).changeColor(Color.RED);
                graph.getNodesConnection(node0, node1).oriented = true;
                if (!graph.oriented) {
                    graph.getNodesConnection(node1, node0).changeColor(Color.RED);
                }
            }
            Main.canvas.repaint();
        }
        
    }
    
}
