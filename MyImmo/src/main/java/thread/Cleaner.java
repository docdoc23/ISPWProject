package main.java.thread;

import main.java.dao.ApartmentResearchDao;
import main.java.dao.RoomResearchDao;
import main.java.entity.ApartmentResearch;
import main.java.entity.Research;
import main.java.exception.EntityNotExistException;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Cleaner implements Runnable {

    private Integer sleepingMillisTime;
    private Integer days;
    private ApartmentResearchDao apartmentResearchDao;
    private RoomResearchDao roomResearchDao;

    public Cleaner(Integer sleepingMillisTime, Integer days) {
        this.sleepingMillisTime = sleepingMillisTime;
        this.days = days;
        apartmentResearchDao = new ApartmentResearchDao();
        roomResearchDao = new RoomResearchDao();
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Research> researches = new ArrayList<>();
                if (apartmentResearchDao.findAll() != null)
                    researches.addAll(apartmentResearchDao.findAll());
                if (roomResearchDao.findAll() != null)
                    researches.addAll(roomResearchDao.findAll());
                GregorianCalendar date = new GregorianCalendar();
                for (Research research : researches) {
                    if ((date.getTimeInMillis() - research.getDate().getTimeInMillis())/(24*3600*1000) > days) {
                        if (research.getClass().equals(ApartmentResearch.class))
                            apartmentResearchDao.delete(research.getID());
                        else
                            roomResearchDao.delete(research.getID());
                    }
                }
                Thread.sleep(this.sleepingMillisTime);
            } catch (EntityNotExistException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
