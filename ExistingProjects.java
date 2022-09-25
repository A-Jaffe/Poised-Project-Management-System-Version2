import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

/**
 * The ExistingProjects class contains methods used to manage existing projects
 * <p></p>
 * This class contains the methods updateProject, finaliseProject, viewIncomplete and
 * viewOverdue which are all used to manage existing projects.
 * @author Aaron Jaffe
 */
public class ExistingProjects {

    static final String ERROR_MESSAGE = "An error has occurred.";

    /**
     * The updateProject method requests the user to select a project by project number.
     * It then reads from the text file "CurrentProjects.txt" and allows the user to update
     * specific details of the project. It then writes the update to the text file.
     */
    public static void updateProject() {
        ArrayList<String> projectList = new ArrayList<>();
        String[] projectInfo;

        // Create file to read project information and create objects
        try {
            File currentProjects = new File("CurrentProjects.txt");
            Scanner project = new Scanner(currentProjects);

            while (project.hasNextLine()) {
                projectList.add(project.nextLine());
            }
            project.close();

            System.out.print("Please enter the number of the project you would like to edit: ");
            int projectChoice = ErrorChecks.intCheck();

            projectInfo = projectList.get(projectChoice - 1).split(", ");
            if (projectChoice == Integer.parseInt(projectInfo[1])) {
                System.out.println(Arrays.toString(projectInfo));

                System.out.print("Would you like to update the due-date (d) or the " +
                            "amount paid so far (a) of the project?");
                String editChoice = ErrorChecks.stringCheck();

                    // Request new deadline for the project from user
                if (editChoice.equals("d")) {
                    System.out.print("Please enter new deadline of the project (in the format YYYY-MM-DD): ");
                    projectInfo[2] = ErrorChecks.stringCheck();
                    String updateDeadline = Arrays.toString(projectInfo).replace("[","")
                            .replace("]", "");
                    projectList.set((projectChoice - 1), updateDeadline);
                }

                    // Request new amount paid so far from user
               else if (editChoice.equals("a")) {
                    System.out.print("Please enter amount paid to date for the project: ");
                    projectInfo[6] = ErrorChecks.stringCheck();
                    String updateAmount = Arrays.toString(projectInfo).replace("[","")
                            .replace("]", "");
                    projectList.set((projectChoice - 1), updateAmount);
                }
                System.out.println(projectList.get(projectChoice - 1));
            }
        } catch (FileNotFoundException e) {
            System.out.println(ERROR_MESSAGE);
        }

        try {
            Formatter projectUpdate = new Formatter("CurrentProjects.txt");
            for (String update : projectList) {
                projectUpdate.format("%s", update + "\r\n");
            }

            projectUpdate.close();
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE);
        }

    }

    /**
     * The finaliseProject method requests the user to select a project by project number.
     * It then reads from the text file "CurrentProjects.txt" and allows the user to generate
     * an invoice if there is still an amount outstanding for the project.
     * <p></p>
     * If the total fee has been paid, the project is marked as finalised and is written
     * to the text file "CompletedProjects.txt".
     */
    public static void finaliseProject() {
        ArrayList<String> projects = new ArrayList<>();
        String[] projectInfo;

        try {
            File currentProjects = new File("CurrentProjects.txt");
            Scanner project = new Scanner(currentProjects);

            while (project.hasNextLine()) {
                projects.add(project.nextLine());
            }
            project.close();

            System.out.print("Please enter the number of the project you'd like to finalise: ");
            int projectChoice = ErrorChecks.intCheck();
            projectInfo = projects.get(projectChoice - 1).split(", ");

            if (projectChoice == Integer.parseInt(projectInfo[1])) {
                System.out.println(Arrays.toString(projectInfo));

                double totalFee = Double.parseDouble(projectInfo[5]);
                double paidSoFar = Double.parseDouble(projectInfo[6]);

                // If there is an outstanding amount to be paid generate an invoice
                if ((totalFee - paidSoFar) > 0) {

                    // Display invoice with customer details outstanding balance
                    System.out.println("\nInvoice generated below: \n");
                    System.out.println("CUSTOMER INFORMATION: ");
                    System.out.println("Name: " + projectInfo[16]);
                    System.out.println("Email: " + projectInfo[17]);
                    System.out.println("Telephone: " + projectInfo[18]);
                    System.out.println("Physical Address: " + projectInfo[19]);

                    System.out.println("Outstanding balance: R" + (totalFee - paidSoFar));
                    System.out.println("\nOnce the total fee has been paid, the project can be marked as complete.");
                }

                // If the total fee has been paid in full.
                else {
                    // No invoice generated
                    System.out.println("The project has been paid in full, no invoice generated.");

                    // Add completed date and mark as finalised
                    System.out.println("Please enter a completion date (YYYY-MM-DD) for this project: ");
                    String completeDate = ErrorChecks.stringCheck();
                    String complete = "**FINALISED**";


                    // Write to file "CompletedProjects.txt" with complete date and mark finalised
                    try {
                        BufferedWriter completed = new BufferedWriter(
                                new FileWriter("CompletedProjects.txt", true));
                        completed.write(projectInfo[0] + ", " + projectInfo[1] + ", " +
                                projectInfo[2] + ", " + projectInfo[3] + ", " + projectInfo[4] +
                                ", " + projectInfo[5] + ", " + projectInfo[6] + ", " +
                                projectInfo[8] + ", " + projectInfo[9] + ", " + projectInfo[10] +
                                ", " + projectInfo[11] + ", " + projectInfo[12] + ", " +
                                projectInfo[13] + projectInfo[14] + ", " + projectInfo[15] + ", " +
                                projectInfo[16] + ", " + projectInfo[17] + ", " + projectInfo[18] +
                                ", " + projectInfo[19] + ", " + completeDate + ", " + complete + "\n");
                        completed.close();

                        // Remove project from list and re-write to CurrentProjects.txt
                        projects.remove((projectChoice - 1));
                        Formatter updateExisting = new Formatter("CurrentProjects.txt");
                        for (String rewrite : projects) {
                            updateExisting.format("%s", rewrite + "\r\n");
                        }
                        updateExisting.close();
                    }
                    catch (IOException e) {
                        System.out.println(ERROR_MESSAGE);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(ERROR_MESSAGE);
        }
    }

    /**
     * The viewIncomplete method reads all projects from the text file "CurrentProjects.txt"
     * and displays them to the user.
     */
    public static void viewIncomplete() {
        String[] projectInfo;

        try {
            File incomplete = new File("CurrentProjects.txt");
            Scanner project = new Scanner(incomplete);

            System.out.println("INCOMPLETE PROJECTS:\n");
            while (project.hasNextLine()) {
                projectInfo = project.nextLine().split(", ");
                System.out.println("Project Name: " + projectInfo[0]);
                System.out.println("Project Number: " + projectInfo[1]);
                System.out.println("Deadline: " + projectInfo[2]);
                System.out.println("Build Type: " + projectInfo[3]);
                System.out.println("ERF Number: " + projectInfo[4]);
                System.out.println("Total Fee: " + projectInfo[5]);
                System.out.println("Amount Paid: " + projectInfo[6] + "\n");

            }
            project.close();
        } catch (FileNotFoundException e) {
            System.out.println(ERROR_MESSAGE);
        }
    }

    /**
     * The viewComplete method reads all projects from the text file "CompletedProjects.txt"
     * and displays them to the user.
     */
    public static void viewComplete() {
        String[] projectInfo;

        try {
            File complete = new File("CompletedProjects.txt");
            Scanner project = new Scanner(complete);

            System.out.println("COMPLETE PROJECTS:\n");
            while (project.hasNextLine()) {
                projectInfo = project.nextLine().split(", ");
                System.out.println("Project Name: " + projectInfo[0]);
                System.out.println("Project Number: " + projectInfo[1]);
                System.out.println("Deadline: " + projectInfo[2]);
                System.out.println("Build Type: " + projectInfo[3]);
                System.out.println("ERF Number: " + projectInfo[4]);
                System.out.println("Total Fee: " + projectInfo[5]);
                System.out.println("Amount Paid: " + projectInfo[6]);
                System.out.println("Completed: " + projectInfo[19]);

            }
            project.close();

        } catch (FileNotFoundException e) {
            System.out.println("There are no completed projects yet.");
        }
    }
    /**
     * The viewOverdue method reads projects that ar passed their deadline from the text file
     * "CurrentProjects.txt" and displays them to the user.
     */
    public static void viewOverdue() {
        String[] projectInfo;

        String currentDate = String.valueOf(LocalDate.now());
        String[] dateSplit = currentDate.split("-");
        int yearNow = Integer.parseInt(dateSplit[0]);
        int monthNow = Integer.parseInt(dateSplit[1]);
        int dayNow = Integer.parseInt(dateSplit[2]);

        try {
            File currentProjects = new File("CurrentProjects.txt");
            Scanner project = new Scanner(currentProjects);

            System.out.println("OVERDUE PROJECTS:\n");
            while (project.hasNextLine()) {
                projectInfo = project.nextLine().split(", ");

                String[] deadLine = projectInfo[2].split("-");
                int yearDue = Integer.parseInt(deadLine[0]);
                int monthDue = Integer.parseInt(deadLine[1]);
                int dayDue = Integer.parseInt(deadLine[2]);

                // run overdue logic
                if (yearDue < yearNow) {
                    System.out.println("Project Name: " + projectInfo[0]);
                    System.out.println("Project Number: " + projectInfo[1]);
                    System.out.println("Deadline: " + projectInfo[2]);
                    System.out.println("Build Type: " + projectInfo[3]);
                    System.out.println("ERF Number: " + projectInfo[4]);
                    System.out.println("Total Fee: " + projectInfo[5]);
                    System.out.println("Amount Paid: " + projectInfo[6] + "\n");
                }

                else if ((yearDue == yearNow) && (monthDue < monthNow)) {
                    System.out.println("Project Name: " + projectInfo[0]);
                    System.out.println("Project Number: " + projectInfo[1]);
                    System.out.println("Deadline: " + projectInfo[2]);
                    System.out.println("Build Type: " + projectInfo[3]);
                    System.out.println("ERF Number: " + projectInfo[4]);
                    System.out.println("Total Fee: " + projectInfo[5]);
                    System.out.println("Amount Paid: " + projectInfo[6] + "\n");
                }

                else if (yearDue == yearNow && monthDue == monthNow && dayDue < dayNow) {
                    System.out.println("Project Name: " + projectInfo[0]);
                    System.out.println("Project Number: " + projectInfo[1]);
                    System.out.println("Deadline: " + projectInfo[2]);
                    System.out.println("Build Type: " + projectInfo[3]);
                    System.out.println("ERF Number: " + projectInfo[4]);
                    System.out.println("Total Fee: " + projectInfo[5]);
                    System.out.println("Amount Paid: " + projectInfo[6] + "\n");
                }
            }
            project.close();
        }

        catch (FileNotFoundException e) {
            System.out.println(ERROR_MESSAGE);
        }
    }
}
