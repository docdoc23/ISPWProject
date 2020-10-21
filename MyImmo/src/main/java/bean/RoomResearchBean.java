package main.java.bean;

import main.java.entity.Sorting;

public class RoomResearchBean extends ResearchBean{
    private Integer roomersNumberMax;
    private Boolean privateBathroom;
    private Boolean onlyFemale;
    private Boolean onlyMale;

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

    public RoomResearchBean(String city, Double priceMin, Double priceMax, Double size, Boolean favorite,
                            Sorting sorting, Integer roomersNumberMax, Boolean privateBathroom, Boolean onlyFemale,
                            Boolean onlyMale) {
        super(city, priceMin, priceMax, size, favorite, sorting);
        this.roomersNumberMax = roomersNumberMax;
        this.privateBathroom = privateBathroom;
        this.onlyFemale = onlyFemale;
        this.onlyMale = onlyMale;


    }
}
