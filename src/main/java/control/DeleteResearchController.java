/**
 * Edit by EC
 */
package main.java.control;

import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.ApartmentResearch;
import main.java.entity.Research;
import main.java.exception.EntityNotExistException;

public class DeleteResearchController {

    private ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();
    private RoomResearchDao roomResearchDao = new RoomResearchDao();

    public Boolean deleteResearch(Research research) {

        try {
            if (research.getClass().equals(ApartmentResearch.class)) {
                return apartmentResearchDao.delete(research.getID());
            } else {
                return roomResearchDao.delete(research.getID());
            }

        } catch (EntityNotExistException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
