package test.java.dao;

import main.java.dao.RoomResearchDao;
import main.java.dao.UserDao;
import main.java.entity.RoomResearch;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import main.java.exception.EntityNotExistException;
import main.java.factory.ResearchFactory;
import main.java.factory.UserFactory;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Edit by EC.
 */
@RunWith(value = Parameterized.class)
public class RoomResearchDaoTest {

    enum Type {CREATE, OTHER, FAVORITE}

    private Type type;
    private RoomResearch roomResearch;
    private User user;
    private RoomResearchDao roomResearchDao;
    private UserDao userDao;

    @Parameterized.Parameters
    public static Collection<Object[]> GetTestParameters() {
        return Arrays.asList(new Object[][] {
                // {Type, city, priceMin, priceMax, size, date, favorite, sorting, roomersNumberMax, privateBathroom,
                // onlyFemale, onlyMale}
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", null,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.OTHER, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.FAVORITE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
                {Type.FAVORITE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.FALSE, "moreRecent", 4,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
        });
    }

    public RoomResearchDaoTest(Type type, String city, Double priceMin, Double priceMax, Double size,
                                    GregorianCalendar date, Boolean favorite, String sorting, Integer roomersNumberMax,
                                    Boolean privateBathroom, Boolean onlyFemale, Boolean onlyMale) {

        this.roomResearchDao = new RoomResearchDao();
        this.userDao = new UserDao();
        this.user = UserFactory.getUser("nickname", "name", "surname",
                "email@email.com","password", 'f');
        this.roomResearch = ResearchFactory.getRoomResearch(null, city, priceMin, priceMax, size,
                date, favorite, this.user, sorting, roomersNumberMax, privateBathroom, onlyFemale, onlyMale);
        this.type = type;
    }

    @Before
    public void setUp() {
        try {
            userDao.create(user.getNickname(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getGender().toString().toCharArray()[0]);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        userDao.delete(this.user.getNickname());
    }

    /**
     * create a new entity e assert if the created entity it's correct
     */
    @Test
    public void create() {
        Assume.assumeTrue(type == Type.CREATE);
        RoomResearch roomResearchCreated = roomResearchDao.create(roomResearch);
        try {
            roomResearchDao.delete(roomResearchCreated.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(roomResearchCreated, roomResearch);
    }

    /**
     * assert if find method return the entity just created
     */
    @Test
    public void findByID() {
        Assume.assumeTrue(type == Type.OTHER);
        RoomResearch roomResearchCreated = roomResearchDao.create(roomResearch);
        assertEquals(roomResearchDao.findByID(roomResearchCreated.getID()), roomResearch);
        try {
            roomResearchDao.delete(roomResearchCreated.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a entity and assert if not exists more in db
     */
    @Test
    public void delete() {
        Assume.assumeTrue(type == Type.OTHER);
        RoomResearch roomResearchCreated = roomResearchDao.create(roomResearch);
        try {
            roomResearchDao.delete(roomResearchCreated.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(roomResearchDao.findByID(roomResearchCreated.getID()), null);
    }

    /**
     * assert if inserting a new entity, the max ID correctly increase
     */
    @Test
    public void getMaxID() {
        Assume.assumeTrue(type == Type.OTHER);
        roomResearchDao.create(roomResearch);
        Integer oldMaxID = roomResearchDao.getMaxID();
        roomResearchDao.create(roomResearch);
        assertEquals(oldMaxID + 1, roomResearchDao.getMaxID().longValue());
        try {
            roomResearchDao.delete(oldMaxID);
            roomResearchDao.delete(oldMaxID + 1);
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    /**
     * add a favorite and a not favorite entity on db and assert if they are return or not by findFavorite method
     */
    @Test
    public void findFavorite() {
        Assume.assumeTrue(type == Type.FAVORITE);
        RoomResearch roomResearchCreated = roomResearchDao.create(roomResearch);
        boolean check = false;
        if (roomResearchDao.findFavorite(user) != null)
            check = roomResearchDao.findFavorite(user).contains(roomResearchCreated);
        if (roomResearchCreated.getFavorite())
            assertEquals(true, check);
        else
            assertEquals(false, check);
        try {
            roomResearchDao.delete(roomResearchCreated.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    /**
     * add three entity with different date and check that are return in chronological order
     */
    @Test
    public void findRecent() {
        Assume.assumeTrue(type == Type.OTHER);
        RoomResearch roomResearchCreated = roomResearchDao.create(roomResearch);
        Integer ID = roomResearchCreated.getID();
        this.roomResearch.getDate().add(Calendar.DAY_OF_YEAR, 10);
        roomResearchDao.create(roomResearch);
        this.roomResearch.getDate().add(Calendar.DAY_OF_YEAR, 10);
        roomResearchDao.create(roomResearch);
        List<RoomResearch> roomResearches = roomResearchDao.findRecent(user);
        boolean check = true;
        for (int i = 0; i < roomResearches.size() - 1; i++) {
            if (roomResearches.get(i).compareTo(roomResearches.get(i + 1)) >= 0) {
                check = false;
                break;
            }
        }
        assertEquals(true, check);
        try {
            roomResearchDao.delete(ID);
            roomResearchDao.delete(ID + 1);
            roomResearchDao.delete(ID + 2);
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    /**
     * add an entity and assert if the return of findAll method contains it
     */
    @Test
    public void findAll() {
        Assume.assumeTrue(type == Type.OTHER);
        RoomResearch roomResearchCreated = roomResearchDao.create(roomResearch);
        boolean check = roomResearchDao.findAll().contains(roomResearchCreated);
        assertEquals(true, check);
        try {
            roomResearchDao.delete(roomResearchCreated.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }
}