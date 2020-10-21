/**
 * Edit by EC
 */

package main.java.control;

import main.java.bean.ApartmentResearchBean;
import main.java.dao.ApartmentDao;
import main.java.dao.ApartmentResearchDao;
import main.java.entity.*;
import main.java.exception.CreationFailedException;
import main.java.factory.ResearchFactory;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class ApartmentResearchController {

    private ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();

    public void newApartmentResearch(ApartmentResearchBean apartmentResearchBean, User userLogged)
            throws CreationFailedException {
        // create a new istance of ApartmentResearch
        GregorianCalendar date = new GregorianCalendar();
        ApartmentResearch apartmentResearch = ResearchFactory.getApartmentResearch(null,
                apartmentResearchBean.getCity(), apartmentResearchBean.getPriceMin(),
                apartmentResearchBean.getPriceMax(), apartmentResearchBean.getSize(), date,
                apartmentResearchBean.getFavorite(), userLogged, String.valueOf(apartmentResearchBean.getSorting()),
                apartmentResearchBean.getLocalsMin(), apartmentResearchBean.getLocalsMax(),
                apartmentResearchBean.getFurnished(), apartmentResearchBean.getBathroomNumberMin(),
                apartmentResearchBean.getBedsNumberMin(),apartmentResearchBean.getBedsNumberMax());

        // save the new research on DB
        ApartmentResearch savedResearch = apartmentResearchDao.create(apartmentResearch);
        if (savedResearch == null)
            throw new CreationFailedException();

        return;
    }

    public List<ApartmentResearch> findFavorite(User user) {
        return apartmentResearchDao.findFavorite(user);
    }

    public List<ApartmentResearch> findRecent(User user) {
        return apartmentResearchDao.findRecent(user);
    }
}
