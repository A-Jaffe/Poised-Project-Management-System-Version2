import java.io.*;
import java.util.*;

/**
 * The Person class is used to create and display Person objects.
 *<p></p>
 * This class contains attributes (position, name, email, telephone, physical address),
 * a constructor to create new Person objects and a toString method to display the object.
 * @author Aaron Jaffe
 */
public class Person {
    // Create attributes of Person objects
    String position;
    String name;
    String email;
    String telephone;
    String physAddress;
    static final String ERROR_MESSAGE = "An error has occurred.";

    /**
     * This is the Person class constructor, it contains the attributes
     * relevant to a Person object
     * @param position the person's role
     * @param name the person's name
     * @param email the person's email address
     * @param telephone the person's telephone number
     * @param physAddress the person's physical address
     */
    public Person(String position, String name, String email, String telephone, String physAddress) {
        this.position = position;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.physAddress = physAddress;
    }

    /**
     * The addContractor method takes in user input and creates
     * a new Contractor Person.
     * <p>
     * @return Person Object
     */
    public static Person addContractor() {
        // Request project Contractor details from user
        System.out.println("\nPlease enter the details for the Contractor on the project: ");
        String contPosition = "Contractor";

        System.out.print("Contractor name: ");
        String contName = ErrorChecks.stringCheck();

        System.out.print("Contractor email address: ");
        String contEmail = ErrorChecks.stringCheck();

        System.out.print("Contractor telephone number: ");
        String contTelephone = ErrorChecks.stringCheck();

        System.out.print("Contractor physical address: ");
        String contPhysAddress = ErrorChecks.stringCheck();

        // Create contractor Person object with user input
        return new Person(contPosition, contName, contEmail,
                contTelephone, contPhysAddress);
    }

    /**
     * The updateContractor method reads the Contractor's information
     * on a specific job from "CurrentProjects.txt".
     * <p></p>
     * It then allows the user to update the information of the Contractor
     * and re-writes it to the text file.
     */
    public static void updateContractor() {
        ArrayList<String> projects = new ArrayList<>();
        String[] projectInfo;

        try {
            File currentProjects = new File("CurrentProjects.txt");
            Scanner project = new Scanner(currentProjects);

            while (project.hasNextLine()) {
                projects.add(project.nextLine());
            }

            System.out.print("Please enter the number of the project you'd like to update the Contractor on: ");
            int projectChoice = ErrorChecks.intCheck();
            projectInfo = projects.get(projectChoice - 1).split(", ");

            if ((projectChoice) == Integer.parseInt(projectInfo[1])) {
                System.out.println(Arrays.toString(projectInfo));

                System.out.println("Please enter new details for the contractor on the project: ");
                System.out.print("New name: ");
                projectInfo[12] = ErrorChecks.stringCheck();

                System.out.print("New email address: ");
                projectInfo[13] = ErrorChecks.stringCheck();

                System.out.print("New telephone number: ");
                projectInfo[14] = ErrorChecks.stringCheck();

                System.out.print("New physical address: ");
                projectInfo[15] = ErrorChecks.stringCheck();

                System.out.println(Arrays.toString(projectInfo));
                String updateContractor = Arrays.toString(projectInfo).replace("[","")
                        .replace("]", "");
                projects.set((projectChoice - 1), updateContractor);
            }
            project.close();
        }
        catch (IOException e) {
            System.out.println(ERROR_MESSAGE);
        }

        // Write new contractor information to file
        try {
            Formatter projectUpdate = new Formatter("CurrentProjects.txt");
            for (String update : projects) {
                projectUpdate.format("%s", update + "\r\n");
            }

            projectUpdate.close();
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE);
        }
    }

    /**
     * The addArchitect method takes in user input and creates a new Architect Person.
     * <p>
     * @return Person Object
     *
     */
    public static Person addArchitect() {
        // Request project Architect details from user
        System.out.println("\nPlease enter the details for the Architect on the project: ");
        String archPosition = "Architect";

        System.out.print("Architect name: ");
        String archName = ErrorChecks.stringCheck();

        System.out.print("Architect email address: ");
        String archEmail = ErrorChecks.stringCheck();

        System.out.print("Architect telephone number: ");
        String archTelephone = ErrorChecks.stringCheck();

        System.out.print("Architect physical address: ");
        String archPhysAddress = ErrorChecks.stringCheck();

        // Create architect Person object with user input
        return new Person(archPosition, archName, archEmail,
                archTelephone, archPhysAddress);
    }

    /**
     * The addCustomer method takes in user input and creates a new Customer Person.
     * <p>
     * @return returns a Person Object
     */
    public static Person addCustomer() {
        // Request project Customer details from user
        System.out.println("\nPlease enter the details for the Customer on the project: ");
        String custPosition = "Customer";

        System.out.print("Customer name: ");
        String custName = ErrorChecks.stringCheck();

        System.out.print("Customer email address: ");
        String custEmail = ErrorChecks.stringCheck();

        System.out.print("Customer telephone number: ");
        String custTelephone = ErrorChecks.stringCheck();

        System.out.print("Customer physical address: ");
        String custPhysAddress = ErrorChecks.stringCheck();

        // Create customer Person object with user input
        return new Person(custPosition, custName, custEmail,
                custTelephone, custPhysAddress);
    }

    /**
     * The toString method displays the information of a Person.
     * <p>
     * @return returns a String
     */
    @Override
    public String toString() {
        String personDetails = "Position: " + position;
        personDetails += "\nName: " + name;
        personDetails += "\nEmail: " + email;
        personDetails += "\nTelephone: " + telephone;
        personDetails += "\nPhysical Address: " + physAddress;

        return personDetails;
    }
}