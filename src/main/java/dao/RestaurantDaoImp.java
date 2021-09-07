package dao;

import model.Restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDaoImp  implements RestaurantDao {
        /*
        The code below is based from TODO App
        */
    String url = "jdbc:postgresql://" + System.getenv("RESTAURANTOWNERAPP_DATABASE_URI") + "/restaurant_owner_app_db";
    String username = System.getenv("RESTAURANTOWNERAPP_DATABASE_USERNAME");
    String password = System.getenv("RESTAURANTOWNERAPP_DATABASE_PASSWORD");

    public RestaurantDaoImp() {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getListOfRestaurant() {
        List<String> restaurants= new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT user_name FROM restaurant_owner;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                restaurants.add(rs.getString(1));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return restaurants;
    }

    @Override
    public Restaurant getRestaurantOwnerInfo(String uName, String uPass) {

        Restaurant restaurant = new Restaurant();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM restaurant_owner WHERE user_name = ? AND user_password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uName);
            ps.setString(2, uPass);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                restaurant = new Restaurant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return restaurant;
    }

    @Override
    public Boolean addNewRestaurantOwner(Restaurant restaurant) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO restaurant_owner VALUES (DEFAULT, ?, ?, ?);";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setString(1, restaurant.getUserName());
            ps.setString(2, restaurant.getPassword());
            ps.setString(3, restaurant.getRestaurantName());
            ps.executeUpdate();
            return  true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }

    @Override
    public String getRestaurantOwnerName(Integer userId) {

        String  restaurantName = "";

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String sql =    "SELECT ro.owner_id, ro.restaurant_name, re.code_id, re.restaurant_id FROM restaurant_owner ro\n" +
                            "JOIN refrigerator re ON re.code_id = ro.owner_id\n" +
                            "WHERE re.code_id = ?;" ;

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                restaurantName = rs.getString(2);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return restaurantName;
    }
}
