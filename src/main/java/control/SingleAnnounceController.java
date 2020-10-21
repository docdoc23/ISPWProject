package main.java.control;

import main.java.dao.ApartmentDao;
import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.Announce;
import main.java.entity.ApartmentAnnounce;
import main.java.entity.RoomAnnounce;

public class SingleAnnounceController {

    private RoomResearchDao roomResearchDao = new RoomResearchDao();
    private RoomDao roomDao = new RoomDao();
    private ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();
    private ApartmentDao apartmentDao = new ApartmentDao();


    public static ApartmentAnnounce ShowSelectedAAnnounce(int selectedAnnounce){

        ApartmentDao apartmentDao = new ApartmentDao();
        ApartmentAnnounce apartmentAnnounce = apartmentDao.findByID(selectedAnnounce);

        return apartmentAnnounce;

    }

    public static RoomAnnounce ShowSelectedRAnnounce(int selectedAnnounce){

        RoomDao roomDao = new RoomDao();
        RoomAnnounce roomAnnounce = roomDao.findByID(selectedAnnounce);

        return roomAnnounce;
    }
}
