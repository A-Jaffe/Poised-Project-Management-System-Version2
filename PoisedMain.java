import java.util.*;
import java.io.*;

/**
 * The PoisedMain class runs the main program method and calls methods from
 * the Project, Person, ErrorChecks and ExistingProjects classes based on
 * the menu selection of the user. It provides the user with a main menu of
 * project management tasks.
 * <p></p>
 * @author Aaron Jaffe
 */
public class PoisedMain {

    static final String ERROR_MESSAGE = "An error has occurred.";

    /**
     * This is the main method, it provides the user with a menu.
     * <p></p>
     * @param args runs the main method
     */
    public static void main(String[] args) {
        System.out.println("\nWelcome to the Poised project manager!\n");

        try {
            File newFile = new File("CurrentProjects.txt");

            if (newFile.createNewFile()) {
                System.out.println("File: " + newFile.getName() + " has been created.");
            } else {
                System.out.println("CurrentProjects.txt already exists.");
            }
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE);
        }

        while (true) {
            // Request menu choice input from user
            System.out.println("""
                    \nWould you like to:
                    1) Add a new project
                    2) Update the details of a project
                    3) Update a contractors details
                    4) Finalise a project
                    5) View Incomplete Projects
                    6) View Completed Projects
                    7) View Overdue projects
                    8) Log Out""");
            int menuChoice = ErrorChecks.intCheck();
            boolean existingProjects = fileCheck();

            if ((!existingProjects) && ((menuChoice == 2) || (menuChoice == 3) ||
                    (menuChoice == 4) || (menuChoice == 5) || (menuChoice == 6) ||
                    (menuChoice == 7))) {
                System.out.println("There are no existing projects, please choose menu option 1 " +
                        "to add a project first");
            }

            else if (menuChoice == 1) {
                Project newProject = Project.addProject();

                // Display new project has been created message and all project details
                System.out.println("\nNew project has been created!");
                System.out.println(newProject);
                System.out.println(newProject.displayPersonOnProject());
            }

            // updateProject(), request type of project edit and update Project object details
            else if ((existingProjects) && (menuChoice == 2)) {
                ExistingProjects.updateProject();
            }

            // updateContractor(), request new Contractor details and update Project object
            else if ((existingProjects) && (menuChoice == 3)) {
                Person.updateContractor();
            }

            // Menu choice 3, finalise the project
            else if ((existingProjects) && (menuChoice == 4)) {
               ExistingProjects.finaliseProject();
            }

            // Menu choice 4, log, out, exit loop
            else if ((existingProjects) && (menuChoice == 5)) {
                ExistingProjects.viewIncomplete();
            }

            else if ((existingProjects) && (menuChoice == 6)) {
                ExistingProjects.viewComplete();
            }

            else if ((existingProjects) && (menuChoice == 7)) {
                ExistingProjects.viewOverdue();
            }

            else if (menuChoice == 8) {
                System.out.println("You have successfully logged out.");
                break;
            }

            // menuChoice error catcher and loop re-starter
            else {
                System.out.println("You have entered and invalid option, please try again.");
            }
        }
    }

    /**
     * @return returns a boolean for whether there are existing projects
     * in "CurrentProjects.txt" or not.
     */
    public static boolean fileCheck() {
        boolean existingCheck = false;

        try {
            File currentProjects = new File("CurrentProjects.txt");
            Scanner input = new Scanner(currentProjects);

            existingCheck = input.hasNextLine();
            input.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(ERROR_MESSAGE);
        }
        return existingCheck;
        }
}