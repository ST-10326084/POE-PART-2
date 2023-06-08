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
