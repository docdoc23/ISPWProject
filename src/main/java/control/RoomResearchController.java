package main.java.control;

import main.java.bean.ApartmentResearchBean;
import main.java.bean.RoomResearchBean;
import main.java.dao.RoomDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.*;
import main.java.exception.CreationFailedException;
import main.java.factory.ResearchFactory;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class RoomResearchController {

    private RoomResearchDao roomResearchDao = new RoomResearchDao();

    public void newRoomResearch(RoomResearchBean roomResearchBean, User userLogged) throws CreationFailedException {
        // create a new istance of RoomResearch
        GregorianCalendar date = new GregorianCalendar();
        RoomResearch roomResearch = ResearchFactory.getRoomResearch(null, roomResearchBean.getCity(),
                roomResearchBean.getPriceMin(), roomResearchBean.getPriceMax(), roomResearchBean.getSize(), date,
                roomResearchBean.getFavorite(), userLogged, String.valueOf(roomResearchBean.getSorting()),
                roomResearchBean.getRoomersNumberMax(), roomResearchBean.getPrivateBathroom(),
                roomResearchBean.getOnlyFemale(), roomResearchBean.getOnlyMale());

        // save the new research on DB
        RoomResearch savedResearch = roomResearchDao.create(roomResearch);
        if (savedResearch == null)
            throw new CreationFailedException();

        return;
    }

    public List<RoomResearch> findFavorite(User user) {
        return roomResearchDao.findFavorite(user);
    }

    public List<RoomResearch> findRecent(User user) {
        return roomResearchDao.findRecent(user);
    }
}
