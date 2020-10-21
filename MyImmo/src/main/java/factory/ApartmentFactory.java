package main.java.factory;

//import com.sun.xml.internal.bind.v2.model.core.ID;
import main.java.entity.Announce;
import main.java.entity.ApartmentAnnounce;
import main.java.entity.User;

import java.util.GregorianCalendar;

public class ApartmentFactory {

    private ApartmentFactory(){}

    public static Announce getApartment (int ID , String city , String address , Double price , String description , double size , boolean available , GregorianCalendar date, User user, Integer locals ,
                                         boolean furnished , Integer bathroomNumber , Integer bedsNumber){

        ApartmentAnnounce apartmentAnnounce = new ApartmentAnnounce(ID ,city ,address , price , description , size , available , date , user ,locals , furnished , bathroomNumber ,
                                             bedsNumber);
        return apartmentAnnounce;
    }

    public static ApartmentAnnounce getApartmentAnnounce(Integer ID, String city , String address , Double price , String description , Double size , Boolean available , GregorianCalendar date, User user, Integer locals ,
                                                         Boolean furnished , Integer bathroomNumber , Integer bedsNumber) {

        ApartmentAnnounce apartmentAnnounce = new ApartmentAnnounce(ID, city, address, price, description, size, available, date, user, locals, furnished, bathroomNumber,
                bedsNumber);
        return apartmentAnnounce;

    }

}
