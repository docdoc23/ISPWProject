package main.java.control;

import main.java.dao.RoomDao;

public class RoomersStateController {

    private static RoomersStateController instance = null;
    private static RoomDao roomDao = null;

    private RoomersStateController() {}

    public static RoomersStateController getInstance() {
        if (instance == null) {
            instance = new RoomersStateController();
            roomDao = new RoomDao();
        }
        return instance;
    }

    public void updateState(Integer roomID, Integer newValue) {
        roomDao.addRoomersNumber(roomDao.findByID(roomID), newValue);
    }
}
