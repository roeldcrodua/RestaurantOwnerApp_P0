package dao;

import model.Food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImp implements FoodDao{

        /*
        The code below is based from TODO App
        */
    String url = "jdbc:postgresql://" + System.getenv("RESTAURANTOWNERAPP_DATABASE_URI") + "/restaurant_owner_app_db";
    String username = System.getenv("RESTAURANTOWNERAPP_DATABASE_USERNAME");
    String password = System.getenv("RESTAURANTOWNERAPP_DATABASE_PASSWORD");

    public FoodDaoImp() {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Food> getAllFoods(Integer userId) {
        List<Food> foods = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql =    "SELECT food.food_id, food.food_name, food.refrigerator_id FROM food " +
                            "JOIN refrigerator ON food.refrigerator_id = refrigerator.code_id " +
                            "JOIN restaurant_owner ON refrigerator.restaurant_id = restaurant_owner.owner_id " +
                            "WHERE restaurant_owner.owner_id = ? " +
                            "ORDER BY food.refrigerator_id, food.food_name;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                foods.add(new Food(rs.getInt(1), rs.getString("food_name"), rs.getInt(3)));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return foods;
    }

    @Override
    public List<Food> getAllFoodsInAFridge(Integer userId, Integer codeId, Integer accountType) {
        List<Food> foods = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT * FROM food WHERE food.refrigerator_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codeId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                foods.add(new Food(rs.getInt(1), rs.getString("food_name"), rs.getInt(3)));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return foods;
    }

    @Override
    public Food getAFoodInAFridge(Integer userId, Integer foodId) {
        Food food = new Food();

        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT food.food_id, food.food_name, food.refrigerator_id FROM food JOIN refrigerator ON food.refrigerator_id = refrigerator.code_id JOIN restaurant_owner ON refrigerator.restaurant_id = restaurant_owner.owner_id WHERE restaurant_owner.owner_id = ? AND food.food_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, foodId);
            ResultSet rs = ps.executeQuery();

            food.setFoodId(rs.getInt(1));
            food.setFoodName(rs.getString(2));
            food.setRefrigeratorId(rs.getInt(3));

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return food;
    }

    @Override
    public Boolean depositFood(Food food) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO food VALUES (DEFAULT, ?, ?);";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setString(1, food.getFoodName());
            ps.setInt(2, food.getRefrigeratorId());
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }

    @Override
    public Boolean transferFood(Integer food_id,Integer code_id) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "UPDATE food SET refrigerator_id = ? WHERE food_id = ?;";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, code_id);
            ps.setInt(2, food_id);
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }

    }

    @Override
    public Boolean removeFood(Integer food_id, Integer code_id) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "DELETE FROM food WHERE food_id = ? AND  refrigerator_id = ?;";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, food_id);
            ps.setInt(2, code_id);
            ps.executeUpdate();
            return  true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }

    }

}
