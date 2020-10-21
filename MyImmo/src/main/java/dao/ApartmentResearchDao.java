/**
 * Edit by EC.
 */

package main.java.dao;

import main.java.entity.ApartmentResearch;
import main.java.entity.User;
import main.java.exception.EntityNotExistException;
import main.java.factory.ResearchFactory;
import main.java.utils.Database;
import main.java.utils.Date;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentResearchDao {

    private DataSource ds = new DataSource();
    private UserDao userDao = new UserDao();

    /**
     * create method. Do by EC
     * @param apartmentResearch
     * @return
     */
    public synchronized ApartmentResearch create(ApartmentResearch apartmentResearch) {
        PreparedStatement stmt = null;
        Connection conn = null;
        Database database = Database.getInstance();
        try {
            conn = this.ds.getConnection();

            Integer count = 0;
            String query = "insert into \"public\".\"ApartmentResearch\" " +
                    "(\"ID\", \"city\", \"priceMin\", \"priceMax\", \"size\", \"favorite\", \"user\", " +
                    "\"sorting\", \"furnished\", \"date\"";

            if (apartmentResearch.getLocalsMin() != null) {
                query += ", \"localsMin\"";
                count++;
            }
            if (apartmentResearch.getLocalsMax() != null) {
                query += ", \"localsMax\"";
                count++;
            }
            if (apartmentResearch.getBathroomNumberMin() != null) {
                query += ", \"bathroomNumberMin\"";
                count++;
            }
            if (apartmentResearch.getBedsNumberMin() != null) {
                query += ", \"bedsNumberMin\"";
                count++;
            }
            if (apartmentResearch.getBedsNumberMax() != null) {
                query += ", \"bedsNumberMax\"";
                count++;
            }
            query += ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
            for (int i = 0; i < count; i++) {
                query += ", ?";
            }
            query += ");";
            count = 1;


            stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Integer ID = database.getID();
            stmt.setInt(1, ID);
            stmt.setString(2, apartmentResearch.getCity());
            stmt.setDouble(3, apartmentResearch.getPriceMin());
            stmt.setDouble(4, apartmentResearch.getPriceMax());
            stmt.setDouble(5, apartmentResearch.getSize());
            stmt.setBoolean(6, apartmentResearch.getFavorite());
            stmt.setString(7, apartmentResearch.getUser().getNickname());
            stmt.setString(8, apartmentResearch.getSorting().toString());
            stmt.setBoolean(9, apartmentResearch.getFurnished());
            stmt.setString(10, Date.gregorianCalendarToString(apartmentResearch.getDate()));
            if (apartmentResearch.getLocalsMin() != null) {
                stmt.setInt(10 + count, apartmentResearch.getLocalsMin());
                count++;
            }
            if (apartmentResearch.getLocalsMax() != null) {
                stmt.setInt(10 + count, apartmentResearch.getLocalsMax());
                count++;
            }
            if (apartmentResearch.getBathroomNumberMin() != null) {
                stmt.setInt(10 + count, apartmentResearch.getBathroomNumberMin());
                count++;
            }
            if (apartmentResearch.getBedsNumberMin() != null) {
                stmt.setInt(10 + count, apartmentResearch.getBedsNumberMin());
                count++;
            }
            if (apartmentResearch.getBedsNumberMax() != null) {
                stmt.setInt(10 + count, apartmentResearch.getBedsNumberMax());
                count++;
            }

            stmt.executeUpdate();

            apartmentResearch.setID(ID);

            stmt.close();
            conn.close();
            return apartmentResearch;
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
     * @param ID
     * @return ApartmentResearch or null if not exists.
     */
    public ApartmentResearch findByID(Integer ID) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ApartmentResearch apartmentResearch = null;

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"ApartmentResearch\" where \"ID\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, ID);
            ResultSet result = stmt.executeQuery();

            if (!result.first()) // rs empty
            return null;

            result.first();

            apartmentResearch = (ApartmentResearch) ResearchFactory.getApartmentResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                    result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                    result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax"));

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

        return apartmentResearch;
    }

    /**
     * Edit by EC.
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

            stmt = conn.prepareStatement("delete from \"public\".\"ApartmentResearch\" where \"ID\" = ?;",
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

            stmt = conn.prepareStatement("select MAX(\"ID\") from \"public\".\"ApartmentResearch\";",
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
    public List<ApartmentResearch> findFavorite(User user) {
        Statement stmt = null;
        Connection conn = null;
        List<ApartmentResearch> apartmentResearches = new ArrayList<>();

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"ApartmentResearch\" where \"favorite\" = true " +
                    "and \"user\" = ?;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ((PreparedStatement) stmt).setString(1, user.getNickname());
            ResultSet result = ((PreparedStatement) stmt).executeQuery();

            if (!result.first()) // rs empty
                return null;

            result.first();

            apartmentResearches.add(ResearchFactory.getApartmentResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                    result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                    result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax")));

            while (result.next()) {
                apartmentResearches.add(ResearchFactory.getApartmentResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                        result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                        result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax")));
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

        return apartmentResearches;
    }

    /**
     * Edit by EC.
     * @param user
     * @return
     */
    public List<ApartmentResearch> findRecent(User user) {
        Statement stmt = null;
        Connection conn = null;
        List<ApartmentResearch> apartmentResearches = new ArrayList<>();

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"ApartmentResearch\" where \"user\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ((PreparedStatement) stmt).setString(1, user.getNickname());
            ResultSet result = ((PreparedStatement) stmt).executeQuery();

            if (!result.first()) // rs empty
                return null;

            result.first();

            apartmentResearches.add(ResearchFactory.getApartmentResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                    result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                    result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax")));

            while (result.next()) {
                apartmentResearches.add(ResearchFactory.getApartmentResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                        result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                        result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax")));
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

        return apartmentResearches;
    }

    /**
     * Edit by EC.
     * @return
     */
    public List<ApartmentResearch> findAll() {
        Statement stmt = null;
        Connection conn = null;
        List<ApartmentResearch> apartmentResearches = new ArrayList<>();

        try {
            conn = this.ds.getConnection();

            stmt = conn.prepareStatement("select * from \"public\".\"ApartmentResearch\";",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = ((PreparedStatement) stmt).executeQuery();

            if (!result.first()) // rs empty
                return null;

            result.first();

            apartmentResearches.add(ResearchFactory.getApartmentResearch(result.getInt("ID"),
                    result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                    result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                    result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                    result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                    result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                    result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax")));

            while (result.next()) {
                apartmentResearches.add(ResearchFactory.getApartmentResearch(result.getInt("ID"),
                        result.getString("city"), result.getDouble("priceMin"), result.getDouble("priceMax"),
                        result.getDouble("size"), Date.stringToGregorianCalendar(result.getString("date")),
                        result.getBoolean("favorite"), userDao.findByNickname(result.getString("user")),
                        result.getString("sorting"), result.getInt("localsMin"), result.getInt("localsMax"),
                        result.getBoolean("furnished"), result.getInt("bathroomNumberMin"),
                        result.getInt("bedsNumberMin"), result.getInt("bedsNumberMax")));
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

        return apartmentResearches;
    }
}
