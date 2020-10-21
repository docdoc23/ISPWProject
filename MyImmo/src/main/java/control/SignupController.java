package main.java.control;

import main.java.dao.UserDao;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;

public class SignupController {

    private UserDao userDao = new UserDao();

    public SignupController(){}

    public User signup(String nickname, String nome , String cognome , String email , String password ,
                       Character gender) throws EntityAlreadyExistException {
        return userDao.create(nickname, nome, cognome, email, password, gender);
    }

}
