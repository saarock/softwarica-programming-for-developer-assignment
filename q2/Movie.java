package q2;


// pheudo code
/*
 * define the value both indexDiff and valueDiff
 * calling the method isPossible which takes make argument arr, indexDiff, valueDiff
 * simply running two loop one is outer and anohter one is innter loop
 * at innter loop startiing form i + 1 and checking each time indexDiff j - i; 
 * after that checking the valueDiff arr[i] - arr[j] <= valueDiff ? true : false;

 */
public class Movie {

    public static boolean isPossible(int[] arr, int indexDiff, int valueDiff) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length && (j - i) <= indexDiff; j++) {
                if (Math.abs(arr[i] - arr[j]) <= valueDiff) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 5, 4, 9};
        int indexDiff = 2;
        int valueDiff = 1;
        System.out.println(isPossible(arr, indexDiff, valueDiff));
    }
}
