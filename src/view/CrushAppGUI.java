package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class CrushAppGUI extends JFrame {
    private JTextField nameField;
    private JTextArea crushArea;
    private JButton predictButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public CrushAppGUI() {
        setTitle("💘 Crush Predictor");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 240, 245));

        // Fonts
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(255, 220, 230));

        JLabel nameLabel = new JLabel("Your Name:");
        nameLabel.setFont(boldFont);
        nameField = new JTextField();
        nameField.setFont(mainFont);
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(255, 180, 200)));

        JLabel crushLabel = new JLabel("Crush Names (comma-separated):");
        crushLabel.setFont(boldFont);
        crushArea = new JTextArea(3, 20);
        crushArea.setFont(mainFont);
        crushArea.setLineWrap(true);
        crushArea.setWrapStyleWord(true);
        crushArea.setBackground(Color.WHITE);
        crushArea.setBorder(BorderFactory.createLineBorder(new Color(255, 180, 200)));
        JScrollPane crushScrollPane = new JScrollPane(crushArea);

        predictButton = new JButton("Predict Match 💖");
predictButton.setFont(boldFont);
predictButton.setBackground(new Color(255, 100, 160));
predictButton.setForeground(Color.WHITE);
predictButton.setFocusPainted(false);
predictButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
predictButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
predictButton.setOpaque(true); // Ensure background color shows up
predictButton.setContentAreaFilled(true);
predictButton.setBorderPainted(false);

// Optional: Add an icon (e.g., a heart emoji or image)
ImageIcon originalIcon = new ImageIcon("assets/flying_6350167.png");
Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
ImageIcon smallIcon = new ImageIcon(scaledImage);

predictButton.setIcon(smallIcon);
predictButton.setText(" Predict Match "); // Add space between icon and text
predictButton.setHorizontalTextPosition(SwingConstants.LEFT); // Icon on left
predictButton.setVerticalTextPosition(SwingConstants.CENTER);

// Hover effect
predictButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        predictButton.setBackground(new Color(255, 60, 140)); // Darker pink
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
        predictButton.setBackground(new Color(255, 100, 160)); // Original color
    }
}); 

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(crushLabel);
        inputPanel.add(crushScrollPane);
        inputPanel.add(new JLabel()); // empty
        inputPanel.add(predictButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table Setup
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 235, 240) : new Color(255, 220, 230));
                }
                return c;
            }
        };
        resultTable.setFont(mainFont);
        resultTable.setRowHeight(25);
        resultTable.getTableHeader().setFont(boldFont);
        resultTable.getTableHeader().setBackground(new Color(255, 100, 160));
        resultTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        tableModel.addColumn("Crush Name");
        tableModel.addColumn("Match %");
        tableModel.addColumn("Result");

        // Status Label
        statusLabel = new JLabel(" ");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(255, 200, 220));
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void addPredictButtonListener(ActionListener listener) {
        predictButton.addActionListener(listener);
    }

    public String getYourName() {
        return nameField.getText().trim();
    }

    public String getCrushNames() {
        return crushArea.getText().trim();
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.WARNING_MESSAGE);
    }

    public void displayResults(StringBuilder htmlResult) {
        tableModel.setRowCount(0); // Clear previous results

        String rawHtml = htmlResult.toString()
                .replace("<html>", "")
                .replace("</html>", "")
                .trim();

        String[] rows = rawHtml.split("<br\\s*/?>");
        for (String row : rows) {
            if (!row.trim().isEmpty()) {
                String cleanRow = row.replaceAll("<[^>]*>", "").trim(); // Strip HTML tags

                String[] parts = cleanRow.split("→", 2); // Split at "→"
                if (parts.length == 2) {
                    String leftSide = parts[0].trim();
                    String message = parts[1].trim();

                    String[] nameAndPercent = leftSide.split(":");

                    if (nameAndPercent.length >= 2) {
                        String name = nameAndPercent[0].trim();
                        String percentStr = nameAndPercent[1].trim().replaceAll("[^0-9]", "");

                        Object[] tableRow = {name, percentStr + "%", message};
                        tableModel.addRow(tableRow);
                    }
                }
            }
        }
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }
}