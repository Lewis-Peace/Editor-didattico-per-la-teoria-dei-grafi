package Backend.Exceptions;

import Backend.Node;

public class NodesAlreadyConnectedException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 7328453010099126262L;
    private String n1;
    private String n2;


    public NodesAlreadyConnectedException (Node n1, Node n2) {
        this.n1 = n1.name;
        this.n2 = n2.name;
    }

    public String toString() {
        return "Nodes " + n1 + " and " + n2 + " are already connected";
    }
}