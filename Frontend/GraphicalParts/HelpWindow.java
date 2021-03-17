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
    private Traduction traducer;

    public HelpWindow(Traduction traducer) {
        super("Help");
        this.traducer = traducer;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JMenuBar menu = new JMenuBar();
        menu.setLayout(new GridLayout(0,1));
        populateMenu(menu);
        this.add(menu, BorderLayout.WEST);
        panel.add(new JLabel(traducer.translate("chsVoc")), BorderLayout.CENTER);
        this.add(panel);
        this.setSize((int)screenSize.getWidth(), 400);
        this.setVisible(true);
    }

    private void populateMenu(JMenuBar menu) {
        menu.add(new JButton(new StandardFunctionsAction(this.traducer, panel, traducer.translate("menEdit"))));
        menu.add(new JButton(new AdvancedFunctionsAction(this.traducer, panel, traducer.translate("menAdv"))));
        menu.add(new JButton(new VisualizeFunctionsAction(this.traducer, panel, traducer.translate("menVis"))));
        menu.add(new JButton(new MouseFunctionsAction(this.traducer, panel, traducer.translate("menPop"))));
    }

    private static class StandardFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;
        private Traduction traducer;

        public StandardFunctionsAction(Traduction traducer, JPanel panel, String name) {
            super(name);
            this.panel = panel;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(traducer.translate("+Node") + ": "));
            panel.add(new JLabel(traducer.translate("+NodeExpl")));
            panel.add(new JLabel(traducer.translate("-Node") + ": "));
            panel.add(new JLabel(traducer.translate("-NodeExpl")));
            panel.add(new JLabel(traducer.translate("clear") + ": "));
            panel.add(new JLabel(traducer.translate("clearExpl")));
            panel.add(new JLabel(traducer.translate("conNodes") + ": "));
            panel.add(new JLabel(traducer.translate("conNodesExpl")));
            panel.add(new JLabel(traducer.translate("reset") + ": "));
            panel.add(new JLabel(traducer.translate("resetExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }

    private static class MouseFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;
        private Traduction traducer;

        public MouseFunctionsAction(Traduction traducer, JPanel panel, String name) {
            super(name);
            this.panel = panel;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(traducer.translate("+Node") + ": "));
            panel.add(new JLabel(traducer.translate("+NodeExplMouse")));
            panel.add(new JLabel(traducer.translate("-Node") + ": "));
            panel.add(new JLabel(traducer.translate("-NodeExplMouse")));
            panel.add(new JLabel(traducer.translate("conThNo") + ": "));
            panel.add(new JLabel(traducer.translate("conThNoExplMouse")));
            panel.add(new JLabel(traducer.translate("delCon") + ": "));
            panel.add(new JLabel(traducer.translate("delConExplMouse")));
            panel.add(new JLabel(traducer.translate("ham") + ": "));
            panel.add(new JLabel(traducer.translate("hamExplMouse")));
            panel.add(new JLabel(traducer.translate("eul") + ": "));
            panel.add(new JLabel(traducer.translate("eulExplMouse")));
            panel.add(new JLabel(traducer.translate("cngCol") + ": "));
            panel.add(new JLabel(traducer.translate("cngColExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }
    
    private static class AdvancedFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;
        private Traduction traducer;

        public AdvancedFunctionsAction(Traduction traducer, JPanel panel, String name) {
            super(name);
            this.panel = panel;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(traducer.translate("divDeg") + ": "));
            panel.add(new JLabel(traducer.translate("divDegExpl")));
            panel.add(new JLabel(traducer.translate("+NdName") + ": "));
            panel.add(new JLabel(traducer.translate("+NdNameExpl")));
            panel.add(new JLabel(traducer.translate("ham") + ": "));
            panel.add(new JLabel(traducer.translate("hamExpl")));
            panel.add(new JLabel(traducer.translate("eul") + ": "));
            panel.add(new JLabel(traducer.translate("eulExpl")));
            panel.add(new JLabel(traducer.translate("stPth") + ": "));
            panel.add(new JLabel(traducer.translate("stPthExpl")));
            panel.add(new JLabel(traducer.translate("bip") + ": "));
            panel.add(new JLabel(traducer.translate("bipExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }
    
    private static class VisualizeFunctionsAction extends AbstractAction {

        private static final long serialVersionUID = -7005096848822839803L;
        private JPanel panel;
        private Traduction traducer;

        public VisualizeFunctionsAction(Traduction traducer, JPanel panel, String name) {
            super(name);
            this.panel = panel;
            this.traducer = traducer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel(traducer.translate("adjMat") + ": "));
            panel.add(new JLabel(traducer.translate("adjMatExpl")));
            panel.add(new JLabel(traducer.translate("incMat") + ": "));
            panel.add(new JLabel(traducer.translate("incMatExpl")));
            panel.add(new JLabel(traducer.translate("ndDeg") + ": "));
            panel.add(new JLabel(traducer.translate("ndDegExpl")));
            panel.revalidate();
            panel.repaint();
        }
    }

}
