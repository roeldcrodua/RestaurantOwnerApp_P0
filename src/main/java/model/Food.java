package model;

public class Food {

    private int foodId;
    private String foodName;
    private int refrigeratorId;

    public int getFoodId() {
        return foodId;
    }
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getRefrigeratorId() {
        return refrigeratorId;
    }
    public void setRefrigeratorId(int refrigeratorId) {
        this.refrigeratorId = refrigeratorId;
    }

    public Food() {
    }

    public Food(int foodId, String foodName, int refrigeratorId) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.refrigeratorId = refrigeratorId;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", refrigeratorId=" + refrigeratorId +
                '}';
    }
}
