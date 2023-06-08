package st10326084;

class Login {

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