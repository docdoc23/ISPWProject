/**
 * Edit by EC
 */
package main.java.control;

import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.Announce;
import main.java.entity.Research;
import main.java.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteFacadeController {

    private ApartmentResearchController apartmentResearchController = new ApartmentResearchController();
    private RoomResearchController roomResearchController = new RoomResearchController();

    public List<Research> findFavoriteResearches(User user) {
        List<Research> researches = new ArrayList<>();
        researches.addAll(apartmentResearchController.findFavorite(user));
        researches.addAll(roomResearchController.findFavorite(user));
        Collections.sort(researches, Collections.reverseOrder());
        return researches;
    }
}
