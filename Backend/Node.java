package Backend;

import java.util.ArrayList;

import Backend.Exceptions.NodeNotAdjacentException;
import Frontend.GraphicalParts.GraphicalNode;

public class Node extends GraphicalNode{

    /**
     * The color used for algorithms
     */
    public Colore colore;
    public int nodeDegree;
    public int distance;
    public Node parent;
    public ArrayList<Node> adjacentNodes;
    public ArrayList<Edge> nodeConnections;

    /**
     * Initialize the node object
     * @param x The x position in the canvas
     * @param y The y position in the canvas
     */
    public Node (int x, int y, String name) { // Initializer method
        this.name = name;
        adjacentNodes = new ArrayList<Node>();
        nodeConnections = new ArrayList<Edge>();
        this.xPos = x;
        this.yPos = y;
        selected = false;
    }

    /**
     * Initialize the node object
     * @param x The x position in the canvas
     * @param y The y position in the canvas
     */
    public Node (int x, int y, int name) { // Initializer method
        this.name = name + "";
        adjacentNodes = new ArrayList<Node>();
        nodeConnections = new ArrayList<Edge>();
        this.xPos = x;
        this.yPos = y;
        selected = false;
    }

    /**
     * Connects this node to the inputed one
     * @param nodeToConnect The node to connect with
     * @param oriented The type of graph
     * @return Returns the new Edges generated with this operation
     */
    protected ArrayList<Edge> connectNode(Node nodeToConnect, Boolean oriented) {
        ArrayList<Edge> newEdges = new ArrayList<Edge>();
        Edge from1To2 = new Edge(this, nodeToConnect, this.diameter / 2, oriented);
        nodeDegree++;
        adjacentNodes.add(nodeToConnect);
        nodeConnections.add(from1To2);
        newEdges.add(from1To2);
        if (!oriented) {
            Edge from2To1 = new Edge(nodeToConnect, this, this.diameter / 2, oriented);
            nodeToConnect.nodeDegree++;
            nodeToConnect.adjacentNodes.add(this);
            nodeToConnect.nodeConnections.add(from2To1);
            newEdges.add(from2To1);
        }
        return newEdges;
    }

    /**
     * Disconnects the node from the inputed node
     * @param nodeToDisconect Node to remove from connection
     * @param oriented Tipe of graph
     * @return Edges touched with this operation
     * @throws NodeNotAdjacentException If connection does not exist
     */
    protected ArrayList<Edge> disconectNode(Node nodeToDisconect, Boolean oriented)
            throws NodeNotAdjacentException {
        Boolean result = adjacentNodes.remove(nodeToDisconect);
        if (result) {
            ArrayList<Edge> edgesRemoved = new ArrayList<Edge>();
            Edge edgeToRemove = getSpecificEdge(this, nodeToDisconect);
            nodeDegree--;
            nodeConnections.remove(edgeToRemove);
            edgesRemoved.add(edgeToRemove);
            if (!oriented) {
                Edge invertedEdge = nodeToDisconect.getSpecificEdge(nodeToDisconect, this);
                nodeToDisconect.nodeDegree--;
                nodeToDisconect.adjacentNodes.remove(this);
                nodeToDisconect.nodeConnections.remove(invertedEdge);
                edgesRemoved.add(invertedEdge);
            }
            return edgesRemoved;
        } else {
            throw new NodeNotAdjacentException(this, nodeToDisconect);
        }
    }

    private Edge getSpecificEdge(Node node0, Node node1) {
        for (Edge edge : node0.nodeConnections) {
            if (edge.startingNode.equals(node0) && edge.endingNode.equals(node1)) {
                return edge;
            }
        }
        return null;
    }

    public int getNodeDegree() {
        return nodeDegree;
    }

    public String toString() {
        String output = "Node " + name;/*
        output += "\n" + "adjacent nodes number: " + adjacentNodes.size();
        output += "\n" +"node degree: " + nodeDegree;
        output += "\n" +"node connections: " + nodeConnections;*/
        output += " coords: " + xPos + " " + yPos;
        return output;
    }


}
