package Frontend.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Backend.AdjacencyMatrix;
import Backend.Graph;
import Backend.IncidenceMatrix;
import Backend.Traduction.Traduction;
import Frontend.GraphicalParts.MatrixWindow;
import Frontend.GraphicalParts.PopupMessage;

public class VisualizeMenuActions {
    public static class VisualizeAdjacenyMatrixAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;
        private Graph graph;
        private Traduction traducer;

        public VisualizeAdjacenyMatrixAction(Graph graph, Traduction traducer, String buttonName) {
            super(buttonName);
            this.traducer = traducer;
            this.graph = graph;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (graph.nodesList.isEmpty()) {
                new PopupMessage(this.traducer.translate("emptyGraph"), this.traducer.translate("error"));
            } else {
                AdjacencyMatrix m = graph.getAdjacencyMatrix();
                new MatrixWindow(m);
            }
        }

    }

    public static class VisualizeIncidenceMatrixAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;
        private Graph graph;
        private Traduction traducer;

        public VisualizeIncidenceMatrixAction(Graph graph, Traduction traducer, String buttonName) {
            super(buttonName);
            this.graph = graph;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (graph.nodesList.isEmpty()) {
                new PopupMessage(this.traducer.translate("emptyGraph"), this.traducer.translate("error"));
            } else {
                IncidenceMatrix m = graph.getIncidenceMatrix();
                new MatrixWindow(m);
            }
        }

    }
    
    public static class VisualizeNodesDegreeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;
        private Graph graph;
        private Traduction traducer;

        public VisualizeNodesDegreeAction(Graph graph, Traduction traducer, String buttonName) {
            super(buttonName);
            this.graph = graph;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (graph.nodesList.isEmpty()) {
                new PopupMessage(this.traducer.translate("emptyGraph"), this.traducer.translate("error"));
            } else {
                new MatrixWindow(this.graph, this.traducer);
            }
        }

    }
}
