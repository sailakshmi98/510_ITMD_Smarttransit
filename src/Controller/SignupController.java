package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.ConnectionClass;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import org.mindrot.jbcrypt.BCrypt; 

public class SignupController implements Initializable {

    @FXML
    public TextField textField;
    @FXML
    public PasswordField passwordField;

    @FXML
    public Button button;
    public TextField fname, lname, phone, age, state, city;
    public RadioButton male;
    public ToggleGroup gender;
    public RadioButton female;
    public Button login;
    public TextField email;
    // @FXML
    // public TextField textField1;

    @FXML
    private void signup(ActionEvent actionEvent) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        try {

            Statement statement = connection.createStatement();
            
            Statement users = connection.createStatement();
            
            String sql = "CREATE TABLE IF NOT EXISTS smart_transit_users (" +
                    "username VARCHAR(255) PRIMARY KEY, " +
                    "password VARCHAR(255), " +
                    "fname VARCHAR(255), " +
                    "lname VARCHAR(255), " +
                    "phone VARCHAR(255), " +
                    "age INT, " +
                    "state VARCHAR(255), " +
                    "city VARCHAR(255), " +
                    "gender VARCHAR(255), " +
                    "email VARCHAR(255)" +
                    ");";
           users.executeUpdate(sql);

            PreparedStatement stmt = connection.prepareStatement("insert into smart_transit_users values (?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, textField.getText());
            
            String password = passwordField.getText();
            String phoneNumber = phone.getText();
            String ageStr = age.getText();
            
            if (!isValidPassword(password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Password");
                alert.setHeaderText(null);
                alert.setContentText("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
                alert.show();
                return;
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Phone Number");
                alert.setHeaderText(null);
                alert.setContentText("Phone number must be exactly 10 digits long.");
                alert.show();
                return;
            }

            if (!isValidAge(ageStr)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Age");
                alert.setHeaderText(null);
                alert.setContentText("Age must be a 1 or 2 digit number greater than 0 and less than 100.");
                alert.show();
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            stmt.setString(2, hashedPassword);
            
            stmt.setString(3, fname.getText());
            stmt.setString(4, lname.getText());
            stmt.setString(5, phoneNumber);
            stmt.setString(6, ageStr);
            stmt.setString(7, state.getText());
            stmt.setString(8, city.getText());
            if (this.male.isSelected()) {
                stmt.setString(9, "Male");
            } else {
                stmt.setString(9, "Female");
            }
            stmt.setString(10, email.getText());
            
            int status = stmt.executeUpdate();
            if (status > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("User registration successful. Click OK to go to the login screen.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    goToLogin(actionEvent);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Invalid ");
                alert.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()-_=+\\|[{]};:'\",<.>/?].*");
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidAge(String age) {
        if (age.matches("\\d{1,2}") && !age.equals("0")) {
            int ageValue = Integer.parseInt(age);
            return ageValue >= 1 && ageValue <= 99;
        }
        return false;
    }

    
    public void goToLogin(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/SampleView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> textField.requestFocus());
    }


    public void login(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/SampleView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
    @FXML
    public void focusTextField(MouseEvent event) {
        textField.requestFocus();
    }
    
    public void cancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/views/SampleView.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
