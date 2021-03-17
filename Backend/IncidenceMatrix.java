package Backend;

import Backend.Exceptions.NodeNotAdjacentException;

public class IncidenceMatrix {

    public String[][] matrix;
    private Graph graph;
    private String[] nodesSerialNumbers;
    private String[] connectionsName;

    // Class constructor
    public IncidenceMatrix(Graph graph) {
        this.graph = graph.cloneThisGraph();
        if (!this.graph.oriented) {
            removeRedudancyEdges(this.graph.edgesList.size() / 2);
        }
        int nodeNumber = this.graph.nodesList.size();
        int connectionsNumber = this.graph.edgesList.size();
        matrix = new String[nodeNumber + 1][connectionsNumber + 1];
        for (String[] string : matrix) {
            for (int i = 0; i < string.length; i++) {
                string[i] = "0 ";
            }
        }
        populateMatrix();
    }

    private void removeRedudancyEdges(int edgesToRemove) {
        graph.oriented = true;
        for (int i = 0; i < edgesToRemove; i++) {
            Edge edge = graph.edgesList.get(i);
            try {
                graph.deleteConnection(edge.endingNode, edge.startingNode);
            } catch (NodeNotAdjacentException e) {
                e.printStackTrace();
            }
        }
        graph.oriented = false;
    }
    
    // Fills the array with the arrays serial number in the graph
    private void populateNodesSerialNumbers() {
        int nodeNumber = graph.nodesList.size();
        nodesSerialNumbers = new String[nodeNumber];
        for (int i = 0; i < nodeNumber; i++) {
            nodesSerialNumbers[i] = graph.nodesList.get(i).name;
        }
    }   
    
    // Fills the array with the graph connections
    private void populateConnectionsName() {
        int connectionsNumber = this.graph.edgesList.size();
        connectionsName = new String[connectionsNumber];
        for (int i = 0; i < connectionsNumber; i++) {
            String nodeName1 = graph.edgesList.get(i).startingNode.name;
            String nodeName2 = graph.edgesList.get(i).endingNode.name;
            connectionsName[i] = nodeName1 + "," + nodeName2;
        }
    }

    // Fills the matrix with all the names and cells needing with a 1
    private void populateMatrix() {
        populateNames();
        for (int i = 0; i < connectionsName.length; i++) {
            String[] nodes = connectionsName[i].split(",");
            for (int j = 0; j < nodesSerialNumbers.length; j++) {
                if (nodesSerialNumbers[j].equals(nodes[0]) || nodesSerialNumbers[j].equals(nodes[1])) {
                    matrix[j + 1][i + 1] = "1 ";
                }
            }
        }
    }

    // Fills the naming cells in the matrix with specific names
    private void populateNames() {
        populateNodesSerialNumbers();
        populateConnectionsName();
        for (int i = 0; i < nodesSerialNumbers.length; i++) {
            matrix[i + 1][0] = nodesSerialNumbers[i] + " ";
        }
        for (int i = 0; i < connectionsName.length; i++) {
            matrix[0][i + 1] = connectionsName[i];
        }
    }


    public String toString() {
        String output = "Incidence matrix\n";
        for (String[] is : matrix) {
            for (int i = 0; i < is.length; i++)  {
                output += is[i] + " ";
            }
            output += "\n";
        }
        return output;
    }

}
