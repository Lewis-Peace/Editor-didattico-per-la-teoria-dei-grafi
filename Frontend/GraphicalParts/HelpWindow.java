package Frontend.GraphicalParts;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import Backend.Traduction.Traduction;

public class HelpWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel = new JPanel(new GridLayout(0,2));

    public HelpWindow() {
        super("Help");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JMenuBar menu = new JMenuBar();
        menu.setLayout(new GridLayout(0,1));
        populateMenu(menu);
        this.add(menu, BorderLayout.WEST);
        panel.add(new JLabel(Traduction.translate("chsVoc")), BorderLayout.CENTER);
        this.add(panel);
        this.setSize((int)screenSize.getWidth(), 400);
        this.setVisible(true);
    }

    private void populateMenu(JMenuBar menu) {
        menu.add(new JButton(new StandardFunctionsAction(panel, Traduction.translate("menEdit"))));
        menu.add(new JButton(new AdvancedFunctionsAction(panel, Traduction.translate("menAdv"))));
        menu.add(new JButton(new VisualizeFunctionsAction(panel, Traduction.translate("menVis"))));
        menu.add(new JButton(new MouseFunctionsAction(panel, Traduction.translate("menPop"))));
    }

    private static class StandardFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;

        public StandardFunctionsAction(JPanel panel, String name) {
            super(name);
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(Traduction.translate("+Node") + ": "));
            panel.add(new JLabel(Traduction.translate("+NodeExpl")));
            panel.add(new JLabel(Traduction.translate("-Node") + ": "));
            panel.add(new JLabel(Traduction.translate("-NodeExpl")));
            panel.add(new JLabel(Traduction.translate("clear") + ": "));
            panel.add(new JLabel(Traduction.translate("clearExpl")));
            panel.add(new JLabel(Traduction.translate("conNodes") + ": "));
            panel.add(new JLabel(Traduction.translate("conNodesExpl")));
            panel.add(new JLabel(Traduction.translate("reset") + ": "));
            panel.add(new JLabel(Traduction.translate("resetExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }

    private static class MouseFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;

        public MouseFunctionsAction(JPanel panel, String name) {
            super(name);
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(Traduction.translate("+Node") + ": "));
            panel.add(new JLabel(Traduction.translate("+NodeExplMouse")));
            panel.add(new JLabel(Traduction.translate("-Node") + ": "));
            panel.add(new JLabel(Traduction.translate("-NodeExplMouse")));
            panel.add(new JLabel(Traduction.translate("conThNo") + ": "));
            panel.add(new JLabel(Traduction.translate("conThNoExplMouse")));
            panel.add(new JLabel(Traduction.translate("delCon") + ": "));
            panel.add(new JLabel(Traduction.translate("delConExplMouse")));
            panel.add(new JLabel(Traduction.translate("ham") + ": "));
            panel.add(new JLabel(Traduction.translate("hamExplMouse")));
            panel.add(new JLabel(Traduction.translate("eul") + ": "));
            panel.add(new JLabel(Traduction.translate("eulExplMouse")));
            panel.add(new JLabel(Traduction.translate("cngCol") + ": "));
            panel.add(new JLabel(Traduction.translate("cngColExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }
    
    private static class AdvancedFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;

        public AdvancedFunctionsAction(JPanel panel, String name) {
            super(name);
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(Traduction.translate("divDeg") + ": "));
            panel.add(new JLabel(Traduction.translate("divDegExpl")));
            panel.add(new JLabel(Traduction.translate("+NdName") + ": "));
            panel.add(new JLabel(Traduction.translate("+NdNameExpl")));
            panel.add(new JLabel(Traduction.translate("ham") + ": "));
            panel.add(new JLabel(Traduction.translate("hamExpl")));
            panel.add(new JLabel(Traduction.translate("eul") + ": "));
            panel.add(new JLabel(Traduction.translate("eulExpl")));
            panel.add(new JLabel(Traduction.translate("stPth") + ": "));
            panel.add(new JLabel(Traduction.translate("stPthExpl")));
            panel.add(new JLabel(Traduction.translate("bip") + ": "));
            panel.add(new JLabel(Traduction.translate("bipExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }
    
    private static class VisualizeFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;

        public VisualizeFunctionsAction(JPanel panel, String name) {
            super(name);
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(Traduction.translate("adjMat") + ": "));
            panel.add(new JLabel(Traduction.translate("adjMatExpl")));
            panel.add(new JLabel(Traduction.translate("incMat") + ": "));
            panel.add(new JLabel(Traduction.translate("incMatExpl")));
            panel.add(new JLabel(Traduction.translate("ndDeg") + ": "));
            panel.add(new JLabel(Traduction.translate("ndDegExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }

}
