// package q5;


/**
 * Function longestHike(nums, k):
    Initialize longest to 0
    Initialize prev to the first element of nums
    Initialize maxLen to 0

    For i from 1 to length of nums - 1:
        Set current to nums[i]

        If current is greater than prev and (current - prev) is less than or equal to k:
            Increment longest by 1

        Update maxLen to the maximum of maxLen and (longest + 1)

        Set prev to current

    Return maxLen

 * @param args
 */
  

public class Hiking {

    // Function to find the longest hike within the elevation gain limit
    public static int longestHike(int[] nums, int k) {
        int longest = 0;   // Tracks the length of the current valid hike
        int prev = nums[0]; // Initialize the previous altitude as the first element
        int maxLen = 0;    // Tracks the maximum length of valid hikes found

        // Iterate over the trail starting from the second element
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i]; // Current altitude

            // Check if the current altitude is higher than the previous one and within the allowed gain limit
            if (current > prev && (current - prev <= k)) {
                longest++; // Increment the length of the current valid hike
            }
            System.out.println(longest + " THis is the lonest " + maxLen);
            // Update maxLen with the maximum value between current maxLen and (longest + 1)
            maxLen = Math.max(maxLen, longest + 1);

            prev = nums[i]; // Update prev to the current altitude for the next iteration
        }
        longest = 0;
        return maxLen; // Return the length of the longest valid hike
    }

    public static void main(String[] args) {
        int[] trail = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int k = 3;
        System.out.println(longestHike(trail, k));  // Expected Output: 5
    }
}
