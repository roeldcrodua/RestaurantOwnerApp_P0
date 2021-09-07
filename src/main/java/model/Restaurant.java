package model;

public class Restaurant {

    private int id;
    private String userName;
    private String password;
    private String restaurantName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() { return password;  }
    public void setPassword(String password) { this.password = password;    }

    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Restaurant() {
    }

    public Restaurant(int id, String userName, String password, String restaurantName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
