/**
 * Edit by EC.
 */

package main.java.factory;

import com.sun.istack.internal.NotNull;
import main.java.entity.*;

import java.util.GregorianCalendar;

public class ResearchFactory {

    private ResearchFactory() {}

    public static Research getResearch(String type) {
        if (type.equalsIgnoreCase("ApartmentResearch")) return new ApartmentResearch();
        if (type.equalsIgnoreCase("RoomResearch")) return new RoomResearch();
        return null;
    }

    public static ApartmentResearch getApartmentResearch(Integer ID, @NotNull String city, @NotNull Double priceMin,
                                                         @NotNull Double priceMax, @NotNull Double size,
                                                         @NotNull GregorianCalendar date, @NotNull Boolean favorite,
                                                         @NotNull User user, @NotNull String sorting,
                                                         Integer localsMin, Integer localsMax,
                                                         @NotNull Boolean furnished, Integer bathroomNumberMin,
                                                         Integer bedsNumberMin, Integer bedsNumberMax) {
        ApartmentResearch apartmentResearch = new ApartmentResearch(city, priceMin, priceMax, size, date, favorite,
                user, localsMin, localsMax, furnished, bathroomNumberMin, bedsNumberMin, bedsNumberMax);
        apartmentResearch.setSorting(sorting);
        apartmentResearch.setID(ID);
        return apartmentResearch;
    }

    public static RoomResearch getRoomResearch(Integer ID, @NotNull String city, @NotNull Double priceMin,
                                               @NotNull Double priceMax, @NotNull Double size,
                                               @NotNull GregorianCalendar date, @NotNull Boolean favorite,
                                               @NotNull User user, @NotNull String sorting,
                                               Integer roomersNumberMax, @NotNull Boolean privateBathroom,
                                               @NotNull Boolean onlyFemale, @NotNull Boolean onlyMale) {
        if (onlyFemale.equals(Boolean.TRUE) && onlyMale.equals(Boolean.TRUE))
            return null;
        RoomResearch roomResearch = new RoomResearch(city, priceMin, priceMax, size, date, favorite, user,
                roomersNumberMax, privateBathroom, onlyFemale, onlyMale);
        roomResearch.setSorting(sorting);
        roomResearch.setID(ID);
        return roomResearch;
    }
}
