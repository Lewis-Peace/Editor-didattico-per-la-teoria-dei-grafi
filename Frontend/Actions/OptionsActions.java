package Frontend.Actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.WindowConstants;

import Main.Main;
import Frontend.GraphicalParts.HelpWindow;
import Frontend.GraphicalParts.InputTextField;
import Frontend.GraphicalParts.GraphicalEdge.ArrowType;

public class OptionsActions {

    public static class ChangeDiameterAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = -482735659668325299L;

        public ChangeDiameterAction(String buttonName) {
            super(buttonName);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int diameter = Integer.parseInt(new InputTextField(Main.traducer.translate("diamLab")).getInput("Options"));
            Main.graph.changeNodesDiameter(diameter);
            Main.canvas.repaint();
        }

    }

    public static class ChangeArrowTypeAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 9040806341699508339L;
        private ArrowType arrowType;

        public ChangeArrowTypeAction(String buttonName, ArrowType type) {
            super(buttonName);
            this.arrowType = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.graph.changeArrowType(this.arrowType);
            Main.canvas.repaint();
        }

    }

    public static class ChangeLanguageAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2517029647508976026L;

        private String language;

        public ChangeLanguageAction(String language, String name) {
            super(name);
            this.language = language;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            Main.frame.dispatchEvent(new WindowEvent(Main.frame, WindowEvent.WINDOW_CLOSING));
            Main.openWindow(Main.graph, language);
        }

    }

    public static class OpenHelpWindowAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        public OpenHelpWindowAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.traducer.language.equals("italian")) {
                this.openItalianHelpPage();
            } else {
                new HelpWindow(Main.traducer);
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
