package com.addhen.delay.queue;


/**
 * Observer base class
 *
 * @author Henry Addo
 */
public abstract class Observer {

    protected Subject mSubject;

    public abstract void update();
}
