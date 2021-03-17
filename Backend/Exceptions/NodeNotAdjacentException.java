package Backend.Exceptions;

import Backend.Node;

public class NodeNotAdjacentException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 7191941452845350328L;
    private Node startingNode;
    private Node missingNode;
    
    public NodeNotAdjacentException(Node startingNode, Node missingNode) {
        this.startingNode = startingNode;
        this.missingNode = missingNode;
    }

    public String toString() {
        return "There is no connection from "+ startingNode.toString() + " and "+ missingNode.toString();
    }
}
