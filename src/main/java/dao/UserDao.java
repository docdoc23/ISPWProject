package main.java.dao;

import main.java.entity.Gender;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import main.java.exception.EntityNotExistException;
import main.java.factory.UserFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private DataSource ds = new DataSource();
    private RoomDao roomDao;

    public User findByNicknameAndPassword(String nickname, String password) {
        PreparedStatement stmt = null;
        Connection conn = null;
        User u = null;
        try {
            conn = this.ds.getConnection();



            stmt = conn.prepareStatement("select * from \"public\".\"Users\" " +
                    "where \"nickname\" = ? and \"password\" = ?;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, nickname);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (!rs.first()) // rs empty
                return null;

            boolean moreThanOne = rs.first() && rs.next();


            rs.first();

            String name = rs.getString("nome");
            String surname = rs.getString("cognome");
            String nicknameLoaded = rs.getString("nickname");
            String email = rs.getString("email");
            Character gender = rs.getString("gender").toCharArray()[0];



            u = UserFactory.getUser(nicknameLoaded, name, surname, email, "", gender);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return u;
    }

    public synchronized User create(String nickname, String nome , String cognome , String email , String password ,
                       Character gender) throws EntityAlreadyExistException{
        PreparedStatement stmt = null;
        Connection conn = null;
        User v = null;
        try {
            conn = this.ds.getConnection();

            if (findByNickname(nickname) != null)
                throw new EntityAlreadyExistException();

            stmt = conn.prepareStatement("insert into \"public\".\"Users\" (\"nickname\" , \"nome\" , \"cognome\" , " +
                    "\"email\" , \"password\" , \"gender\") values (?,?,?,?,?,?);",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, nickname);
            stmt.setString(2, nome);
            stmt.setString(3, cognome);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setString(6, String.valueOf(gender));
            stmt.executeUpdate();

            v = UserFactory.getUser(nickname, nome, cognome, email, "", gender);

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return v;
    }

    public User findByNickname(String nickname) {
        PreparedStatement stmt = null;
        Connection conn = null;
        User u = null;
        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"Users\" where \"nickname\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, nickname);
            ResultSet rs = stmt.executeQuery();

            if (!rs.first()) // rs empty
                return null;

            rs.first();

            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String nicknameLoaded = rs.getString("nickname");
            String email = rs.getString("email");
            Character gender = rs.getString("gender").toCharArray()[0];

            u = new User(nicknameLoaded, nome, cognome, email, "");
            u.setGender(gender);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return u;
    }

    /**
     * Edit by EC.
     *
     * @param nickname
     * @return
     */
    public synchronized Boolean delete(String nickname) {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = this.ds.getConnection();

            if (findByNickname(nickname) == null)
                throw new EntityNotExistException();

            stmt = conn.prepareStatement("delete from \"public\".\"Users\" where \"nickname\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, nickname);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            return Boolean.TRUE;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return Boolean.FALSE;
    }

    public List<User> findByRoomRented(Integer roomID) {
        PreparedStatement stmt = null;
        Connection conn = null;
        List<User> users = new ArrayList<>();
        roomDao = new RoomDao();
        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"Users\" where \"roomRented\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, roomID);
            ResultSet rs = stmt.executeQuery();

            if (!rs.first()) // rs empty
                return null;

            rs.first();

            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String nicknameLoaded = rs.getString("nickname");
            String email = rs.getString("email");
            Character gender = rs.getString("gender").toCharArray()[0];
            User user = new User(nicknameLoaded, nome, cognome, email, "");
            user.setGender(gender);
            user.setRoomRented(roomDao.findByID(roomID));
            users.add(user);
            while (rs.next()) {
                nome = rs.getString("nome");
                cognome = rs.getString("cognome");
                nicknameLoaded = rs.getString("nickname");
                email = rs.getString("email");
                gender = rs.getString("gender").toCharArray()[0];
                user = new User(nicknameLoaded, nome, cognome, email, "");
                user.setGender(gender);
                user.setRoomRented(roomDao.findByID(roomID));
                users.add(user);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return users;
    }

}