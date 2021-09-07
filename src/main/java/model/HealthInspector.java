package model;

public class HealthInspector {

    private int codeNumber;
    private String userName;
    private String password;

    public int getId() {
        return codeNumber;
    }
    public void setId(int id) {
        this.codeNumber = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() { return password;  }
    public void setPassword(String password) { this.password = password;    }

    public HealthInspector() {
    }

    public HealthInspector(int codeNumber, String userName, String password) {
        this.codeNumber = codeNumber;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "HealthInspector{" +
                "codeNumber=" + codeNumber +
                ", userName='" + userName + '\'' +
                '}';
    }
}
