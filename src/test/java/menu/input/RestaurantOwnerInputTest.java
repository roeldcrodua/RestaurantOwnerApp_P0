package menu.input;

import junit.framework.TestCase;
import model.Restaurant;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import model.Food;
import org.junit.Test;

public class RestaurantOwnerInputTest {

    @Test
    public void testSignUpInputValue() {
    }

    public void testLoginInputValue() {
    }

    @Test
    public void testIsInValid() {
        RestaurantOwnerInput restaurantOwnerInput = new RestaurantOwnerInput();
        //assign
        boolean expectedResult = false;

        // act
        Restaurant restaurant = new Restaurant();
        restaurant.setUserName("wel");
        restaurant.setPassword("wel");
        boolean actualResult = restaurantOwnerInput.isInValid(restaurant);

        //assert
        assertEquals(expectedResult, actualResult);

    }
}