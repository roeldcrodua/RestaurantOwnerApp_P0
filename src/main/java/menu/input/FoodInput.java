package menu.input;

import model.Food;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FoodInput {

    Scanner scanner = new Scanner(System.in);
    Food food = new Food();

    public Food fridgeNumberInputValue() {
        boolean valid;
        String inputData;
        do {
            System.out.print("Select available fridge number: \t");
            try {
                inputData = scanner.nextLine();
                if (Integer.parseInt(inputData) > 0) {
                    food.setRefrigeratorId(Integer.parseInt(inputData));
                    valid = true;
                } else {
                    System.out.println("Invalid Input!");
                    valid = false;
                }
            } catch (Exception ex){
                valid = false;
            }
        } while(!valid);
        return food;
    }

    public Food foodNumberInputValue() {
        boolean valid;
        String inputData;
        do {
            System.out.print("Select food number: \t");
            try {
                inputData = scanner.nextLine();
                if (Integer.parseInt(inputData) > 0) {
                    food.setFoodId(Integer.parseInt(inputData));
                    valid = true;
                } else {
                    System.out.println("Invalid Input!");
                    valid = false;
                }
            } catch (Exception ex){
                valid = false;
            }
        } while(!valid);
        return food;
    }

    public Food foodNameInputValue() {
        System.out.print("Please enter food name: \t");
        do {
            try {
                food.setFoodName(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
            }
        } while( isInValid(food.getFoodName()));
        return food;


    }

    public boolean isInValid(String input) {
        return input.isEmpty() || input.equals(null);
    }
}
