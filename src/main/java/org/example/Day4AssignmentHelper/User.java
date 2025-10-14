package org.example.Day4AssignmentHelper;

public class User {
    private final int id;
    private final String userName;
    private final String password;

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", userName='" + userName + "', password='" + password + "'}";
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
