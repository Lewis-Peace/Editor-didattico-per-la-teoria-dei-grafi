package Frontend.GraphicalParts;

import javax.swing.JOptionPane;

public class PopupMessage extends JOptionPane {
    
    /**
     *
     */
    private static final long serialVersionUID = 5552201480324845858L;

    public PopupMessage(String message, String messageTitle) {
        JOptionPane.showMessageDialog(null, message, messageTitle, JOptionPane.INFORMATION_MESSAGE);
    }
}
