package test.java.dao;

import main.java.dao.UserDao;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import main.java.factory.UserFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

/**
 * Edit by EC.
 */
@RunWith(value = Parameterized.class)
public class UserDaoTest {

    private User user;
    private UserDao userDao;

    @Parameterized.Parameters
    public static Collection<Object[]> GetTestParameters() {
        return Arrays.asList(new Object[][] {
                // {nickname, name, surname, email, password, gender}
                {"nickname", "name", "surname", "email@email.com","password", 'f'},
        });
    }

    public UserDaoTest(String nickname, String name, String surname, String email, String password,
                       Character gender) {

        this.userDao = new UserDao();
        this.user = UserFactory.getUser(nickname, name, surname, email, password, gender);
    }

    /**
     * create a new entity e assert if with correct and uncorrect crediantials all works well
     */
    @Test
    public void findByNicknameAndPassword() {
        User userCreated = null;
        try {
            userCreated = userDao.create(user.getNickname(), user.getName(), user.getSurname(), user.getEmail(),
                    user.getPassword(), user.getGender().toString().toCharArray()[0]);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
        assertEquals(userDao.findByNicknameAndPassword(user.getNickname(), user.getPassword()), user);
        assertEquals(userDao.findByNicknameAndPassword(user.getNickname(), ""), null);
        assertEquals(userDao.findByNicknameAndPassword("", user.getPassword()), null);
        assertEquals(userDao.findByNicknameAndPassword("", ""), null);
        userDao.delete(userCreated.getNickname());
    }

    /**
     * create a new entity e assert if the created entity it's correct
     */
    @Test
    public void create() {
        User userCreated = null;
        try {
            userCreated = userDao.create(user.getNickname(), user.getName(), user.getSurname(), user.getEmail(),
                    user.getPassword(), user.getGender().toString().toCharArray()[0]);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
        userDao.delete(userCreated.getNickname());
        assertEquals(userCreated, user);
    }

    /**
     * assert if find method return the entity just created
     */
    @Test
    public void findByNickname() {
        User userCreated = null;
        try {
            userCreated = userDao.create(user.getNickname(), user.getName(), user.getSurname(), user.getEmail(),
                    user.getPassword(), user.getGender().toString().toCharArray()[0]);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
        assertEquals(userDao.findByNickname(userCreated.getNickname()), user);
        userDao.delete(userCreated.getNickname());
    }

    /**
     * delete a entity and assert if not exists more in db
     */
    @Test
    public void delete() {
        User userCreated = null;
        try {
            userCreated = userDao.create(user.getNickname(), user.getName(), user.getSurname(), user.getEmail(),
                    user.getPassword(), user.getGender().toString().toCharArray()[0]);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
        userDao.delete(userCreated.getNickname());
        assertEquals(userDao.findByNickname(userCreated.getNickname()), null);
    }
}