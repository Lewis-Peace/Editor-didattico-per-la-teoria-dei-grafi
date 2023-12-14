package Frontend;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Backend.Traduction.Traduction;
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
        menu = new JMenu(Traduction.translate("advMenu"));
        //menu.add(new JMenuItem(new AdvancedActions.ColorNodesByDegreeAction(graph, canvas, traducer.translate("divDeg"))));
        //menu.addSeparator();
        item = new JMenuItem(new AdvancedActions.AddNodeWithSpecificNameAction(Traduction.translate("+NdName")));
        setupItem(menu, item, "");
        item = new JMenuItem(new AdvancedActions.FindHamiltonianPathAction(Traduction.translate("ham")));
        setupItem(menu, item, Traduction.translate("toolTipHam"));
        item = new JMenuItem(new AdvancedActions.FindEulerianPathAction(Traduction.translate("eul")));
        setupItem(menu, item, Traduction.translate("toolTipEul"));
        item = menu.add(new JMenuItem(new AdvancedActions.FindShortestPathFrom2Nodes(Traduction.translate("stPth"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new AdvancedActions.FindIfBipartedGraph(Traduction.translate("bip"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new AdvancedActions.FindArborescenceAction(Traduction.translate("arbo"))));
        return menu;
    }

    private JMenu visualizeMenu() {
        menu = new JMenu(Traduction.translate("viewMenu"));
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeAdjacenyMatrixAction(Traduction.translate("adjMat"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeIncidenceMatrixAction(Traduction.translate("incMat"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new VisualizeMenuActions.VisualizeNodesDegreeAction(Traduction.translate("ndDeg"))));
        return menu;
    }

    /**
     * Initializes the file menu options so it can be added in the toolbar
     * @return The file menu options
     */
    private JMenu fileMenu() {
        JMenu specificLoadMenu = famousGraphs();
        menu = new JMenu(Traduction.translate("fileMenu"));
        item = menu.add(new JMenuItem(new ButtonsActions.SaveGraphAction(Traduction.translate("save"))));
        item.setToolTipText(Traduction.translate("toolTipSave"));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.LoadGraphAction(Traduction.translate("load"))));
        item.setToolTipText(Traduction.translate("toolTipLoad"));
        menu.addSeparator();
        menu.add(specificLoadMenu);
        return menu;
    }

    private JMenu famousGraphs() {
        JMenu menu = new JMenu(Traduction.translate("famousGraphs"));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/clebschGraph.xml", Traduction.translate("clebsch"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/completeGraph.xml", Traduction.translate("completeGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/cubeGraph.xml", Traduction.translate("cubeGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/dyckGraph.xml", Traduction.translate("dyckGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/grotzschGraph.xml", Traduction.translate("grotzsGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/wagnerGraph.xml", Traduction.translate("wagnerGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/dudeneyGraph.xml", Traduction.translate("dudeneyGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/franklinGraph.xml", Traduction.translate("franklinGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/markstromGraph.xml", Traduction.translate("markstromGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/herschelGraph.xml", Traduction.translate("herschelGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/petersenGraph.xml", Traduction.translate("petersenGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/youngFibonacciGraph.xml", Traduction.translate("fiboGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/strangeBipartedGraph.xml", Traduction.translate("strBiGraph"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/bipartedGraph.xml", Traduction.translate("loadBi"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/eulerianGraph.xml", Traduction.translate("loadEul"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/hamiltonianGraph.xml", Traduction.translate("loadHam"))));
        menu.add(new JMenuItem(new ButtonsActions.LoadSpecificGraphAction("./Graphs/exampleGraph.xml", Traduction.translate("exampleGraph"))));
        return menu;
    }

    /**
     * Initializes the edit menu options so it can be added in the toolbar
     * @return The edit menu options
     */
    private JMenu editMenu() {
        menu = new JMenu(Traduction.translate("editMenu"));
        item = menu.add(new JMenuItem(new ButtonsActions.AddNodeAction(Traduction.translate("+Node"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.RemoveNodeAction(Traduction.translate("-Node"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ClearAllAction(Traduction.translate("clear"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ConnectSelectedNodesAction(Traduction.translate("conNodes"))));
        menu.addSeparator();
        item = menu.add(new JMenuItem(new ButtonsActions.ResetAction(Traduction.translate("reset"))));
        return menu;
    }

    /**
     * Initializes the options menu so it can be added in the toolbar
     * @return The options menu
     */
    private JMenu optionsMenu() {
        menu = new JMenu(Traduction.translate("optMenu"));
        JMenu arrowTypesMenu = new JMenu(Traduction.translate("arTyMen"));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(Traduction.translate("arTy1"), ArrowType.FILLED)));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(Traduction.translate("arTy2"), ArrowType.EMPTY)));
        arrowTypesMenu.add(new JMenuItem(new OptionsActions.ChangeArrowTypeAction(Traduction.translate("arTy3"), ArrowType.STANDARD)));
        menu.add(new JMenuItem(new OptionsActions.ChangeDiameterAction(Traduction.translate("cgDiBt"))));
        menu.addSeparator();
        menu.add(arrowTypesMenu);
        menu.addSeparator();
        JMenu languageMenu = new JMenu(Traduction.translate("langMenu"));
        languageMenu.add(new JMenuItem(new OptionsActions.ChangeLanguageAction("italian", Traduction.translate("langMenuOpt1"))));
        languageMenu.add(new JMenuItem(new OptionsActions.ChangeLanguageAction("english", Traduction.translate("langMenuOpt2"))));
        menu.add(languageMenu);
        menu.addSeparator();
        menu.add(new JMenuItem(new OptionsActions.OpenHelpWindowAction(Traduction.translate("help"))));
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
            return Traduction.translate("gOrientation1");
        } else {
            return Traduction.translate("gOrientation2");
        }
    }

}
