package st10326084;
import java.util.Scanner;
import javax.swing.JOptionPane;
// This is the combined code from POE PART 1, AND PART 2. 
// This is version 13 
// Dean Gibson
// I have included references at the bottom of this document indicating websites, and youtube videos i used to complete this project

public class ST10326084 {
    // main method has been reduced to store only registerAndLogin() method to allow user to signup or sign in
    // and the runTaskManagement() method to start part 2, which contains the menu's for the user to start with
    // the taskmanagement portion of the program
    public static void main(String[] args) {
        String[] nameAndSurname = registerAndLogin();
        String name = nameAndSurname[0];
        String surname = nameAndSurname[1];
        Task.runTaskManagement(name, surname);
    }
    
    // this method is used to compile most of the work from poe part 1, into a separate method so that working on part 2 is much easier
    public static String[] registerAndLogin() {
        Scanner input = new Scanner(System.in);
        System.out.println("\tWelcome To Dean's Trusty Login System!");

        boolean isLoggedIn = false;
        String name;
        String surname;
        int userRegister = 0;
        //accepts anything in name and surname String to ensure it's progressive 
        System.out.print("\tEnter Name: ");
        name = input.next();
        System.out.print("\tEnter Surname :");
        surname = input.next();

        while (!isLoggedIn) {
            System.out.println("\nEnter 1 to register a new account, or 2 to login:");
            int decision = input.nextInt();
            input.nextLine();

            if (decision == 1) {
                userRegister++;
                System.out.print("\nEnter a username (5 or fewer characters, containing an underscore):");
                String username = input.next();

                System.out.print("Enter a password (8 or more characters, containing a digit, capital letter, and special character):");
                String password = input.next();

                Login newUser = new Login(username, password);
                String registrationStatus = newUser.registerUser();

                if (registrationStatus.contains("successful")) {
                    System.out.println("\n" + registrationStatus);
                } else {
                    System.out.println("\nRegistration failed. " + registrationStatus);
                }
            } else if (decision == 2 && userRegister >= 1) {
                System.out.println("Enter your username:");
                String username = input.nextLine();

                System.out.println("Enter your password:");
                String password = input.nextLine();

                Login existingUser = new Login(username, password);
                boolean loginStatus = existingUser.loginUser();

                if (loginStatus) {
                    isLoggedIn = true;
                    System.out.println(existingUser.returnLoginStatus(true));
                } else {
                    System.out.println(existingUser.returnLoginStatus(false));
                }
            } else {
                System.out.println("You have not registered or you have entered an Invalid input. Please try again.");
            }
        }
        // welcome message 
        System.out.println("Welcome " + name + " " + surname + ", it is great to see you again!");
        return new String[]{name, surname};
    }

//class called Login for good coding standards are met
    public static class Login {

        private String username;
        private String password;

        //this used as the parameters and then original values share the same name. Instance variables
        public Login(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
    //cycles through the username until it finds a underscore     
    public boolean checkUserName() {
    boolean containsUnderscore = false;
    for (int i = 0; i < username.length(); i++) {
        if (username.charAt(i) == '_') {
            containsUnderscore = true;
           }
    }
    return containsUnderscore && username.length() <= 5;
}
    //password character checker
    //cycles through password and ensures a upper case is found
    //a digit is found, and a special character from that range is found
    //aswell as the password being equal or larger than 8 characters long
    public boolean checkPasswordComplexity() {
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if ("!@#$%^&*()".indexOf(c) != -1) { //!= means not equal 
                hasSpecialCharacter = true;
            }
        }
        return password.length() >= 8 && hasUpperCase && hasNumber && hasSpecialCharacter;
    }     
        //register user method checks if the username has been entered correclty and outputs the neccessary statement.
        //aswell as the password is correct
        //if both are correct then "Registration successful!" is returned to be used elsewhere
        public String registerUser() {
            if (!checkUserName()) {
                return "Invalid username format. Must contain an underscore (_) and be no more than 5 characters.";
            } else if (!checkPasswordComplexity()) {
                return "Invalid password format. Must be at least 8 characters long and contain a capital letter, a number, and a special character.";
            } else {
                return "Registration successful!";
            }
        }
        //This method is used to check if a user can successfully login by verifying that the provided username and password match the username and password stored in the Login object.
        //It first compares the username entered by the user with the username stored in the Login object
        //using the equals method of the String class. 
        //indicates true and false based on if the username and password match
        public boolean loginUser() { return this.username.equals(username) && this.password.equals(password);}
        
            //passes the variable succesful and uses it to return a true and false statement based on if the login was sucesful 
        public String returnLoginStatus(boolean successful) {
            if (successful) {
                return "Login successful!";
            } else {
                return "Login failed. Please check your username and password and try again.";
                }
        }
    }
}
// This is where part 2 mainly takes place
// a new class was created and called Task, this holds all the backend for the part 2 of this program.
class Task {
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
// application runs until user selects quit
// user recieves options on where to go in the program
// when a user selects add tasks they are able to enter the required information
// full details are available after they have been entered by choosing option 2 and viewing the report
// the total number of hours of tasks is added together and displayed when choosing option 2
// the code has been written into different methods including the 4 compulsory methods namely: checkTaskDescription(), createTaskID(), printTaskDetails(), and returnTotalHours(), however the total hours method has been made a float to display the hrs more precisly
// i have created unit tests to test all the different possible suggestion given

// this is the end of the program
//poe part 2 
//ST10326084

// Website link: https://www.w3schools.com/java/java_methods.asp [accessed 08/06/2023]
// Youtube video: https://www.youtube.com/watch?v=xk4_1vDrzzo&t=6486s  [accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_booleans.asp[accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_math.asp [accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_switch.asp [accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_methods_param.asp [accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_arrays.asp [accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_for_loop.asp [accessed 08/06/2023]
// Website link: https://www.w3schools.com/java/java_break.asp [accessed 08/06/2023]
// Youtube video: https://www.youtube.com/watch?v=K1DPBpljXog [accessed 08/06/2023]
// Youtube video: https://www.youtube.com/watch?v=vZm0lHciFsQ [accessed 08/06/2023]
// Website Link: https://hackr.io/blog/best-java-ides [accessed 08/06/2023]
// Website Link: https://www.techtarget.com/searchsoftwarequality/definition/unit-testing#:~:text=Unit%20testing%20is%20a%20software,tests%20during%20the%20development%20process. [accessed 08/06/2023]
// Website Link: https://www.geeksforgeeks.org/unit-testing-software-testing/ [accessed 08/06/2023]
