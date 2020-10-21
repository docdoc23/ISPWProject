package main.java.entity;

import main.java.control.RoomersStateController;
import main.java.utils.Date;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class RoomAnnounce extends Announce {
    private int roomersNumber;
    private boolean privateBathroom;
    private List<User> roomers;
    private Integer apartmentID;

    public RoomAnnounce(int ID, String city, String address, Double price, String description, double size,
                        boolean available, GregorianCalendar date, User user, int roomersNumber,
                        boolean privateBathroom, Integer apartmentID) {
        super(ID, city, address, price, description, size, available, date, user);
        this.roomersNumber = roomersNumber;
        this.privateBathroom = privateBathroom;
        this.roomers = new ArrayList<>();
        this.apartmentID = apartmentID;
    }

    public RoomAnnounce(int roomersNumber, boolean privateBathroom, Integer apartmentID) {
        this.roomersNumber = roomersNumber;
        this.privateBathroom = privateBathroom;
        this.roomers = new ArrayList<>();
        this.apartmentID = apartmentID;
    }

    public RoomAnnounce() {

    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
        this.notifyObservers();
    }

    public Integer getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(Integer apartmentID) {
        this.apartmentID = apartmentID;
    }

    public int getRoomersNumber() {
        return roomersNumber;
    }

    public void setRoomersNumber(int roomersNumber) {
        this.roomersNumber = roomersNumber;
    }

    public boolean isPrivateBathroom() {
        return privateBathroom;
    }

    public void setPrivateBathroom(boolean privateBathroom) {
        this.privateBathroom = privateBathroom;
    }

    public List<User> getRoomers() {
        return roomers;
    }

    public void setRoomers(List<User> roomers) {
        this.roomers = roomers;
    }

    @Override
    public String toString() {
        return "----- RoomAnnounce -----\n" +
                "ID: " + this.ID +
                "\ncity: " + this.city +
                "\ndate: "  + Date.gregorianCalendarToString(this.date) +
                "\nuser: " + this.user.getNickname() +
                "\nsize: " + this.size +
                "\nprice: " + this.price + "\n";
    }

    @Override
    public boolean equals(Object o) {
        return this.city.equals(((RoomAnnounce) o).getCity()) &&
                this.price.equals(((RoomAnnounce) o).price) &&
                this.size == (((RoomAnnounce) o).getSize()) &&
                Date.gregorianCalendarToString(this.date).equals(Date.gregorianCalendarToString(((RoomAnnounce) o).date)) &&
                this.user.equals(((RoomAnnounce) o).getUser()) &&
                (this.privateBathroom == ((RoomAnnounce) o).isPrivateBathroom());
    }
}
