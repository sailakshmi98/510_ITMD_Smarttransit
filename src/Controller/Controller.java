package Controller;

import javafx.event.ActionEvent;

import org.mindrot.jbcrypt.BCrypt;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import models.ConnectionClass;
import models.MyBookingModel;

public class Controller extends Thread implements Initializable {

    public TextField textField;
    public Label textLabel;
    public PasswordField passwordField;
    public Button button;
    public Hyperlink signUp; // Change this line to use Hyperlink instead of Button
    public Button adminlogin;
    ResultSet resultSet = null;
    PreparedStatement pst = null;

    public void button(ActionEvent actionEvent) {
        String username = textField.getText();
        String password = passwordField.getText();

        if (username.equals("") && password.equals("")) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Invalid username or password");
            a.show();
        } else {
            try {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                pst = connection.prepareStatement("select * from smart_transit_users where username=?");
                pst.setString(1, username);
                resultSet = pst.executeQuery();

                if (resultSet.next()) {
                    String storedPasswordHash = resultSet.getString("password");

                    if (BCrypt.checkpw(password, storedPasswordHash)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookView.fxml"));
                        Parent parent = loader.load();

                        Scene scene = new Scene(parent, 800, 600);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(scene);
                        window.show();
                        window.setMaximized(false);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid username or password");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.NONE);
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid username or password");
                    alert.show();
                }
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> textField.requestFocus());
    }


    public void signup(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/SignupView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    



    public void adminlogin(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/AdminloginView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}