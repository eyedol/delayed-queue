package com.addhen.delay.queue;

/**
 * Implement this to provide your own action
 *
 * @author Henry Aood
 */
public interface Task {

    void execute(Event event);
}
