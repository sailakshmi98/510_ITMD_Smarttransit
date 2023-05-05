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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.AddBusTableModel;
import models.ConnectionClass;
import models.ServiceModel;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BusDetailsController extends Thread  {
	
    @FXML
    private Label sourcelabel;
    @FXML
    private Label dlabel;
    @FXML
    private Label serlabel;
    @FXML
    private Label datelabel;
    @FXML
    private Label flabel;
    @FXML
    private TextField txtphone;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtseat;
    @FXML
    private Label avaiseats;
    @FXML
    private Label totalfare;
    // ... other fields ...
    
    @FXML
    private Button back;
    
    private boolean initDataCalled = false;

    private ServiceModel selectedService;

    public void initData(ServiceModel serviceModel) {
    	 initDataCalled = true;
    	 this.selectedService = serviceModel;
    	    
    	 if (selectedService != null) {
    	        sourcelabel.setText(serviceModel.getSource());
    	        dlabel.setText(serviceModel.getDestination());
    	        serlabel.setText(serviceModel.getService());
    	        datelabel.setText(serviceModel.getDate().toString());
    	        flabel.setText(String.valueOf(serviceModel.getFare()));
    	    }
    	 this.x = serlabel.getText();
		 this.farex = flabel.getText();
		 setcellvalue();
    	}
    
  boolean bta1, bta2, bta3, bta4, btb1, btb2, btb3, btb4, btc1, btc2, btc3, btc4, btd1, btd2, btd3, btd4;
  String x;
  static String farex;
  String services;
  String fares;
  Boolean count;
  int seatCounta = 0, seatCountb = 0, seatCountc = 0, seatCountd = 0, seatCounte = 0, seatCountf = 0, seatCountg = 0,
      seatCounth = 0, seatCounti = 0,
      seatCountj = 0, seatCountk = 0, seatCountl = 0, seatCountm = 0, seatCountn = 0, seatCounto = 0, seatCountp = 0;

  String yellow = "-fx-background-color:#ffb805";
  String red = "-fx-background-color:#FF0000";
  String green = "-fx-background-color:#39FF14";
  ArrayList<Integer> list2 = new ArrayList<>();
  ArrayList<Integer> list4 = new ArrayList<>();
  ArrayList<Integer> list = new ArrayList<Integer>();
  ArrayList<Integer> list6 = new ArrayList<Integer>();

  public Button book;
  public TextField txtsource;
  public TextField txtdest;
  public TextField txtservice;
  public TextField txtdate;
  public TextField txtfare;
  public Hyperlink logout;
  public Button proceed;
  public Button cancel;
  ResultSet resultSet;
  PreparedStatement pst;
  @FXML
  public DatePicker date;
  @FXML
  public Button bt_1;
  @FXML
  public Button a2;
  @FXML
  private Button reset;
  @FXML
  private Button A3;
  @FXML
  private Button rset;
  @FXML
  private Label welcome;
  @FXML
  private Label pending;
  @FXML
  private Button mybook;
  @FXML
  private Button bookticket;

  @FXML
  private Button A4;

  @FXML
  private Button B2;
  @FXML
  private Button B1;
  @FXML
  public Label pay;

  @FXML
  private Button B3;

  @FXML
  private Button B4;

  @FXML
  private Button C1;
  @FXML
  private Button C2;
  @FXML
  private Button C3;

  @FXML
  private Button C4;

  @FXML
  private Button D1;

  @FXML
  private Button D2;

  @FXML
  private Button D3;

  @FXML
  private Button D4;

  @FXML
  private TableView<ServiceModel> table;

  @FXML
  private TableColumn<ServiceModel, String> service;

  @FXML
  private TableColumn<ServiceModel, String> source;

  @FXML
  private TableColumn<ServiceModel, String> destination;

  @FXML
  private TableColumn<ServiceModel, Integer> fare;
  @FXML
  private TableColumn<ServiceModel, Integer> dtime;
  @FXML
  private TableColumn<ServiceModel, Integer> atime;
  @FXML
  private TableColumn<ServiceModel, Integer> seats;
  @FXML
  private Pane box_bus;

  public void setSelectedService(ServiceModel selectedService) {
	    this.selectedService = selectedService;
	}


  public void fare(String f) {
	    this.farex = f;
	  }
	  
  
  public void handleReservedButton(ActionEvent event) {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("Reservation Warning");
	    alert.setHeaderText(null);
	    alert.setContentText("Reserved! Can't book.");

	    alert.showAndWait();
  }


  public void initialize() {
	  
	    connect();
	    createTable_seats();
	    if (initDataCalled) {
	        sourcelabel.setText(selectedService.getSource());
	        dlabel.setText(selectedService.getDestination());
	        serlabel.setText(selectedService.getService());
	        datelabel.setText(selectedService.getDate().toString());
	        flabel.setText(String.valueOf(selectedService.getFare()));
		    setcellvalue();
	    }
	   
	}

  
  public void connect() {
	    ConnectionClass connectionClass = new ConnectionClass();
	    Connection connection = connectionClass.getConnection();
	    ObservableList<Object> data = FXCollections.observableArrayList();

	    try {
	        // Create the smart_transit_book table if it doesn't exist
	        String createTableQuery = "CREATE TABLE IF NOT EXISTS smart_transit_book (" +
	                                  "`from` VARCHAR(255)," +
	                                  "`to` VARCHAR(255)" +
	                                  ");";

	        PreparedStatement book = connection.prepareStatement(createTableQuery);
	        book.executeUpdate();

	        // Select data from the smart_transit_book table
	        pst = connection.prepareStatement("SELECT * FROM smart_transit_book");
	        resultSet = pst.executeQuery();

	        // Populate ComboBox UI elements with the retrieved data
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
  
  public void viewbuses_users(ActionEvent actionEvent) {
      ObservableList<ServiceModel>  list = FXCollections.observableArrayList();
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();

      try {
          PreparedStatement ps =connection.prepareStatement("select * from smart_transit_search");
          ResultSet rs = ps.executeQuery();

          while (rs.next()){

               list.add(new ServiceModel(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getTime(5),rs.getTime(6),rs.getInt(7),rs.getString(8)));
          

          }
      } catch (SQLException t) {
          t.printStackTrace();
      }


  }



  @FXML
  void A1(ActionEvent event) {
    String service1 = x;
    if (service1 == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='A1' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();

        } else {
          bt_1.setOnAction(e -> {
            bta1 = true;
            seatCounta = 1;
            bt_1.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  }

  @FXML
  void A2(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='A2' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1 || bta2) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          a2.setOnAction(e -> {
            bta2 = true;
            seatCountb = 1;
            a2.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  }

  @FXML
  void a3(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='A3' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          A3.setOnAction(e -> {
            bta3 = true;
            seatCountc = 1;
            A3.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void a4(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='A4' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          A4.setOnAction(e -> {
            bta4 = true;
            seatCountd = 1;
            A4.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void b1(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='B1' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          B1.setOnAction(e -> {
            btb1 = true;
            seatCounte = 1;
            B1.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  }

  @FXML
  void b2(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='B2' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          B2.setOnAction(e -> {
            btb2 = true;
            seatCountf = 1;
            B2.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void b3(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='B3' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          B3.setOnAction(e -> {
            btb3 = true;
            seatCountg = 1;
            B3.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void b4(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='B4' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          B4.setOnAction(e -> {
            btb4 = true;
            seatCounth = 1;
            B4.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void c1(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='C1' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          C1.setOnAction(e -> {
            btc1 = true;
            seatCounti = 1;
            C1.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void c2(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='C2' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          C2.setOnAction(e -> {
            btc2 = true;
            seatCountj = 1;
            C2.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void c3(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='C3' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          C3.setOnAction(e -> {
            btc3 = true;
            seatCountk = 1;
            C3.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void c4(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='C4' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          C4.setOnAction(e -> {
            btc4 = true;
            seatCountl = 1;
            C4.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void d1(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='D1' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          D1.setOnAction(e -> {
            btd1 = true;
            seatCountm = 1;
            D1.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void d2(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='D2' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          D3.setOnAction(e -> {
            btd2 = true;
            seatCountn = 1;
            D3.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void d3(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='D3' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          D3.setOnAction(e -> {
            btd3 = true;
            seatCounto = 1;
            D3.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  @FXML
  void d4(ActionEvent event) {
    String service1 = x;
    if (x == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setContentText("Select a bus first!");
      Optional<ButtonType> result = alert.showAndWait();

    } else {
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      try {
        PreparedStatement ps1 = connection
            .prepareStatement("select count from smart_transit_seats WHERE seatname='D4' and service='" + service1 + "'");
        ResultSet r = ps1.executeQuery();
        r.next();
        int s = r.getInt(1);
        if (s == 1) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setAlertType(Alert.AlertType.WARNING);
          alert.setContentText("oops! The seat is booked already. Select another");
          Optional<ButtonType> result = alert.showAndWait();
        } else {
          D4.setOnAction(e -> {
            btd4 = true;
            seatCountp = 1;
            D4.setStyle(yellow);
            txtseat.setText(String.valueOf(seatCounta + seatCountb + seatCountc + seatCountd + seatCounte + seatCountf
                + seatCountg + seatCounth +
                seatCounti + seatCountj + seatCountk + seatCountl + seatCountm + seatCountn + seatCounto + seatCountp));

          });
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  public void selection() {
    String service1 = x;

    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    if (bta1) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='A1' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (bta2) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='A2' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (bta3) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='A3' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (bta4) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='A4' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btc1) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='C1' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btc2) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='C2' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btc3) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='C3' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btc4) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='c4' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (btb1) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='B1' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btb2) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='B2' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btb3) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='B3' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btb4) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='B4' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btd1) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='D1' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btd2) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='D2' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btd3) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='D3' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (btd4) {
      try {
        PreparedStatement ps = connection
            .prepareStatement("UPDATE smart_transit_seats SET count = 1 WHERE seatname='D4' and service='" + service1 + "'");
        ps.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }


  public void setcellvalue() {

	    String service1 = x;
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();
      PreparedStatement pst;
      try {
        pst = connection.prepareStatement("SELECT count FROM smart_transit_seats WHERE service='" + service1 + "'");

        ResultSet resultSet = pst.executeQuery();

        while (resultSet.next()) {
          list.add(resultSet.getInt(1));

        }
        if (list.get(0) == 1) {
          bt_1.setStyle("-fx-background-color:red");
        }
        if (list.get(1) == 1) {
          a2.setStyle("-fx-background-color:red");

        }
        if (list.get(2) == 1) {
          A3.setStyle("-fx-background-color:red");
        }
        if (list.get(3) == 1) {
          A4.setStyle("-fx-background-color:red");

        }
        if (list.get(4) == 1) {
          B1.setStyle("-fx-background-color:red");
        }

        if (list.get(5) == 1) {
          B2.setStyle("-fx-background-color:red");
        }
        if (list.get(6) == 1) {
          B3.setStyle("-fx-background-color:red");

        }
        if (list.get(7) == 1) {
          B4.setStyle("-fx-background-color:red");
        }
        if (list.get(8) == 1) {
          C1.setStyle("-fx-background-color:red");

        }
        if (list.get(9) == 1) {
          C2.setStyle("-fx-background-color:red");
        }
        if (list.get(10) == 1) {
          C3.setStyle("-fx-background-color:red");

        }
        if (list.get(11) == 1) {
          C4.setStyle("-fx-background-color:red");
        }
        if (list.get(12) == 1) {
          D1.setStyle("-fx-background-color:red");

        }
        if (list.get(13) == 1) {
          D2.setStyle("-fx-background-color:red");
        }
        if (list.get(14) == 1) {
          D3.setStyle("-fx-background-color:red");

        }
        if (list.get(15) == 1) {
          D4.setStyle("-fx-background-color:red");

        }

      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }

    };
    
    @FXML
    void back_(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/Views/BookView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  

  @FXML
  void rset(ActionEvent event) {

    String service1 = x;

    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();
    try {
      PreparedStatement ps1 = connection.prepareStatement(
          "select count,uname from smart_transit_seats WHERE (seatname='A1' or seatname='A2' or seatname='A3' or seatname='A4' or seatname='B1' or seatname='B2' or seatname='B3' or seatname='B4' or seatname='C1' or seatname='C2' or seatname='C3' or seatname='C4' or seatname='D1' or seatname='D2' or seatname='D3' or seatname='D4') and service='"
              + service1 + "'");
      ResultSet r = ps1.executeQuery();
      while (r.next()) {
        list2.add(r.getInt(1));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    rset.setOnAction(e -> {
      txtseat.setText("");
      if (list2.get(0) == 0) {
        bta1 = false;
        seatCounta = 0;
        bt_1.setStyle(green);

      }
      if (list2.get(1) == 0) {
        bta2 = false;
        seatCountb = 0;
        a2.setStyle(green);

      }
      if (list2.get(2) == 0) {
        bta3 = false;
        seatCountc = 0;
        A3.setStyle(green);

      }
      if (list2.get(3) == 0) {
        bta4 = false;
        seatCountd = 0;
        A4.setStyle(green);

      }
      if (list2.get(4) == 0) {
        btb1 = false;
        seatCounte = 0;
        B1.setStyle(green);
      }
      if (list2.get(5) == 0) {
        btb2 = false;
        seatCountf = 0;
        B2.setStyle(green);

      }
      if (list2.get(6) == 0) {
        btb3 = false;
        seatCountg = 0;
        B3.setStyle(green);

      }
      if (list2.get(7) == 0) {
        btb4 = false;
        seatCounth = 0;
        B4.setStyle(green);
      }
      if (list2.get(8) == 0) {
        btc1 = false;
        seatCounti = 0;
        C1.setStyle(green);
      }
      if (list2.get(9) == 0) {
        btc2 = false;
        seatCountj = 0;

        C2.setStyle(green);
      }
      if (list2.get(10) == 0) {
        btc3 = false;
        seatCountk = 0;

        C3.setStyle(green);
      }
      if (list2.get(11) == 0) {
        btc4 = false;
        seatCountl = 0;
        C4.setStyle(green);
      }
      if (list2.get(12) == 0) {
        btd1 = false;
        seatCountm = 0;

        D1.setStyle(green);

      }
     
      if (list2.get(14) == 0) {
        btd3 = false;

        seatCounto = 0;

        D3.setStyle(green);
      }
      if (list2.get(15) == 0) {
        btd4 = false;
        seatCountp = 0;

        D4.setStyle(green);
      }

    });

  }
  private void createTable_seats() {
  	
	  String createTableQuery = "CREATE TABLE IF NOT EXISTS smart_transit_seats (" +
				"seatname VARCHAR(255) NOT NULL," +
				"uname VARCHAR(255) NOT NULL," +
				"service VARCHAR(255) NOT NULL," +
				"count INT NOT NULL," +
				"id INT AUTO_INCREMENT PRIMARY KEY" +
				")";

		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();
		
		try {
		PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
		createTableStatement.execute();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		}
  public void bookticket(ActionEvent event) throws IOException {
	    String se = x;

	    book.setOnAction(e -> {
	    	
	    	book.setDisable(true);
	    	
	        ConnectionClass connectionClass = new ConnectionClass();
	        Connection connection = connectionClass.getConnection();
	        PreparedStatement pst;
	        try {
	            pst = connection.prepareStatement("SELECT count FROM smart_transit_seats WHERE service='" + se + "'");

	            ResultSet resultSet = pst.executeQuery();

	            while (resultSet.next()) {
	                list6.add(resultSet.getInt(1));
	            }
	        } catch (Exception ei) {
	            System.out.println(ei);
	        }
	        
	 

	        if ((list6.get(0) == 1 && bta1)
	                || (list6.get(2) == 1 && bta3)
	                || (list6.get(3) == 1 && bta4)
	                || (list6.get(4) == 1 && btb1)
	                || (list6.get(5) == 1 && btb2)
	                || (list6.get(6) == 1 && btb3)
	                || (list6.get(7) == 1 && btb4)
	                || (list6.get(8) == 1 && btc1)
	                || (list6.get(9) == 1 && btc2)
	                || (list6.get(10) == 1 && btc3)
	                || (list6.get(11) == 1 && btc4)
	                || (list6.get(12) == 1 && btd1)
	                || (list6.get(13) == 1 && btd2)
	                || (list6.get(14) == 1 && btd3)
	                || (list6.get(15) == 1 && btd4)) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Sorry! The Seat is booked");
	            alert.showAndWait();
	        } else {

	            selection();

	            
	            String name = txtname.getText();
	            String no = txtphone.getText();
	            String seatss = txtseat.getText();
	            String date = datelabel.getText();
	            String far = flabel.getText();
	            String sourc = sourcelabel.getText();
	            String des = dlabel.getText();
	            String ser = serlabel.getText();
	            
	            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
	            alert1.setTitle("Confirmation Dialog");
	            alert1.setHeaderText(null);
	            alert1.setContentText("Booking confirmed, proceed to checkout.");
	            alert1.showAndWait();
	            

	            if (far == null || far.isEmpty() || seatss == null || seatss.isEmpty()) {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Please enter valid fare and seat values.");
	                alert.showAndWait();
	            } else {
	                try {
	                    int tot = Integer.parseInt(far) * Integer.parseInt(seatss);
	                    totalfare.setText(String.valueOf(tot));
	                    String tfare = totalfare.getText();
	                    fare(tfare);
	                    Statement statement = connection.createStatement();
	                    int status = statement.executeUpdate(
	                            "insert into smart_transit_bookings values('" + name + "','" + no + "','" + sourc + "','" + des + "','" + ser + "'," +
	                                    "'" + date + "','" + seatss + "','" + tfare + "')");
	                    
	                    
	                    
	                    if (status > 0) {

	            

          } else {
              Alert alert = new Alert(Alert.AlertType.NONE);
              alert.setAlertType(Alert.AlertType.ERROR);
              alert.setContentText("Invalid ");
              alert.show();
          }

      } catch (NumberFormatException ex) {
    	  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	  alert.setContentText("Booking Confirmed.");
          alert.showAndWait();
      } catch (SQLException throwables) {
          throwables.printStackTrace();
      }
    }
  }
	        book.setDisable(false);
	    });
  }

  public void logout(ActionEvent actionEvent) {

    Parent parent = null;
    try {
      parent = FXMLLoader.load(getClass().getResource("/views/SampleView.fxml"));
      Scene scene = new Scene(parent);
      Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      window.setScene(scene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void proceed(ActionEvent actionEvent) {

    try {
      Parent parent = FXMLLoader.load(getClass().getResource("/views/PaymentView.fxml"));
      Scene scene = new Scene(parent);
      Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      window.setScene(scene);
      window.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void cancel(ActionEvent actionEvent) {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("Are you sure ?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {

      try {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/BookView.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

  }

  @FXML
  void mybook(ActionEvent event) {
    try {
      Parent parent = FXMLLoader.load(getClass().getResource("/views/MyBookingView.fxml"));
      Scene scene = new Scene(parent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(scene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  

  @FXML
  void reset(ActionEvent event) {
    try {

      Parent parent = FXMLLoader.load(getClass().getResource("/views/BookView.fxml"));
      Scene scene = new Scene(parent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(scene);
      window.show();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
