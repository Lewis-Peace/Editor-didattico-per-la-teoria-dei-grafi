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
    Traduction traducer;

    public MenuPopUp(Graph graph, Canvas canvas, Traduction traducer, ArrayList<Edge> selectedPath, int[] position, EdgesSelectedWindow edgesSelectedWindow) {
        this.graph = graph;
        this.canvas = canvas;
        this.traducer = traducer;
        this.add(new JMenuItem(new PopupButtonsActions.AddNodeAction(graph, position, traducer.translate("+Node"))));
        this.add(new JMenuItem(new PopupButtonsActions.RemoveNodeAction(graph, position, traducer.translate("-Node"))));
        this.add(new JMenuItem(new PopupButtonsActions.ConnectAllSelectedToNode(graph, position, traducer.translate("conThNo"))));
        this.add(new JMenuItem(new PopupButtonsActions.FindHamiltonianPathAction(graph, traducer, position, traducer.translate("ham"))));
        this.add(new JMenuItem(new PopupButtonsActions.FindEulerianPathAction(graph, traducer, position, traducer.translate("eul"))));
        addColorsToMenu(this, canvas, graph, position);
    }

    private void addColorsToMenu(JPopupMenu menu, Canvas canvas, Graph graph, int[] position) {
        Node node = graph.getNodeByPosition(position);
        JMenu colorsMenu = new JMenu(traducer.translate("cngCol"));
        colorsMenu.add(new JMenuItem(new PopupButtonsActions.ChangeColorOfNodeAction(node, Color.BLACK, traducer.translate("black"))));
        colorsMenu.add(new JMenuItem(new PopupButtonsActions.ChangeColorOfNodeAction(node, Color.WHITE, traducer.translate("white"))));
        colorsMenu.add(new JMenuItem(new PopupButtonsActions.ChangeColorOfNodeAction(node, Color.RED, traducer.translate("red"))));
        menu.add(colorsMenu);
    }

}
