package test.java.dao;


import main.java.dao.ApartmentDao;
import main.java.dao.ApartmentResearchDao;
import main.java.dao.UserDao;
import main.java.entity.ApartmentAnnounce;
import main.java.entity.ApartmentResearch;
import main.java.entity.RoomAnnounce;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import main.java.exception.EntityNotExistException;
import main.java.factory.ApartmentFactory;
import main.java.factory.ResearchFactory;
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
public class ApartmentDaoTest {

    enum Type {CREATE, OTHER, CONDITION}

    private ApartmentDaoTest.Type type;
    private ApartmentAnnounce apartmentAnnounce;
    private ApartmentDao apartmentDao;
    private User user;
    private UserDao userDao;

    private ApartmentResearch apartmentResearch;
    private main.java.dao.ApartmentResearchDao apartmentResearchDao;

    @Parameterized.Parameters
    public static Collection<Object[]> GetTestParameters() {
        return Arrays.asList(new Object[][] {
                // {Type ,ID, city, address, price, description, size, available, date, user ,locals ,furnished ,bathroomNumber ,bedsNumber }

                // {Type, city, priceMin, priceMax, size, date, favorite, sorting, localsMin, localsMax, furnished,
                // bathroomMin, bedsNumberMin, bedsNumberMax,
                // city, address, price, description, size, available, date, locals ,furnished ,bathroomNumber ,bedsNumber}
                {Type.CREATE, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreBig", 1, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), 1,
                        Boolean.TRUE, 3, 2},
                {Type.OTHER, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreBig", 1, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 50 ,Boolean.TRUE,new GregorianCalendar(), 1,
                        Boolean.TRUE, 3, 2},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreBig", 1, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "lessBig", 1, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreExpensive", 1, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "lessExpensive", 1, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", null, 3,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, null,
                        Boolean.TRUE, 1, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, null, 2, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, null, 5,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
                {Type.CONDITION, "Roma", 200.0, 800.0, 50.0, new GregorianCalendar(), Boolean.TRUE, "moreRecent", 1, 3,
                        Boolean.TRUE, 1, 2, null,
                        "Roma", "via ", 600.0, "una descrizione", 60 ,Boolean.TRUE,new GregorianCalendar(), 2,
                        Boolean.TRUE, 2, 4},
        });
    }


    public ApartmentDaoTest(Type type, String city, Double priceMin, Double priceMax, Double size,
                                    GregorianCalendar date, Boolean favorite, String sorting, Integer localsMin,
                                    Integer localsMax, Boolean furnished, Integer bathroomNumberMin,
                                    Integer bedsNumberMin, Integer bedsNumberMax,
                            String cityAp, String address, Double price, String description, double sizeAp,
                            boolean available, GregorianCalendar dateAp, Integer locals, boolean furnishedAp,
                            Integer bathroomNumber, Integer bedsNumber) {

        this.apartmentDao = new ApartmentDao();
        this.userDao = new UserDao();
        this.user = UserFactory.getUser("nickname", "name", "surname",
                "email@gmail.com","password", 'f');
        if (type == Type.CONDITION) {
            this.apartmentResearch = ResearchFactory.getApartmentResearch(null, city, priceMin, priceMax, size,
                    date, favorite, this.user, sorting, localsMin, localsMax, furnished, bathroomNumberMin,
                    bedsNumberMin, bedsNumberMax);
            this.apartmentResearchDao = new ApartmentResearchDao();
        }
        this.apartmentAnnounce = ApartmentFactory.getApartmentAnnounce(1 ,cityAp ,address , price , description ,
                sizeAp , available , dateAp , this.user ,locals , furnishedAp , bathroomNumber , bedsNumber);
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


    @Test
    public void create() {
        Assume.assumeTrue(type == ApartmentDaoTest.Type.CREATE);
        ApartmentAnnounce apartmentAnnounceCreate = apartmentDao.create(apartmentAnnounce);
        try {
            apartmentDao.delete(apartmentAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(apartmentAnnounceCreate, apartmentAnnounce);
    }

    @Test
    public void findByID() {
        Assume.assumeTrue(type == ApartmentDaoTest.Type.OTHER);
        ApartmentAnnounce apartmentAnnounceCreate = apartmentDao.create(apartmentAnnounce);
        assertEquals(apartmentDao.findByID(apartmentAnnounceCreate.getID()), apartmentAnnounce);
        try {
            apartmentDao.delete(apartmentAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        Assume.assumeTrue(type == ApartmentDaoTest.Type.OTHER);
        ApartmentAnnounce apartmentAnnounceCreate = apartmentDao.create(apartmentAnnounce);
        try {
            apartmentDao.delete(apartmentAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        assertEquals(apartmentDao.findByID(apartmentAnnounceCreate.getID()), null);
    }

    /**
     * Insert an apartment that verify the research and assert if this apartment was return by the findByCondition
     * Edit by EC.
     */
    @Test
    public void findByCondition(){
        Assume.assumeTrue(type == Type.CONDITION);
        ApartmentAnnounce apartmentAnnounceCreate = apartmentDao.create(apartmentAnnounce);
        List<ApartmentAnnounce> apartmentAnnounceList = apartmentDao.findByCondition(apartmentResearch);
        try {
            apartmentDao.delete(apartmentAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        boolean check = apartmentAnnounceList.contains(apartmentAnnounceCreate);
        assertEquals(true, check);

    }

    @Test
    public void getMaxID() {
        Assume.assumeTrue(type == ApartmentDaoTest.Type.OTHER);
        apartmentDao.create(apartmentAnnounce);
        Integer oldMaxID = apartmentDao.getMaxID();
        apartmentDao.create(apartmentAnnounce);
        assertEquals(oldMaxID + 1, apartmentDao.getMaxID().longValue());
        try {
            apartmentDao.delete(oldMaxID);
            apartmentDao.delete(oldMaxID + 1);
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAll() {
        Assume.assumeTrue(type == ApartmentDaoTest.Type.OTHER);
        ApartmentAnnounce apartmentAnnounceCreate = apartmentDao.create(apartmentAnnounce);
        boolean check = apartmentDao.findAll().contains(apartmentAnnounceCreate);
        assertEquals(true, check);
        try {
            apartmentDao.delete(apartmentAnnounceCreate.getID());
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
    }
}
