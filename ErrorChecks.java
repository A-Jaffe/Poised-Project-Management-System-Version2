import java.util.Scanner;

/**
 * The ErrorChecks class runs input checks for all user input.
 * It contains three check methods for various data types.
 */
public class ErrorChecks {
    static final String ERROR_MESSAGE = "Incorrect entry, please try again: ";
    /**
     * The intCheck method takes in input from the user and
     * ensures that an integer is returned.
     * @return returns integer value
     */
    public static int intCheck() {
        while (true) {
            Scanner input = new Scanner(System.in);
            String integer = input.nextLine();

            try {
                return Integer.parseInt(integer);
            }
            catch (NumberFormatException e) {
                System.out.print(ERROR_MESSAGE);
            }
        }
    }

    /**
     * The stringCheck method takes in input from the user and
     * ensures that a string is returned.
     * @return returns string value
     */
    public static String stringCheck() {
        while (true) {
            Scanner input = new Scanner(System.in);
            String string = input.nextLine().toLowerCase();

            if ((string.equals("")) || (string.length() > 25)) {
                System.out.print(ERROR_MESSAGE);
            }
            else {
                return string;
            }
        }
    }

    /**
     * The doubleCheck method takes in input from the user and
     * ensures that a double is returned.
     * @return returns double value
     */
    public static double doubleCheck() {
        while (true) {
            Scanner input = new Scanner(System.in);
            String doubleInput = input.nextLine();

            try {
                return Double.parseDouble(doubleInput);
            }
            catch (NumberFormatException e) {
                System.out.print(ERROR_MESSAGE);
            }
        }
    }
}

