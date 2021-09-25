package com.medical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class UpdateMedicineFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel titleLabel = new JLabel("Update Quantity Of Medicine And Price", JLabel.CENTER);
    JLabel medicineLabel = new JLabel("Select Medicine");
    public JComboBox<String> comboBox = new JComboBox<>();
    JLabel showMedQuantityAndPriceLabel = new JLabel();
    JButton updateMedicineButton = new JButton("Update Medicine");
    JButton updatePriceButton = new JButton("Update Price");
    JButton backToHomeButton = new JButton("Back To Home");
    public int quantity;
    public int oldPrice;

    UpdateMedicineFrame() {
        loadMedicineNames();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        setButtonColors();
        setFontsProperties();
        addActionEvent();
    }

    private void loadMedicineNames() {
        CreateConn createConnection = CreateConn.estConnection();
        String query = "SElECT MEDICINE_NAME FROM Medicines ORDER BY MEDICINE_NAME ASC;";
        ResultSet resultSet;
        try {
            try (Statement statement = createConnection.conn.createStatement()) {
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    comboBox.addItem(resultSet.getString("MEDICINE_NAME"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setLayoutManager() {
        container.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(300, 100, 800, 70);
        medicineLabel.setBounds(585, 210, 250, 50);
        comboBox.setBounds(500, 250, 330, 40);
        showMedQuantityAndPriceLabel.setBounds(840, 250, 400, 40);
        updateMedicineButton.setBounds(500, 310, 160, 32);
        updatePriceButton.setBounds(670, 310, 160, 32);
        backToHomeButton.setBounds(1180, 20, 160, 32);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(medicineLabel);
        container.add(comboBox);
        container.add(showMedQuantityAndPriceLabel);
        container.add(updateMedicineButton);
        container.add(updatePriceButton);
        container.add(backToHomeButton);
    }

    private void setButtonColors() {
        updateMedicineButton.setBackground(new Color(153, 255, 51));
        updateMedicineButton.setForeground(Color.BLACK);
        updatePriceButton.setBackground(new Color(153, 255, 51));
        updatePriceButton.setForeground(Color.BLACK);
        backToHomeButton.setBackground(Color.darkGray);
        backToHomeButton.setForeground(Color.white);
    }

    private void setFontsProperties() {
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        medicineLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        comboBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
        showMedQuantityAndPriceLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        showMedQuantityAndPriceLabel.setForeground(Color.RED);
        updateMedicineButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        updatePriceButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        backToHomeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
    }

    private void addActionEvent() {
        comboBox.addActionListener(this);
        updateMedicineButton.addActionListener(this);
        updatePriceButton.addActionListener(this);
        backToHomeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == comboBox) {

            CreateConn createConnection = CreateConn.estConnection();
            String selectedMedicine = String.valueOf(comboBox.getSelectedItem());
            String query = "SELECT QUANTITY , PRICE_PER_TABLET FROM Medicines WHERE MEDICINE_NAME = '" + selectedMedicine + "'";

            try {
                try (Statement statement = createConnection.conn.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(query);
                    if (comboBox.getSelectedItem() != null) {
                        quantity = resultSet.getInt("QUANTITY");
                        oldPrice = resultSet.getInt("PRICE_PER_TABLET");
                        showMedQuantityAndPriceLabel.setText("<html>Available quantity of " + selectedMedicine + " is " + resultSet.getString("QUANTITY") + ".<br/>Price per tablet is " + oldPrice + "</html>");
                        showMedQuantityAndPriceLabel.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "No medicine in database");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (e.getSource() == updateMedicineButton) {
            try {
                int quantityToAdd = 0;
                quantityToAdd = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter quantity to add in existing quantity.", "Update Medicine",
                        JOptionPane.PLAIN_MESSAGE));

                if (quantityToAdd >= 0) {
                    quantity = quantityToAdd + quantity;
                    CreateConn createConnection = CreateConn.estConnection();
                    String selectedMedicine = String.valueOf(comboBox.getSelectedItem());
                    String updateQuery = "UPDATE Medicines SET QUANTITY = ? WHERE MEDICINE_NAME = ?";

                    try {
                        try (PreparedStatement ps = createConnection.conn.prepareStatement(updateQuery)) {
                            ps.setInt(1, quantity);
                            ps.setString(2, selectedMedicine);
                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(this, selectedMedicine + " Updated Successfully. New available quantity is " + quantity + ". Price per tablet is " + oldPrice + ".");
                            showMedQuantityAndPriceLabel.setText("<html>Available quantity of " + selectedMedicine + " is " + quantity + ".<br/>Price per tablet is " + oldPrice + ".</html>");
//                            quantity = quantity + quantityToAdd;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Entered Quantity is not valid");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be an integer!");
            }
        }

        if (e.getSource() == updatePriceButton) {
            try {
                String selectedMedicine = String.valueOf(comboBox.getSelectedItem());
                int newPrice = Integer.parseInt(JOptionPane.showInputDialog(this, "Current price per tablet of " + selectedMedicine + " is " + oldPrice + ". Enter new price per tablet.", "Update Price",
                        JOptionPane.PLAIN_MESSAGE));

                if (newPrice >= 0) {

                    oldPrice = newPrice;
                    CreateConn createConnection = CreateConn.estConnection();
                    String updateQuery = "UPDATE Medicines SET PRICE_PER_TABLET = ? WHERE MEDICINE_NAME = ?";

                    try {
                        try (PreparedStatement ps = createConnection.conn.prepareStatement(updateQuery)) {
                            ps.setInt(1, newPrice);
                            ps.setString(2, selectedMedicine);
                            ps.executeUpdate();
                            showMedQuantityAndPriceLabel.setText("<html>Available quantity of " + selectedMedicine + " is " + (quantity) + "." + ".<br/>Price per tablet is " + newPrice + ".</html>");
                            JOptionPane.showMessageDialog(this, selectedMedicine + " Price Updated Successfully. New Price per tablet is " + (newPrice) + ".");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Entered Quantity is not valid");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price must be an integer!");
            }
        }

        if (e.getSource() == backToHomeButton) {
            JOptionPane.showMessageDialog(this, "Back to home page");
        }
    }
}

public class UpdateMedicine {

    public static void main(String[] args) {
        UpdateMedicineFrame updateFrame = new UpdateMedicineFrame();
        updateFrame.setTitle("Update Medicine From Database");
        updateFrame.setVisible(true);
        updateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateFrame.setResizable(false);
    }
}
