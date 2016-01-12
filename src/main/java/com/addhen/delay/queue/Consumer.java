package com.addhen.delay.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry Addo
 */
public class Consumer implements Callable<Boolean> {

    private BlockingQueue<DelayedEvent<Event>> mDelayedEventBlockingDeque;

    private long mDelaySeconds;

    private Task mTask;

    public Consumer(BlockingQueue<DelayedEvent<Event>> delayedEventBlockingDeque,
            long delayTimeSeconds, Task task) {
        mDelayedEventBlockingDeque = delayedEventBlockingDeque;
        mDelaySeconds = delayTimeSeconds;
        mTask = task;
    }

    @Override
    public Boolean call() {
        while (mDelayedEventBlockingDeque.peek() != null) {
            try {
                TimeUnit.SECONDS.sleep(mDelaySeconds);
                DelayedEvent<Event> delayedEvent = mDelayedEventBlockingDeque.take();
                pushEvent(delayedEvent.getEvent());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (mDelayedEventBlockingDeque.isEmpty()) {
            return true;
        }
        return false;
    }

    private void pushEvent(Event event) {
        if ((mTask != null)) {
            mTask.execute(event);
        }
    }
}
