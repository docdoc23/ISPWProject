/**
 * Edit by EC.
 */

package main.java.dao;

//import com.sun.xml.internal.bind.v2.model.core.ID;
import main.java.entity.RoomResearch;
import main.java.entity.User;
import main.java.exception.EntityNotExistException;
import main.java.factory.ResearchFactory;
import main.java.utils.Database;
import main.java.utils.Date;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomResearchDao {

    private DataSource ds = new DataSource();
    private UserDao userDao = new UserDao();

    /**
     * create method. Do by EC.
     *
     * @param roomResearch
     * @return
     */
    public synchronized RoomResearch create(RoomResearch roomResearch) {
        PreparedStatement stmt = null;
        Connection conn = null;
        Database database = Database.getInstance();
        try {
            conn = this.ds.getConnection();

            String query = "insert into \"public\".\"RoomResearch\" " +
                    "(\"ID\", \"city\", \"priceMin\", \"priceMax\", \"size\", \"favorite\", \"user\", " +
                    "\"sorting\", \"privateBathroom\", \"onlyFemale\", \"onlyMale\", " +
                    "\"date\"";

            if (roomResearch.getRoomersNumberMax() != null)
                query += ", \"roomersNumberMax\"";


            query += ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";

            if (roomResearch.getRoomersNumberMax() != null)
                query += ", ?";

            query += ");";

            stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Integer ID = database.getID();
            stmt.setInt(1, ID);
            stmt.setString(2, roomResearch.getCity());
            stmt.setDouble(3, roomResearch.getPriceMin());
            stmt.setDouble(4, roomResearch.getPriceMax());
            stmt.setDouble(5, roomResearch.getSize());
            stmt.setBoolean(6, roomResearch.getFavorite());
            stmt.setString(7, roomResearch.getUser().getNickname());
            stmt.setString(8, roomResearch.getSorting().toString());
            stmt.setBoolean(9, roomResearch.getPrivateBathroom());
            stmt.setBoolean(10, roomResearch.getOnlyFemale());
            stmt.setBoolean(11, roomResearch.getOnlyMale());
            stmt.setString(12, Date.gregorianCalendarToString(roomResearch.getDate()));
            if (roomResearch.getRoomersNumberMax() != null)
                stmt.setInt(13, roomResearch.getRoomersNumberMax());

            stmt.executeUpdate();

            roomResearch.setID(ID);

            stmt.close();
            conn.close();
            return roomResearch;
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

        return null;
    }

    /**
     * Do by EC.
     *
     * @param ID
     * @return RoomResearch or null if not exists.
     */
    public RoomResearch findByID(Integer ID) {
        PreparedStatement stmt = null;
        Connection conn = null;
        RoomResearch roomResearch = null;

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"RoomResearch\" where \"ID\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, ID);
            ResultSet result = stmt.executeQuery();

            if (result.next() && roomResearch == null) {
                roomResearch = (RoomResearch) ResearchFactory.getRoomResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("roomersNumberMax"),
                        result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                        result.getBoolean("onlyMale"));
            }
            result.close();
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

        return roomResearch;
    }

    /**
     * Edit by EC.
     *
     * @param ID
     * @return
     */
    public synchronized Boolean delete(Integer ID) throws EntityNotExistException {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = this.ds.getConnection();

            if (findByID(ID) == null)
                throw new EntityNotExistException();

            stmt = conn.prepareStatement("delete from \"public\".\"RoomResearch\" where \"ID\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, ID);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            return Boolean.TRUE;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    /**
     * Edit by EC. Return the max ID for this entity. Use for initialize the ID generator.
     * @return
     */
    public Integer getMaxID() {
        PreparedStatement stmt = null;
        Connection conn = null;
        Integer maxID = null;

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select MAX(\"ID\") from \"public\".\"RoomResearch\";",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = stmt.executeQuery();

            if (result.next() && maxID == null) {
                maxID = result.getInt(1);
            }
            result.close();
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

        return maxID;
    }

    /**
     * Edit by EC.
     * @param user
     * @return
     */
    public List<RoomResearch> findFavorite(User user) {
        Statement stmt = null;
        Connection conn = null;
        List<RoomResearch> roomResearches = new ArrayList<>();

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"RoomResearch\" where \"favorite\" = true " +
                    "and \"user\" = ?;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ((PreparedStatement) stmt).setString(1, user.getNickname());
            ResultSet result = ((PreparedStatement) stmt).executeQuery();

            if (!result.first()) // rs empty
                return null;

            result.first();

            roomResearches.add(ResearchFactory.getRoomResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("roomersNumberMax"),
                    result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                    result.getBoolean("onlyMale")));


            while (result.next()) {
                roomResearches.add(ResearchFactory.getRoomResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("roomersNumberMax"),
                        result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                        result.getBoolean("onlyMale")));
            }

            result.close();
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

        return roomResearches;
    }

    /**
     * Edit by EC.
     * @param user
     * @return
     */
    public List<RoomResearch> findRecent(User user) {
        Statement stmt = null;
        Connection conn = null;
        List<RoomResearch> roomResearches = new ArrayList<>();

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"RoomResearch\" where \"user\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ((PreparedStatement) stmt).setString(1, user.getNickname());
            ResultSet result = ((PreparedStatement) stmt).executeQuery();

            if (!result.first()) // rs empty
                return null;

            result.first();

            roomResearches.add(ResearchFactory.getRoomResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("roomersNumberMax"),
                    result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                    result.getBoolean("onlyMale")));


            while (result.next()) {
                roomResearches.add(ResearchFactory.getRoomResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("roomersNumberMax"),
                        result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                        result.getBoolean("onlyMale")));
            }

            result.close();
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

        return roomResearches;
    }

    /**
     * Edit by EC.
     * @return
     */
    public List<RoomResearch> findAll() {
        Statement stmt = null;
        Connection conn = null;
        List<RoomResearch> roomResearches = new ArrayList<>();

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"RoomResearch\";",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = ((PreparedStatement) stmt).executeQuery();

            if (!result.first()) // rs empty
                return null;

            result.first();

            roomResearches.add(ResearchFactory.getRoomResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("roomersNumberMax"),
                    result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                    result.getBoolean("onlyMale")));


            while (result.next()) {
                roomResearches.add(ResearchFactory.getRoomResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("roomersNumberMax"),
                        result.getBoolean("privateBathroom"), result.getBoolean("onlyFemale"),
                        result.getBoolean("onlyMale")));
            }

            result.close();
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

        return roomResearches;
    }

}
