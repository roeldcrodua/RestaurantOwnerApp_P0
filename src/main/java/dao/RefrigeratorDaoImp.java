package dao;

import model.Food;
import model.HealthInspector;
import model.Refrigerator;
import model.Restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RefrigeratorDaoImp implements RefrigeratorDao{

        /*
        The code below is based from TODO App
        */
    String url = "jdbc:postgresql://" + System.getenv("RESTAURANTOWNERAPP_DATABASE_URI") + "/restaurant_owner_app_db";
    String username = System.getenv("RESTAURANTOWNERAPP_DATABASE_USERNAME");
    String password = System.getenv("RESTAURANTOWNERAPP_DATABASE_PASSWORD");

    public RefrigeratorDaoImp() {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    @Override
    public List<Refrigerator> getAllRefrigerator(Integer userId) {

        List<Refrigerator> fridges = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT * FROM refrigerator WHERE restaurant_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                fridges.add(new Refrigerator(rs.getInt(1), rs.getInt(2)));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return fridges;
    }

    @Override
    public Boolean addNewFridge(Refrigerator fridge) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {
            String sql = "";
            sql = "INSERT INTO refrigerator VALUES (DEFAULT, ?);";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, fridge.getRestaurantCode());
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeFridge(Integer codeId, Integer userId) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "DELETE FROM refrigerator WHERE code_id = ? AND restaurant_id = ?;";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, codeId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }

    @Override
    public Boolean assignInspector(Integer inspector_id, Integer code_id) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO fridge_inspector_assignment VALUES (? , ?);";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, inspector_id);
            ps.setInt(2, code_id);
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }

    @Override
    public Boolean removeInspectorAssignInAFridge(Integer inspector_id, Integer code_id) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "DELETE FROM fridge_inspector_assignment WHERE inspector_id = ? AND fridge_id = ?;";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, inspector_id);
            ps.setInt(2, code_id);
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }

    @Override
    public List<Integer> getAllAssignRefrigerator(Integer inspectorId) {

        List<Integer> fridgeInspector = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT fridge_id FROM fridge_inspector_assignment WHERE inspector_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, inspectorId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                fridgeInspector.add(rs.getInt(1));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return fridgeInspector;
    }

    @Override
    public List<Integer> getAllInspectorAssignInAFridge(Integer code_id) {

        List<Integer> fridgeInspector = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT inspector_id FROM fridge_inspector_assignment WHERE fridge_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, code_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                fridgeInspector.add(rs.getInt(1));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return fridgeInspector;
    }

    @Override
    public Integer getRefrigeratorInfo(Integer code_id) {

        Integer fridgeId = 1 ;
        try(Connection connection = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT restaurant_id FROM refrigerator WHERE code_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, code_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                fridgeId = rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println();
        }
        return fridgeId;
    }
}
