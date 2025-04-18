package utils;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static List<User> loadUsers(String path) {
        List<User> userList = new ArrayList<>();
        try {
            String content = Files.readString(Paths.get(path));
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String username = obj.getString("username");
                String password = obj.getString("password");
                userList.add(new User(username, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}