package test.java;

import main.java.dao.*;
import main.java.entity.*;
import main.java.factory.ApartmentFactory;
import main.java.factory.RoomFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreationDatabase {

    public static void main(String args[]) {
        try {
            UserDao userDao = new UserDao();
            User u;
            if (userDao.findByNickname("emanuele") == null) {
                u = userDao.create("emanuele", "Emanuele ", "Camarda",
                        "emanuelecamarda@gmail.com", "emanuele", 'm');
            } else {
                u = userDao.findByNickname("emanuele");
            }

            // ----------- Apartment Research ------------------------

            ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();
            GregorianCalendar date = new GregorianCalendar();
            ApartmentResearch apartmentResearch = new ApartmentResearch("Roma", 200.0, 800.0,
                    50.0, date, Boolean.TRUE, u, 1, 3, Boolean.TRUE,
                    1, 2, 4);
            apartmentResearch.setSorting("moreRecent");
            apartmentResearch = apartmentResearchDao.create(apartmentResearch);

            date.add(Calendar.DAY_OF_YEAR, -1);
            apartmentResearch = new ApartmentResearch("Roma", 150.0, 600.0,
                    30.0, date, Boolean.FALSE, u, 1, 3, Boolean.TRUE,
                    1, 2, 4);
            apartmentResearch.setSorting("lessRecent");
            apartmentResearch = apartmentResearchDao.create(apartmentResearch);

            date.add(Calendar.DAY_OF_YEAR, -2);
            apartmentResearch = new ApartmentResearch("Roma", 400.0, 1000.0,
                    80.0, date, Boolean.TRUE, u, 1, 3, Boolean.TRUE,
                    1, 2, 4);
            apartmentResearch.setSorting("lessBig");
            apartmentResearch = apartmentResearchDao.create(apartmentResearch);

            date.add(Calendar.DAY_OF_YEAR, -1);
            apartmentResearch = new ApartmentResearch("Roma", 350.0, 800.0,
                    30.0, date, Boolean.TRUE, u, 1, 3, Boolean.TRUE,
                    1, 2, 4);
            apartmentResearch.setSorting("lessExpensive");
            apartmentResearch = apartmentResearchDao.create(apartmentResearch);

            date.add(Calendar.DAY_OF_YEAR, -3);
            apartmentResearch = new ApartmentResearch("Roma", 150.0, 600.0,
                    30.0, date, Boolean.FALSE, u, 1, 3, Boolean.TRUE,
                    1, 2, 4);
            apartmentResearch.setSorting("moreExpensive");
            apartmentResearch = apartmentResearchDao.create(apartmentResearch);

            // ---------------- Room Research -------------------------

            RoomResearchDao roomResearchDao = new RoomResearchDao();
            date = new GregorianCalendar();
            RoomResearch roomResearch = new RoomResearch("Roma", 200.0, 800.0, 10.0, date,
                    Boolean.FALSE, u, 3, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
            roomResearch.setSorting("moreRecent");
            roomResearch = roomResearchDao.create(roomResearch);

            date.add(Calendar.DAY_OF_YEAR, -1);
            roomResearch = new RoomResearch("Roma", 250.0, 600.0, 10.0, date,
                    Boolean.FALSE, u, 3, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
            roomResearch.setSorting("lessRecent");
            roomResearch = roomResearchDao.create(roomResearch);

            date.add(Calendar.DAY_OF_YEAR, -5);
            roomResearch = new RoomResearch("Roma", 150.0, 700.0, 15.0, date,
                    Boolean.FALSE, u, 3, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
            roomResearch.setSorting("lessBig");
            roomResearch = roomResearchDao.create(roomResearch);

            date.add(Calendar.DAY_OF_YEAR, -3);
            roomResearch = new RoomResearch("Roma", 100.0, 900.0, 15.0, date,
                    Boolean.FALSE, u, 3, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
            roomResearch.setSorting("lessExpensive");
            roomResearch = roomResearchDao.create(roomResearch);

            date.add(Calendar.DAY_OF_YEAR, -1);
            roomResearch = new RoomResearch("Roma", 180.0, 650.0, 12.0, date,
                    Boolean.FALSE, u, 3, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
            roomResearch.setSorting("moreExpensive");
            roomResearch = roomResearchDao.create(roomResearch);

            // ----------- Locator -------------------

            User locator;
            if (userDao.findByNickname("locator") == null) {
                locator = userDao.create("locator", "Pippo", "De Pippis",
                        "pippodepippis@gmail.com", "locator", 'm');
            } else {
                locator = userDao.findByNickname("locator");
            }

            // --------------- Apartment -----------------

            ApartmentDao apartmentDao = new ApartmentDao();
            date = new GregorianCalendar();
            ApartmentAnnounce apartmentAnnounce = (ApartmentAnnounce) ApartmentFactory.getApartment(1, "Roma",
                    "Via delle Marmore", 650.0, "An apartment", 55.0, Boolean.TRUE, date,
                    locator, 3, Boolean.TRUE, 1, 4);
            apartmentAnnounce = apartmentDao.create(apartmentAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -1);
            apartmentAnnounce = (ApartmentAnnounce) ApartmentFactory.getApartment(1, "Roma",
                    "Via delle Robinie", 850.0, "An apartment", 75.0, Boolean.FALSE, date,
                    locator, 3, Boolean.TRUE, 1, 4);
            apartmentAnnounce = apartmentDao.create(apartmentAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -2);
            apartmentAnnounce = (ApartmentAnnounce) ApartmentFactory.getApartment(1, "Roma",
                    "Via delle More", 600.0, "An apartment", 48.0, Boolean.TRUE, date,
                    locator, 3, Boolean.TRUE, 1, 4);
            apartmentAnnounce = apartmentDao.create(apartmentAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -5);
            apartmentAnnounce = (ApartmentAnnounce) ApartmentFactory.getApartment(1, "Roma",
                    "Via delle Mele", 700.0, "An apartment", 65.0, Boolean.FALSE, date,
                    locator, 3, Boolean.TRUE, 1, 4);
            apartmentAnnounce = apartmentDao.create(apartmentAnnounce);

            date.add(Calendar.DAY_OF_YEAR, 0);
            apartmentAnnounce = (ApartmentAnnounce) ApartmentFactory.getApartment(1, "Roma",
                    "Via delle Pesche", 500.0, "An apartment", 50.0, Boolean.TRUE, date,
                    locator, 3, Boolean.TRUE, 1, 4);
            apartmentAnnounce = apartmentDao.create(apartmentAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -1);
            apartmentAnnounce = (ApartmentAnnounce) ApartmentFactory.getApartment(1, "Roma",
                    "Via delle Robinie", 720.0, "An apartment", 88.0, Boolean.TRUE, date,
                    locator, 3, Boolean.TRUE, 1, 4);
            apartmentAnnounce = apartmentDao.create(apartmentAnnounce);

            // ---------------- Room ---------------------------

            RoomDao roomDao = new RoomDao();
            date = new GregorianCalendar();
            RoomAnnounce roomAnnounce = (RoomAnnounce) RoomFactory.getRoom(1, "Roma", "Via dei Gelsi",
                    350.0,"A Room", 18.0, Boolean.TRUE, date, locator, 2,
                    Boolean.FALSE, null);
            roomAnnounce = roomDao.create(roomAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -1);
            roomAnnounce = (RoomAnnounce) RoomFactory.getRoom(1, "Roma", "Via dei Frassini",
                    400.0,"A Room", 25.0, Boolean.TRUE, date, locator, 2,
                    Boolean.FALSE,null);
            roomAnnounce = roomDao.create(roomAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -3);
            roomAnnounce = (RoomAnnounce) RoomFactory.getRoom(1, "Roma", "Via dei Gelsomini",
                    500.0,"A Room", 28.0, Boolean.TRUE, date, locator, 2,
                    Boolean.FALSE,null);
            roomAnnounce = roomDao.create(roomAnnounce);

            date.add(Calendar.DAY_OF_YEAR, 0);
            roomAnnounce = (RoomAnnounce) RoomFactory.getRoom(1, "Roma", "Via dei Fabbri",
                    250.0,"A Room", 19.0, Boolean.TRUE, date, locator, 2,
                    Boolean.FALSE,null);
            roomAnnounce = roomDao.create(roomAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -2);
            roomAnnounce = (RoomAnnounce) RoomFactory.getRoom(1, "Roma", "Via dei Colombi",
                    300.0,"A Room", 15.0, Boolean.FALSE, date, locator, 2,
                    Boolean.FALSE,null);
            roomAnnounce = roomDao.create(roomAnnounce);

            date.add(Calendar.DAY_OF_YEAR, -1);
            roomAnnounce = (RoomAnnounce) RoomFactory.getRoom(1, "Roma", "Via dei Frati",
                    450.0,"A Room", 22.0, Boolean.FALSE, date, locator, 2,
                    Boolean.FALSE,null);
            roomAnnounce = roomDao.create(roomAnnounce);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
