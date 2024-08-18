import java.util.ArrayList;
import java.util.Random;

public class HillClimbSalesPerson {
    
    // Define a 2D array representing the distances between cities
    public static int[][] tsp = {
            {0, 400, 500, 300},
            {400, 0, 300, 500},
            {500, 300, 0, 400},
            {300, 500, 400, 0}
    };

    // Random object for generating random indices
    public static Random random = new Random();

    /**
     * Generates a random permutation of cities.
     * 
     * Pseudocode:
     * 1. Create a list of city indices.
     * 2. Create an empty list for the solution.
     * 3. While the list of cities is not empty:
     *    a. Pick a random city from the list.
     *    b. Add it to the solution list.
     *    c. Remove it from the list of cities.
     * 4. Return the solution list.
     */
    public static ArrayList<Integer> generateRandomSolution(int[][] tsp) {
        ArrayList<Integer> cities = new ArrayList<>();
        for (int i = 0; i < tsp.length; i++) {
            cities.add(i);
        }
        ArrayList<Integer> solution = new ArrayList<>();
        while (!cities.isEmpty()) {
            int randomIndex = random.nextInt(cities.size());
            int randomCity = cities.get(randomIndex);
            solution.add(randomCity);
            cities.remove(randomIndex);
        }
        return solution;
    }

    /**
     * Calculates the total route length for a given solution.
     * 
     * Pseudocode:
     * 1. Initialize route length to 0.
     * 2. For each city in the solution:
     *    a. Find the next city (wrap around to the start if at the end).
     *    b. Add the distance between the current city and the next city to the route length.
     * 3. Return the route length.
     */
    public static int routeLength(int[][] tsp, ArrayList<Integer> randomSolution) {
        int routeLen = 0;
        for (int i = 0; i < randomSolution.size(); i++) {
            int fromCity = randomSolution.get(i);
            int toCity = randomSolution.get((i + 1) % randomSolution.size());
            routeLen += tsp[fromCity][toCity];
        }
        return routeLen;
    }

    /**
     * Generates neighboring solutions by swapping pairs of cities.
     * 
     * Pseudocode:
     * 1. Initialize a list to store neighboring solutions.
     * 2. For each pair of indices (i, j) in the solution:
     *    a. Create a copy of the current solution.
     *    b. Swap the cities at indices i and j in the copied solution.
     *    c. Add the modified solution to the list of neighbors.
     * 3. Return the list of neighboring solutions.
     */
    public static ArrayList<ArrayList<Integer>> getNeighbors(ArrayList<Integer> currentSolution) {
        ArrayList<ArrayList<Integer>> neighbors = new ArrayList<>();

        for (int i = 0; i < currentSolution.size(); i++) {
            for (int j = i + 1; j < currentSolution.size(); j++) {
                ArrayList<Integer> newNeighbor = new ArrayList<>(currentSolution);
                // Swap the elements at indices i and j
                int temp = newNeighbor.get(i);
                newNeighbor.set(i, newNeighbor.get(j));
                newNeighbor.set(j, temp);
                neighbors.add(newNeighbor);
            }
        }
        return neighbors;
    }

    /**
     * Performs hill climbing to find a solution with the shortest route length.
     * 
     * Pseudocode:
     * 1. Generate an initial random solution.
     * 2. Calculate the route length of the initial solution.
     * 3. Set improvement flag to true.
     * 4. While improvement is possible:
     *    a. Set improvement flag to false.
     *    b. Generate neighbors of the current solution.
     *    c. For each neighbor:
     *       i. Calculate the route length of the neighbor.
     *       ii. If the neighbor's route length is shorter:
     *           - Update the current solution.
     *           - Update the current route length.
     *           - Set improvement flag to true.
     * 5. Print the best solution and its route length.
     * 6. Return the best route length.
     */
    public static int hillClimbing() {
        ArrayList<Integer> currentSolution = generateRandomSolution(tsp);
        int currentRouteLength = routeLength(tsp, currentSolution);

        boolean improvement = true;
        while (improvement) {
            improvement = false;
            ArrayList<ArrayList<Integer>> neighbors = getNeighbors(currentSolution);

            for (ArrayList<Integer> neighbor : neighbors) {
                int neighborRouteLength = routeLength(tsp, neighbor);
                if (neighborRouteLength < currentRouteLength) {
                    currentSolution = neighbor;
                    currentRouteLength = neighborRouteLength;
                    improvement = true;
                }
            }
        }

        // Print the best solution and its route length
        System.out.println("Best Solution: " + currentSolution);
        System.out.println("Best Route Length: " + currentRouteLength);

        return currentRouteLength;
    }

    // Main method to run the hill climbing algorithm
    public static void main(String[] args) {
        hillClimbing();
    }
}
