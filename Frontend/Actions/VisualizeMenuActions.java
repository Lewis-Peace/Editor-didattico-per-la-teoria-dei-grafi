package Frontend.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Backend.AdjacencyMatrix;
import Backend.IncidenceMatrix;
import Frontend.GraphicalParts.MatrixWindow;
import Frontend.GraphicalParts.PopupMessage;

import Main.Main;

public class VisualizeMenuActions {
    public static class VisualizeAdjacenyMatrixAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;

        public VisualizeAdjacenyMatrixAction(String buttonName) {
            super(buttonName);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.nodesList.isEmpty()) {
                new PopupMessage(Main.traducer.translate("emptyGraph"), Main.traducer.translate("error"));
            } else {
                AdjacencyMatrix m = Main.graph.getAdjacencyMatrix();
                new MatrixWindow(m);
            }
        }

    }

    public static class VisualizeIncidenceMatrixAction extends AbstractAction {

        private static final long serialVersionUID = 7298071656742468894L;

        public VisualizeIncidenceMatrixAction(String buttonName) {
            super(buttonName);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.nodesList.isEmpty()) {
                new PopupMessage(Main.traducer.translate("emptyGraph"), Main.traducer.translate("error"));
            } else {
                IncidenceMatrix m = Main.graph.getIncidenceMatrix();
                new MatrixWindow(m);
            }
        }

    }
    
    public static class VisualizeNodesDegreeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 7298071656742468894L;

        public VisualizeNodesDegreeAction(String buttonName) {
            super(buttonName);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.graph.nodesList.isEmpty()) {
                new PopupMessage(Main.traducer.translate("emptyGraph"), Main.traducer.translate("error"));
            } else {
                new MatrixWindow(Main.graph, Main.traducer);
            }
        }

    }
}
