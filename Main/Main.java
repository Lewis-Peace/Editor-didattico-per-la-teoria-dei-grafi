package Main;

import java.awt.*;

import javax.swing.*;

import Backend.*;
import Backend.Traduction.Traduction;
import Frontend.KeyboardHandler;
import Frontend.ToolBar;
import Frontend.Canvas;

public class Main {

    public static JFrame frame;
    public static Canvas canvas;
    public static Graph graph;
    public static Traduction traducer;

    public static void main(String[] args) {
        Main.graph = new Graph(false);
        
        openWindow(graph, "italian");
    }

    public static void openWindow(Graph graph, String language) {
        Main.graph = graph;
        Main.traducer = new Traduction(language);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int windowWidth = (int) screenSize.getWidth();
        int canvasHeight = (int) (screenSize.getHeight() * 3 / 4);
        Main.frame = new JFrame("Graph editor");
        Main.canvas = new Canvas(windowWidth, canvasHeight, Color.WHITE);
        ToolBar toolBar = new ToolBar();
        Main.frame.addKeyListener(new KeyboardHandler());
        Main.frame.add(toolBar, BorderLayout.PAGE_START);
        Main.frame.add(Main.canvas, BorderLayout.CENTER);
        Main.frame.setSize(windowWidth, canvasHeight);
        Main.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Main.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Main.frame.setVisible(true);

    }
}