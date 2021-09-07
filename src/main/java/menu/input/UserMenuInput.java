package menu.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserMenuInput {

    Scanner scanner = new Scanner(System.in);

    public Integer inputValue() {

        String input = "-1";
        int accountType = -1;

        do {
            System.out.print("Please enter valid option: \t");
            try {
                input = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        } while(isInValid(input, accountType));
        return Integer.parseInt(input);
    }

    public Integer inputValue(Integer accountType) {
        String input = "-1";
        do {
            System.out.print("Please enter valid option: \t");
            try {
                input = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!.");
            }
        } while(isInValid(input, accountType));
        return Integer.parseInt(input);
    }


    public boolean isInValid(String input, Integer accountType) {
        if (accountType == 1) {
            switch (input) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10": return false;
                case "0": exit();
                default: return true;
            }
        }
        else if ( accountType == 2){
            switch (input) {
                case "1":
                case "2":
                case "3": return false;
                case "0": exit();
                default: return true;
            }
        }
        else {
            switch (input) {
                case "1":
                case "2":
                case "3": return false;
                case "0": exit();
                default: return true;
            }
        }
    }

    protected void exit(){
        System.exit(0);
    }
}
