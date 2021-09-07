package menu.input;

import model.Restaurant;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RestaurantOwnerInput {

    Scanner scanner = new Scanner(System.in);
    Restaurant restaurant = new Restaurant();

    public Restaurant signUpInputValue() {
        do{
            try {
                System.out.println("\n========================");
                System.out.println("Sign Up Details Required");
                System.out.print("Username: ");
                restaurant.setUserName(scanner.nextLine().toLowerCase());
                System.out.print("Password: ");
                restaurant.setPassword(scanner.nextLine());
                System.out.print("Restaurant Name: ");
                restaurant.setRestaurantName(scanner.nextLine().toLowerCase());
                System.out.println("========================");
            } catch(InputMismatchException e){
                e.printStackTrace();
            }
        } while(isInValid(restaurant));
        return restaurant;
    }

    public Restaurant loginInputValue() {
        do{
            try {
                System.out.println("\n========================");
                System.out.println("Login Details Required ");
                if(restaurant.getUserName() == null || restaurant.getUserName().equals("")){
                    System.out.print("Username: ");
                    restaurant.setUserName(scanner.nextLine().toLowerCase());
                }

                if(restaurant.getPassword() == null || restaurant.getPassword().equals("")){
                    System.out.print("Password: ");
                    restaurant.setPassword(scanner.nextLine());
                }
                System.out.println("========================");
            } catch(InputMismatchException e){
                e.printStackTrace();
            }
        } while(isInValid(restaurant));
        return restaurant;
    }


    public boolean isInValid(Restaurant input) {
        return input.getUserName() == null || input.getPassword() == null ||
                 input.getUserName().equals("") || input.getPassword().equals("");
    }

}
