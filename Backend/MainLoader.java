package Backend;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Backend.Exceptions.NodesAlreadyConnectedException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;

public class MainLoader {

    /**
     * Saves the graph in an xml file
     * @param fileLocation The location and name of the new file
     * @param graph The graph to save
     */
    public static void save(String fileLocation, Graph graph) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element graphXML = doc.createElement("graph");
            graphXML.setAttribute("oriented", graph.oriented.toString());
            graphXML.setAttribute("numberOfNodes", graph.nodesList.size() + "");

            Element node, connectedTo;

            for (int i = 0; i < graph.nodesList.size(); i++) {
                node = doc.createElement("node");

                node.setAttribute("name", graph.nodesList.get(i).name + "");
                node.setAttribute("xPos", graph.nodesList.get(i).xPos + "");
                node.setAttribute("yPos", graph.nodesList.get(i).yPos + "");
                node.setAttribute("numberOfConnections", graph.nodesList.get(i).nodeConnections.size() + "");

                connectedTo = doc.createElement("connectedTo");
                String connectedToString = "";
                for (Backend.Node node1: graph.nodesList.get(i).adjacentNodes) {
                    connectedToString += node1.name;
                    if (!node1.equals(graph.nodesList.get(i).adjacentNodes.get(graph.nodesList.get(i).adjacentNodes.size() - 1))) {
                        connectedToString += " ";
                    }
                }
                connectedTo.appendChild(doc.createTextNode(connectedToString));

                node.appendChild(connectedTo);
                graphXML.appendChild(node);
            }

            doc.appendChild(graphXML);
            
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileLocation)));

        } catch (Exception e) {
        }
    }


    /**
     * Loads a graph from an xml file
     * @return The graph into the file
     */
    public static Graph load() {
        try {
            JFileChooser chooser = new JFileChooser("./");
            chooser.showOpenDialog(null);
            File fileHandler = chooser.getSelectedFile();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileHandler);
            doc.getDocumentElement().normalize();

            int totalNodes = Integer.parseInt(doc.getDocumentElement().getAttribute("numberOfNodes"));

            Graph graph = new Graph(true);
            NodeList nodeN = doc.getElementsByTagName("node");
            populateGraph(totalNodes, nodeN, graph);
            graph.oriented = doc.getDocumentElement().getAttribute("oriented").equals("true");
            graph.resetGraphToStandardValues();
            return graph;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Loads a graph from an xml file
     * @return The graph into the file
     */
    public static Graph load(String fileLocation) {
        try {
            File fileHandler = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileHandler);
            doc.getDocumentElement().normalize();

            int totalNodes = Integer.parseInt(doc.getDocumentElement().getAttribute("numberOfNodes"));

            Graph graph = new Graph(true);
            NodeList nodeN = doc.getElementsByTagName("node");
            populateGraph(totalNodes, nodeN, graph);
            graph.oriented = doc.getDocumentElement().getAttribute("oriented").equals("true");
            graph.resetGraphToStandardValues();
            return graph;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fills the graph with the data from the loaded file
     * @param totalNodes Total number of nodes in the graph
     * @param list The list of nodes in the xml file
     * @param graph The graph where the data will be saved
     * @throws NodesAlreadyConnectedException If a connection already exists
     */
    private static void populateGraph(int totalNodes, NodeList list, Graph graph)
            throws NodesAlreadyConnectedException {
        addAllNodes(totalNodes, list, graph);
        addAllConnections(totalNodes, list, graph);
    }

    /**
     * Adds all nodes in the graph while loading
     * @param totalNodes The total number of nodes in the graph
     * @param list The list of nodes loaded from the xml file
     * @param graph The graph where i have to save the nodes
     */
    private static void addAllNodes(int totalNodes, NodeList list, Graph graph) {
        if (list != null) {
            for (int i = 0; i < totalNodes; i++) {
                Node nNode = list.item(i);
                Element eElement = (Element) nNode;

                int xPos = Integer.parseInt(eElement.getAttribute("xPos"));
                int yPos = Integer.parseInt(eElement.getAttribute("yPos"));
                Backend.Node node = new Backend.Node(xPos, yPos, eElement.getAttribute("name"));
                graph.addNode(node);
            }
        }
    }

    /**
     * Adds all connections in the specific format to the graph while loading
     * @param totalNodes The number of nodes to check for connections
     * @param list The list of elements taken from the xml file
     * @param graph The graph where i'll add the conenctions
     * @throws NodesAlreadyConnectedException
     */
    private static void addAllConnections(int totalNodes, NodeList list, Graph graph)
            throws NodesAlreadyConnectedException {
        for (int i = 0; i < totalNodes; i++) {
            Node nNode = list.item(i);
            Element eElement = (Element) nNode;
            String nodeName = eElement.getAttribute("name");
            if (eElement.getElementsByTagName("connectedTo").getLength() != 0) {
                String[] nodeConnections = (eElement.getElementsByTagName("connectedTo").item(0).getTextContent()).split(" ");
                Backend.Node node = graph.getNodeByName(nodeName);
                for (int j = 0; j < nodeConnections.length; j++) {
                    if (nodeConnections[j] != "") {
                        Backend.Node secondNode = graph.getNodeByName(nodeConnections[j]);
                        graph.connectNodes(node, secondNode);
                    }
                }
            }
        }
    }
}