package Frontend;

import javax.swing.*;

import Backend.*;
import Backend.Traduction.Traduction;

import java.awt.*;

public class MainGraphical extends JComponent {

  /**
   *
   */
  private static final long serialVersionUID = 958793555816817482L;

  public static void openWindow(Graph graph, String language) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int windowWidth = (int) screenSize.getWidth();
    int canvasHeight = (int) (screenSize.getHeight() * 3 / 4);
    Traduction traducer = new Traduction(language);
    JFrame window = new JFrame();
    Canvas canvas = new Canvas(window, windowWidth, canvasHeight, Color.WHITE, graph, traducer);
    ToolBar toolBar = new ToolBar(graph, canvas, window, traducer);
    window.addKeyListener(new KeyboardHandler(graph, canvas));
    window.add(toolBar, BorderLayout.PAGE_START);
    window.add(canvas, BorderLayout.CENTER);
    window.setSize(windowWidth, canvasHeight);
    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setVisible(true);

  }

}
