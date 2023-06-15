package mendinterview;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public void addEdge(Node sourceNode, Node destinationNode, String color) {
        Edge edge = new Edge(destinationNode, color);
        sourceNode.addEdge(edge);
    }

    public void addUndirectedEdge(Node sourceNode, Node destinationNode, String color) {
        Edge sourceToDestination = new Edge(destinationNode, color);
        sourceNode.addEdge(sourceToDestination);

        Edge destinationToSource = new Edge(sourceNode, color);
        destinationNode.addEdge(destinationToSource);
    }
}