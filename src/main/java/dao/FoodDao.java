package dao;

import model.Food;
import model.HealthInspector;
import model.Refrigerator;
import model.Restaurant;

import java.util.List;

public interface FoodDao {

    List<Food> getAllFoods(Integer userId);
    List<Food> getAllFoodsInAFridge(Integer userId, Integer codeId, Integer accountType);
    Food getAFoodInAFridge(Integer userId, Integer foodId);
    Boolean depositFood(Food food);
    Boolean transferFood(Integer codeId, Integer foodId);
    Boolean removeFood(Integer foodId, Integer codeId);
}
