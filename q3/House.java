package q3;

/**
 * INITIALIZE numberOfHouses to 5
CREATE array parent of size numberOfHouses
CREATE array approvalOrDisApporableMessage
CREATE array rank of size numberOfHouses

FUNCTION initialize:
    FOR i from 0 to numberOfHouses - 1:
        SET parent[i] to i

FUNCTION find(x):
    IF parent[x] == x:
        RETURN x
    ELSE:
        SET parent[x] to find(parent[x])
        RETURN parent[x]

FUNCTION union(x, y):
    SET rootX to find(x)
    SET rootY to find(y)
    IF rootX == rootY:
        RETURN
    IF rank[rootX] < rank[rootY]:
        SET parent[rootX] to rootY
    ELSE IF rank[rootX] > rank[rootY]:
        SET parent[rootY] to rootX
    ELSE:
        SET parent[rootX] to rootY
        INCREMENT rank[rootY] by 1

FUNCTION isRes(res, x, y):
    FOR EACH restriction in res:
        SET restrictedA to find(restriction[0])
        SET restrictedB to find(restriction[1])
        IF (x == restrictedA AND y == restrictedB) OR (x == restrictedB AND y == restrictedA):
            RETURN true
    RETURN false

FUNCTION approvedFriendRequest(restrictions, requests):
    SET approvalOrDisApporableMessage to new array of size requests.length
    FOR i from 0 to requests.length - 1:
        SET x to find(requests[i][0])
        SET y to find(requests[i][1])
        IF x != y AND NOT isRes(restrictions, x, y):
            CALL union(x, y)
            SET approvalOrDisApporableMessage[i] to "approved"
        ELSE:
            SET approvalOrDisApporableMessage[i] to "denied"

FUNCTION main:
    INITIALIZE restrictions with values {{0, 1}, {1, 2}, {2, 3}}
    INITIALIZE requests with values {{0, 4}, {1, 2}, {3, 1}, {3, 4}}
    CALL initialize
    CALL approvedFriendRequest(restrictions, requests)
    FOR EACH message in approvalOrDisApporableMessage:
        PRINT message

 */
public class House {

    public static int numberOfHouses = 5;
    public static int[] parent = new int[numberOfHouses];
    public static String[] approvalOrDisApporableMessage;
    // rank for union by rank
    public static int[] rank = new int[numberOfHouses];

    public House() {
        for (int i = 0; i < numberOfHouses; i++) {
            parent[i] = i;
        }
    }

    public static int find(int x) {
        if (parent[x] == x) return x;
        // path compression for better time complexity
        return parent[x] = find(parent[x]);
    }


    public static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;
        // doing union by rank for better time complexity
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootX] = rootY;
            rank[rootY] = rank[rootY] + 1;
        }
    }


    // this method help's to check directly or indirectly connection by the find method;
    public static boolean isRes(int[][] res, int x, int y) {
        for (int[] restriction : res) {
            int restrictedA = find(restriction[0]);
            int restrictedB = find(restriction[1]);
            if ((x == restrictedA && y == restrictedB) || (x == restrictedB && y == restrictedA)) {
                return true;
            }
        }
        return false;
    }


    public static void approvedFriendRequest(int[][] restrictions, int[][] requests) {
        approvalOrDisApporableMessage = new String[requests.length];
        for (int i = 0; i < requests.length; i++) {
            int x = find(requests[i][0]);
            int y = find(requests[i][1]);
            if (x != y && !isRes(restrictions, x, y)) {
                union(x, y);
                approvalOrDisApporableMessage[i] = "approved";
            } else {
                approvalOrDisApporableMessage[i] = "denied";
            }
        }
    }


    public static void main(String[] args) {
        int[][] restrictions = {{0, 1}, {1,2}, {2,3}};
        int[][] requests = {{0,4}, {1, 2}, {3, 1}, {3,4}};

        new House();  
        approvedFriendRequest(restrictions, requests);

        for (String message : approvalOrDisApporableMessage) {
            System.out.println(message);
        }
    }
}
