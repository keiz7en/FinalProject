package com.cse213.fproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login {

    @FXML
    private TextField loginUserNo;
    @FXML
    private ComboBox<String> loginDepartment;
    @FXML
    private TextField loginPassword;
    @FXML
    private Button loginButton;

    @FXML
    private TextField signupUserNo;
    @FXML
    private ComboBox<String> signupDepartment;
    @FXML
    private TextField signupPassword;
    @FXML
    private Button signupButton;

    private final String[] departments = {
            "Manager", "HR Manager", "Sales & Marketing Manager",
            "Accountant", "Supply Chain Manager", "Quality Control Manager",
            "Refinery Operator", "IT Administrator"
    };

    private final Map<String, User> userDatabase = new HashMap<>();

    @FXML
    private void initialize() {
        loginDepartment.getItems().addAll(departments);
        signupDepartment.getItems().addAll(departments);
    }

    @FXML
    private void login(ActionEvent event) {
        String userId = loginUserNo.getText();
        String password = loginPassword.getText();
        String department = loginDepartment.getValue();

        if (userDatabase.containsKey(userId)) {
            User registeredUser = userDatabase.get(userId);

            if (registeredUser.getDepartment().equals(department) && registeredUser.getPassword().equals(password)) {
                loadPage(department);
            } else {
                showAlert("Login Failed", "Department or Password is incorrect.");
            }
        } else {
            showAlert("User Not Found", "Please sign up before logging in.");
        }
    }

    @FXML
    private void signup() {
        String userId = signupUserNo.getText();
        String department = signupDepartment.getValue();
        String password = signupPassword.getText();

        if (validateUserId(userId) && department != null && !password.isEmpty()) {
            if (userDatabase.containsKey(userId)) {
                showAlert("Signup Error", "User ID already exists. Please use a different ID.");
            } else {
                userDatabase.put(userId, new User(department, password));
                showAlert("Success", "Sign Up Successful!");
            }
        } else {
            showAlert("Validation Error", "Ensure User ID is 7 digits and fields are filled.");
        }
    }

    private boolean validateUserId(String userId) {
        return userId != null && userId.matches("\\d{7}");
    }

    private void loadPage(String department) {
        String fxmlFile;
        switch (department) {
            case "Manager":
                fxmlFile = "Manager.fxml";
                break;
            case "HR Manager":
                fxmlFile = "HRManager.fxml";
                break;
            case "Sales & Marketing Manager":
                fxmlFile = "SalesMarketing.fxml";
                break;
            case "Accountant":
                fxmlFile = "Accountant.fxml";
                break;
            case "Supply Chain Manager":
                fxmlFile = "SupplyChain.fxml";
                break;
            case "Quality Control Manager":
                fxmlFile = "QualityControl.fxml";
                break;
            case "Refinery Operator":
                fxmlFile = "Refinery.fxml";
                break;
            case "IT Administrator":
                fxmlFile = "ITAdmin.fxml";
                break;
            default:
                showAlert("Invalid Department", "Please select a valid department.");
                return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class User {
        private final String department;
        private final String password;

        public User(String department, String password) {
            this.department = department;
            this.password = password;
        }

        public String getDepartment() {
            return department;
        }

        public String getPassword() {
            return password;
        }
    }
}
