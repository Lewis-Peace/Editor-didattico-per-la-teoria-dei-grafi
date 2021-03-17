package Backend;

public class AdjacencyMatrix {

    public String[][] matrix;
    private Graph graph;
    private String[] nodesNames;

    // Constructor of the class
    public AdjacencyMatrix (Graph graph) {
        int i,j;
        int nodeNumber = graph.nodesList.size();
        matrix = new String[nodeNumber + 1][nodeNumber + 1];
        nodesNames = new String[nodeNumber];
        this.graph = graph;
        for (i = 0; i < nodeNumber + 1; i++) {
            for (j = 0; j < nodeNumber + 1; j++) {
                matrix[i][j] = "0";
            }
        }
        populateNodeSerialNumbersArray();
        populateMatrix();
    }

    // Gets all the serial numbers from the nodes in the graph
    private void populateNodeSerialNumbersArray() {
        int i = 0;
        for (Node node : graph.nodesList) {
            nodesNames[i] = node.name;
            i++;
        }
    }

    // Populates the matrix filling only the cells where a connection appear
    private void populateMatrix() {
        nameExternalRowsWithNodeSerialNumbers();
        for (Edge connection : graph.edgesList) {
            int x = getXPositionOfConnection(connection) + 1;
            int y = getYPositionOfConnection(connection) + 1;
            matrix[x][y] = "1";
        }
    }

    // Gets the y position of a connection in the matrix
    private int getYPositionOfConnection(Edge connection) {
        String serialNumberOfSearchedNode = connection.endingNode.name;
        for (int i = 0; i < nodesNames.length; i++) {
            if (nodesNames[i] == serialNumberOfSearchedNode) {
                return i;
            }
        }
        return -1;
    }

    // Gets the x position of a connection in the matrix
    private int getXPositionOfConnection(Edge connection) {
        String serialNumberOfSearchedNode = connection.startingNode.name;
        for (int i = 0; i < nodesNames.length; i++) {
            if (nodesNames[i] == serialNumberOfSearchedNode) {
                return i;
            }
        }
        return -1;
    }

    // Gives the names row and column the specific names
    private void nameExternalRowsWithNodeSerialNumbers() {
        // first row
        for (int i = 0; i < nodesNames.length; i++) {
            matrix[0][i + 1] = nodesNames[i];
        }
        // first column
        for (int i = 0; i < nodesNames.length; i++) {
            matrix[i + 1][0] = nodesNames[i];
        }
    }
    
    public String toString() {
        String output = "Adjacency matrix\n";
        for (String[] is : matrix) {
            for (int i = 0; i < matrix.length; i++)  {
                output += is[i] + " ";
            }
            output += "\n";
        }
        return output;
    }

}
