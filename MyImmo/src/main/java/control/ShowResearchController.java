package main.java.control;

import main.java.bean.ApartmentResearchBean;
import main.java.bean.ResearchBean;
import main.java.bean.RoomResearchBean;
import main.java.dao.ApartmentDao;
import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.*;
import main.java.factory.ResearchFactory;
import main.java.factory.RoomFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class ShowResearchController {

    private RoomResearchDao roomResearchDao = new RoomResearchDao();
    private RoomDao roomDao = new RoomDao();
    private ApartmentResearchDao apartmentResearchDao = new ApartmentResearchDao();
    private ApartmentDao apartmentDao = new ApartmentDao();

    public List<Announce> showCurrentResearch(ResearchBean researchBean , User loggedUser){

        List<Announce> announces = new ArrayList<Announce>();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        if (researchBean.getClass().equals(ApartmentResearchBean.class)){

            ApartmentResearch research = ResearchFactory.getApartmentResearch(null , researchBean.getCity() ,
                    researchBean.getPriceMin() , researchBean.getPriceMax() , researchBean.getSize() ,
                    gregorianCalendar , researchBean.getFavorite() , loggedUser ,researchBean.getSorting().toString() ,
                    ((ApartmentResearchBean) researchBean).getLocalsMin() ,
                    ((ApartmentResearchBean) researchBean).getLocalsMax() ,
                    ((ApartmentResearchBean) researchBean).getFurnished() ,
                    ((ApartmentResearchBean) researchBean).getBathroomNumberMin() ,
                    ((ApartmentResearchBean) researchBean).getBedsNumberMin() ,
                    ((ApartmentResearchBean) researchBean).getBedsNumberMax() );
            if (apartmentDao.findByCondition(research) != null)
                announces.addAll(apartmentDao.findByCondition(research));
        }
        else {
            RoomResearch research = ResearchFactory.getRoomResearch(null , researchBean.getCity() ,
                    researchBean.getPriceMin() , researchBean.getPriceMax() , researchBean.getSize() ,
                    gregorianCalendar , researchBean.getFavorite() , loggedUser , researchBean.getSorting().toString(),
                    ((RoomResearchBean) researchBean).getRoomersNumberMax() ,
                    ((RoomResearchBean) researchBean).getPrivateBathroom() ,
                    ((RoomResearchBean) researchBean).getOnlyFemale(), ((RoomResearchBean) researchBean).getOnlyMale());
            if (roomDao.findByCondition(research) != null)
                announces.addAll(roomDao.findByCondition(research));
            if (research.getOnlyFemale() && announces != null) {
                for (Announce announce : announces) {
                    if (announce.getUser().getGender().equals(Gender.male))
                        announces.remove(announce);
                }
            }
            if (research.getOnlyMale() && announces != null) {
                for (Announce announce : announces) {
                    if (announce.getUser().getGender().equals(Gender.female))
                        announces.remove(announce);
                }
            }
        }

        if (researchBean.getSorting().equals(Sorting.moreRecent))
            Collections.sort(announces, Collections.reverseOrder());
        if (researchBean.getSorting().equals(Sorting.lessRecent))
            Collections.sort(announces);
        return announces;


    }


}
