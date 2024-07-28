


 // Pseudo code
// Function encode(input: String, shifts: 2D array of integers) -> String:
//     # Convert the input string to a character array for manipulation
//     chars = Convert input to character array

//     # Iterate over each shift operation
//     For each shift in shifts:
//         # Extract shift details
//         start_index = shift[0]
//         end_index = shift[1]
//         rotation_type = shift[2]

//         # Apply rotation based on type
//         If rotation_type is 0: # Counter-clockwise
//             For k from start_index to end_index (inclusive):
//                 # Decrease ASCII value by 1
//                 current_value = ASCII value of chars[k] - 1
//                 # Convert updated value back to character
//                 current_char = Character corresponding to current_value
//                 # Update character array
//                 chars[k] = current_char
//         Else: # Clockwise
//             For k from start_index to end_index (inclusive):
//                 # Increase ASCII value by 1
//                 current_value = ASCII value of chars[k] + 1
//                 # Convert updated value back to character
//                 current_char = Character corresponding to current_value
//                 # Update character array
//                 chars[k] = current_char

//     # Convert the modified character array back to a String
//     result_string = Convert chars back to String

//     Return result_string

// Function main():
//     # Define input string and shifts
//     input = "hello"
//     shifts = [[0, 1, 1], [2, 3, 0], [0, 2, 1]]

//     # Call encode function
//     result = encode(input, shifts)

//     # Print the result
//     Print result followed by " this is the answer"



public class Rotation {

    // Encodes the input based on shifts
    public static String encode(String input, int[][] shifts) {
        // Convert the input string to a char array for manipulation
        char[] chars = input.toCharArray();

        for (int i = 0; i < shifts.length; i++) {

            int clockOrAntiClock = shifts[i][2];

            // Apply rotation
            if (clockOrAntiClock == 0) {
                // Counter-clockwise
                for (int k = shifts[i][0]; k<=shifts[i][1]; k++) {
                    if (chars[k] == 'a') {
                        chars[k] = 'z';
                        continue;
                    }
                    int current = chars[k] - 1;
                    char currentChar = (char) current;                    
                    chars[k] = currentChar;
                }

            } else {
                // Clockwise
                for (int k = shifts[i][0]; k<=shifts[i][1]; k++) {
                    if (chars[k] == 'z') {
                        chars[k] = 'a';
                        continue;
                    }
                    int current = chars[k] + 1;
                    char currentChar = (char) current;
                    chars[k] = currentChar;
                }

            }
        }

        // Convert the char array back to a String
        return new String(chars);
    }

    public static void main(String[] args) {
        String input = "hello";
        int[][] shifts = {{0, 1, 1}, {2, 3, 0}, {0, 2, 1}};

        // Encode the input string with the given shifts
        String result = encode(input, shifts);

        // Output the result
        System.out.println(result + " this is the answer");
    }
}
