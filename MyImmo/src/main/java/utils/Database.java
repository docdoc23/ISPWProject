/**
 * Edit by EC.
 */

package main.java.utils;

import main.java.dao.ApartmentDao;
import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomDao;
import main.java.dao.RoomResearchDao;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance;
    private static Integer countID;

    private Database(){}

    /**
     * Singleton pattern
     * @return
     */
    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
            countID = getMaxID();
        }
        return instance;
    }

    /**
     * Get a new ID for database
     * @return
     */
    public Integer getID() {
        increaseID();
        return countID;
    }

    public void increaseID() {
        countID++;
    }

    public static Integer getMaxID() {
        Integer ID = 0;
        List<Integer> maxIDs = new ArrayList<>();
        RoomResearchDao roomResearchDao = new RoomResearchDao();
        ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();
        RoomDao roomDao = new RoomDao();
        ApartmentDao apartmentDao = new ApartmentDao();

        maxIDs.add(roomResearchDao.getMaxID());
        maxIDs.add(apartmentResearchDao.getMaxID());
        maxIDs.add(roomDao.getMaxID());
        maxIDs.add(apartmentDao.getMaxID());

        for (Integer i : maxIDs) {
            if (i > ID)
                ID = i;
        }
        return ID;
    }
}
