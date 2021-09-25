// @formatter:off
package com.medical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;

public class AddMedicine extends JFrame implements ActionListener {
    private JLabel titleLabel, nameLabel, shelfLabel, quantityLabel, priceLabel;
    private JTextField nameInput, shelfInput, quantityInput, priceInput;
    private JButton addButton;

    AddMedicine() {
        titleLabel = new JLabel("Enter Medicine Details");
        nameLabel = new JLabel("Name");
        shelfLabel = new JLabel("Shelf");
        quantityLabel = new JLabel("Quantity");
        priceLabel = new JLabel("Price");
        nameInput = new JTextField();
        shelfInput = new JTextField();
        quantityInput = new JTextField();
        priceInput = new JTextField();
        addButton = new JButton("Add");
        appendListener();
        setComponentStyle();
        setLayoutManager();
        addComponent();
        setOnCloseEvent();
    }

    private void appendListener() {
        addButton.addActionListener(this);

        quantityInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    quantityInput.setEditable(true);
                } else {
                    quantityInput.setEditable(false);
                }
            }
        });
        priceInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    priceInput.setEditable(true);
                } else {
                    priceInput.setEditable(false);
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        CreateConn con = CreateConn.estConnection();

        try {
            try (PreparedStatement ps = con.conn.prepareStatement("SELECT MEDICINE_NAME FROM Medicines WHERE MEDICINE_NAME LIKE ?")) {
                ps.setString(1, nameInput.getText());
                ResultSet rs = ps.executeQuery();
                if (rs.next() == false) {
                    PreparedStatement stmt = con.conn.prepareStatement("insert into Medicines values(?,?,?,?)");
                    stmt.setString(1, nameInput.getText());
                    stmt.setString(2, shelfInput.getText());
                    stmt.setInt(3, Integer.valueOf(quantityInput.getText()));
                    stmt.setInt(4, Integer.valueOf(priceInput.getText()));
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Added Successfully.");

                } else {
                    JOptionPane.showMessageDialog(this, "Medicine already exists!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
                nameInput.setText("");
                shelfInput.setText("");
                quantityInput.setText("");
                priceInput.setText("");
            }
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(this, "Unable to add try again!!!");
        }
    }

    private void setComponentStyle() {
        setLocationAndSize();
        setColor();
        setFont();
        titleLabel.setFont(new Font("Vardana", Font.BOLD, 40));
        nameLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        shelfLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        quantityLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        addButton.setFont(new Font("Vardana", Font.BOLD, 22));
        priceLabel.setFont(new Font("Vardana", Font.BOLD, 22));
    }

    private void setLocationAndSize() {
        titleLabel.setBounds(460, 100, 700, 50);
        nameLabel.setBounds(450, 210, 150, 30);
        shelfLabel.setBounds(450, 270, 150, 30);
        quantityLabel.setBounds(450, 330, 150, 30);
        priceLabel.setBounds(450, 390, 150, 30);
        nameInput.setBounds(695, 210, 310, 30);
        shelfInput.setBounds(695, 270, 310, 30);
        quantityInput.setBounds(695, 330, 310, 30);
        priceInput.setBounds(695, 390, 310, 30);
        addButton.setBounds(695, 450, 310, 30);
    }

    private void setColor() {
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
    }

    private void setFont() {
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 40));
        nameLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        nameInput.setFont(new Font("Vardana", Font.PLAIN, 17));
        shelfLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        shelfInput.setFont(new Font("Vardana", Font.PLAIN, 17));
        quantityLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        quantityInput.setFont(new Font("Vardana", Font.PLAIN, 17));
        addButton.setFont(new Font("Vardana", Font.BOLD, 22));
        priceLabel.setFont(new Font("Vardana", Font.BOLD, 22));
        priceInput.setFont(new Font("Vardana", Font.PLAIN, 17));
    }

    private void setLayoutManager() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLayout(null);
    }

    private void addComponent() {
        add(titleLabel);
        add(nameLabel);
        add(shelfLabel);
        add(nameInput);
        add(shelfInput);
        add(quantityLabel);
        add(quantityInput);
        add(addButton);
        add(priceLabel);
        add(priceInput);
    }

    private void setOnCloseEvent() {
        this.setVisible(false);
    }
}