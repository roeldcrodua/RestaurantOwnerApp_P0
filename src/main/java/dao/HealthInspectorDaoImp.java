package dao;

import model.HealthInspector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HealthInspectorDaoImp implements HealthInspectorDao{
        /*
        The code below is based from TODO App
        */
    String url = "jdbc:postgresql://" + System.getenv("RESTAURANTOWNERAPP_DATABASE_URI") + "/restaurant_owner_app_db";
    String username = System.getenv("RESTAURANTOWNERAPP_DATABASE_USERNAME");
    String password = System.getenv("RESTAURANTOWNERAPP_DATABASE_PASSWORD");

    public HealthInspectorDaoImp() {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getHealthInspectorList() {
        List<String> inspectors = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT user_name FROM health_inspector;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                inspectors.add(rs.getString(1));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return inspectors;
    }


    @Override
    public List<Integer> getListOfInspectorId() {
        List<Integer> inspectors = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT inspector_id FROM health_inspector;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                inspectors.add(rs.getInt(1));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return inspectors;
    }


    @Override
    public HealthInspector getHealthInspectorInfo(String uName, String uPass) {
        HealthInspector inspector = new HealthInspector();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM health_inspector WHERE user_name = ? AND user_password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uName);
            ps.setString(2, uPass);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                inspector = new HealthInspector(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return inspector;
    }

    @Override
    public Boolean addNewHealthInspector(HealthInspector inspector) {
        try(Connection connect = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO health_inspector VALUES (DEFAULT, ?, ?);";
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setString(1, inspector.getUserName());
            ps.setString(2, inspector.getPassword());
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }
}
