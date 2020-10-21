package main.java.factory;

import main.java.entity.Announce;
import main.java.entity.RoomAnnounce;
import main.java.entity.User;

import java.util.GregorianCalendar;
import java.util.List;

public class RoomFactory {

    private RoomFactory() {}

    public static Announce getRoom (int ID, String city, String address, Double price, String description, double size, boolean available,
                                    GregorianCalendar date, User user, int roomersNumber, boolean privateBathroom, Integer apartmentID){

        RoomAnnounce roomAnnounce = new RoomAnnounce(ID ,city, address, price, description, size, available, date,
                user, roomersNumber, privateBathroom, apartmentID);
        return roomAnnounce;
    }

    public static RoomAnnounce getRoomAnnounce (int ID, String city, String address, Double price, String description, double size, boolean available,
                                    GregorianCalendar date, User user, int roomersNumber, boolean privateBathroom, Integer apartmentID){

        RoomAnnounce roomAnnounce = new RoomAnnounce(ID ,city, address, price, description, size, available, date, user, roomersNumber, privateBathroom, apartmentID);
        return roomAnnounce;
    }
}
