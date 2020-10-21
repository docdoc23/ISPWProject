package test.java;

import main.java.dao.ApartmentDao;
import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomResearchDao;
import main.java.dao.UserDao;
import main.java.entity.ApartmentResearch;
import main.java.entity.RoomResearch;
import main.java.entity.User;

import java.util.GregorianCalendar;

public class Test {


    public static void main(String args[]) {
        try {
            UserDao userDao = new UserDao();
            User u = userDao.create("pippo", "pippo ", "de pippis", "pippo@gmail.com",
                    "pippo", 'm');
            System.out.println(u);
            u = userDao.findByNicknameAndPassword("pippo", "pippo");
            System.out.println(u);
            u = userDao.findByNickname("pippo");
            System.out.println(u);

            ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();
            GregorianCalendar date = new GregorianCalendar();
            ApartmentResearch apartmentResearch = new ApartmentResearch("Roma", 200.0, 800.0,
                    50.0, date, Boolean.TRUE, u, 1, 3, Boolean.TRUE,
                    1, 2, 4);
            apartmentResearch.setSorting("moreRecent");
            apartmentResearch = apartmentResearchDao.create(apartmentResearch);
            System.out.println(apartmentResearchDao.findByID(apartmentResearch.getID()));

            RoomResearchDao roomResearchDao = new RoomResearchDao();
            RoomResearch roomResearch = new RoomResearch("Roma", 200.0, 800.0, 50.0, date,
                    Boolean.TRUE, u, 3, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
            roomResearch.setSorting("moreRecent");
            roomResearch = roomResearchDao.create(roomResearch);
            System.out.println(roomResearchDao.findByID(roomResearch.getID()));

            ApartmentDao apartmentDao = new ApartmentDao();
            System.out.println(apartmentDao.findAll());

            apartmentResearchDao.delete(apartmentResearch.getID());
            roomResearchDao.delete(roomResearch.getID());
            userDao.delete(u.getNickname());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
