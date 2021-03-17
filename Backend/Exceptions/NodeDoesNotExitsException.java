package Backend.Exceptions;

import Backend.Node;

public class NodeDoesNotExitsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -5241502337464356384L;
    private Node node;    

    public NodeDoesNotExitsException(Node node) {
        this.node = node;
    }

    public String toString() {
        return "Can't delete " + node.toString() + " because this node doesn't exists";
    }
}