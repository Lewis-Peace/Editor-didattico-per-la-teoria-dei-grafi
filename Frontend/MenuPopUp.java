package Frontend;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Backend.Edge;
import Backend.Graph;
import Backend.Node;
import Backend.Traduction.Traduction;
import Frontend.Actions.PopupButtonsActions;
import Frontend.GraphicalParts.EdgesSelectedWindow;

public class MenuPopUp extends JPopupMenu {

    /**
     *
     */
    private static final long serialVersionUID = 3815404717160132218L;

    Graph graph;
    Canvas canvas;

    public MenuPopUp(Graph graph, Canvas canvas, ArrayList<Edge> selectedPath, int[] position, EdgesSelectedWindow edgesSelectedWindow) {
        this.graph = graph;
        this.canvas = canvas;
        this.add(new JMenuItem(new PopupButtonsActions.AddNodeAction(position, Traduction.translate("+Node"))));
        this.add(new JMenuItem(new PopupButtonsActions.RemoveNodeAction(position, Traduction.translate("-Node"))));
        this.add(new JMenuItem(new PopupButtonsActions.ConnectAllSelectedToNode(position, Traduction.translate("conThNo"))));
        this.add(new JMenuItem(new PopupButtonsActions.FindHamiltonianPathAction(position, Traduction.translate("ham"))));
        this.add(new JMenuItem(new PopupButtonsActions.FindEulerianPathAction(position, Traduction.translate("eul"))));
        addColorsToMenu(this, canvas, graph, position);
    }

    private void addColorsToMenu(JPopupMenu menu, Canvas canvas, Graph graph, int[] position) {
        Node node = graph.getNodeByPosition(position);
        JMenu colorsMenu = new JMenu(Traduction.translate("cngCol"));
        colorsMenu.add(new JMenuItem(new PopupButtonsActions.ChangeColorOfNodeAction(node, Color.BLACK, Traduction.translate("black"))));
        colorsMenu.add(new JMenuItem(new PopupButtonsActions.ChangeColorOfNodeAction(node, Color.WHITE, Traduction.translate("white"))));
        colorsMenu.add(new JMenuItem(new PopupButtonsActions.ChangeColorOfNodeAction(node, Color.RED, Traduction.translate("red"))));
        menu.add(colorsMenu);
    }

}
