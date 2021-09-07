package service;

import dao.*;
import model.Food;
import model.HealthInspector;
import model.Refrigerator;
import model.Restaurant;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class ListService {

    private RestaurantDao restaurantDao;
    private RefrigeratorDao refrigeratorDao;
    private FoodDao foodDao;
    private HealthInspectorDao healthInspectorDao;

    public ListService(){
        this.restaurantDao = new RestaurantDaoImp();
        this.refrigeratorDao = new RefrigeratorDaoImp();
        this.foodDao = new FoodDaoImp();
        this.healthInspectorDao = new HealthInspectorDaoImp();
    }

    //////// RESTAURANT //////////////////

    public List<String> getListOfRestaurants (){
        return this.restaurantDao.getListOfRestaurant();
    }

    public Restaurant getRestaurantInfo(String userName, String userPass){
        return this.restaurantDao.getRestaurantOwnerInfo(userName, userPass);
    }

    public void addNewRestaurant(Restaurant restaurant){
        if(this.restaurantDao.addNewRestaurantOwner(restaurant))
            System.out.println("\tNew restaurant profile added successfully!");
        else
            System.out.println("\tError was encountered during registering the new restaurant.");
    }

    /////// HEALTH INSPECTOR ////////

    public List<String> getListOfInspectors (){
        return this.healthInspectorDao.getHealthInspectorList();
    }

    public List<Integer> getListOfInspectorId () { return this.healthInspectorDao.getListOfInspectorId();}

    public HealthInspector getInspectorInfo(String userName, String userPass){
        return this.healthInspectorDao.getHealthInspectorInfo(userName, userPass);
    }

    public void addNewInspector(HealthInspector inspector){
        if(this.healthInspectorDao.addNewHealthInspector(inspector))
            System.out.println("\tNew health inspector profile added successfully!");
        else
            System.out.println("\tError was encountered.");
    }

    //////// REFRIGERATOR /////////////////

    public List<Refrigerator> getAllRestaurantFridges(Integer userId){
        return this.refrigeratorDao.getAllRefrigerator(userId);
    }

    public List<Integer> getFridgeCodeList(List<Refrigerator> fridges){
        List<Integer> fridgeList = new ArrayList<>();
        for (Refrigerator fridge : fridges){
            fridgeList.add(fridge.getCode_number());
        }
        return fridgeList;
    }

    public void addNewRestaurantFridge(Refrigerator fridge){
        if(this.refrigeratorDao.addNewFridge(fridge))
            System.out.println("\tNew Fridge added successfully!");
        else
            System.out.println("\tError was encountered during addition of fridge.");
    }

    public void removedRestaurantFridge(Integer codeId, Integer userId){
        if(this.refrigeratorDao.removeFridge(codeId, userId))
            System.out.println("\tFridge " + codeId + " was removed successfully!");
        else
            System.out.println("\tError was encountered during removal of fridge.");
    }

    public void assignInspectorToFridges(Integer inspector_id, Integer code_id){
        if(this.refrigeratorDao.assignInspector(inspector_id, code_id))
            System.out.println("\tNew Inspector was assigned successfully!");
        else
            System.out.println("\tError was encountered.");
    }

    public List<Integer> getAllAssignedFridges(Integer inspectorId){
        return this.refrigeratorDao.getAllAssignRefrigerator(inspectorId);
    }

    public List<Integer> getListOfInspectorAssignInAFridge(Integer codeId){
        return this.refrigeratorDao.getAllInspectorAssignInAFridge(codeId);
    }

    public String inspectorListAssignmentInAFridge (Integer codeId){
        String inspectorList = "";
        List<Integer> inspectors = this.refrigeratorDao.getAllInspectorAssignInAFridge(codeId);
        if (inspectors.size() > 0){
            for (Integer inspector: inspectors){
                if (inspector > 0){
                    inspectorList += inspector + "\t";
                }
            }
        } else {
            inspectorList += "None";
        }
        return inspectorList;
    }

    public void removeAssignInspectorInAFridge(Integer inspectorId, Integer codeId){
        if(this.refrigeratorDao.removeInspectorAssignInAFridge(inspectorId, codeId)){
            System.out.println("\tSucessfully removed inspector " + inspectorId);
        }
    }

    /////// FOOD ///////////////////

    public List<Food> getAllAvailableFoods(Integer userId) {
        return this.foodDao.getAllFoods(userId);
    }

    public List<Integer> getFoodIdList(List<Food> foods){
        List<Integer> foodList = new ArrayList<>();
        for (Food food : foods){
            foodList.add(food.getFoodId());
        }
        return foodList;
    }

    public List<Food> getAllFoodItemsInAFridge(Integer userId, Integer codeId, Integer accountType){
        return this.foodDao.getAllFoodsInAFridge(userId, codeId, accountType);
    }

    public void displayAllFoodsInAFridge(List<Food> foods, Integer codeId){
        System.out.println("\nList of food in fridge " + codeId);
        System.out.println("=====================================");
        if (foods.size() > 0) {
            for (Food food : foods) {
                System.out.println("\tFood ID: " + food.getFoodId() + "\t Food Name: " + food.getFoodName());
            }
        } else {
            System.out.println("\tNo food available in the fridge.");
        }
        System.out.println("=====================================");
    }

    public boolean isFridgeNotFull(List<Food> foods) {
        return foods.size() < 5;
    }

    public boolean isFridgeEmpty(List<Food> foods) {
        return foods.size() == 0;
    }

    public int getCountFoodsInFridge(List<Food> foods) {
        return foods.size();
    }

    public void depositFoodInFridge(Food food, Integer userId, Integer accountType){
        try {
            if (this.foodDao.depositFood(food)) {
                System.out.println("\tNew Food added successfully!");
            } else {
                System.out.println("\tError was encountered during addition of " + food.getFoodName());
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void withdrawFoodFromFridge(Integer food_id, Integer code_id){
        if(this.foodDao.removeFood(food_id, code_id))
            System.out.println("\tFood was withdrawn successfully!");
        else
            System.out.println("\tError was encountered. Cannot find the food id in food table.");
    }

    public void transferFoodInFridge(Integer food_id, Integer code_id, Integer userId, Integer accountType){
        try {
            if (this.foodDao.transferFood(food_id, code_id)) {
                System.out.println("\tFood was transferred successfully!");
            } else
                System.out.println("\tError was encountered.");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getFridgeInfo(Integer codeId){
        Integer restaurantId = this.refrigeratorDao.getRefrigeratorInfo(codeId);
        String restaurantName = this.restaurantDao.getRestaurantOwnerName(restaurantId);
        return "\tRestaurant ID: \t" + restaurantId + "\t\tRestaurant Name:\t" + restaurantName;
    }

}
