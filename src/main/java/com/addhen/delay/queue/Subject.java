package com.addhen.delay.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Henry Addo
 */
public class Subject {

    private List<Observer> mObservers;

    private Event mEvent;

    public Subject() {
        mObservers = new ArrayList<Observer>();
    }

    public void setEvent(Event event) {
        mEvent = event;
        notifyAllObservers();
    }

    public Event getEvent() {
        return mEvent;
    }

    public void addObserver(Observer observer) {
        mObservers.add(observer);
    }

    private void notifyAllObservers() {
        for (Observer observer : mObservers) {
            observer.update();
        }
    }
}