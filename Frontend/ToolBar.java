package Frontend;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Backend.*;
import Backend.Traduction.Traduction;
import Frontend.Actions.AdvancedActions;
import Frontend.Actions.ButtonsActions;
import Frontend.Actions.OptionsActions;
import Frontend.Actions.VisualizeMenuActions;
import Frontend.GraphicalParts.GraphicalEdge.ArrowType;

public class ToolBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 6229926663645183112L;
    public static Graph graph;
    public static Canvas canvas;
    private static JFrame window;
    private static Traduction traducer;
    private JMenu menu;
    private JMenuItem item;

    public ToolBar(Graph graph, Canvas canvas, JFrame window, Traduction traducer) {
        ToolBar.graph = graph;
        ToolBar.canvas = canvas;
        ToolBar.window = window;
        ToolBar.traducer = traducer;
        this.populateToolbar(this);
    }

    /**
     * Fills the toolbar with all the elements
     * @param toolbar The toolbar to fill
     */
    private void populateToolbar(JMenuBar toolbar) {
        toolbar.add(fileMenu());
        toolbar.add(editMenu());
        toolbar.add(advancedMenu());
        toolbar.add(visualizeMenu());
        toolbar.add(optionsMenu());
        toolbar.add(new JMenuItem(new ButtonsActions.ChangeGraphOrientation(window, graph, canvas, orientedButtonName(graph.oriented), traducer)));
    }

    /**
     * Initializes the advanced options menu so it can be added in the toolbar
     * @return The advanced menu options
     */
    private JMenu advancedMenu() {
        menu = new JMenu(traducer.translate("advMenu"));
        //menu.add(new JMenuItem(new AdvancedActions.ColorNodesByDegreeAction(graph, canvas, traducer.translate("divDeg"))));
        //menu.addSeparator();
        item = new JMenuItem(new AdvancedActions.AddNodeWithSpecificNameAction(graph, canvas, traducer, traducer.translate("+NdName")));
        setupItem(menu, item, "");
        item = new JMenuItem(new AdvancedActions.FindHamiltonianPathAction(graph, canvas, traducer, traducer.translate("ham")));
        setupItem(menu, item, traducer.translate("toolTipHam"));
        item = new JMenuItem(new AdvancedActions.FindEulerianPathAction(graph, canvas, traducer, traducer.translate("eul")));
        setupItem(menu, item, traducer.translate("toolTipEul"));
        item = menu.add(new JMenuItem(new AdvancedActions.FindShortestPathFrom2Nodes(graph, canvas, traducer, traducer.translate("stPth"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new AdvancedActions.FindIfBipartedGraph(graph, canvas, traducer, traducer.translate("bip"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new AdvancedActions.FindArborescenceAction(graph, canvas, traducer, traducer.translate("arbo"))));
        return menu;
    }

    private JMenu visualizeMenu() {
        menu = new JMenu(traducer.translate("viewMenu"));
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeAdjacenyMatrixAction(graph, traducer, traducer.translate("adjMat"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeIncidenceMatrixAction(graph, traducer, traducer.translate("incMat"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeNodesDegreeAction(graph, traducer, traducer.translate("ndDeg"))));
        return menu;
    }

    /**
     * Initializes the file menu options so it can be added in the toolbar
     * @return The file menu options
     */
    private JMenu fileMenu() {
        JMenu specificLoadMenu = famousGraphs();
        menu = new JMenu(traducer.translate("fileMenu"));
        item = menu.add(new JMenuItem(new ButtonsActions.SaveGraphAction(graph, traducer.translate("save"))));
        item.setToolTipText(traducer.translate("toolTipSave"));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.LoadGraphAction(window, traducer, traducer.translate("load"))));
        item.setToolTipText(traducer.translate("toolTipLoad"));
        menu.addSeparator();
        menu.add(specificLoadMenu);
        return menu;
    }

    private JMenu famousGraphs() {
        JMenu menu = new JMenu(traducer.translate("famousGraphs"));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/clebschGraph.xml", traducer.translate("clebsch"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/completeGraph.xml", traducer.translate("completeGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/cubeGraph.xml", traducer.translate("cubeGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/dyckGraph.xml", traducer.translate("dyckGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/grotzschGraph.xml", traducer.translate("grotzsGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/wagnerGraph.xml", traducer.translate("wagnerGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/dudeneyGraph.xml", traducer.translate("dudeneyGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/franklinGraph.xml", traducer.translate("franklinGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/markstromGraph.xml", traducer.translate("markstromGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/herschelGraph.xml", traducer.translate("herschelGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/petersenGraph.xml", traducer.translate("petersenGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/youngFibonacciGraph.xml", traducer.translate("fiboGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/strangeBipartedGraph.xml", traducer.translate("strBiGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/bipartedGraph.xml", traducer.translate("loadBi"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/eulerianGraph.xml", traducer.translate("loadEul"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/hamiltonianGraph.xml", traducer.translate("loadHam"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction(window, traducer, "./Graphs/exampleGraph.xml", traducer.translate("exampleGraph"))));
        return menu;
    }

    /**
     * Initializes the edit menu options so it can be added in the toolbar
     * @return The edit menu options
     */
    private JMenu editMenu() {
        menu = new JMenu(traducer.translate("editMenu"));
        item = menu.add(new JMenuItem(new ButtonsActions.AddNodeAction(graph, canvas, traducer.translate("+Node"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.RemoveNodeAction(graph, canvas, traducer, traducer.translate("-Node"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ClearAllAction(graph, canvas, traducer.translate("clear"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ConnectSelectedNodesAction(graph, canvas, traducer.translate("conNodes"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ResetAction(graph, canvas, traducer.translate("reset"))));
        return menu;
    }

    /**
     * Initializes the options menu so it can be added in the toolbar
     * @return The options menu
     */
    private JMenu optionsMenu() {
        menu = new JMenu(traducer.translate("optMenu"));
        JMenu arrowTypesMenu = new JMenu(traducer.translate("arTyMen"));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(traducer.translate("arTy1"), graph, canvas, ArrowType.FILLED)));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(traducer.translate("arTy2"), graph, canvas, ArrowType.EMPTY)));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(traducer.translate("arTy3"), graph, canvas, ArrowType.STANDARD)));
        menu.add(new JMenuItem(new OptionsActions.ChangeDiameterAction(traducer.translate("cgDiBt"), graph, canvas, traducer)));
        menu.addSeparator();
        menu.add(arrowTypesMenu);
        menu.addSeparator();
        JMenu languageMenu = new JMenu(traducer.translate("langMenu"));
        languageMenu.add(new JMenuItem(new OptionsActions.ChangeLanguageAction(window, graph, "italian", traducer.translate("langMenuOpt1"))));
        languageMenu.add(new JMenuItem(new OptionsActions.ChangeLanguageAction(window, graph, "english", traducer.translate("langMenuOpt2"))));
        menu.add(languageMenu);
        menu.addSeparator();
        menu.add(new JMenuItem(new OptionsActions.OpenHelpWindowAction(traducer, traducer.translate("help"))));
        return menu;
    }

    private void setupItem(JMenu menu, JMenuItem item, String tooltip) {
        if (!tooltip.equals("")) {
            item.setToolTipText(tooltip);
        }
        menu.add(item);
        menu.addSeparator();
    }

    private String orientedButtonName(Boolean oriented) {
        if (oriented) {
            return traducer.translate("gOrientation1");
        } else {
            return traducer.translate("gOrientation2");
        }
    }

}
