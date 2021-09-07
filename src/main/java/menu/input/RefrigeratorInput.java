package menu.input;

import model.HealthInspector;
import model.Refrigerator;


import java.util.InputMismatchException;
import java.util.Scanner;


public class RefrigeratorInput {

    Scanner scanner = new Scanner(System.in);
    Refrigerator refrigerator = new Refrigerator();

    public Refrigerator addInputValue(Integer userId) {
        try {
            System.out.println("Creating New Fridge... ");
            refrigerator.setRestaurantCode(userId);
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
        return refrigerator;
    }

    public Refrigerator removeInputValue() {
        boolean valid;
        String inputData;
        do {
            System.out.print("Please specify the fridge code number: \t");
            try {
                inputData = scanner.nextLine();
                if (Integer.parseInt(inputData) > 0) {
                    refrigerator.setCode_number(Integer.parseInt(inputData));
                    valid = true;
                } else {
                    valid = false;
                }
            } catch (Exception ex){
                System.out.println("Invalid Input!");
                valid = false;
            }
        } while(!valid);
        return refrigerator;
    }

}
