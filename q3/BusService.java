// package q3;

import java.util.*;


// Function reArrange(passenger: List of Integer, k: Integer)
//     // Calculate the number of full groups of size k
//     endIndex = Floor(size of passenger / k)

//     // Iterate through each group
//     for i from 0 to endIndex - 1 do
//         // Calculate the start and end indices of the current group
//         j = i * k
        
//         // Ensure that swapping only occurs within bounds
//         if j + k - 1 < size of passenger then
//             // Get the elements to swap
//             p1 = passenger[j]
//             p2 = passenger[j + k - 1]

//             // Swap the elements
//             passenger[j] = p2
//             passenger[j + k - 1] = p1

//             // Move to the next group
//             j = j + (k - 1)
//         end if
//     end for
// End Function

// Function main()
//     // Create a list of passengers
//     passengers = new ArrayList of Integer
//     list = [1, 2, 3, 4, 5]

//     // Add elements to the passengers list
//     for each element in list do
//         passengers.add(element)
//     end for

//     // Set the value of k
//     k = 2

//     // Rearrange the passengers list
//     reArrange(passengers, k)

//     // Print the rearranged list
//     for each element in passengers do
//         print element
//     end for
//     print newline
// End Function


public class BusService {

    

    public static void reArrange(List<Integer> passenger, int k) {
        int endIndex = (int) Math.floor(passenger.size() /  k); // Cast to double for accurate division
        for (int i = 0, j = 0; i< endIndex; i++, j++) {

            int p1 = passenger.get(j);
            int p2 = passenger.get((j+k)-1);


            passenger.set(j, p2);
            passenger.set((j+k)-1, p1);



            j= j + (k-1);
        }
        
    }


    public static void main(String[] args) {
        ArrayList<Integer> passengers = new ArrayList<>();
        int[] list = {1,2,3,4,5};
        for (int i =0; i<list.length; i++) {
            passengers.add(list[i]);
        }


        int k = 2;


        reArrange(passengers, k);

        for (int i : passengers) {
            System.out.print(i + " ");
        }
        System.out.println();

    }
}
