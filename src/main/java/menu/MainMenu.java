package menu;


import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
    }

    public static void displayWelcome(){
        System.out.println("+====================================================+");
        System.out.println("| WELCOME TO PROJECT 0: RESTAURANT OWNER APPLICATION |");
        System.out.println("+===================================================+");
    }


    public static void displayUserMenu(){
        System.out.println(
                "\nChoose account type\n" +
                "=====================================================================================\n" +
                "[1]  Restaurant Owner\t\t" +
                "[2]  Health Inspector\t\t" +
                "[3]  Restart\t\t" +
                "[0]  Exit\n" +
                "======================================================================================");
    }


    public static void displayLoginSignUp(Integer accountType){
        System.out.println(
                "\nChoose an option to proceed as " + (accountType == 1 ? "RESTAURANT OWNER" : "HEALTH INSPECTOR") +
                "\n========================================================================================\n" +
                "[1] Create New Account\t\t" +
                "[2] Login to Existing Account\t\t" +
                "[3] Go Back\t\t" +
                "[0] Exit\n" +
                "========================================================================================");
    }


    public static void displayMainMenuOptions(Integer userAccountType, Integer userId){
        if ( userAccountType == 1) {
            System.out.println(
                    "\n=====================================\n" +
                    "RESTAURANT OWNER MENU OPTION  " + "\tID: " + userId +
                    "\n=====================================\n\t" +
                    "[1] List All Fridges\n\t" +
                    "[2] Add New Fridge\n\t" +
                    "[3] Remove a Fridge\n\t" +
                    "[4] List All Foods\n\t" +
                    "[5] Deposit a Food to a Fridge\n\t" +
                    "[6] Remove a Food from a Fridge\n\t" +
                    "[7] Transfer Food to Another Fridge\n\t" +
                    "[8] Assign Fridge's Health Inspector\n\t" +
                    "[9] Remove Inspector Assigned in a Fridge\n\t" +
                    "[10] Logout\n\t" +
                    "[0] Exit\n" +
                    "=====================================" );
        }
        else if ( userAccountType == 2){
            System.out.println(
                    "\n=====================================\n" +
                    "HEALTH INSPECTOR MENU OPTION" + "\tID: " + userId +
                    "\n=====================================\n\t" +
                    "[1] List of Assign Fridges\n\t" +
                    "[2] Remove Food Item In The Fridge\n\t" +
                    "[3] Logout\n\t" +
                    "[0] Exit\n" +
                    "=====================================" );
        }
        else {
            System.out.println("Invalid option! Please try again!\n");
        }
    }
}
