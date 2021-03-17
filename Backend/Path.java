package Backend;

import java.util.ArrayList;

import Backend.Exceptions.NodeNotAdjacentException;
import Backend.Exceptions.NodesAlreadyConnectedException;
import Backend.Exceptions.PathRequestNotPossibleException;

// TODO: make this class better
public class Path {

    public Node startingNode;
    public Node finalNode;
    public ArrayList<Edge> edgePath;
    public ArrayList<Node> nodePath;

    public Path(Node startingNode, Node finalNode) {
        this.startingNode = startingNode;
        this.finalNode = finalNode;
        edgePath = new ArrayList<Edge>();
        nodePath = new ArrayList<Node>();
    }

    /**
     * Asks if the current object starts and ends in the same node
     * 
     * @return true if before statement is true else false
     */
    public Boolean isThisPathACircuit() {
        if (startingNode.equals(finalNode)) {
            return true;
        }
        return false;
    }

    /**
     * Populates the path global var with all the parents of the finalNode until he
     * reaches the startingNode
     * 
     * @param graph The graph where the work is done
     */
    public void getShortestPath(Graph graph) {
        if (!startingNode.equals(finalNode)) {
            BFS(graph, startingNode);
            Node workingNode = finalNode;
            Node parent;
            while (workingNode.parent != null) {
                parent = workingNode.parent;
                Edge connection = graph.getNodesConnection(parent, workingNode);
                edgePath.add(0, connection);
                workingNode = parent;
            }
        } else {
            Edge connection = graph.getNodesConnection(startingNode, finalNode);
            if (connection != null) {
                edgePath.add(connection);
            } else {
                System.err.println("This path does not exist");
            }
        }
    }

    /**
     * BFS algoithm implemented for the current data structure that sets all nodes
     * with the data stated after establishing starting node inputed
     * 
     * @param graph        Current graph where all the work will be done
     * @param startingNode Starting node where the algorithm starts
     */
    private void BFS(Graph graph, Node startingNode) {
        for (Node node : graph.nodesList) {
            setNodeData(node, Colore.BIANCO, -1, null);
        }
        setNodeData(startingNode, Colore.GRIGIO, 0, null);
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(startingNode);
        Node workingNode;
        while (!queue.isEmpty()) {
            workingNode = queue.remove(0);

            for (Node adiacentNode : graph.getListOfNodesConnectedFromThisNode(workingNode)) {
                if (adiacentNode.colore == Colore.BIANCO) {
                    setNodeData(adiacentNode, Colore.GRIGIO, workingNode.distance + 1, workingNode);
                    queue.add(adiacentNode);
                }
            }
            workingNode.colore = Colore.NERO;
        }
    }

    // Function used by BFS for support
    private void setNodeData(Node node, Colore color, int distance, Node parent) {
        node.colore = color;
        node.distance = distance;
        node.parent = parent;
    }

    public int getPathLength() {
        if (edgePath.isEmpty()) {
            return nodePath.size();
        } else {
            return edgePath.size();
        }
    }

    /**
     * Accepts only paths longer than 1 and returns random path of n length this
     * function doesn't remember if the edge or node was already used so use it
     * wisely
     * 
     * @param graph        The graph where the path is formed
     * @param lengthOfPath The length of the path
     * @throws NodeNotAdjacentException If a node isn't near another node
     */
    public void getPathOfNLength(Graph graph, int lengthOfPath) throws NodeNotAdjacentException {
        ArrayList<Node> path = new ArrayList<>();
        if (lengthOfPath > 1) {
            Node nodeOnUse = startingNode;
            for (int i = 0; i < lengthOfPath; i++) {
                path.add(nodeOnUse);
                int randomIndex = (int) Math.floor(Math.random() * nodeOnUse.adjacentNodes.size());
                nodeOnUse = nodeOnUse.adjacentNodes.get(randomIndex);
            }
            nodePath = path;
        } else {
            try {
                throw new PathRequestNotPossibleException(0);
            } catch (PathRequestNotPossibleException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Function used to find if an hamiltonian path exists
     * @param graph the graph where we will search the ciruit
     * @param nodesToTouch the number of nodes i want to pass
     */
    public void findHamiltonianPath(Graph graph, int nodesToTouch) {
        foundHamiltonianPath = false;
        nodePath.add(startingNode);
        findHamiltonianPathRecusive(graph, startingNode, nodesToTouch - 1, nodesToTouch);
    }

    public Boolean foundHamiltonianPath;

    private void findHamiltonianPathRecusive(Graph graph, Node node, int nodesToTouch, int expectedNodes) {
        for (Node adjNode : node.adjacentNodes) {
            if (nodesToTouch == 0 && adjNode.equals(finalNode)) {
                foundHamiltonianPath = true;
                if (nodePath.size() != expectedNodes) {
                    nodePath = null;
                }
                return;
            }
        }
        for (int i = 0; i < node.adjacentNodes.size(); i++) {
            Node adjacentNode = node.adjacentNodes.get(i);
            if (!nodePath.contains(adjacentNode)) { // Node not already passed on
                if (!foundHamiltonianPath) { // Avoids random addons on path after it was found
                    nodePath.add(adjacentNode);
                }
                findHamiltonianPathRecusive(graph, adjacentNode, nodesToTouch - 1, expectedNodes);
                if (!foundHamiltonianPath) { // Avoids random remove on path after it was found
                    nodePath.remove(adjacentNode);
                }
            }
        }
    }

    /**
     * Finds if an Eulerian path of given lenght is possible and saves it into the
     * object
     * @param graph        the graph where we will search the ciruit
     * @param lengthOfPath the lenght of the searched path
     */
    public void findEulerianPath(Graph graph, int lengthOfPath) {
        eluerianEnded = false;
        try {
            Graph newGraph = graph.cloneThisGraph();
            this.newStartingNode = newGraph.getNodeByName(this.startingNode.name);
            findeEulerianPathRecursive(newGraph, newStartingNode, lengthOfPath);
        } catch (NodesAlreadyConnectedException | NodeNotAdjacentException e) {
            e.printStackTrace();
        }
    }

    public Boolean eluerianEnded;
    private Node newStartingNode;

    private void findeEulerianPathRecursive(Graph clonedGraph, Node node, int lengthOfPath)
            throws NodesAlreadyConnectedException, NodeNotAdjacentException {
        if (clonedGraph.edgesList.isEmpty() && node.equals(this.newStartingNode)) {
            eluerianEnded = true;
            return;
        }
        ArrayList<Edge> edges = clonedGraph.getAllNodeConnections(node);
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (!eluerianEnded) {
                clonedGraph.deleteConnection(edge.startingNode, edge.endingNode);
                edgePath.add(edge);
            }
            findeEulerianPathRecursive(clonedGraph, edge.endingNode, lengthOfPath - 1);
            if (!eluerianEnded) {
                clonedGraph.connectNodes(edge.startingNode, edge.endingNode);
                edgePath.remove(edge);
            }
        }
    }
    
    public String toString() {
        String output = "Path from node " + startingNode.name + " to " + finalNode.name + " is: \n";
        if (edgePath != null && !edgePath.isEmpty()) {
            for (Edge connection : edgePath) {
                output += connection + " ";
            }
        } else if (nodePath != null && !nodePath.isEmpty()) {
            for (Node node : nodePath) {
                output += node + " ";
            }
        }
        return output;
    }


}
