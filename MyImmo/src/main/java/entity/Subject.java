package main.java.entity;

import java.util.*;

public abstract class Subject {

    private List<Observer> observers;

    public Subject() {
        this((Observer) null);
    }

    public Subject(Observer obs) {
        this(new Vector<>());
        if (obs != null)
            this.observers.add(obs);
    }

    public Subject(List<Observer> list) {
        this.observers = list;
    }

    public void attach(Observer obs) {
        this.observers.add(obs);
    }

    public void detach(Observer obs) {
        this.observers.remove(obs);
    }

    public void notifyObservers() {
        if (this.observers != null){
            Iterator<Observer> i = this.observers.iterator();
            while (i.hasNext()){
                Observer obs = i.next();
                obs.update();
            }
        }
    }
}
