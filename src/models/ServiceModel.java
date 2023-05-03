package models;

import javafx.beans.property.*;
import java.sql.Time;

public class ServiceModel {
    private final StringProperty service;
    private final StringProperty source;
    private final StringProperty destination;
    private IntegerProperty fare;
    private ObjectProperty<Time> depart;
    private ObjectProperty<Time> arrival;
    private IntegerProperty seat;
    private final StringProperty date;

    public ServiceModel(String service, String source, String destination, Integer fare, Time depart, Time arrival, Integer seat, String date) {
        this.service = new SimpleStringProperty(service);
        this.source = new SimpleStringProperty(source);
        this.destination = new SimpleStringProperty(destination);
        this.fare = new SimpleIntegerProperty(fare);
        this.depart = new SimpleObjectProperty<>(depart);
        this.arrival = new SimpleObjectProperty<>(arrival);
        this.seat = new SimpleIntegerProperty(seat);
        this.date = new SimpleStringProperty(date);
    }

    public String getService() {
        return service.get();
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public String getSource() {
        return source.get();
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public int getFare() {
        return fare.get();
    }

    public IntegerProperty fareProperty() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare.set(fare);
    }

    public Time getDepart() {
        return depart.get();
    }

    public ObjectProperty<Time> departProperty() {
        return depart;
    }

    public void setDepart(Time depart) {
        this.depart.set(depart);
    }

    public Time getArrival() {
        return arrival.get();
    }

    public ObjectProperty<Time> arrivalProperty() {
        return arrival;
    }

    public void setArrival(Time arrival) {
        this.arrival.set(arrival);
    }

    public int getSeat() {
        return seat.get();
    }

    public IntegerProperty seatProperty() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat.set(seat);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}
