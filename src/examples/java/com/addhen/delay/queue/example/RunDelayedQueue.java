package com.addhen.delay.queue.example;

import com.addhen.delay.queue.DelayedEvent;
import com.addhen.delay.queue.DelayedQueue;
import com.addhen.delay.queue.Event;
import com.addhen.delay.queue.Subject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class RunDelayedQueue {

    private static final long DELAY_IN_SECONDS = 60;

    public static void main(String args[]) throws Exception {
        BlockingQueue<DelayedEvent<Event>> blockingQueue = new PriorityBlockingQueue();
        Subject subject = new Subject();
        DelayedQueue delayedQueue = new DelayedQueue(subject, blockingQueue);

        delayedQueue.enqueueEvent("key", "Message one", DELAY_IN_SECONDS);

        // Simulate delay before adding item two
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {

        }*/
        delayedQueue.enqueueEvent("key", "Message two", DELAY_IN_SECONDS);
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {

        }*/

        // Simulate delay before adding item three
        delayedQueue.enqueueEvent("key", "Message three", DELAY_IN_SECONDS);
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {

        }*/
        delayedQueue.enqueueEvent("key1", "Message Four", DELAY_IN_SECONDS);
        delayedQueue.enqueueEvent("key", "Message Five", DELAY_IN_SECONDS);
        delayedQueue.enqueueEvent("key", "Message Six", DELAY_IN_SECONDS);
        delayedQueue.enqueueEvent("key2", "Message Seven", DELAY_IN_SECONDS);
        delayedQueue.shutDown();
        System.out.println("Queue is being processed");
    }
}
