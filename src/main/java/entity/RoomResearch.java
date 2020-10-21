package main.java.entity;

import main.java.utils.Date;

import java.util.GregorianCalendar;

public class RoomResearch extends Research {
    private Integer roomersNumberMax;
    private Boolean privateBathroom;
    private Boolean onlyFemale;
    private Boolean onlyMale;

    public RoomResearch(String city, Double priceMin, Double priceMax, Double size, GregorianCalendar date,
                        Boolean favorite, User user, Integer roomersNumberMax, Boolean privateBathroom,
                        Boolean onlyFemale, Boolean onlyMale) {
        super(city, priceMin, priceMax, size, date, favorite, user);
        this.roomersNumberMax = roomersNumberMax;
        this.privateBathroom = privateBathroom;
        this.onlyFemale = onlyFemale;
        this.onlyMale = onlyMale;
    }

    public RoomResearch() {

    }

    public Integer getRoomersNumberMax() {
        return roomersNumberMax;
    }

    public void setRoomersNumberMax(Integer roomersNumberMax) {
        this.roomersNumberMax = roomersNumberMax;
    }

    public Boolean getPrivateBathroom() {
        return privateBathroom;
    }

    public void setPrivateBathroom(Boolean privateBathroom) {
        this.privateBathroom = privateBathroom;
    }

    public Boolean getOnlyFemale() {
        return onlyFemale;
    }

    public void setOnlyFemale(Boolean onlyFemale) {
        this.onlyFemale = onlyFemale;
    }

    public Boolean getOnlyMale() {
        return onlyMale;
    }

    public void setOnlyMale(Boolean onlyMale) {
        this.onlyMale = onlyMale;
    }

    @Override
    public String toString() {
        return "----- RoomResearch -----\n" +
                "ID: " + this.ID +
                "\ncity: " + this.city +
                "\ndate: "  + Date.gregorianCalendarToString(this.date) +
                "\nsorting: " + this.sorting +
                "\nuser: " + this.user.getNickname() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        return this.city.equals(((RoomResearch) o).getCity()) &&
                this.priceMin.equals(((RoomResearch) o).priceMin) &&
                this.priceMax.equals(((RoomResearch) o).getPriceMax()) &&
                this.size.equals(((RoomResearch) o).getSize()) &&
                Date.gregorianCalendarToString(this.date).equals(Date.gregorianCalendarToString(((RoomResearch) o).date)) &&
                this.favorite.equals(((RoomResearch) o).getFavorite()) &&
                this.user.equals(((RoomResearch) o).getUser()) &&
                (this.roomersNumberMax == ((RoomResearch) o).getRoomersNumberMax()) &&
                this.privateBathroom.equals(((RoomResearch) o).getPrivateBathroom()) &&
                this.onlyFemale.equals(((RoomResearch) o).getOnlyFemale()) &&
                this.onlyMale.equals(((RoomResearch) o).getOnlyMale());
    }
}
