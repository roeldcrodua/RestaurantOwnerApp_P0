package dao;

import static org.junit.jupiter.api.Assertions.*;
import model.Restaurant;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class RestaurantDaoImpTest  {

    RestaurantDaoImp restaurantDaoImp;

    @BeforeEach
    void setUp() {
        restaurantDaoImp = new RestaurantDaoImp();}

    @Test
    public void testGetRestaurantOwnerInfo() {
        //Assign
        Restaurant account = new Restaurant();

        account.setUserName("wel");
        account.setPassword("crodua");

        Restaurant expectedResult = null;


        //Act
        Restaurant actualResult = restaurantDaoImp.getRestaurantOwnerInfo(account.getUserName(), account.getPassword());

        //Assert
        assertEquals(expectedResult, actualResult);

    }
}