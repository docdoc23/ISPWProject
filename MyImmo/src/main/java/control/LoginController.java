package main.java.control;

import main.java.dao.UserDao;
import main.java.entity.User;
import main.java.exception.EntityNotExistException;

public class LoginController {

    private UserDao userdao = new UserDao();

    public LoginController() {}

    public User login(String nickname, String password) throws EntityNotExistException {
        return userdao.findByNicknameAndPassword(nickname, password);

    }
}
