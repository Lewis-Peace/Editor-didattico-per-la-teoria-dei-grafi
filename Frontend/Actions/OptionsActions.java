package Frontend.Actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Backend.Graph;
import Backend.Traduction.Traduction;
import Frontend.Canvas;
import Frontend.MainGraphical;
import Frontend.GraphicalParts.HelpWindow;
import Frontend.GraphicalParts.InputTextField;
import Frontend.GraphicalParts.GraphicalEdge.ArrowType;

public class OptionsActions {

    public static class ChangeDiameterAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = -482735659668325299L;
        private Graph graph;
        private Canvas canvas;
        private Traduction traducer;

        public ChangeDiameterAction(String buttonName, Graph graph, Canvas canvas, Traduction traducer) {
            super(buttonName);
            this.graph = graph;
            this.canvas = canvas;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int diameter = Integer.parseInt(new InputTextField(traducer.translate("diamLab")).getInput("Options"));
            graph.changeNodesDiameter(diameter);
            canvas.repaint();
        }

    }

    public static class ChangeArrowTypeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 9040806341699508339L;
        private Graph graph;
        private Canvas canvas;
        private ArrowType arrowType;

        public ChangeArrowTypeAction(String buttonName, Graph graph, Canvas canvas, ArrowType type) {
            super(buttonName);
            this.graph = graph;
            this.canvas = canvas;
            this.arrowType = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            graph.changeArrowType(this.arrowType);
            canvas.repaint();
        }

    }

    public static class ChangeLanguageAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2517029647508976026L;

        private JFrame window;
        private String language;
        private Graph graph;

        public ChangeLanguageAction(JFrame window, Graph graph, String language, String name) {
            super(name);
            this.window = window;
            this.language = language;
            this.graph = graph;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            MainGraphical.openWindow(graph, language);
        }

    }

    public static class OpenHelpWindowAction extends AbstractAction {

        private static final long serialVersionUID = 1L;
        private Traduction traducer;

        public OpenHelpWindowAction(Traduction traducer, String name) {
            super(name);
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (traducer.language.equals("italian")) {
                this.openItalianHelpPage();
            } else {
                new HelpWindow(traducer);
            }
        }

        private void openItalianHelpPage() {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        File helpFile = new File("HelpPage/index.html");
                        Desktop.getDesktop().browse(helpFile.toURI());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                
            }).start();
        }

    }

}
