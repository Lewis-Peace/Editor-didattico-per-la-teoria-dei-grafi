package Backend;

import Frontend.GraphicalParts.GraphicalEdge;

public class Edge extends GraphicalEdge{

    public Edge(Node node0, Node node1, int diameter, Boolean oriented) {
        startingNode = node0;
        endingNode = node1;
        this.oriented = oriented;
    }

    /**
     * Checks if this edge has the goven node
     * @param node Node to search
     * @return Returns true if the node is into the edge object else returns false
     */
    public Boolean doIContainThisNode(Node node) {
        if (node.equals(startingNode) || node.equals(endingNode)) {
            return true;
        }
        return false;
    }

    protected void revertConnection() {
        if (oriented) {
            Node node0 = this.startingNode;
            Node node1 = this.endingNode;
            this.startingNode = node1;
            this.endingNode = node0;
        }
    }

    /* Initializes a new object with reverted nodes*/
    protected Edge initializeNewRevertedConnection() {
        if (oriented) {
            Node node0 = this.startingNode;
            Node node1 = this.endingNode;
            Edge revertedConnection = new Edge(node1, node0, this.radius, this.oriented);
            return revertedConnection;
        }
        return this;
    }

    public String toString() {
        return "(" + startingNode.name + " " + endingNode.name + ")";
    }


}
