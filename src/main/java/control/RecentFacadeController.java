package main.java.control;

import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.Research;
import main.java.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecentFacadeController {

    private ApartmentResearchController apartmentResearchController = new ApartmentResearchController();
    private RoomResearchController roomResearchController= new RoomResearchController();

    public List<Research> recentResearch(User user) {
        List<Research> researches = new ArrayList<>();
        researches.addAll(apartmentResearchController.findRecent(user));
        researches.addAll(roomResearchController.findRecent(user));
        Collections.sort(researches, Collections.reverseOrder());
        return researches;
    }
}
