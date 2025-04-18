package service;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonUtil;

import java.util.List;

public class AuthService {
    private final List<User> users;

    public AuthService() {
        this.users = JsonUtil.loadUsers("database/users.json");
    }

    public User authenticate(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}