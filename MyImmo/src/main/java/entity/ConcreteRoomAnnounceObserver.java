package main.java.entity;

import main.java.control.RoomersStateController;

public class ConcreteRoomAnnounceObserver extends Observer {

    private RoomAnnounce roomAnnounceSubject;
    private Integer roomIDObserver;

    public ConcreteRoomAnnounceObserver(RoomAnnounce roomAnnounceSubject, Integer roomIDObserver) {
        this.roomAnnounceSubject = roomAnnounceSubject;
        this.roomIDObserver = roomIDObserver;
    }

    @Override
    public void update() {
        if (roomAnnounceSubject.getavailable())
            RoomersStateController.getInstance().updateState(roomIDObserver, 1);
        else
            RoomersStateController.getInstance().updateState(roomIDObserver, -1);
    }
}
