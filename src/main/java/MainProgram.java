import menu.MainMenu;
import menu.input.*;
import model.Food;
import model.HealthInspector;
import model.Refrigerator;
import model.Restaurant;
import service.ListService;

import java.util.*;

public class MainProgram {

    Scanner scanner = new Scanner(System.in);
    ListService listService = new ListService();

    public void runProgram(){ //MainDriver
        UserMenuInput userMenuInput = new UserMenuInput();

        Integer accountType = -1; // Set the account type if 1 = restaurant, 2 = inspector
        Integer accessType = -1; // Set the value of 1 or 2 for signup or login.
        int userId = -1; // Set the user_id of restaurant DB and inspector_id of inspector DB
        boolean runningProgram;

        do {
            runningProgram = true;
            MainMenu.displayUserMenu();
            // Ask the user for what type of account (restaurant or inspector)
            accountType = userMenuInput.inputValue(); //validated through UserMenuInput
            switch(accountType) {
                case 1:  // RESTAURANT
                    boolean runningLoginRestaurant;
                    do {
                        runningLoginRestaurant = true;
                        MainMenu.displayLoginSignUp(accountType);
                        // Ask the user to Login or to Signup
                        accessType = userMenuInput.inputValue();
                        switch (accessType) {
                            case 1:
                                userId = signUpAsRestaurant().getId();
                                if (userId > 0) {
                                    runMainMenu(accountType, userId);
                                }
                                break;
                            case 2:
                                userId = loginAsRestaurant().getId();
                                if (userId > 0) {
                                    runMainMenu(accountType, userId);
                                }
                                break;
                            case 3:
                                runningLoginRestaurant = false;
                                break;
                            case 0:
                                exit();
                                break;
                        }
                    } while (runningLoginRestaurant);
                    break;
                case 2: // INSPECTOR
                    boolean runningLoginInspector;
                    do {
                        runningLoginInspector = true;
                        MainMenu.displayLoginSignUp(accountType);
                        // Ask the user to Login or to Signup
                        accessType = userMenuInput.inputValue();
                        switch (accessType) {
                            case 1:
                                userId = signUpAsInspector().getId();
                                if (userId > 0) {
                                    runMainMenu(accountType, userId);
                                }
                                break;
                            case 2:
                                userId = loginAsInspector().getId();
                                if (userId > 0) {
                                    runMainMenu(accountType, userId);
                                }
                                break;
                            case 3:
                                runningLoginInspector = false;
                                break;
                            case 0:
                                exit();
                                break;
                        }
                    } while (runningLoginInspector);
                    break;
                case 3:
                    runProgram();
                    break;
                case 0:
                    runningProgram = false;
                    exit();
                    break;
            }
        } while (runningProgram);
        System.out.println("Thank you! See you again..");
    }

    // RESTAURANT OWNER

    public void runMainMenu(Integer accountType, Integer userId){
        UserMenuInput userMenuInput = new UserMenuInput();
        RestaurantOwnerInput restaurantInput = new RestaurantOwnerInput();
        HealthInspectorInput healthInspectorInput = new HealthInspectorInput();
        FoodInput foodInput = new FoodInput();

        MainMenu.displayMainMenuOptions(accountType, userId);
        if (accountType == 1) {
            boolean runningRestaurant;
            do {
                runningRestaurant = true;
                switch (userMenuInput.inputValue(accountType)) {
                    case 1:
                        listAllFridges(userId, accountType); // Added userId for Dao sql statement purposes
                        runningRestaurant = true;
                        break;
                    case 2:
                        addNewFridge(userId, accountType);
                        runningRestaurant = true;
                        break;
                    case 3:
                        removeFridge(userId, accountType);
                        runningRestaurant = true;
                        break;
                    case 4:
                        listAllFoods(userId);
                        runningRestaurant = true;
                        break;
                    case 5:
                        depositFoodIntoFridges(userId, accountType);
                        runningRestaurant = true;
                        break;
                    case 6:
                        withdrawFoodFromFridges(userId, accountType); //added userId to display only the food for the restaurant in a specific fridge after withdrawal of food.
                        runningRestaurant = true;
                        break;
                    case 7:
                        transferFoodToOtherFridge(userId, accountType);
                        runningRestaurant = true;
                        break;
                    case 8:
                        assignHealthInspector(userId, accountType);
                        runningRestaurant = true;
                        break;
                    case 9:
                        removeInspectorAssignedInAFridges(userId, accountType);
                        runningRestaurant = true;
                        break;
                    case 10:
                        runningRestaurant = false;
                        break;
                    case 0:
                        exit();
                        break;
                }
                if (runningRestaurant) {
                    runMainMenu(accountType, userId);
                } else {
                    break;
                }
            } while (runningRestaurant);
        } else {
            boolean runningInspector;
            do {
                runningInspector = true;
                switch (userMenuInput.inputValue(accountType)) {
                    case 1:
                        listAllAssignFridges(userId);
                        runningInspector = true;
                        break;
                    case 2:
                        removeFoodFromFridges(userId, accountType); // Added accountType to check if its restaurant or inspector
                        runningInspector = true;
                        break;
                    case 3:
                        runningInspector = false;
                        break;
                    case 0:
                        exit();
                        break;
                }
                if (runningInspector) {
                    runMainMenu(accountType, userId);
                } else {
                    break;
                }
            } while (runningInspector);
        }

    }

    public Restaurant  signUpAsRestaurant(){
        RestaurantOwnerInput restaurantInput = new RestaurantOwnerInput();
        Restaurant restaurant = new Restaurant(); // add to database
        Restaurant restaurantFromDb = new Restaurant();
        List<String> restaurantList;
        try {
            do { // added to check if the username already exist in the restaurant table
                restaurantList = listService.getListOfRestaurants();
                restaurant = restaurantInput.signUpInputValue();
                if (!restaurantList.contains(restaurant.getUserName())) {
                    listService.addNewRestaurant(restaurant);
                    restaurantFromDb = getRestaurantInfo(restaurant); // getting the restaurant info from DB
                } else {
                    System.out.println("The restaurant username already exist. Please try again.");
                }
                restaurantList = listService.getListOfRestaurants();
            } while (!restaurantList.contains(restaurant.getUserName())); // loop until username is unique
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return restaurantFromDb;
    }

    public Restaurant  loginAsRestaurant(){
        UserMenuInput userMenuInput = new UserMenuInput();
        RestaurantOwnerInput restaurantInput = new RestaurantOwnerInput();
        HealthInspectorInput healthInspectorInput = new HealthInspectorInput();
        FoodInput foodInput = new FoodInput();

        Restaurant restaurant;
        Restaurant restaurantFromDb = new Restaurant();
        List<String> restaurantList;
        String userName;
        try {
            do { // added to check if the username already exist in the restaurant table
                userName = "";
                restaurantList = listService.getListOfRestaurants();
                restaurant = restaurantInput.loginInputValue();
                userName = restaurant.getUserName();
                if (restaurantList.contains(userName)) {
                    restaurantFromDb = getRestaurantInfo(restaurant); // getting the restaurant info from DB
                } else {
                    System.out.println("The restaurant username not found. Please try again.");
                }
            } while (!restaurantList.contains(userName)); // loop until username is unique
        }catch (Exception ex){
            System.out.println("Wrong restaurant credential!");
            ex.printStackTrace();
        }
        return  restaurantFromDb;
    }

    public Restaurant getRestaurantInfo(Restaurant restaurant) {
        Restaurant restaurantFromDb = new Restaurant(); // get from database
        restaurantFromDb = listService.getRestaurantInfo(restaurant.getUserName(), restaurant.getPassword());
        if (!restaurantFromDb.getUserName().isEmpty()) {
            System.out.println("\nWelcome " + restaurant.getUserName().toUpperCase() + " on your RESTAURANT profile menu!");
        }
        else{
            System.out.println("Invalid Password!");
        }
        return  restaurantFromDb;
    }

    // HEALTH INSPECTOR

    public HealthInspector signUpAsInspector() {
        HealthInspectorInput healthInspectorInput = new HealthInspectorInput();
        HealthInspector healthInspector = new HealthInspector();
        HealthInspector healthInspectorFromDb = new HealthInspector();

        List<String> initialInspectorList ;
        List<String> finalInspectorList ;
        try{
            do {
                initialInspectorList = listService.getListOfInspectors();
                System.out.println("\n\tSigning up...");
                healthInspector = healthInspectorInput.inputValue();
                if (!initialInspectorList.contains(healthInspector.getUserName())) {
                    listService.addNewInspector(healthInspector);
                    healthInspectorFromDb = getHealthInspectorInfo(healthInspector);
                } else {
                    System.out.println("The inspector username already exist. Please try again!");
                }
                finalInspectorList = listService.getListOfInspectors();
            } while (!finalInspectorList.contains(healthInspector.getUserName()));
        }catch ( Exception ex){
            ex.printStackTrace();
        }
        return healthInspectorFromDb;
    }

    public HealthInspector loginAsInspector(){
        HealthInspectorInput healthInspectorInput1 = new HealthInspectorInput();
        HealthInspector healthInspector = new HealthInspector();
        HealthInspector healthInspectorFromDb = new HealthInspector();
        List<String> inspectorList ;
        try {
            inspectorList = listService.getListOfInspectors();
            do {
                System.out.println("\n\tLogging in...");
                healthInspector = healthInspectorInput1.inputValue();
                if (inspectorList.contains(healthInspector.getUserName())) {
                    healthInspectorFromDb = getHealthInspectorInfo(healthInspector);
                } else {
                    System.out.println("The inspector username not found. Please try again!");
                }
            } while (!inspectorList.contains(healthInspector.getUserName()));
        }catch (Exception ex){
            System.out.println("\tWrong inspector credential!");
        }
        return healthInspectorFromDb;
    }

    public HealthInspector getHealthInspectorInfo(HealthInspector healthInspector) {
        HealthInspector healthInspectorFromDb = new HealthInspector();
        healthInspectorFromDb = listService.getInspectorInfo(healthInspector.getUserName(), healthInspector.getPassword());
        if (!healthInspectorFromDb.getUserName().isEmpty()){
            System.out.println("\nWelcome " + healthInspectorFromDb.getUserName().toUpperCase()+ " on your INSPECTOR profile menu!");
        } else{
            System.out.println("\tInvalid Password!");
        }
        return healthInspectorFromDb;
    }


    // REFRIGERATOR

    public void listAllFridges(Integer userId, Integer accountType) {
        List<Refrigerator> refrigerators;
        try {
            System.out.println("\nListing All Fridges..");
            System.out.println("=====================================");
            refrigerators = listService.getAllRestaurantFridges(userId);

            if (refrigerators.size() > 0) {
                for (Refrigerator refrigerator : refrigerators) {
                    System.out.println("Refrigerator Code Number: " + refrigerator.getCode_number() + " \t\t=>\t" +
                            listService.getCountFoodsInFridge(listService.getAllFoodItemsInAFridge(userId, refrigerator.getCode_number(), accountType)) + " items \t" +
                            "=>  Inspector ID: " + listService.inspectorListAssignmentInAFridge(refrigerator.getCode_number()));
                }
            } else {
                System.out.println("\tNothing fridge to display yet! Please add one.");
            }
            System.out.println("=====================================\n");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addNewFridge(Integer userId, Integer accountType) {
        try{
            RefrigeratorInput refrigeratorInput = new RefrigeratorInput();
            listService.addNewRestaurantFridge(refrigeratorInput.addInputValue(userId));
            listAllFridges(userId, accountType);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void removeFridge(Integer userId, Integer accountType) {
        try {
            RefrigeratorInput refrigeratorInput = new RefrigeratorInput();
            List<Integer> fridgeCodeList;
            List<Integer> inspectorList;
            Refrigerator refrigerator = new Refrigerator();
            // Added this List of refrigerators to filter out only the owned fridges.
            fridgeCodeList = listService.getFridgeCodeList(listService.getAllRestaurantFridges(userId));

            listAllFridges(userId, accountType);
            //added condition to make sure removeFridge woks only if you have one or more fridges.
            if (fridgeCodeList.size() > 0){
                if ( fridgeCodeList.size() > 1) {
                    // added do-while to input multiple times the correct own fridge number
                    // based from the fridge list and stop the loop if got the correct value.
                    Integer fridgeNumber;
                    do {
                        fridgeNumber = refrigeratorInput.removeInputValue().getCode_number();
                        inspectorList = listService.getListOfInspectorAssignInAFridge(fridgeNumber);
                        if (fridgeCodeList.contains(fridgeNumber)) {
                            if (inspectorList.size() > 0) {
                                System.out.println("\tThere is an inspector assigned to the fridge. \n\tRemove first the assignment to be able to remove the fridge.");

                            } else {
                                if (listService.getCountFoodsInFridge(listService.getAllFoodItemsInAFridge(userId, fridgeNumber, accountType)) > 0) {
                                    System.out.println("\tThe fridge contains item/s. Please transfer the item/s before removing the fridge.");
                                } else {
                                    listService.removedRestaurantFridge(fridgeNumber, userId);
                                }
                            }
                        } else {
                            System.out.println("\tFridge Number Not Found! ");
                        }
                    } while (!fridgeCodeList.contains(fridgeNumber));
                } else {
                    System.out.println("\tCannot remove the last fridge.");
                }
                listAllFridges(userId, accountType);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void listAllFoods(Integer userId){
        List<Food>  foods;
        try {
            System.out.println("\nListing All Foods...");
            System.out.println("=====================================");
            foods = listService.getAllAvailableFoods(userId);
            if (foods.size() > 0) {
                for (Food food : foods) {
                    System.out.println("Food Id: " + food.getFoodId() + "\t Food Name: " + food.getFoodName() + "\t\t Fridge Id: " + food.getRefrigeratorId());
                }
            } else {
                System.out.println("\tNothing to display yet! Please add food first!");
            }
            System.out.println("=====================================");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // NOTES:   You cannot deposit a food if you have no fridge yet.
    //          You can add food ONLY to your own fridge. Need a condition to make it happened.
    public void depositFoodIntoFridges(Integer userId, Integer accountType){
        try {
            FoodInput foodInput = new FoodInput();
            Food food  = new Food();

            List<Integer> fridgeCodeList;
            Refrigerator refrigerator = new Refrigerator();
            // Added this List of refrigerators to filter out only the owned fridges.
            fridgeCodeList = listService.getFridgeCodeList(listService.getAllRestaurantFridges(userId));
            // Display all owned fridges
            listAllFridges(userId, accountType);

            if (fridgeCodeList.size() > 0) {
                // added do-while to input multiple times the correct own fridge number
                // based from the fridge list and stop the loop if got the correct value.
                do {
                    food.setRefrigeratorId(foodInput.fridgeNumberInputValue().getRefrigeratorId());
                    refrigerator.setCode_number(food.getRefrigeratorId());
                    if (fridgeCodeList.contains(refrigerator.getCode_number())) {
                        if (listService.isFridgeNotFull(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType))) {
                            food.setFoodName(foodInput.foodNameInputValue().getFoodName());
                            listService.depositFoodInFridge(food, userId, accountType);
                        } else {
                            System.out.println("\tYou have already " + listService.getCountFoodsInFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType)) + " items in your refrigerator. Select another fridge.");
                        }
                        listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType), food.getRefrigeratorId());
                    }
                    else{
                        System.out.println("\tFridge number not found in the list!");
                    }
                } while (!fridgeCodeList.contains(refrigerator.getCode_number()));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // NOTES:   Make sure to check the list of fridges own. Added a loop to make sure the user only input values
    //          what is listed on the fridges list. Also this will filter out and make sure that user will not
    //          not be able to delete anybody's food from the food table.
    public void withdrawFoodFromFridges(Integer userId, Integer accountType) {
        try {
            FoodInput foodInput = new FoodInput();
            Food food  = new Food();

            List<Integer> fridgeCodeList;
            Refrigerator refrigerator = new Refrigerator();
            // Added this List of refrigerators to filter out only the owned fridges.
            fridgeCodeList = listService.getFridgeCodeList(listService.getAllRestaurantFridges(userId));
            // Display all owned fridges
            listAllFridges(userId, accountType);

            // adding this list to list all the foods in the specified fridge
            // this will make sure that only the food items in the fridge can be withdrawn or remove
            // and not from other owned fridge.
            List<Integer> foodIdList ;

            if (fridgeCodeList.size() > 0) {
                // added do-while to input multiple times the correct own fridge number
                // based from the fridge list and stop the loop if got the correct value.
                boolean isEmpty = false;
                do {
                    food.setRefrigeratorId(foodInput.fridgeNumberInputValue().getRefrigeratorId());
                    refrigerator.setCode_number(food.getRefrigeratorId());
                    isEmpty = listService.isFridgeEmpty(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType));
                    if (fridgeCodeList.contains(refrigerator.getCode_number())) {
                        if (!isEmpty) {
                            // List all the foods  inside the fridge.
                            foodIdList = listService.getFoodIdList(listService.getAllFoodItemsInAFridge(userId, refrigerator.getCode_number(), accountType));
                            listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, refrigerator.getCode_number(), accountType), refrigerator.getCode_number());
                            do {
                                food.setFoodId(foodInput.foodNumberInputValue().getFoodId());
                                if (foodIdList.contains(food.getFoodId())) {
                                    listService.withdrawFoodFromFridge(food.getFoodId(), food.getRefrigeratorId());
                                    System.out.println("\tYou have already " + listService.getCountFoodsInFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType)) + " items in your refrigerator.");
                                    listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType), food.getRefrigeratorId());
                                } else{
                                    System.out.println("\tFood number not found in the list!");
                                }
                            } while (!foodIdList.contains(food.getFoodId()));
                        } else{
                            System.out.println("\tFridge number has no item");
                        }
                    } else{
                        System.out.println("\tFridge number not found in the list!");
                    }
                } while (!fridgeCodeList.contains(refrigerator.getCode_number()) || isEmpty);
            } else{
                System.out.println("\tNo fridge yet! Please add one!");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // NOTES:   Make sure to check the fridge has items before doing the transfer.
    //          Make sure that the other fridge is not full or else not doing the transfer.
    //          Make sure to transfer only the food that is own by the user.
    public void transferFoodToOtherFridge(Integer userId, Integer accountType){
        try {
            FoodInput foodInput = new FoodInput();
            Food food  = new Food();
            List<Integer> fridgeCodeList;
            List<Integer> foodIdList ;
            Integer newFridgeNumber;

            // Added this List of refrigerators to filter out only the owned fridges.
            fridgeCodeList = listService.getFridgeCodeList(listService.getAllRestaurantFridges(userId));
            listAllFridges(userId, accountType);

            if (fridgeCodeList.size() > 0) {
                boolean isEmpty = false;
                do {
                    System.out.println("Fridge Origin");
                    food.setRefrigeratorId(foodInput.fridgeNumberInputValue().getRefrigeratorId());
                    // Create a new fridge with a value of food.refrigerator_id for the fridge_id
                    // this will be use in do-while checking if it matches any refrigerator own by the user.
                    isEmpty = listService.isFridgeEmpty(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType));
                    if (fridgeCodeList.contains(food.getRefrigeratorId())) {
                        if (!isEmpty) {
                            listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType), food.getRefrigeratorId());
                            // List all the foods  inside the fridge.
                            foodIdList = listService.getFoodIdList(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType));
                            do {
                                food.setFoodId(foodInput.foodNumberInputValue().getFoodId());
                                if (foodIdList.contains(food.getFoodId())) {
                                    do {
                                        System.out.println("Fridge Destination");
                                        newFridgeNumber = foodInput.fridgeNumberInputValue().getRefrigeratorId();
                                        if (fridgeCodeList.contains(newFridgeNumber)) {
                                            if (listService.isFridgeNotFull(listService.getAllFoodItemsInAFridge(userId, newFridgeNumber, accountType))) {
                                                if (newFridgeNumber != food.getRefrigeratorId()) {
                                                    listService.transferFoodInFridge(food.getFoodId(), newFridgeNumber, userId, accountType);
                                                } else {
                                                    System.out.println("\tPutting back the food to original fridge.");
                                                }
                                                listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType), food.getRefrigeratorId());
                                                listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, newFridgeNumber, accountType), newFridgeNumber);
                                                break;
                                            } else {
                                                System.out.println("\tFridge destination is in full capacity.");
                                            }
                                        }
                                        else{
                                            System.out.println("\tFridge destination number not found in the list!");
                                        }
                                    } while (!fridgeCodeList.contains(newFridgeNumber));
                                }
                                else {
                                    System.out.println("\tFood number not found in the list!");
                                }
                            } while (!foodIdList.contains(food.getFoodId()));
                        } else{
                            System.out.println("\tFridge number has no item");
                        }
                    } else{
                        System.out.println("\tFridge origin number not found in the list!");
                    }
                } while (!fridgeCodeList.contains(food.getRefrigeratorId()) || isEmpty);
            } else{
                System.out.println("\tNo fridge yet! Please add one!");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void assignHealthInspector(Integer userId, Integer accountType){
        try {
            FridgeInspectorInput fridgeInspectorInput = new FridgeInspectorInput();
            List<Integer> inspectorIdList  = new ArrayList<>(); ; // List all InspectorId
            Integer fridgeNumber;
            Integer inspectorNumber;

            // Added this List of refrigerators to filter out only the owned fridges.
            List<Integer> fridgeCodeList = listService.getFridgeCodeList(listService.getAllRestaurantFridges(userId));
            listAllFridges(userId, accountType);
            // Display all available inspector id list for reference.
            System.out.println("Inspector Id Available:");
            System.out.println("=====================================");
            for (Integer inspectorId: listService.getListOfInspectorId()) {
                inspectorIdList.add(inspectorId);
                System.out.print("[" + inspectorId + "],\t");
            }
            System.out.println("\n=====================================");

            do {
                fridgeNumber = fridgeInspectorInput.addFridgeInputValue(userId).getCode_number();
                if (fridgeCodeList.contains(fridgeNumber)) {
                    do {
                        inspectorNumber = fridgeInspectorInput.addInspectorInputValue().getId();
                        if (inspectorIdList.contains(inspectorNumber)) {
                            listService.assignInspectorToFridges(inspectorNumber, fridgeNumber);
                            listAllFridges(userId, accountType);
                        } else{
                            System.out.println("\tInspector Id not found in the list!");
                        }
                    } while (!inspectorIdList.contains(inspectorNumber));
                } else{
                    System.out.println("\tFridge origin number not found in the list!");
                }
            } while (!fridgeCodeList.contains(fridgeNumber));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // NOTES:   Need to output the list of fridges assigned
    //          Display the owner restaurant and the items inside.
    public void listAllAssignFridges(Integer userId) {
        List<Integer>  fridges;
        try {
            System.out.println("\nListing All Assign Fridges...");
            System.out.println("=====================================");
            fridges = listService.getAllAssignedFridges(userId);
            if (fridges.size() > 0) {
                for (Integer fridgeInspector : fridges){
                    System.out.println("Fridge Number:\t" + fridgeInspector + " \t\t=>\t" +
                            listService.getCountFoodsInFridge(listService.getAllFoodItemsInAFridge(userId, fridgeInspector, 2)) + " items \t" +
                            listService.getFridgeInfo(fridgeInspector));
                }
            } else {
                System.out.println("\tNo fridge assignment yet");
            }
            System.out.println("=====================================");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void removeFoodFromFridges(Integer userId, Integer accountType){
        try {
            FoodInput foodInput = new FoodInput();
            Food food  = new Food();

            List<Integer> fridgeCodeList;

            Refrigerator refrigerator = new Refrigerator();
            // Added this List of refrigerators to filter out only the owned fridges.
            fridgeCodeList = listService.getAllAssignedFridges(userId);
            // Display all assigned fridges
            listAllAssignFridges(userId);

            // adding this list to list all the foods in the specified fridge
            // this will make sure that only the food items in the fridge can be withdrawn or remove
            // and not from other owned fridge.
            List<Integer> foodIdList ;

            if (fridgeCodeList.size() > 0) {
                boolean isEmpty = false;
                boolean hasInspectorList = false;
                // added do-while to input multiple times the correct own fridge number
                // based from the fridge list and stop the loop if got the correct value.
                do {
                    food.setRefrigeratorId(foodInput.fridgeNumberInputValue().getRefrigeratorId());
                    refrigerator.setCode_number(food.getRefrigeratorId());

                    isEmpty = listService.isFridgeEmpty(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType));
                    if (fridgeCodeList.contains(refrigerator.getCode_number())) {
                        if (!isEmpty) {
                            // List all the foods  inside the fridge.
                            foodIdList = listService.getFoodIdList(listService.getAllFoodItemsInAFridge(userId, refrigerator.getCode_number(), accountType));
                            listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, refrigerator.getCode_number(), accountType), refrigerator.getCode_number());
                            do {
                                food.setFoodId(foodInput.foodNumberInputValue().getFoodId());
                                if (foodIdList.contains(food.getFoodId())) {
                                    listService.withdrawFoodFromFridge(food.getFoodId(), food.getRefrigeratorId());
                                    System.out.println("\tThe fridge now has " + listService.getCountFoodsInFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType)) + " item/s left.");
                                    listService.displayAllFoodsInAFridge(listService.getAllFoodItemsInAFridge(userId, food.getRefrigeratorId(), accountType), food.getRefrigeratorId());
                                } else {
                                    System.out.println("\tFood number not found in the list!");
                                }
                            } while (!foodIdList.contains(food.getFoodId()));
                        } else {
                            System.out.println("\tFridge number has no items");
                        }
                    } else{
                        System.out.println("\tFridge number not found in the list!");
                    }
                } while (!fridgeCodeList.contains(refrigerator.getCode_number()) || isEmpty);
            } else{
                System.out.println("\tNo assigned fridge yet!");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // NOTES:   Require a
    public void removeInspectorAssignedInAFridges(Integer userId, Integer accountType){
        try {
            RefrigeratorInput refrigeratorInput = new RefrigeratorInput();
            FridgeInspectorInput fridgeInspectorInput = new FridgeInspectorInput();
            List<Integer> inspectorIdList  = new ArrayList<>(); ; // List all InspectorId
            Integer fridgeNumber;
            Integer inspectorNumber;

            // Added this List of refrigerators to filter out only the owned fridges.
            List<Integer> fridgeCodeList = listService.getFridgeCodeList(listService.getAllRestaurantFridges(userId));
            listAllFridges(userId, accountType);

            do {
                fridgeNumber = refrigeratorInput.removeInputValue().getCode_number();
                if (fridgeCodeList.contains(fridgeNumber)) {
                    System.out.println("Listing Assigned Inspector in Fridge "+ fridgeNumber);
                    System.out.println("========================================");
                    System.out.println(listService.inspectorListAssignmentInAFridge(fridgeNumber));
                    System.out.println("========================================\n");
                    do {
                        inspectorNumber = fridgeInspectorInput.addInspectorInputValue().getId();
                        inspectorIdList = listService.getListOfInspectorAssignInAFridge(fridgeNumber);
                        if (inspectorIdList.contains(inspectorNumber)) {
                            listService.removeAssignInspectorInAFridge(inspectorNumber, fridgeNumber);
                            listAllFridges(userId, accountType);
                        } else{
                            System.out.println("\tInspector Id not found in the list!");
                        }
                    } while (!inspectorIdList.contains(inspectorNumber));
                } else{
                    System.out.println("\tFridge number not found in the list!");
                }
            } while (!fridgeCodeList.contains(fridgeNumber));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    protected void exit(){
        System.out.println("Goodbye!");
        System.exit(0);
    }

    public void reset(Integer userId, Integer accountType, Integer accessType){
        userId = -1;
        accountType = -1;
        accessType = -1;
    }
}
