package test.java.dao;

import main.java.dao.ApartmentResearchDao;
import main.java.dao.UserDao;
import main.java.entity.ApartmentResearch;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import main.java.exception.EntityNotExistException;
import main.java.factory.ResearchFactory;
import main.java.factory.UserFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Edit by EC.
 */
@RunWith(value = Parameterized.class)
public class ApartmentResearchDaoTest {

    enum Type {CREATE, OTHER, FAVORITE}

    private Type type;
    private ApartmentResearch apartmentResearch;
    private User user;
    private ApartmentResearchDao apartmentResearchDao;
    private UserDao userDao;

    @Parameterized.Parameters
    public static Collection<Object[]> GetTestParameters() {
        return Arrays.asList(new Object[][] {
                // {Type, city, priceMin, priceMax, size, date, favorite, sorting, localsMin, localsMax, furnished,
                // bathroomMin, bedsNumberMin, bedsNumberMax}
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, 2, 5},
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", null, 3,
                        Boolean.TRUE, 1, 2, 5},
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, null,
                        Boolean.TRUE, 1, 2, 5},
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, null, 2, 5},
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, null, 5},
                {Type.CREATE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, 2, null},
                {Type.OTHER, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, 2, 5},
                {Type.FAVORITE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, 2, 5},
                {Type.FAVORITE, "Roma", 200.0, 600.0, 50.0, new GregorianCalendar(), Boolean.FALSE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, 2, 5},
        });
    }

    public ApartmentResearchDaoTest(Type type, String city, Double priceMin, Double priceMax, Double size,
                                    GregorianCalendar date, Boolean favorite, String sorting, Integer localsMin,
                                    Integer localsMax, Boolean furnished, Integer bathroomNumberMin,
                                    Integer bedsNumberMin, Integer bedsNumberMax) {

        this.apartmentResearchDao = new ApartmentResearchDao();
        this.userDao = new UserDao();
        this.user = UserFactory.getUser("nickname", "name", "surname",
                "email@gmail.com","password", 'f');
        this.apartmentResearch = ResearchFactory.getApartmentResearch(null, city, priceMin, priceMax, size,
                date, favorite, this.user, sorting, localsMin, localsMax, furnished, bathroomNumberMin,
                bedsNumberMin, bedsNumberMax);
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
        ApartmentResearch apartmentResearchCreate = apartmentResearchDao.create(apartmentResearch);
        try {
            apartmentResearchDao.delete(apartmentResearchCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(apartmentResearchCreate, apartmentResearch);
    }

    /**
     * assert if find method return the entity just created
     */
    @Test
    public void findByID() {
        Assume.assumeTrue(type == Type.OTHER);
        ApartmentResearch apartmentResearchCreate = apartmentResearchDao.create(apartmentResearch);
        assertEquals(apartmentResearchDao.findByID(apartmentResearchCreate.getID()), apartmentResearch);
        try {
            apartmentResearchDao.delete(apartmentResearchCreate.getID());
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
        ApartmentResearch apartmentResearchCreate = apartmentResearchDao.create(apartmentResearch);
        try {
            apartmentResearchDao.delete(apartmentResearchCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(apartmentResearchDao.findByID(apartmentResearchCreate.getID()), null);
    }

    /**
     * assert if inserting a new entity, the max ID correctly increase
     */
    @Test
    public void getMaxID() {
        Assume.assumeTrue(type == Type.OTHER);
        apartmentResearchDao.create(apartmentResearch);
        Integer oldMaxID = apartmentResearchDao.getMaxID();
        apartmentResearchDao.create(apartmentResearch);
        assertEquals(oldMaxID + 1, apartmentResearchDao.getMaxID().longValue());
        try {
            apartmentResearchDao.delete(oldMaxID);
            apartmentResearchDao.delete(oldMaxID + 1);
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
        ApartmentResearch apartmentResearchCreated = apartmentResearchDao.create(apartmentResearch);
        boolean check = false;
        if (apartmentResearchDao.findFavorite(user) != null)
            check = apartmentResearchDao.findFavorite(user).contains(apartmentResearchCreated);
        if (apartmentResearchCreated.getFavorite())
            assertEquals(true, check);
        else
            assertEquals(false, check);
        try {
            apartmentResearchDao.delete(apartmentResearchCreated.getID());
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
        ApartmentResearch apartmentResearchCreated = apartmentResearchDao.create(apartmentResearch);
        Integer ID = apartmentResearchCreated.getID();
        this.apartmentResearch.getDate().add(Calendar.DAY_OF_YEAR, 10);
        apartmentResearchDao.create(apartmentResearch);
        this.apartmentResearch.getDate().add(Calendar.DAY_OF_YEAR, 10);
        apartmentResearchDao.create(apartmentResearch);
        List<ApartmentResearch> apartmentResearches = apartmentResearchDao.findRecent(user);
        boolean check = true;
        for (int i = 0; i < apartmentResearches.size() - 1; i++) {
            if (apartmentResearches.get(i).compareTo(apartmentResearches.get(i + 1)) >= 0) {
                check = false;
                break;
            }
        }
        assertEquals(true, check);
        try {
            apartmentResearchDao.delete(ID);
            apartmentResearchDao.delete(ID + 1);
            apartmentResearchDao.delete(ID + 2);
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
        ApartmentResearch apartmentResearchCreated = apartmentResearchDao.create(apartmentResearch);
        boolean check = apartmentResearchDao.findAll().contains(apartmentResearchCreated);
        assertEquals(true, check);
        try {
            apartmentResearchDao.delete(apartmentResearchCreated.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }
}