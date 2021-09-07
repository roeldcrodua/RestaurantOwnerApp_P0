package menu.input;

import model.HealthInspector;
import java.util.InputMismatchException;
import java.util.Scanner;


public class HealthInspectorInput {

    Scanner scanner = new Scanner(System.in);
    HealthInspector healthInspector = new HealthInspector();

    public HealthInspector inputValue() {
        do{
            try {
                System.out.println("\n========================");
                System.out.println("Health Inspector Details Required");
                if(healthInspector.getUserName() == null || healthInspector.getUserName().equals("")){
                    System.out.print("Username: ");
                    healthInspector.setUserName(scanner.nextLine());
                }

                if(healthInspector.getPassword() == null || healthInspector.getPassword().equals("")){
                    System.out.print("Password: ");
                    healthInspector.setPassword(scanner.nextLine());
                }
                System.out.println("========================");
            } catch(InputMismatchException e){
                e.printStackTrace();
            }
        } while(isInValid(healthInspector));
        return healthInspector;
    }


    public boolean isInValid(HealthInspector input) {
        return input.getUserName() == null || input.getPassword() == null ||
                input.getUserName().equals("") || input.getPassword().equals("");
    }
}
