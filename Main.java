import Backend.Graph;
import Frontend.MainGraphical;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(false);
        MainGraphical.openWindow(graph, "italian");
    }
}