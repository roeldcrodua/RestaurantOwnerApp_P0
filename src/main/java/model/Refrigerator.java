package model;

public class Refrigerator {

    private int code_number;
    private int restaurantCode;

    public int getCode_number() {
        return code_number;
    }
    public void setCode_number(int code_number) {
        this.code_number = code_number;
    }

    public int getRestaurantCode() { return restaurantCode; }
    public void setRestaurantCode(int restaurantCode) { this.restaurantCode = restaurantCode; }

    public Refrigerator() {
    }

    public Refrigerator(int code_number, int restaurantCode) {
        this.code_number = code_number;
        this.restaurantCode = restaurantCode;
    }

    @Override
    public String toString() {
        return "Refrigerator{" +
                "code_number=" + code_number +
                ", restaurantCode=" + restaurantCode +
                '}';
    }


}
