package Backend;

import java.awt.Color;

import java.util.ArrayList;

import Backend.Exceptions.*;
import Frontend.GraphicalParts.GraphicalEdge.ArrowType;

public class Graph {
    public Boolean oriented;
    public ArrayList<Node> nodesList;
    public ArrayList<Edge> edgesList;
    private Node cyclicNode;
    public Boolean stepper = false;

    /**
     * Constructor of this class, it represents the container of all the nodes and
     * edges, also contains all the functions used to operate with them.
     * 
     * @param oriented If true all the edges will have an orientation, else not
     */
    public Graph(Boolean oriented) {
        this.oriented = oriented;
        nodesList = new ArrayList<Node>();
        edgesList = new ArrayList<Edge>();
    }

    /**
     * Adds a node in the graph
     * 
     * @param node The node to add
     */
    public void addNode(Node node) {
        nodesList.add(node);
    }

    /**
     * Chechs if a connection between 2 given nodes already exists
     * 
     * @param node0 The starting node
     * @param node1 The ending node
     * @return Returns true if the conenctions exists, false if does not exists
     */
    private Boolean connectionAlreadyExist(Node node0, Node node1) {
        Edge check = getNodesConnection(node0, node1);
        if (check == null) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Connects 2 nodes in the graph keeping in mind if the graph is oriented or
     * not, if it is it adds only the connection frome node0 to node 1 else it
     * connects also from node1 to node0
     */
    public void connectNodes(Node node0, Node node1) throws NodesAlreadyConnectedException {
        if (connectionAlreadyExist(node0, node1)) {
            throw new NodesAlreadyConnectedException(node0, node1);
        }
        ArrayList<Edge> newConnections = node0.connectNode(node1, oriented);
        for (int i = 0; i < newConnections.size(); i++) {
            if (newConnections.get(i) != null) {
                edgesList.add(newConnections.get(i));
            }
        }
    }

    /**
     * Deletes the connection of 2 given nodes
     * 
     * @param node0 Node where the connection starts
     * @param node1 Node where the connection ends
     * @throws NodeNotAdjacentException Thrown if the nodes doesn't have a
     *                                  connection when the function is called
     */
    public void deleteConnection(Node node0, Node node1) throws NodeNotAdjacentException {
        ArrayList<Edge> connectionsToRemove = node0.disconectNode(node1, oriented);
        for (int i = 0; i < connectionsToRemove.size(); i++) {
            if (connectionsToRemove.get(i) != null) {
                edgesList.remove(connectionsToRemove.get(i));
            }
        }
    }

    /**
     * Deletes every connection related to a given node
     * 
     * @param node The node there te connections come or starts
     * @throws NodeNotAdjacentException If the connection does not exists
     */
    public void deleteEveryConnectionOfOneNode(Node node) throws NodeNotAdjacentException {
        if (node != null) {
            int connectionsNumber = node.nodeConnections.size();
            for (int i = 0; i < connectionsNumber; i++) {
                Edge edge = node.nodeConnections.get(0);
                deleteConnection(edge.startingNode, edge.endingNode);
            }
        }
    }

    /**
     * Deletes a node from the graph with his conenctions
     * 
     * @param node The node to delete
     * @throws NodeDoesNotExitsException Thrown if the node does not exists
     */
    public void deleteNode(Node node) throws NodeDoesNotExitsException {
        try {
            if (nodesList.remove(node)) {
                deleteEveryConnectionOfOneNode(node);
            } else {
                throw new NodeDoesNotExitsException(node);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Used to scan the connections list searching for the connection object with
     * node1 as starting node and node2 as ending node
     * 
     * @param node1 The starting node of the connection
     * @param node2 The ending node of the connection
     * @return The object Edge establishing connection from node1 to node2, if this
     *         doesn't exists returns null
     */
    public Edge getNodesConnection(Node node1, Node node2) {
        for (Edge connectionOfNodes : edgesList) {
            Node candidateNode1 = connectionOfNodes.startingNode;
            Node candidateNode2 = connectionOfNodes.endingNode;
            if (candidateNode1.equals(node1) && candidateNode2.equals(node2)) {
                return connectionOfNodes;
            }
        }
        return null;
    }

    /**
     * Finds the node searching by specific name
     * 
     * @param name The name of the node to search
     * @return The node with the name or null if that doesn't exists
     */
    public Node getNodeByName(String name) {
        for (Node node : nodesList) {
            if (node.name.equals(name)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns a list of connections associated with the input node
     * 
     * @param node The node where the connections needs to start
     * @return The list of connections from that node
     */
    public ArrayList<Edge> getAllNodeConnections(Node node) {
        return node.nodeConnections;
    }

    /**
     * Returns a list of the nodes reachable from the input node
     * 
     * @param node The node where we start
     * @return Returns an arrayList of nodes reachable by one step from startin node
     */
    public ArrayList<Node> getListOfNodesConnectedFromThisNode(Node node) {
        return node.adjacentNodes;
    }

    /**
     * Use this function to initialaze the adjacency matrix of the graph it takes
     * the working graph and returns a new object
     * 
     * @return Returns the adjacency matrix object already computed
     */
    public AdjacencyMatrix getAdjacencyMatrix() {
        AdjacencyMatrix matrix = new AdjacencyMatrix(this);
        return matrix;
    }

    /**
     * Use this function to initialaze the incidence matrix of the graph it takes
     * the working graph and returns a new object
     * 
     * @return Returns the incidence matrix object already computed
     */
    public IncidenceMatrix getIncidenceMatrix() {
        IncidenceMatrix matrix = new IncidenceMatrix(this);
        return matrix;
    }

    /**
     * Gets the node in the position in the graph
     * 
     * @param position An array of 2 integers stating for x and y
     * @return The node existing in the x and y position
     */
    public Node getNodeByPosition(int[] position) {
        int radius;
        if (nodesList.isEmpty()) {
            return null;
        }
        for (Node node : nodesList) {
            radius = node.diameter / 2;
            if (position[0] > node.xPos - radius && position[0] < node.xPos + radius) {
                if (position[1] > node.yPos - radius && position[1] < node.yPos + radius) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Changes the arrow type for each edge in the graph
     * 
     * @param type The new arrow type
     */
    public void changeArrowType(ArrowType type) {
        for (Edge edge : edgesList) {
            edge.changeArrowType(type);
        }
    }

    /**
     * Changes the diameter of all the nodes in the graph
     * 
     * @param diameter The new diameter
     */
    public void changeNodesDiameter(int diameter) {
        for (Node node : nodesList) {
            node.changeDiameter(diameter);
        }
        for (Edge edge : edgesList) {
            edge.changeRadius(diameter / 2);
        }
    }

    /**
     * Changes the graph orientation adding the missing connections if needed
     */
    public void changeGraphOrientation() {
        resetGraphToStandardValues();
        this.oriented = !this.oriented;
        for (int i = 0; i < this.edgesList.size(); i++) {
            Edge edge = this.edgesList.get(i);
            edge.oriented = this.oriented;
            try {
                this.connectNodes(edge.endingNode, edge.startingNode);
            } catch (NodesAlreadyConnectedException e) {
            }
        }
    }

    /**
     * Changes the stepper status
     */
    public void changeStepperStatus() {
        stepper = !stepper;
    }
    
    /**
     * Resets graph to it's standard values
     */
    public void resetGraphToStandardValues() {
        for (Edge edge : edgesList) {
            edge.changeColor(Color.BLACK);
            edge.oriented = oriented;
        }
        for (Node node : nodesList) {
            node.changeNodeColor(Color.BLACK);
            node.diameter = 35;
        }
    }

    /**
     * Creates a copy of the starting graph with the same nodes and edges
     * @return The copy of the graph as a new object
     */
    public Graph cloneThisGraph() {
        Graph newGraph = new Graph(this.oriented);
        cloningThisGraphNodesList(newGraph);
        cloningThisGraphConnectionsList(newGraph);
        return newGraph;
    }

    private void cloningThisGraphConnectionsList(Graph newGraph) {
        for (int i = 0; i < this.edgesList.size(); i++) {
            Edge connection = this.edgesList.get(i);
            Node node0 = newGraph.getNodeByName(connection.startingNode.name);
            Node node1 = newGraph.getNodeByName(connection.endingNode.name);
            try {
                newGraph.connectNodes(node0, node1);
            } catch (NodesAlreadyConnectedException e) {
            }

        }
    }

    private void cloningThisGraphNodesList(Graph newGraph) {
        for (int i = 0; i < this.nodesList.size(); i++) {
            Node oldNode = this.nodesList.get(i);
            Node newNode = new Node(oldNode.xPos, oldNode.yPos, oldNode.name);
            newGraph.addNode(newNode);
        }
    }

    /**
     * This function search using BFS algorithm the shortest path from one node to another
     * @param start The starting node for the path
     * @param end The final node for the path
     * @return Returns an object of type Path with the requested path into him
     */
    public Path minPathFromFirstNodeToSecond(Node start, Node end) {
        Path gPath = new Path(start, end);
        gPath.getShortestPath(this);
        return gPath;
    }

    /**
     * Checks if the graph is connected or not (all the nodes are reachable with a connection)
     * @return Returns true if the graph is connected else returns false
     */
    public Boolean isGraphConnected() {
        for (Node node : this.nodesList) {
            node.colore = Colore.BIANCO;
        }
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(this.nodesList.get(0));
        Node workingNode;
        while (!queue.isEmpty()) {
            workingNode = queue.remove(0);
            for (Node node : this.getListOfNodesConnectedFromThisNode(workingNode)) {
                if (node.colore == Colore.BIANCO) {
                    node.colore = Colore.GRIGIO;
                    queue.add(node);
                }
                workingNode.colore = Colore.NERO;
            }
        }
        for (Node node : this.nodesList) {
            if (node.colore == Colore.BIANCO) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the graph is biparted and colors the colore var in each node with
     * specific node color if succesfull
     * @return Returns true if the graph is biparted else returns false
     */
    public void isBiparted() {
        for (Node node : this.nodesList) {
            node.colore = null;
        }
        this.isBiparted = true;
        for (Node node : this.nodesList) {
            if (node.colore == null && this.isBiparted) {
                node.colore = Colore.BIANCO;
                ArrayList<Node> toVisit = new ArrayList<Node>();
                toVisit.add(node);
                isBipartedRecursive(toVisit);
            }
        }
    }
    public Boolean isBiparted;
    public ArrayList<Node> sameColorNodesForBiparted = new ArrayList<Node>();
    private void isBipartedRecursive(ArrayList<Node> toVisit) {
        while (!toVisit.isEmpty()) {
            Node node = toVisit.remove(0);
            for (Node adjacentNode : node.adjacentNodes) {
                if (node.colore == adjacentNode.colore) {
                    if (sameColorNodesForBiparted.isEmpty()) {
                        sameColorNodesForBiparted.add(node);
                        sameColorNodesForBiparted.add(adjacentNode);
                    }
                    this.isBiparted = false;
                    return;
                } if (adjacentNode.colore == null) {
                    adjacentNode.colore = getInvertedColor(node.colore);
                    toVisit.add(adjacentNode);
                }
            }
        }
        this.isBiparted = true;
    }

    private Colore getInvertedColor(Colore colore) {
        if (colore == Colore.BIANCO) {
            return Colore.NERO;
        } else {
            return Colore.BIANCO;
        }
    }

    /**
     * Divides the nodes by nodes with an even degree and nodes with ano odd degree
     * after that they will be displayed with different colors
     */
    public void divideByOddOrEvenDegree() {
        for (Node node : this.nodesList) {
            int deg = node.getNodeDegree();
            if (deg % 2 == 0) { // Even degree
                node.changeNodeColor(Color.white);
            } else { // Odd degree
                node.changeNodeColor(Color.black);
            }
        }
    }
    
    /**
     * Checks if the graph is n regular (means all nodes are connected at max n connections)
     * @param n The value of connections every node has to be
     * @return If every node has n connections true else false
     */
    public Boolean isNRegular(int n) {
        for (Node node : this.nodesList) {
            if (node.getNodeDegree() != n) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the graph has all the possible edges
     * @return Returns true if this graph is a clique else false
     */
    public Boolean isAClique() {
        int n = this.nodesList.size();
        int maximumEdgesInGraph = (n * (n - 1)) / 2;
        if (this.edgesList.size() == maximumEdgesInGraph) {
            return true;
        }
        return false;
    }

    /**
     * Initializes a new Object Graph with inverted connections also called negate graph
     * @return Returns the negated graph form in a new object
     */
    public Graph negateThisGraphAndGetANewOne() {
        if (this.oriented) {
            Graph gNegation = this.cloneThisGraph();
            gNegation.edgesList = null;

            ArrayList<Edge> revertedConnections = new ArrayList<Edge>();
            for (Edge connection : this.edgesList) {
                Edge revertedConnection = connection.initializeNewRevertedConnection();
                revertedConnections.add(revertedConnection);
            }
            gNegation.edgesList = revertedConnections;
            return gNegation;
        } else {
            return (Graph) this;
        }
    }

    /**
     * Checks if the graph has cycles
     * @return Returns true if a cycle exists else returns false
     */
    public Boolean checkIfCyclesExist() {
        for (Node node : this.nodesList) {
            node.colore = Colore.BIANCO;
            node.parent = null;
        }
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(this.nodesList.get(0));
        Node workingNode;
        while (!queue.isEmpty()) {
            workingNode = queue.remove(0);
            for (Node node : this.getListOfNodesConnectedFromThisNode(workingNode)) {
                if (node.colore == Colore.BIANCO) {
                    node.colore = Colore.GRIGIO;
                    node.parent = workingNode;
                    queue.add(node);
                }
                if (node.colore == Colore.NERO) {
                    cyclicNode = node;
                    return true;
                }
                workingNode.colore = Colore.NERO;
            }
        }
        return false;
    }

    /**
     * Finds an arborescence starting from the given node
     * an arborescence is all the nodes the starting node car directly reach
     * @param node The node where all the arborescence starts
     */
    public void findArborescence(Node node) {
        ArrayList<Node> nodeToVisit = new ArrayList<Node>();
        for (Node n : this.nodesList) {
            n.parent = null;
        }
        node.parent = node;
        nodeToVisit.add(node);
        while(!nodeToVisit.isEmpty()) {
            Node in = nodeToVisit.remove(0);
            for (int i = 0; i < in.adjacentNodes.size(); i++) {
                Node adjNode = in.adjacentNodes.get(i);
                if (adjNode.parent == null) {
                    adjNode.parent = in;
                    nodeToVisit.add(adjNode);
                }
            }
        }
    }

    /**
     * Finds where the cycle is
     * @return Returns a node in the cycle
     */ 
    public Node whereIsTheCycle() {
        this.checkIfCyclesExist();
        return cyclicNode;
    }
    
    public String toString() {
        String output = "G = (V,E)\nV = { ";
        for (Node node : nodesList) {
            output += node.toString() + ", ";
        }
        output = output.substring(0, output.length() - 2);
        output += " }\nE = { ";
        for (Edge c : edgesList) {
            output += c.toString() + ", ";
        }
        output = output.substring(0, output.length() - 2);
        output += " }\n";
        output += "|V| = " + nodesList.size();
        output += "\n";
        output += "|E| = " + (edgesList.size() / (oriented ? 1 : 2));
        return output;
    }
    
}
