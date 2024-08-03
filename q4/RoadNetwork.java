// package q4;

// import java.util.ArrayList;


// public class RoadNetwork {

//     static class Edge {
//         int src, dest, weight;
//         public Edge(int src, int dest, int weight) {
//             this.src = src;
//             this.dest = dest;
//             this.weight = weight;
//         }
//     }


//     public static void createGraph(ArrayList<Edge>[] edges) {
//         edges[4].add(new Edge(4, 1, -1));  // Edge from node 4 to node 1
//         edges[2].add(new Edge(2, 0, -1));  // Edge from node 2 to node 0
//         edges[0].add(new Edge(0, 3, -1));  // Edge from node 0 to node 3
//         edges[4].add(new Edge(4, 3, -1));  // Edge from node 4 to node 3
//     }

//     public static void DFS(ArrayList<Edge>[] graph,  int src, int dest, int targetTime) {
//         for (int i =0; i<graph[src].size(); i++) {
//             Edge edge = graph[src].get(i);
//             int u = edge.src;
//             int v = edge.dest;
//             int weight = edge.weight;

//             if (weight == -1) {
//                 weight = 1;
//                 if (u == src || v == dest) {
//                     weight =
//                 }
//             }
//         }
//     }




//     public static void main(String[] args) {
//         int LOCATION = 5;
//         ArrayList<Edge>[] edges = new ArrayList[LOCATION];
//         for (int i = 0; i < LOCATION; i++) {
//             edges[i] = new ArrayList<>();
//         }
//         createGraph(edges);
//         ArrayList<Integer> allPaths = new ArrayList<>();
//     }
// }
