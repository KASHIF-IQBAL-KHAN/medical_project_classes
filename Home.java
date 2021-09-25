package com.medical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {
    private JButton addMedicineButton, deleteMedicineButton, updateMedicineButton, selectMedicineButton, logoutButton;
    private JLabel notificationLabel;

    Home() {
        notificationLabel = new JLabel("Notifications");
        addMedicineButton = new JButton("Add Medicine");
        deleteMedicineButton = new JButton("Delete Medicine");
        updateMedicineButton = new JButton("Update Medicine");
        selectMedicineButton = new JButton("Select Medicine");
        logoutButton = new JButton("Logout");

        appendListener();
        setComponentStyle();
        setLayoutManager();
        addComponent();
        setOnCloseEvent();
    }

    private void setComponentStyle() {
        setLocationAndSize();
        setColor();
        setFont();
    }

    private void setLocationAndSize() {
        notificationLabel.setBounds(590, 300, 250, 40);
        addMedicineButton.setBounds(300, 100, 350, 55);
        deleteMedicineButton.setBounds(700, 100, 350, 55);
        updateMedicineButton.setBounds(300, 200, 350, 55);
        selectMedicineButton.setBounds(700, 200, 350, 55);
        logoutButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 150, 20, 100, 30);
    }

    private void setColor() {
        addMedicineButton.setBackground(Color.BLACK);
        addMedicineButton.setForeground(Color.white);
        deleteMedicineButton.setBackground(Color.BLACK);
        deleteMedicineButton.setForeground(Color.white);
        updateMedicineButton.setBackground(Color.BLACK);
        updateMedicineButton.setForeground(Color.white);
        selectMedicineButton.setBackground(Color.BLACK);
        selectMedicineButton.setForeground(Color.white);
        logoutButton.setBackground(Color.BLACK);
        logoutButton.setForeground(Color.white);
        notificationLabel.setForeground(Color.BLACK);
    }

    private void setFont() {
        notificationLabel.setFont(new Font("Vardana", Font.BOLD, 30));
        addMedicineButton.setFont(new Font("Vardana", Font.PLAIN, 30));
        deleteMedicineButton.setFont(new Font("Vardana", Font.PLAIN, 30));
        updateMedicineButton.setFont(new Font("Vardana", Font.PLAIN, 30));
        selectMedicineButton.setFont(new Font("Vardana", Font.PLAIN, 30));
        logoutButton.setFont(new Font("Vardana", Font.BOLD, 17));
    }

    private void setLayoutManager() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLayout(null);
    }

    private void addComponent() {
        add(notificationLabel);
        add(addMedicineButton);
        add(deleteMedicineButton);
        add(updateMedicineButton);
        add(selectMedicineButton);
        add(logoutButton);
    }

    private void appendListener() {
        addMedicineButton.addActionListener(this);
        deleteMedicineButton.addActionListener(this);
        updateMedicineButton.addActionListener(this);
        selectMedicineButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add Medicine":
                AddMedicine obj = new AddMedicine();
                obj.setVisible(true);
                break;
            case "Delete Medicine":
                DeleteMedicineFrame obj3 = new DeleteMedicineFrame();
                obj3.setVisible(true);
                break;
            case "Update Medicine":
                UpdateMedicineFrame obj2 = new UpdateMedicineFrame();
                obj2.setVisible(true);
                break;
            case "Select Medicine":
                SelectMedicine obj1 = new SelectMedicine();
                obj1.setVisible(true);
                break;
            case "Logout":
                this.setVisible(false);
                LoginFrame obj4 = new LoginFrame();
                obj4.setVisible(true);
                break;
        }
    }

    private void setOnCloseEvent() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}