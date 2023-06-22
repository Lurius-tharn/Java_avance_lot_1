package com.esiee.java_avance_lot_1.singleton;

public final class UserSession {
    private static UserSession instance;
    private String username;
    private int role;

    private UserSession(String username, int role) {
        this.username = username;
        this.role = role;
    }

    public static UserSession getInstance(String username, int role) {
        if(instance == null) {
            instance = new UserSession(username, role);
        }
        return instance;
    }

    public static UserSession getInstance() {
        if(instance != null) {
            return instance;
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public int getRole() {
        return role;
    }

    public void cleanUserSession() {
        instance = null;
    }
    @Override
    public String toString() {
        return "UserSession{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
