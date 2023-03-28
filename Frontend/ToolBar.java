package Frontend;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Frontend.Actions.AdvancedActions;
import Frontend.Actions.ButtonsActions;
import Frontend.Actions.OptionsActions;
import Frontend.Actions.VisualizeMenuActions;
import Frontend.GraphicalParts.GraphicalEdge.ArrowType;
import Main.Main;

public class ToolBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 6229926663645183112L;
    private JMenu menu;
    private JMenuItem item;

    public ToolBar() {
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
        toolbar.add(new JMenuItem(new ButtonsActions.ChangeGraphOrientation(orientedButtonName(Main.graph.oriented))));
    }

    /**
     * Initializes the advanced options menu so it can be added in the toolbar
     * @return The advanced menu options
     */
    private JMenu advancedMenu() {
        menu = new JMenu(Main.traducer.translate("advMenu"));
        //menu.add(new JMenuItem(new AdvancedActions.ColorNodesByDegreeAction(graph, canvas, traducer.translate("divDeg"))));
        //menu.addSeparator();
        item = new JMenuItem(new AdvancedActions.AddNodeWithSpecificNameAction(Main.traducer.translate("+NdName")));
        setupItem(menu, item, "");
        item = new JMenuItem(new AdvancedActions.FindHamiltonianPathAction(Main.traducer.translate("ham")));
        setupItem(menu, item, Main.traducer.translate("toolTipHam"));
        item = new JMenuItem(new AdvancedActions.FindEulerianPathAction(Main.traducer.translate("eul")));
        setupItem(menu, item, Main.traducer.translate("toolTipEul"));
        item = menu.add(new JMenuItem(new AdvancedActions.FindShortestPathFrom2Nodes(Main.traducer.translate("stPth"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new AdvancedActions.FindIfBipartedGraph(Main.traducer.translate("bip"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new AdvancedActions.FindArborescenceAction(Main.traducer.translate("arbo"))));
        return menu;
    }

    private JMenu visualizeMenu() {
        menu = new JMenu(Main.traducer.translate("viewMenu"));
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeAdjacenyMatrixAction(Main.traducer.translate("adjMat"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeIncidenceMatrixAction(Main.traducer.translate("incMat"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeNodesDegreeAction(Main.traducer.translate("ndDeg"))));
        return menu;
    }

    /**
     * Initializes the file menu options so it can be added in the toolbar
     * @return The file menu options
     */
    private JMenu fileMenu() {
        JMenu specificLoadMenu = famousGraphs();
        menu = new JMenu(Main.traducer.translate("fileMenu"));
        item = menu.add(new JMenuItem(new ButtonsActions.SaveGraphAction(Main.traducer.translate("save"))));
        item.setToolTipText(Main.traducer.translate("toolTipSave"));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.LoadGraphAction(Main.traducer.translate("load"))));
        item.setToolTipText(Main.traducer.translate("toolTipLoad"));
        menu.addSeparator();
        menu.add(specificLoadMenu);
        return menu;
    }

    private JMenu famousGraphs() {
        JMenu menu = new JMenu(Main.traducer.translate("famousGraphs"));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/clebschGraph.xml", Main.traducer.translate("clebsch"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/completeGraph.xml", Main.traducer.translate("completeGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/cubeGraph.xml", Main.traducer.translate("cubeGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/dyckGraph.xml", Main.traducer.translate("dyckGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/grotzschGraph.xml", Main.traducer.translate("grotzsGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/wagnerGraph.xml", Main.traducer.translate("wagnerGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/dudeneyGraph.xml", Main.traducer.translate("dudeneyGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/franklinGraph.xml", Main.traducer.translate("franklinGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/markstromGraph.xml", Main.traducer.translate("markstromGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/herschelGraph.xml", Main.traducer.translate("herschelGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/petersenGraph.xml", Main.traducer.translate("petersenGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/youngFibonacciGraph.xml", Main.traducer.translate("fiboGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/strangeBipartedGraph.xml", Main.traducer.translate("strBiGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/bipartedGraph.xml", Main.traducer.translate("loadBi"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/eulerianGraph.xml", Main.traducer.translate("loadEul"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/hamiltonianGraph.xml", Main.traducer.translate("loadHam"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/exampleGraph.xml", Main.traducer.translate("exampleGraph"))));
        return menu;
    }

    /**
     * Initializes the edit menu options so it can be added in the toolbar
     * @return The edit menu options
     */
    private JMenu editMenu() {
        menu = new JMenu(Main.traducer.translate("editMenu"));
        item = menu.add(new JMenuItem(new ButtonsActions.AddNodeAction(Main.traducer.translate("+Node"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.RemoveNodeAction(Main.traducer.translate("-Node"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ClearAllAction(Main.traducer.translate("clear"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ConnectSelectedNodesAction(Main.traducer.translate("conNodes"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ResetAction(Main.traducer.translate("reset"))));
        return menu;
    }

    /**
     * Initializes the options menu so it can be added in the toolbar
     * @return The options menu
     */
    private JMenu optionsMenu() {
        menu = new JMenu(Main.traducer.translate("optMenu"));
        JMenu arrowTypesMenu = new JMenu(Main.traducer.translate("arTyMen"));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(Main.traducer.translate("arTy1"), ArrowType.FILLED)));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(Main.traducer.translate("arTy2"), ArrowType.EMPTY)));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(Main.traducer.translate("arTy3"), ArrowType.STANDARD)));
        menu.add(new JMenuItem(new OptionsActions.ChangeDiameterAction(Main.traducer.translate("cgDiBt"))));
        menu.addSeparator();
        menu.add(arrowTypesMenu);
        menu.addSeparator();
        JMenu languageMenu = new JMenu(Main.traducer.translate("langMenu"));
        languageMenu.add(new JMenuItem(new OptionsActions.ChangeLanguageAction("italian", Main.traducer.translate("langMenuOpt1"))));
        languageMenu.add(new JMenuItem(new OptionsActions.ChangeLanguageAction("english", Main.traducer.translate("langMenuOpt2"))));
        menu.add(languageMenu);
        menu.addSeparator();
        menu.add(new JMenuItem(new OptionsActions.OpenHelpWindowAction(Main.traducer.translate("help"))));
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
            return Main.traducer.translate("gOrientation1");
        } else {
            return Main.traducer.translate("gOrientation2");
        }
    }

}
