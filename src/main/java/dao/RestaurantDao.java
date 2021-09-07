package dao;

import model.Food;
import model.HealthInspector;
import model.Refrigerator;
import model.Restaurant;

import java.util.List;

public interface RestaurantDao {

    List<String> getListOfRestaurant();
    Restaurant getRestaurantOwnerInfo(String userName, String userPass);
    Boolean addNewRestaurantOwner(Restaurant restaurant);
    String getRestaurantOwnerName(Integer code_id);
}
