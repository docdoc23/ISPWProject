package test.java;

import main.java.dao.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = {
        test.java.dao.ApartmentResearchDaoTest.class,
        test.java.dao.RoomResearchDaoTest.class,
        test.java.dao.UserDaoTest.class,
        test.java.dao.ApartmentDaoTest.class,
        test.java.dao.RoomDaoTest.class,
})
public class TestSuite {
}
