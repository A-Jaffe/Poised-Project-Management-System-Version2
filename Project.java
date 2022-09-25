import java.util.Scanner;
import java.io.*;

/**
 * The Project class creates Project objects and displays their information to the user.
 * <p></p>
 * This class contains attributes of a Project object, a constructor to create new objects,
 * an addProject method used to take input from the user about new Projects, a toString
 * method to display the project's information and a displayPersonOnProject method
 * to display the people associated with the project.
 * @author Aaron Jaffe
 */
public class Project {
    // Create attributes for Project objects
    String projName;
    int projNumber;
    String deadLine;
    String buildType;
    int erf;
    double totalFee;
    double paidSoFar;
    boolean finalised;
    Person architect;
    Person contractor;
    Person customer;

    /**
     * This is the Project class constructor, it contains attributes relevant to each
     * Project object.
     * <p></p>
     * It also contains a conditional statement to set projName
     * if one is not provided by the user.
     * @param projName the project's name
     * @param projNumber the project's number
     * @param deadline the project's deadline
     * @param buildType the kind of building
     * @param erf the property's erf number
     * @param totalFee the total cost of the project
     * @param paidSoFar the amount paid so far for the job
     * @param finalised whether a project is complete or not
     * @param architect the Architect for the project
     * @param contractor the Contractor for the project
     * @param customer the customer for the project
     */
    public Project(String projName, int projNumber, String deadline, String buildType,
                   int erf, double totalFee, double paidSoFar, boolean finalised,
                   Person architect, Person contractor, Person customer) {
        this.projName = projName;
        this.projNumber = projNumber;
        this.deadLine = deadline;
        this.buildType = buildType;
        this.erf = erf;
        this.totalFee = totalFee;
        this.paidSoFar = paidSoFar;
        this.finalised = finalised;
        this.architect = architect;
        this.contractor = contractor;
        this.customer = customer;

        // If no project name has been entered, create one using building type and customer name
        if (projName.equals("")) {
            this.projName = buildType + customer.name;
        }
    }

    /**
     * The addProject method takes input from the user and creates a new Project object.
     * @return returns a Project object
     */
    public static Project addProject() {
        System.out.print("Please enter a name for the project: ");
        Scanner input = new Scanner(System.in);
        String projName = input.nextLine(); // Use independent scanner to avoid stringCheck()

        System.out.print("Please enter the project number: ");
        int projNumber = ErrorChecks.intCheck();

        System.out.print("Please enter a deadline for the project (in the format YYYY-MM-DD): ");
        String deadLine = ErrorChecks.stringCheck();

        System.out.print("Please enter the type of building for project: ");
        String buildType = ErrorChecks.stringCheck();

        System.out.print("Please enter the ERF number of the building: ");
        int erf = ErrorChecks.intCheck();

        System.out.print("Please enter the total fee for the project: ");
        double totalFee = ErrorChecks.doubleCheck();

        System.out.print("Please enter the amount paid so far: ");
        double paidSoFar = ErrorChecks.doubleCheck();

        // Set project status to NOT finalised
        boolean finalised = false;

        // Call addArchitect method to add Architect to project
        Person architect = Person.addArchitect();

        // Call addContractor method to add Contractor to project
        Person contractor = Person.addContractor();

        // Call addCustomer method to add Customer to project
        Person customer = Person.addCustomer();

        // Create Project object with project information AND Person objects
        Project newProject = new Project(projName, projNumber, deadLine, buildType, erf, totalFee,
                paidSoFar, finalised, architect, contractor, customer);

        String projectInfo = (newProject.projName + ", " + projNumber + ", " + deadLine + ", " + buildType +
                ", " + erf + ", " + totalFee + ", " + paidSoFar + ", " + finalised + ", " +
                architect.name + ", " + architect.email + ", " + architect.telephone + ", " +
                architect.physAddress + ", " + contractor.name + ", " + contractor.email + ", " +
                contractor.telephone + ", " + contractor.physAddress + ", " + customer.name +
                ", " + customer.email + ", " + customer.telephone + ", " + customer.physAddress);

        try {
            BufferedWriter output = new BufferedWriter(
                    new FileWriter("CurrentProjects.txt", true));
            output.write(projectInfo + "\n");
            output.close();
        } catch (IOException e) {
            System.out.println("An error has occurred");
        }

        return newProject;
    }

    /**
     * The toString method displays the details of a Project.
     * @return returns a String
     */
    @Override
    public String toString() {
        System.out.println("\nPROJECT DETAILS: ");
        String projectDetails = "Project Name: " + projName;
        projectDetails += "\nProject Number: " + projNumber;
        projectDetails += "\nProject Deadline: " + deadLine;
        projectDetails += "\nBuilding Type: " + buildType;
        projectDetails += "\nERF Number: " + erf;
        projectDetails += "\nTotal Fee of project: R" + totalFee;
        projectDetails += "\nAmount Paid so far: R" + paidSoFar;

        return projectDetails;
    }

    /**
     * The displayPersonOnProject method displays the relevant Persons associated with a Project.
     * @return returns a String
     */
    public String displayPersonOnProject() {
        String peopleOnProject = "\nPEOPLE ON PROJECT: ";
        peopleOnProject += "\n" + architect.toString() + "\n\n" +
                contractor.toString()  + "\n\n" + customer.toString() ;

        return peopleOnProject;
    }
}