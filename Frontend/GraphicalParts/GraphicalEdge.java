package Frontend.GraphicalParts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import Backend.Node;

public abstract class GraphicalEdge {

    public Boolean oriented;
    public Node startingNode;
    public Node endingNode;
    private Color color = Color.BLACK;
    public int radius = new Node(0, 0, 0).diameter / 2;
    public ArrowType arrowType = ArrowType.STANDARD;
    public enum ArrowType{FILLED, EMPTY, STANDARD};

    public void changeRadius(int radius) {
        this.radius = radius;
    }

    public void drawEdge(Graphics g) {
        g.setColor(this.color);
        g.drawLine(startingNode.xPos, startingNode.yPos, endingNode.xPos, endingNode.yPos);
        if (oriented) {
            drawArrows(g, endingNode.xPos, endingNode.yPos);
        }
    }

    public void changeArrowType(ArrowType type) {
        arrowType = type;
    }

    private void drawArrows(Graphics g, int x, int y) {
        int arrowLength = 10;
        int[] A = {startingNode.xPos, startingNode.yPos};
        int[] B = {endingNode.xPos, endingNode.yPos};
        int c = B[0] - A[0];
        int b = B[1] - A[1];
        double a = Math.sqrt(Math.pow(c , 2) + Math.pow(b , 2));
        double alpha = Math.asin(b / a);
        double beta = Math.asin(c / a);
        int[] C = {
           A[0] + (int)((a - radius) * Math.sin(beta)), A[1] +(int)((a - radius) * Math.sin(alpha)) 
        };
        double arrowAngle1 = 135 * Math.PI / 180 + alpha;
        double arrowAngle2 = 45 * Math.PI / 180 + alpha;
        if (A[0] < B[0]) {
            arrowAngle1 = arrowAngle1 * -1;
            arrowAngle2 = arrowAngle2 * -1;
        }
        int[] AR1 = {
            (int) (arrowLength * Math.sin(arrowAngle1)), (int) (arrowLength * Math.cos(arrowAngle1))
        };
        int[] AR2 = {
            (int) (arrowLength * Math.sin(arrowAngle2)), (int) (arrowLength * Math.cos(arrowAngle2))
        };
        if (arrowType == ArrowType.STANDARD) {
            g.drawLine(C[0], C[1], C[0] + AR1[0], C[1] + AR1[1]);
            g.drawLine(C[0], C[1], C[0] + AR2[0], C[1] + AR2[1]);
        } else {
            int[] arrowXPoints = {C[0], C[0] + AR1[0], C[0] + AR2[0]};
            int[] arrowYPoints = {C[1], C[1] + AR1[1], C[1] + AR2[1]};
            Polygon poly = new Polygon(arrowXPoints, arrowYPoints, 3);
            if (arrowType == ArrowType.FILLED) {
                g.fillPolygon(poly);
            } else {
                g.drawPolygon(poly);
            }
        }
    }

    public void changeColor(Color color) {
        this.color = color;
    }

}
