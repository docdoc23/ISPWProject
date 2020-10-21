package test.java.dao;


import main.java.dao.RoomDao;
import main.java.dao.RoomResearchDao;
import main.java.dao.UserDao;
import main.java.entity.ApartmentAnnounce;
import main.java.entity.RoomAnnounce;
import main.java.entity.RoomResearch;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import main.java.exception.EntityNotExistException;
import main.java.factory.ApartmentFactory;
import main.java.factory.ResearchFactory;
import main.java.factory.RoomFactory;
import main.java.factory.UserFactory;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class RoomDaoTest {

    enum Type {CREATE, OTHER, CONDITION}

    private RoomDaoTest.Type type;
    private RoomAnnounce roomAnnounce;
    private RoomDao roomDao;
    private User user;
    private UserDao userDao;

    private RoomResearchDao roomResearchDao;
    private RoomResearch roomResearch;

    @Parameterized.Parameters
    public static Collection<Object[]> GetTestParameters() {
        return Arrays.asList(new Object[][] {
                // {Type ,ID, city, address, price, description, size, available, date, user , roomersNumber, privateBathroom, apartmentID
                // city, priceMin, priceMax, size, date, favorite, sorting, roomersNumberMax, privateBathroom,
                // onlyFemale, onlyMale}
                {Type.CREATE, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 3
                        ,Boolean.TRUE ,5,
                        "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.CREATE, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 3,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.CREATE, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 0,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.CREATE, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 1,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.OTHER, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 0,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.OTHER, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 2,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.CONDITION, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 2,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 800.0, 40.0, new GregorianCalendar(), Boolean.TRUE, "moreBig", 4,
                        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE},
                {Type.CONDITION, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 2,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 800.0, 40.0, new GregorianCalendar(), Boolean.TRUE, "lessBig", 4,
                        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE},
                {Type.CONDITION, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 2,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 800.0, 40.0, new GregorianCalendar(), Boolean.TRUE, "moreExpensive", 4,
                        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE},
                {Type.CONDITION, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 2,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 800.0, 40.0, new GregorianCalendar(), Boolean.TRUE, "lessExpensive", 4,
                        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE},
                {Type.CONDITION, 1 ,"Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), new User().getNickname(), 2,
                        Boolean.TRUE, 5,
                        "Roma", 200.0, 800.0, 40.0, new GregorianCalendar(), Boolean.TRUE, "moreBig", null,
                        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE},

        });
    }

    public RoomDaoTest(RoomDaoTest.Type type , int ID, String city, String address, Double price, String description, double size,
                            boolean available, GregorianCalendar date, User user, int roomersNumber,
                            boolean privateBathroom, Integer apatmentID,
                            String cityRes, Double priceMin, Double priceMax, Double sizeRes,
                            GregorianCalendar dateRes, Boolean favorite, String sorting, Integer roomersNumberMax,
                            Boolean privateBathroomRes, Boolean onlyFemale, Boolean onlyMale) {

        this.roomDao = new RoomDao();
        this.type = type;
        this.userDao = new UserDao();
        this.user = UserFactory.getUser("nickname", "name", "surname",
                "email@gmail.com", "password", 'f');
        this.roomAnnounce = RoomFactory.getRoomAnnounce(ID, city, address, price, description, size, available, date,
                this.user, roomersNumber, privateBathroom, apatmentID);
        if (type == Type.CONDITION) {
            this.roomResearchDao = new RoomResearchDao();
            this.roomResearch = ResearchFactory.getRoomResearch(null, cityRes, priceMin, priceMax, sizeRes,
                    dateRes, favorite, this.user, sorting, roomersNumberMax, privateBathroomRes, onlyFemale, onlyMale);
        }
    }

    @Before
    public void setUp() {
        try {
            userDao.create(user.getNickname(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(),
                    user.getGender().toString().toCharArray()[0]);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        userDao.delete(this.user.getNickname());
    }


    @Test
    public void create() {
        Assume.assumeTrue(type == RoomDaoTest.Type.CREATE);
        RoomAnnounce roomAnnounceCreate = roomDao.create(roomAnnounce);
        try {
            roomDao.delete(roomAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(roomAnnounceCreate, roomAnnounce);
    }

    @Test
    public void findByID() {
        Assume.assumeTrue(type == RoomDaoTest.Type.OTHER);
        RoomAnnounce roomAnnounceCreate = roomDao.create(roomAnnounce);
        assertEquals(roomDao.findByID(roomAnnounceCreate.getID()), roomAnnounce);
        try {
            roomDao.delete(roomAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test

    public void delete() {
        Assume.assumeTrue(type == RoomDaoTest.Type.OTHER);
        RoomAnnounce roomAnnounceCreate = roomDao.create(roomAnnounce);
        try {
            roomDao.delete(roomAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(roomDao.findByID(roomAnnounceCreate.getID()), null);
    }

    @Test
    public void findByCondition(){
        Assume.assumeTrue(type == Type.CONDITION);
        RoomAnnounce roomAnnounceCreate = roomDao.create(roomAnnounce);
        List<RoomAnnounce> roomAnnouncesList = roomDao.findByCondition(roomResearch);
        try {
            roomDao.delete(roomAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        boolean check = roomAnnouncesList.contains(roomAnnounceCreate);
        assertEquals(true, check);

    }

    @Test
    public void getMaxID() {
        Assume.assumeTrue(type == RoomDaoTest.Type.OTHER);
        roomDao.create(roomAnnounce);
        Integer oldMaxID = roomDao.getMaxID();
        roomDao.create(roomAnnounce);
        assertEquals(oldMaxID + 1, roomDao.getMaxID().longValue());
        try {
            roomDao.delete(oldMaxID);
            roomDao.delete(oldMaxID + 1);
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAll() {
        Assume.assumeTrue(type == RoomDaoTest.Type.OTHER);
        RoomAnnounce roomAnnounceCreate = roomDao.create(roomAnnounce);
        boolean check = roomDao.findAll().contains(roomAnnounceCreate);
        assertEquals(true, check);
        try {
            roomDao.delete(roomAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

}
