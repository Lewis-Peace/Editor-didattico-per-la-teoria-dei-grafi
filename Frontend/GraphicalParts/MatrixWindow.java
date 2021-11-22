package Frontend.GraphicalParts;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Backend.AdjacencyMatrix;
import Backend.Graph;
import Backend.IncidenceMatrix;
import Backend.Traduction.Traduction;

public class MatrixWindow extends JFrame{
    
    /**
     *
     */
    private static final long serialVersionUID = 7118686907371982488L;

    private int row;
    private int col;
    private JLabel[][] label;
    private static final Font LABEL_FONT = new Font(Font.DIALOG, Font.PLAIN, 24);
    private int GAP = 1;
    private String[][] matrix;

    public MatrixWindow(AdjacencyMatrix m) {
        this.row = m.matrix.length;
        this.col = m.matrix[0].length;
        this.matrix = m.matrix;
        display(true);
    }

    public MatrixWindow(IncidenceMatrix m) {
        this.row = m.matrix.length;
        this.col = m.matrix[0].length;
        this.matrix = m.matrix;
        display(true);
    }

    public MatrixWindow(Graph graph, Traduction traducer) {
        this.col = graph.nodesList.size() + 1;
        this.row = 2;
        this.matrix = new String[this.row][this.col];
        matrix[0][0] = traducer.translate("nd");
        matrix[1][0] = traducer.translate("deg");
        for (int i = 0; i < this.row; i++) {
            for (int j = 1; j < this.col; j++) {
                if (i == 0) {
                    matrix[i][j] = graph.nodesList.get(j - 1).name;
                }
                if (i == 1) {
                    matrix[i][j] = graph.nodesList.get(j - 1).nodeDegree + "";
                }
            }
        }
        display(false);
    }

    private void display(Boolean ignoreIntestation) {
        JPanel canvas = new JPanel(new GridLayout(row, col, GAP, GAP));
        canvas.setBackground(Color.BLACK);
        displayMatrix(this.matrix, canvas, ignoreIntestation);
        this.pack();
        this.setVisible(true);
    }

    private void displayMatrix(String[][] matrix, JPanel jPanel, Boolean ignoreIntestation) {
        label = new JLabel[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String cellContent = matrix[i][j];
                if (i == 0 && j == 0 && ignoreIntestation) {
                    cellContent = "     ";
                }
                label[i][j] = new JLabel(cellContent, SwingConstants.CENTER);
                label[i][j].setFont(LABEL_FONT);
                label[i][j].setOpaque(true);
                if ((i == 0 || j == 0) && ignoreIntestation) {
                    label[i][j].setBackground(Color.GREEN);
                } else {
                    label[i][j].setBackground(Color.WHITE);
                }
                jPanel.add(label[i][j]);
            }
        }
        JScrollPane scroll = new JScrollPane(jPanel);
        this.add(scroll);
    }
}
