package view;

import controller.CrushController;
import model.CrushDAO;
import model.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginGUI extends JFrame {
    public LoginGUI() {
        setTitle("🔐 Login - Crush Predictor");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        getContentPane().setBackground(new Color(255, 240, 245));

        // Fonts
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(255, 220, 230));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(boldFont);
        JTextField userField = new JTextField();
        userField.setFont(mainFont);
        userField.setBackground(Color.WHITE);
        userField.setBorder(BorderFactory.createLineBorder(new Color(255, 180, 200)));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(boldFont);
        JPasswordField passField = new JPasswordField();
        passField.setFont(mainFont);
        passField.setBackground(Color.WHITE);
        passField.setBorder(BorderFactory.createLineBorder(new Color(255, 180, 200)));

        JButton loginButton = new JButton("Login");
loginButton.setFont(boldFont);
loginButton.setBackground(new Color(255, 100, 160));
loginButton.setForeground(Color.WHITE);
loginButton.setFocusPainted(false);
loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
loginButton.setOpaque(true);
loginButton.setContentAreaFilled(true);
loginButton.setBorderPainted(false);

// Load and scale the icon
ImageIcon originalIcon = new ImageIcon("assets/unlock_6147621.png");
Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
ImageIcon smallIcon = new ImageIcon(scaledImage);

// Set icon and text position
loginButton.setIcon(smallIcon);
loginButton.setText(" Login"); // Add space between icon and text
loginButton.setHorizontalTextPosition(SwingConstants.LEFT); // Icon on the left, text on the right
loginButton.setVerticalTextPosition(SwingConstants.CENTER);

// Add hover effect
loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        loginButton.setBackground(new Color(255, 60, 140)); // Darker pink
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
        loginButton.setBackground(new Color(255, 100, 160)); // Original color
    }
});

        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);
        panel.add(new JLabel()); panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            try {
                if (new UserDAO().authenticate(username, password)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose(); // Close login window

                    CrushAppGUI appGUI = new CrushAppGUI();
                    new CrushController(new CrushDAO(), appGUI);
                    appGUI.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}