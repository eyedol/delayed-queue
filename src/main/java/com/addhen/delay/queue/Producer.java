package com.addhen.delay.queue;

import java.util.concurrent.BlockingQueue;

/**
 * @author Henry Addo
 */
public class Producer implements Runnable {

    private BlockingQueue<DelayedEvent<Event>> mDelayedEventBlockingDeque;

    private Event mEvent;

    public Producer(Event event, BlockingQueue<DelayedEvent<Event>> delayedEventBlockingDeque) {
        mDelayedEventBlockingDeque = delayedEventBlockingDeque;
        mEvent = event;
    }

    private void enqueueEvent() {
        DelayedEvent<Event> delayedEvent = new DelayedEvent<Event>(mEvent);
        // Don't add duplicated items to the queue. If there is an existing key and same message don't
        // don't add it to the queue.
        if (!mDelayedEventBlockingDeque.contains(delayedEvent)) {
            mDelayedEventBlockingDeque.offer(delayedEvent);
        }
    }

    @Override
    public void run() {
        enqueueEvent();
    }
}
