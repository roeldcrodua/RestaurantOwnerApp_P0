package menu.input;

import model.HealthInspector;
import model.Refrigerator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FridgeInspectorInput {

    Scanner scanner = new Scanner(System.in);
    Refrigerator refrigerator = new Refrigerator();
    HealthInspector inspector = new HealthInspector();

    public Refrigerator addFridgeInputValue(Integer userId) {
        boolean valid;
        String inputData;
        do {
            System.out.print("Enter Fridge Number: \t");
            try {
                inputData = scanner.nextLine();
                if (Integer.parseInt(inputData) > 0) {
                    refrigerator.setCode_number(Integer.parseInt(inputData));
                    refrigerator.setRestaurantCode(userId);
                    valid = true;
                } else {
                    System.out.println("Invalid Input!");
                    valid = false;
                }
            } catch (Exception ex){
                valid = false;
            }
        } while(!valid);
        return refrigerator;
    }

    public HealthInspector addInspectorInputValue() {
        boolean valid;
        String inputData;
        do {
            System.out.print("Enter Health Inspector Number: \t");
            try {
                inputData = scanner.nextLine();
                if (Integer.parseInt(inputData) > 0) {
                    inspector.setId(Integer.parseInt(inputData));
                    valid = true;
                } else {
                    valid = false;
                }
            } catch (Exception ex){
                System.out.println("Invalid Input!");
                valid = false;
            }
        } while(!valid);
        return inspector;

    }

}
