package service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import model.Food;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListServiceTest  {


    @BeforeEach
    void setUp(){
    }


    @Test
    public void testIsFridgeNotFull() {
        // assign
        Boolean expectedResult = false;

        // act
        ListService listService =  new ListService();
        List<Food> foods = new ArrayList<>();
        Food food = new Food();

        food.setFoodId(1);
        food.setFoodName("bread");
        food.setRefrigeratorId(1);
        foods.add(food);

        Boolean actualResult = listService.isFridgeEmpty(foods);

        // assert
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testGetCountFoodsInFridge() {
        // assign
        int expectedResult = 2;

        // act
        ListService listService =  new ListService();
        List<Food> foods = new ArrayList<>();
        Food food1 = new Food();

        food1.setFoodId(1);
        food1.setFoodName("cake");
        food1.setRefrigeratorId(1);
        foods.add(food1);

        Food food2 = new Food();
        food2.setFoodId(1);
        food2.setFoodName("cake");
        food2.setRefrigeratorId(1);
        foods.add(food2);

        int actualResult = listService.getCountFoodsInFridge(foods);

        // assert
        assertEquals(expectedResult, actualResult);
    }
}