package q4;


/**
 * Class Pair:
    Attributes:
        - node: Integer
        - distance: Integer

    Method compareTo(Pair that):
        Return comparison of this.distance and that.distance

Method dijkstra(graph, src, numNodes):
    Create priority queue (pq)
    Add Pair(src, 0) to pq
    
    Initialize distance array (dist) with infinity
    Initialize visited array with false
    Set dist[src] to 0
    
    While pq is not empty:
        Poll the smallest distance node (curr) from pq
        Set u to curr.node

        If curr.distance is greater than dist[u]:
            Continue

        If u is not visited:
            Mark u as visited

            For each neighbor (Pair) of node u:
                Set v to neighbor.node
                Set weight to neighbor.distance
                Set newDist to dist[u] + weight

                If newDist is less than dist[v] and v is not visited:
                    Update dist[v] to newDist
                    Add Pair(v, newDist) to pq
    
    Return dist

Method findShortestPath(graph, src, dest, numNodes):
    Call dijkstra(graph, src, numNodes) to get distances
    Return distance to dest

Method adjustRoadWeights(roads, src, dest, targetTime, numNodes):
    Initialize graph as empty list of lists
    Initialize constructionEdges as empty list
    
    For each road in roads:
        Set u, v, weight from road
        
        If weight is -1:
            Add [u, v] to constructionEdges
        Else:
            Add Pair(v, weight) to graph[u]
            Add Pair(u, weight) to graph[v]

    For each road in constructionEdges:
        Set u, v
        Add Pair(v, 1) to graph[u]
        Add Pair(u, 1) to graph[v]

    Set currentShortestTime to findShortestPath(graph, src, dest, numNodes)
    Print initial shortest time

    While currentShortestTime is not equal to targetTime:
        If currentShortestTime is less than targetTime:
            For each road in constructionEdges:
                Set u, v
                Set additionalTime to targetTime - currentShortestTime
                Set newWeight to random integer between 1 and additionalTime + 1
                Update edge weights in graph with newWeight
                Set currentShortestTime to findShortestPath(graph, src, dest, numNodes)
                If currentShortestTime equals targetTime:
                    Break

        Else:
            For each road in constructionEdges:
                Set u, v
                Set decreaseTime to currentShortestTime - targetTime
                Set newWeight to random integer between 1 and decreaseTime + 1
                Update edge weights in graph with newWeight
                Set currentShortestTime to findShortestPath(graph, src, dest, numNodes)
                If currentShortestTime equals targetTime:
                    Break

        Print updated shortest time
        If currentShortestTime equals targetTime:
            Break

    If currentShortestTime is not equal to targetTime:
        Print unable to meet target time
        Return empty result

    Initialize result array of size roads.length
    For each road in roads:
        Set u, v, weight from road
        
        If weight is -1:
            Find updated weight for construction edges
            Set weight to updated weight

        Set result[index++] to [u, v, weight]

    Return result

Method updateEdgeWeights(graph, u, v, newWeight):
    Remove existing Pair(u, v) from graph[u]
    Remove existing Pair(v, u) from graph[v]
    Add Pair(v, newWeight) to graph[u]
    Add Pair(u, newWeight) to graph[v]

Method randomInRange(min, max):
    Return random integer between min and max

Main method:
    Initialize roads as a list of edges
    Set src, dest, targetTime, numNodes
    Call adjustRoadWeights with these parameters
    Print modified roads

 */
import java.util.*;

public class CityPlanner {

    // Class to represent a pair of node and distance
    static class Pair implements Comparable<Pair> {
        int node, distance;

        // Constructor
        public Pair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        // Comparison based on distance for priority queue
        @Override
        public int compareTo(Pair that) {
            return Integer.compare(this.distance, that.distance);
        }
    }

    // Method to find shortest distances from source to all nodes using Dijkstra's algorithm
    public static int[] dijkstra(List<List<Pair>> graph, int src, int numNodes) {
        // Priority queue to fetch node with minimum distance
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src, 0)); // Start with source node

        // Distance array to store shortest distance to each node
        int[] dist = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize all distances as infinity
        dist[src] = 0; // Distance to source is zero

        // Process nodes until the queue is empty
        while (!pq.isEmpty()) {
            Pair curr = pq.poll(); // Get node with smallest distance
            int u = curr.node;

            // Skip if this node has been visited or has a greater distance than already found
            if (curr.distance > dist[u]) {
                continue;
            }

            if (!visited[u]) {
                visited[u] = true; // Mark node as visited

                // Update distances for all neighbors of node u
                for (Pair neighbor : graph.get(u)) {
                    int v = neighbor.node;
                    int weight = neighbor.distance;
                    int newDist = dist[u] + weight;

                    if (newDist < dist[v] && !visited[v]) {
                        dist[v] = newDist;
                        pq.add(new Pair(v, newDist)); // Add updated distance to the queue
                    }
                }
            }
        }
        return dist; // Return shortest distances from source
    }

    // Method to find the shortest path distance from src to dest
    public static int findShortestPath(List<List<Pair>> graph, int src, int dest, int numNodes) {
        int[] distance = dijkstra(graph, src, numNodes);
        return distance[dest]; // Return distance to destination
    }

    // Method to adjust road weights to achieve a target shortest path time
    public static int[][] adjustRoadWeights(int[][] roads, int src, int dest, int targetTime, int numNodes) {
        // Initialize graph and list for unbuilt roads
        List<List<Pair>> graph = new ArrayList<>();
        List<int[]> constructionEdges = new ArrayList<>();

        // Create graph with nodes and edges
        for (int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges to graph; keep track of construction roads
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int weight = road[2];

            if (weight == -1) {
                constructionEdges.add(new int[]{u, v}); // Unbuilt road
            } else {
                graph.get(u).add(new Pair(v, weight));
                graph.get(v).add(new Pair(u, weight)); // Undirected graph
            }
        }

        // Set initial weight for unbuilt roads
        for (int[] road : constructionEdges) {
            int u = road[0];
            int v = road[1];
            graph.get(u).add(new Pair(v, 1)); // Temporarily set weight to 1
            graph.get(v).add(new Pair(u, 1)); // Undirected graph
        }

        // Check the current shortest path with initial weights
        int currentShortestTime = findShortestPath(graph, src, dest, numNodes);
        System.out.println("Initial shortest time: " + currentShortestTime);

        // Adjust weights to match the target time
        while (currentShortestTime != targetTime) {
            if (currentShortestTime < targetTime) {
                // Increase weights if current time is less than target
                for (int[] road : constructionEdges) {
                    int u = road[0];
                    int v = road[1];
                    int additionalTime = targetTime - currentShortestTime;
                    int newWeight = randomInRange(1, additionalTime+1);
                    updateEdgeWeights(graph, u, v, newWeight);
                    currentShortestTime = findShortestPath(graph, src, dest, numNodes);
                    if (currentShortestTime == targetTime) break; // Stop if target is reached
                }
            } else {
                // Decrease weights if current time is more than target
                for (int[] road : constructionEdges) {
                    int u = road[0];
                    int v = road[1];
                    int decreaseTime = currentShortestTime - targetTime;
                    int newWeight = randomInRange(1, decreaseTime+1);
                    updateEdgeWeights(graph, u, v, newWeight);
                    currentShortestTime = findShortestPath(graph, src, dest, numNodes);
                    if (currentShortestTime == targetTime) break; // Stop if target is reached
                }
            }

            System.out.println("Updated shortest time: " + currentShortestTime);
            if (currentShortestTime == targetTime) {
                break;
            }
        }

        // If unable to meet target time, return empty result
        if (currentShortestTime != targetTime) {
            System.out.println("Unable to meet the target time exactly.");
            return new int[][]{};
        }

        // Convert graph back to edge list format
        int[][] result = new int[roads.length][3];
        int index = 0;
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int weight = road[2];

            if (weight == -1) {
                // Find the updated weight for construction edges
                for (Pair pair : graph.get(u)) {
                    if (pair.node == v) {
                        weight = pair.distance;
                        break;
                    }
                }
            }

            result[index++] = new int[]{u, v, weight};
        }
        return result;
    }

    // Helper method to update edge weights in the graph
    private static void updateEdgeWeights(List<List<Pair>> graph, int u, int v, int newWeight) {
        graph.get(u).removeIf(pair -> pair.node == v);
        graph.get(v).removeIf(pair -> pair.node == u);
        graph.get(u).add(new Pair(v, newWeight));
        graph.get(v).add(new Pair(u, newWeight));
    }

    // Helper method to generate a random integer within a range
    private static int randomInRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    // Main method for testing
    public static void main(String[] args) {
        int[][] roads = {
                {4, 1, -1},
                {2, 0, -1},
                {0, 3, -1},
                {4, 3, -1}
        };

        int src = 0;
        int dest = 1;
        int targetTime = 5;
        int city = 5;

        int[][] modifiedRoads = adjustRoadWeights(roads, src, dest, targetTime, city);
        System.out.println("Modified Roads:");
        for (int[] road : modifiedRoads) {
            System.out.println(Arrays.toString(road));
        }
    }
}
