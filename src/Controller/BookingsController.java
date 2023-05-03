
package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.ConnectionClass;
import models.ViewBookingsModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BookingsController implements Initializable {
    @FXML
    private TableView<ViewBookingsModel> table;
    @FXML
    private TableColumn<ViewBookingsModel, String> name;
    @FXML
    public TableColumn<ViewBookingsModel, Integer> phone;
    @FXML
    public TableColumn<ViewBookingsModel, String> source;
    @FXML
    public TableColumn<ViewBookingsModel, String> destination;
    @FXML
    public TableColumn<ViewBookingsModel, String> service;
    @FXML
    public TableColumn<ViewBookingsModel, Time> depart;
    @FXML
    public TableColumn<ViewBookingsModel, Time> arrival;
    @FXML
    public TableColumn<ViewBookingsModel, String> date;
    @FXML
    public TableColumn<ViewBookingsModel, Integer> seats;
    @FXML
    public TableColumn<ViewBookingsModel, Integer> amount;
    @FXML
    public Button back;
    @FXML
    public Button logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	createProjectBookingsTable();
    	initCol();
        load();

    }
    
    private void createProjectBookingsTable() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS smart_transit_bookings ("
                    + "name VARCHAR(255) NOT NULL,"
                    + "phone VARCHAR(255) NOT NULL,"
                    + "source VARCHAR(255) NOT NULL,"
                    + "destination VARCHAR(255) NOT NULL,"
                    + "service VARCHAR(255) NOT NULL,"
                    + "date DATE NOT NULL,"
                    + "seats INT NOT NULL,"
                    + "amount INT NOT NULL"
                    + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(createTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initCol() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        seats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void load() {
        ObservableList<ViewBookingsModel> list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select * from smart_transit_bookings");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                list.add(new ViewBookingsModel(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getString(8)));

                table.setItems(list);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addbus(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/AddBusView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/SampleView.fxml"));
            Scene scene = new Scene(parent, 800, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.setMaximized(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
