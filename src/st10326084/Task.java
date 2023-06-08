
package st10326084;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Dean
 */
public class Task {
    // This is where part 2 mainly takes place
    // a new class was created and called Task, this holds all the backend for the part 2 of this program.

    //declarations ( declared here so that they are useable anywhere inside this class
    private static String[] taskdescripArray;
    private static String[] taskname;
    public static int tasks = 0;
    private static String[] menuChoices = new String[tasks];
    private static float[] taskduration = new float[tasks];
    public static Scanner input = new Scanner(System.in);
    public static int numTasks = 0;
    public static int totalHours;
    public static String name;
    public static String surname;
    
    // this method conatins the groundwork for part 2, it launches the system and allows the user..
    // to choose what section of the program they want to access
    public static void runTaskManagement(String name, String surname) {
        int choice;
        do {
            choice = Task.Menu();
            System.out.println("\t\t\nLoading System: " + choice);

            if (choice == 1) {
                Task.option1(name, surname);
            } else if (choice == 2) {
                Task.option2(name, surname);
            } else {
                System.out.println("Exiting System...");
            }

        } while (choice != 3);
    }
    
    // menu to choose what the user wants to do
    public static int Menu() {
        int choice = 0;
        // try & catch to ensure no user errors
        try {
            
            System.out.println("\t\tMenu:");
            System.out.println("\n============================================================");
            System.out.println("\t\t1. Option 1 - Add Tasks");
            System.out.println("\t\t2. Option 2 - Show Report");
            System.out.println("\t\t3. Option 3 - Exit ");
            System.out.println("\n============================================================");
            System.out.print("\t\tEnter your choice: ");

            // Read the user's choice
            choice = input.nextInt();
            input.nextLine(); // Consume the newline character in the buffer

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid option. Enter a number from 1-3 to proceed.");
            }
        } catch (Exception e) {
            System.out.println("Invalid option. Enter a number from 1-3 to proceed.");
            input.nextLine(); // Clear the input buffer
            choice = 0; // Set an invalid choice to repeat the loop
        }
        return choice;
    }
    
// option 1 refers the menu() method. This method is repsonsible for handling the creation of tasks, and all the extra details.
    public static void option1(String name, String surname) {
        System.out.println("How many tasks do you want to add?");
        tasks = input.nextInt();
        input.nextLine(); // Consume the newline character in the buffer
        
        taskdescripArray = new String[tasks];
        taskname = new String[tasks];
        menuChoices = new String[tasks];
        taskduration = new float[tasks]; // Add this line to initialize the taskduration array

        for (int i = 0; i < tasks; i++) {
            System.out.println("Option 1 - Add Tasks");
            System.out.println("Enter the task name");
            taskname[i] = input.nextLine();
            
            while (true) {
                System.out.println("Enter the task's Description (50 or fewer characters)");
                String taskdescrip = input.nextLine();
                if (checkTaskDescription(taskdescrip)) {
                    taskdescripArray[i] = taskdescrip;
                    break; // Exit the loop when a valid description is entered
                } else {
                    System.out.println("Invalid task description. Please try again.");
                }
            }

            // Ask for task duration
            taskduration[i] = returnTotalHours(); //i is a int cannot be a int[]
            totalHours += taskduration[i];
            menuChoices[i] = taskStatus(); // Ask for task status for each task
            numTasks++;
        }
    }
    
    // option 2 is for prepping the data to be displayed. Ensures all data members are included in one string for easy display.
    public static void option2(String name, String surname) {
        System.out.println("Option 2 - Show Report");
        System.out.println("This feature is: COMING SOON (however it currently displays all added tasks)");
        if (numTasks > 0) {
            for (int i = 0; i < numTasks; i++) {
                String taskID = Task.createTaskID(taskname[i], i , surname);
                String output = "\nTask Status: " + menuChoices[i] + "\n";
                output += "Developer Details: " +name+" "+surname+ "\n";
                output += "Task Number: " + i + "\n";
                output += "Task Name: " + taskname[i] + "\n";
                output += "Task Description: " + taskdescripArray[i] + "\n";
                output += "Task ID: " +taskID+ "\n";
                output += "Task Duration: " + taskduration[i] + " hours\n"; 
                output += "\n";
                output += "Total number of hours (rounded)" + totalHours + "\n"; //totalHours value needs to be pulled from option1()
                printTaskDetails(output);
            }
        } else {
            System.out.println("No tasks added yet.");
        }
    }
    
    // this method ensures the description of each task in not longer than 50 char's, including spaces.
    public static boolean checkTaskDescription(String taskdescrip) {
        return taskdescrip.length() <= 50;
    }
    
    // this method uses JOptionPane to display the strings made in option2()
    public static String printTaskDetails(String output) {
        //System.out.println(output);
        JOptionPane.showMessageDialog(null, output, "Task Details", JOptionPane.INFORMATION_MESSAGE);
        return output;
    }
    
    // this method gathers the duration of each task and rounds it up.
    public static float returnTotalHours() {
    boolean validInput = false;
    float fduration = 0;
    
    while (!validInput) {
        System.out.print("Enter the length of the task in hours (Example 1.5 = 1 and a half hours): ");
        if (input.hasNextFloat()) {
            fduration = input.nextFloat();
            validInput = true;
        } else {
            System.out.println("Invalid input. Please enter a float or an integer.");
            input.nextLine(); // Clear the input buffer
        }
    }
    input.nextLine(); // Consume the newline character
    return fduration;
}

    // this method creates the TaskID
    public static String createTaskID(String taskname, int tasksnumber, String surname) {
        if (taskname.isEmpty()) {
            return "";
        }
        String firstletters = taskname.substring(0, Math.min(2, taskname.length())).toUpperCase();
        String lastThreeDevName = surname.length() >= 3 ? surname.substring(surname.length() - 3).toUpperCase() : surname.toUpperCase();
        return firstletters + ":" + tasksnumber + ":" + lastThreeDevName;
    }
    
    // this method allows the user to choose what status each task is in
    public static String taskStatus() {
        String decision = "";
        System.out.println("\nTask Status Menu");
        System.out.println("Enter (1) to add this task to: TO DO");
        System.out.println("Enter (2) to add this task to: DOING");
        System.out.println("Enter (3) to add this task to: DONE");

        while (true) {
            if (input.hasNextInt()) {
                int menuChoice = input.nextInt();
                input.nextLine(); // Consume the newline character
                if (menuChoice == 1) {
                    decision = "TO DO";
                    break;
                } else if (menuChoice == 2) {
                    decision = "DOING";
                    break;
                } else if (menuChoice == 3) {
                    decision = "DONE";
                    break;
                } else {
                    System.out.println("Invalid option. Enter a number from 1-3.");
                }
            } else {
                System.out.println("Invalid input. Enter a number from 1-3.");
                input.nextLine(); // Consume the invalid input
            }
        }
        return decision;
    }
}

