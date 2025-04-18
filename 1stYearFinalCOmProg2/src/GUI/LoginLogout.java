package GUI;

import com.formdev.flatlaf.FlatLightLaf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class LoginLogout {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Apply modern UI theme
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ensureUserJsonExists();
            new LoginWindow();
        });
    }

    // Automatically creates the users.json file if it doesn't exist
    private static void ensureUserJsonExists() {
        File file = new File("users.json");
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                JSONObject user1 = new JSONObject();
                user1.put("username", "admin");
                user1.put("password", "admin123");

                JSONObject user2 = new JSONObject();
                user2.put("username", "jane");
                user2.put("password", "doe456");

                JSONArray usersArray = new JSONArray();
                usersArray.put(user1);
                usersArray.put(user2);

                JSONObject usersJson = new JSONObject();
                usersJson.put("users", usersArray);

                writer.println(usersJson.toString(4)); // Pretty-print JSON
                System.out.println("Created users.json with default users.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class LoginWindow extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginWindow() {
        setTitle("Login");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main Panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Username Label + Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        // Password Label + Field
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Login Button
        gbc.gridy++;
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn, gbc);

        add(panel);
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (validateCredentials(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            new LogoutWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateCredentials(String username, String password) {
        try (InputStream is = new FileInputStream("users.json")) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject obj = new JSONObject(tokener);
            JSONArray users = obj.getJSONArray("users");

            for (int i = 0; i < users.length(); i++) {
                JSONObject userObj = users.getJSONObject(i);
                String storedUser = userObj.getString("username");
                String storedPass = userObj.getString("password");

                if (Objects.equals(storedUser, username) && Objects.equals(storedPass, password)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading user data", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}

class LogoutWindow extends JFrame {
    public LogoutWindow() {
        setTitle("Welcome");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("You are logged in!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow();
        });

        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.CENTER);
        add(logoutBtn, BorderLayout.SOUTH);

        setVisible(true);
    }
}
