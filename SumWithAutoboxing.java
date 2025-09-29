import java.util.ArrayList;
import java.util.Scanner;

public class SumWithAutoboxing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter integers separated by spaces (or press Enter when done):");
        String line = scanner.nextLine().trim();

        // If user entered nothing, exit
        if (line.isEmpty()) {
            System.out.println("No numbers entered. Sum = 0");
            scanner.close();
            return;
        }

        String[] tokens = line.split("\\s+");
        ArrayList<Integer> numbers = new ArrayList<>();

        // Parse each token into an Integer (autoboxing will store primitive ints as Integer objects)
        for (String t : tokens) {
            try {
                int value = Integer.parseInt(t); // parse string to primitive int
                // Autoboxing: primitive int -> Integer when adding to ArrayList<Integer>
                numbers.add(value);
            } catch (NumberFormatException e) {
                System.out.printf("Skipping invalid integer token: '%s'%n", t);
            }
        }

        // Sum using enhanced for-loop (unboxing happens automatically: Integer -> int)
        int sum = 0;
        for (Integer num : numbers) { // num is Integer object; it's unboxed to int for arithmetic
            sum += num;               // unboxing happens here implicitly
        }

        System.out.println("Numbers parsed: " + numbers);
        System.out.println("Sum = " + sum);

        scanner.close();
    }
}
