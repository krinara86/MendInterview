package mendinterview;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/api/v1/graphs")
public class GraphController {
    @PostMapping("/shortestPaths")
    @GetMapping("/api/v1/graphs/shortestPath/{filePath}/{startNodeId}")
    public ResponseEntity<Map<String, Integer>> calculateShortestPaths(@RequestBody Graph graph,
            @RequestParam String startNodeId) {
        // Store distances with 0 and 1 red edge
        Map<Node, Integer> distNoRed = new HashMap<>();
        Map<Node, Integer> distOneRed = new HashMap<>();

        // Find the start node from the list of nodes
        Node startNode = null;
        for (Node node : graph.getNodes()) {
            if (node.getId().equals(startNodeId)) {
                startNode = node;
            }
            // Set all distances to infinity initially
            distNoRed.put(node, Integer.MAX_VALUE);
            distOneRed.put(node, Integer.MAX_VALUE);
        }

        // The distance to the start node is 0
        distNoRed.put(startNode, 0);

        // Create a priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(node -> distNoRed.get(node) + distOneRed.get(node)));

        // Add start node to the priority queue
        pq.add(startNode);

        // While there are nodes left in the queue
        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // Iterate over all edges
            for (Edge edge : current.getEdges()) {
                // The new distance is the distance to the current node plus the edge weight
                // (weight is 1 since it is unweighted)
                int newDistance = distNoRed.get(current) + 1;

                // Update the distances based on the edge color
                Node destinationNode = findNodeById(graph, edge.getDestination());
                if (edge.getColor().equals("BLUE")) {
                    if (newDistance < distNoRed.get(destinationNode)) {
                        distNoRed.put(destinationNode, newDistance); 
                        pq.add(destinationNode);  
                    }
                } else if (edge.getColor().equals("RED")) {
                    if (newDistance < distOneRed.get(destinationNode)) {  
                        distOneRed.put(destinationNode, newDistance);  
                        pq.add(destinationNode);  
                    }
                }
            }
        }

        // Combine the two maps to have the shortest distances with at most one red edge
        Map<String, Integer> shortestDistances = new HashMap<>();
        for (Node node : distNoRed.keySet()) {
            shortestDistances.put(node.getId(), Math.min(distNoRed.get(node), distOneRed.get(node)));
        }

        return ResponseEntity.ok(shortestDistances);
    }

    private Node findNodeById(Graph graph, String nodeId) {
        for (Node node : graph.getNodes()) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        throw new IllegalArgumentException("Node not found with ID: " + nodeId);
    }

}