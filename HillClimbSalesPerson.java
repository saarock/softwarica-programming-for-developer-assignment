import java.util.ArrayList;
import java.util.Random;


// 2 question complete
public class HillClimbSalesPerson {
    public static int[][] tsp = {
            {0, 400, 500, 300},
            {400, 0, 300, 500},
            {500, 300, 0, 400},
            {300, 500, 400, 0}
    };

    // Step 1: Generate a random solution
    public static Random random = new Random();

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

    public static int routeLength(int[][] tsp, ArrayList<Integer> randomSolution) {
        int routeLen = 0;
        for (int i = 0; i < randomSolution.size(); i++) {
            int fromCity = randomSolution.get(i);
            int toCity = randomSolution.get((i + 1) % randomSolution.size());
            routeLen += tsp[fromCity][toCity];
        }
        return routeLen;
    }

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

        System.out.println("Best Solution: " + currentSolution);
        System.out.println("Best Route Length: " + currentRouteLength);

        return currentRouteLength;
    }

    public static void main(String[] args) {
        hillClimbing();
    }
}
