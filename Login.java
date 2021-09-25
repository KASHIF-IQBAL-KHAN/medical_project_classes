package com.medical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class LoginFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel titleLabel = new JLabel("Medical Software Application");
    JLabel passwordLabel = new JLabel("Password");
    JPasswordField password = new JPasswordField(50);
    JCheckBox checkBox = new JCheckBox("Show Password");
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");

    LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        setButtonColors();
        setFontsProperties();
        addActionEvent();
    }

    private void setLayoutManager() {
        container.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(385, 100, 700, 70);
        passwordLabel.setBounds(610, 210, 100, 40);
        password.setBounds(500, 240, 310, 30);
        checkBox.setBounds(500, 270, 150, 30);
        loginButton.setBounds(500, 310, 150, 30);
        resetButton.setBounds(660, 310, 150, 30);
    }

    private void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(passwordLabel);
        container.add(password);
        container.add(loginButton);
        container.add(resetButton);
        container.add(checkBox);
    }

    private void setButtonColors() {
        loginButton.setBackground(new Color(153, 255, 51));
        resetButton.setBackground(Color.darkGray);
        resetButton.setForeground(Color.white);
    }

    private void setFontsProperties() {
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 45));
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        checkBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
        password.setFont(new Font("Times New Roman", Font.BOLD, 17));
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
        resetButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
    }

    private void addActionEvent() {
        checkBox.addActionListener(this);
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkBox) {
            if (checkBox.isSelected()) {
                password.setEchoChar((char) 0);
            } else {
                password.setEchoChar(('●'));
            }
        }

        if (e.getSource() == loginButton) {
            CreateConn createConnection = CreateConn.estConnection();
            String enteredPassword = String.valueOf(password.getPassword());
            String query = "SELECT PASSWORDKEY FROM Login";
            ResultSet resultSet;
            String storedPassword = "";

            try {
                try (Statement statement = createConnection.conn.createStatement()) {
                    resultSet = statement.executeQuery(query);
                    storedPassword = resultSet.getString(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (enteredPassword.equals(storedPassword)) {
                this.setVisible(false);
                Home objHome = new Home();
                objHome.setVisible(true);
            } else if (enteredPassword.equals("")) {
                JOptionPane.showMessageDialog(this, "Password is empty.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Password");
                password.setText("");
            }
        }

        if (e.getSource() == resetButton) {
            new ResetPasswordFrame();
        }
    }
}

class ResetPasswordFrame extends JFrame implements ActionListener {

    Container resetPasswordContainer = getContentPane();
    JLabel oldPasswordLabel = new JLabel("Enter Old Password");
    JTextField oldPasswordField = new JTextField(30);
    JLabel newPasswordLabel = new JLabel("Enter New Password");
    JTextField newPasswordField = new JTextField(30);
    JButton changePasswordButton = new JButton("Change Password");

    ResetPasswordFrame() {
        rpSetLayout();
        rpSetButtonColor();
        rpAddActionEvent();
        rpSetFontsProperties();
        rpSetLocationAndSize();
        rpSetFrameBoundsAndTitle();
        rpSetVisibilityAndResize();
        rpAddComponentsToContainer();
    }

    private void rpSetLayout() {
        setLayout(null);
    }

    private void rpSetButtonColor() {
        changePasswordButton.setBackground(new Color(153, 255, 51));
    }

    private void rpAddActionEvent() {
        changePasswordButton.addActionListener(this);
    }

    private void rpSetFontsProperties() {
        oldPasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        newPasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        changePasswordButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
    }

    private void rpSetLocationAndSize() {
        oldPasswordLabel.setBounds(20, 30, 200, 30);
        oldPasswordField.setBounds(20, 70, 310, 30);
        newPasswordLabel.setBounds(20, 110, 200, 30);
        newPasswordField.setBounds(20, 150, 310, 30);
        changePasswordButton.setBounds(20, 210, 310, 35);
    }

    private void rpSetFrameBoundsAndTitle() {
        setTitle("Change Password");
        setBounds(100, 100, 350, 330);
    }

    private void rpSetVisibilityAndResize() {
        setVisible(true);
        setResizable(false);
    }

    private void rpAddComponentsToContainer() {
        resetPasswordContainer.add(oldPasswordLabel);
        resetPasswordContainer.add(oldPasswordField);
        resetPasswordContainer.add(newPasswordLabel);
        resetPasswordContainer.add(newPasswordField);
        resetPasswordContainer.add(changePasswordButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateConn createConnection = CreateConn.estConnection();
        String enteredOldPassword = oldPasswordField.getText();
        String enteredNewPassword = newPasswordField.getText();
        String dbStoredPassword = "";
        ResultSet resultSet;

        try {
            try (Statement statement = createConnection.conn.createStatement()) {
                String query = "SELECT PASSWORDKEY FROM Login";
                resultSet = statement.executeQuery(query);
                dbStoredPassword = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (enteredOldPassword.equals(dbStoredPassword)) {
            if (enteredOldPassword.equals(enteredNewPassword)) {
                JOptionPane.showMessageDialog(this, "New password is same as old password. Enter a different password", "Alert", JOptionPane.WARNING_MESSAGE);
            } else {
                String updateQuery = "UPDATE Login SET PASSWORDKEY = ?;";
                try {
                    try (PreparedStatement ps = createConnection.conn.prepareStatement(updateQuery)) {
                        ps.setString(1, enteredNewPassword);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Password changed Successfully.");
                        setVisible(false);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (enteredOldPassword.equals("") || enteredNewPassword.equals("")) {
            JOptionPane.showMessageDialog(this, "Any one password is empty.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Old Password");
        }
    }
}

public class Login {

    public static void main(String[] args) {
        LoginFrame loginframe = new LoginFrame();
        loginframe.setTitle("Authentication");
        loginframe.setVisible(true);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setResizable(false);
    }
}
