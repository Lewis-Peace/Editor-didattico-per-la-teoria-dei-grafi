package Backend.Interfaces;

import java.util.ArrayList;

import Backend.Edge;
import Backend.Node;
import Backend.Exceptions.NodeDoesNotExitsException;
import Backend.Exceptions.NodeNotAdjacentException;
import Backend.Exceptions.NodesAlreadyConnectedException;
import Frontend.GraphicalParts.GraphicalEdge.ArrowType;

public interface IGraph {
    
    Boolean oriented = false;
    ArrayList<Node> nodesList = new ArrayList<Node>();
    ArrayList<Edge> edgesList = new ArrayList<Edge>();

    //#region Nodes
    /**
     * Adds a node in the graph
     * 
     * @param node The node to add
     */
    void addNode(Node node);
    /**
     * Deletes a node from the graph with his conenctions
     * 
     * @param node The node to delete
     * @throws NodeDoesNotExitsException Thrown if the node does not exists
     */
    void deleteNode(Node node) throws NodeDoesNotExitsException;
    /*
     * Connects 2 nodes in the graph keeping in mind if the graph is oriented or
     * not, if it is it adds only the connection frome node0 to node 1 else it
     * connects also from node1 to node0
     */

    /**
     * Finds the node searching by specific name
     * 
     * @param name The name of the node to search
     * @return The node with the name or null if that doesn't exists
     */
    Node getNodeByName(String name);
    
    /**
     * Returns a list of the nodes reachable from the input node
     * 
     * @param node The node where we start
     * @return Returns an arrayList of nodes reachable by one step from startin node
     */
    ArrayList<Node> getListOfNodesConnectedFromThisNode(Node node);

    /**
     * Gets the node in the position in the graph
     * 
     * @param position An array of 2 integers stating for x and y
     * @return The node existing in the x and y position
     */
    Node getNodeByPosition(int[] position);

    //#endregion
    
    //#region Edges

    /**
     * Connects the two given nodes by adding an edge object to establish that
     * @param node0 Node to be connected
     * @param node1 Node to be connected
     * @throws NodesAlreadyConnectedException Threw if connection already exists
     */
    void connectNodes(Node node0, Node node1) throws NodesAlreadyConnectedException;
    
    /**
     * Used to scan the connections list searching for the connection object with
     * node1 as starting node and node2 as ending node
     * 
     * @param node1 The starting node of the connection
     * @param node2 The ending node of the connection
     * @return The object Edge establishing connection from node1 to node2, if this
     *         doesn't exists returns null
     */
    Edge getNodesConnection(Node node1, Node node2);
    
    /**
     * Returns a list of connections associated with the input node
     * 
     * @param node The node where the connections needs to start
     * @return The list of connections from that node
     */
    ArrayList<Edge> getAllNodeConnections(Node node);
    
    /**
     * Deletes the connection of 2 given nodes
     * 
     * @param node0 Node where the connection starts
     * @param node1 Node where the connection ends
     * @throws NodeNotAdjacentException Thrown if the nodes doesn't have a
     *                                  connection when the function is called
     */
    void deleteConnection(Node node0, Node node1) throws NodeNotAdjacentException;
    
    /**
     * Deletes every connection related to a given node
     * 
     * @param node The node there te connections come or starts
     * @throws NodeNotAdjacentException If the connection does not exists
     */
    void deleteEveryConnectionOfNode(Node node) throws NodeNotAdjacentException;
    //#endregion

    //#region Graphical
    /**
     * Changes the arrow type for each edge in the graph
     * 
     * @param type The new arrow type
     */
    void changeArrowType(ArrowType type);

    /**
     * Changes the diameter of all the nodes in the graph
     * 
     * @param diameter The new diameter
     */
    void changeNodesDiameter(int diameter);
    //#endregion

    //#region Algorithms
    
    /**
     * Checks if the graph is biparted and colors the colore var in each node with
     * specific node color if succesfull
     * @return Returns true if the graph is biparted else returns false
     */
    void isBiparted();
    
    /**
     * Checks if the graph is n regular (means all nodes are connected at max n connections)
     * @param n The value of connections every node has to be
     * @return If every node has n connections true else false
     */
    Boolean isNRegular(int n);
    
    /**
     * Checks if the graph has all the possible edges
     * @return Returns true if this graph is a clique else false
     */
    Boolean isAClique();
    
    /**
     * Checks if the graph has cycles
     * @return Returns true if a cycle exists else returns false
     */
    Boolean checkIfCyclesExist();

    /**
     * Finds where the cycle is
     * @return Returns a node in the cycle
     */ 
    Node whereIsTheCycle();
    
    /**
     * Finds an arborescence starting from the given node
     * an arborescence is all the nodes the starting node car directly reach
     * @param node The node where all the arborescence starts
     */
    void findArborescence(Node node);
    //#endregion
}
