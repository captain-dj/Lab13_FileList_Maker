import java.util.Scanner;

public class SafeInput {
    private static Scanner scanner = new Scanner(System.in);

    public static String getRegexString(String regex, String prompt) {
        String input;
        boolean isValid;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            isValid = input.matches(regex);
            if (!isValid) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!isValid);
        return input;
    }

    public static int getRangedInt(int min, int max, String prompt) {
        int input;
        boolean isValid;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            input = scanner.nextInt();
            isValid = input >= min && input <= max;
            if (!isValid) {
                System.out.println("Input out of range. Please enter a number between " + min + " and " + max + ".");
            }
        } while (!isValid);
        scanner.nextLine(); // Clear the buffer
        return input;
    }

    public static boolean getYNConfirm(String prompt) {
        String input;
        boolean isValid;
        do {
            System.out.print(prompt + " (Y/N): ");
            input = scanner.nextLine().trim().toLowerCase();
            isValid = input.equals("y") || input.equals("n");
            if (!isValid) {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        } while (!isValid);
        return input.equals("y");
    }
}
