package Frontend.GraphicalParts;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputTextField extends JOptionPane {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField txtF;
    private JPanel line1;

    public InputTextField(String labelText) {
        this.line1 = new JPanel();
        this.txtF = new JTextField(20);
        this.line1.add(new JLabel(labelText));
        this.line1.add(this.txtF);
        this.add(this.line1, BorderLayout.PAGE_START);
    }

    public String getInput(String jFrameText) {
        int result = JOptionPane.showConfirmDialog(null, this.line1, jFrameText, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return this.txtF.getText();
        } else {
            return null;
        }
    }

}
