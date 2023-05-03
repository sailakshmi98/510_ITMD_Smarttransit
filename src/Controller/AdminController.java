package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    public Button addbus;
    @FXML
    public Button viewbookings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addbus(ActionEvent actionEvent) {
        addbus.setStyle("-fx-background-color:  #0598ff");

        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/views/AddBusView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void viewbookings(ActionEvent actionEvent) {
        {

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/views/ViewBookingsView.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (IOException e) {

            }
        }

    }

    @FXML
    public void logout(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/SampleView.fxml"));
            Scene scene = new Scene(parent, 800, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.setMaximized(false);
        } catch (IOException e) {

        }
    }
}
