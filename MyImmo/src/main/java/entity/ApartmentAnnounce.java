package main.java.entity;

import main.java.utils.Date;

import java.util.GregorianCalendar;

public class ApartmentAnnounce extends Announce {
    private Integer locals;
    private Boolean furnished;
    private Integer bathroomNumber;
    private Integer bedsNumber;


    public ApartmentAnnounce(int ID, String city, String address, Double price, String description, double size, boolean available, GregorianCalendar date, User user, Integer locals, Boolean furnished, Integer bathroomNumber, Integer bedsNumber) {
        super(ID, city, address, price, description, size, available, date, user);
        this.locals = locals;
        this.furnished = furnished;
        this.bathroomNumber = bathroomNumber;
        this.bedsNumber = bedsNumber;
    }

    public ApartmentAnnounce(int locals, boolean furnished, int bathroomNumber, int bedsNumber) {
        this.locals = locals;
        this.furnished = furnished;
        this.bathroomNumber = bathroomNumber;
        this.bedsNumber = bedsNumber;
    }

    public ApartmentAnnounce() {}

    public Integer getLocals() {
        return locals;
    }

    public void setLocals(Integer locals) {
        this.locals = locals;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Integer getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(Integer bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public Integer getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(Integer bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    @Override
    public String toString() {
        return "----- ApartmentAnnounce -----\n" +
                "ID: " + this.ID +
                "\ncity: " + this.city +
                "\ndate: "  + Date.gregorianCalendarToString(this.date) +
                "\nuser: " + this.user.getNickname() +
                "\nsize: " + this.size +
                "\nprice: " + this.price + "\n";
    }

    @Override
    public boolean equals(Object o) {
        return this.city.equals(((ApartmentAnnounce) o).getCity()) &&
                this.price.equals(((ApartmentAnnounce) o).price) &&
                this.size == (((ApartmentAnnounce) o).getSize()) &&
                Date.gregorianCalendarToString(this.date).equals(Date.gregorianCalendarToString(((ApartmentAnnounce) o).date)) &&
                this.user.equals(((ApartmentAnnounce) o).getUser()) &&
                (this.locals == ((ApartmentAnnounce) o).getLocals()) &&
                this.furnished.equals(((ApartmentAnnounce) o).getFurnished()) &&
                (this.bathroomNumber == ((ApartmentAnnounce) o).getBathroomNumber()) &&
                (this.bedsNumber == ((ApartmentAnnounce) o).getBedsNumber());
    }
}

