package com.addhen.delay.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Requirements:
 *
 * Design and code a general system that receives events, and sends events no more than once a
 * minute.
 *
 * - If multiple events with the same "key" come in within that minute, these are "rolled up" and
 * only one event is sent out at the end of the minute.
 * - There is never more than a one minute delay between an incoming event and when an outgoing
 * event is sent out.
 * - Events are only sent out if there was at least one incoming event.
 *
 * Usage:
 *
 * DelayedQueue delayedQueue = new DelayedQueue();
 *
 * @author Henry Addo
 */
public class DelayedQueue extends Observer {

    private final BlockingQueue<DelayedEvent<Event>> mDelayedEvents;

    private Subject mSubject;

    private long mDelayTimeInSeconds;

    private final ExecutorService mConsumerExecutor = Executors.newSingleThreadExecutor();

    private final ExecutorService mProducerExecutor = Executors.newSingleThreadExecutor();

    private Future<Boolean> mFuture;

    public DelayedQueue(Subject subject, BlockingQueue<DelayedEvent<Event>> delayQueue) {
        mDelayedEvents = delayQueue;
        mSubject = subject;
        mSubject.addObserver(this);
    }

    public void enqueueEvent(String key, String message, long delayTimeInSeconds) {
        mDelayTimeInSeconds = delayTimeInSeconds;
        mSubject.setEvent(new Event(key, message));
    }

    @Override
    public void update() {
        Producer producer = new Producer(mSubject.getEvent(), mDelayedEvents);
        // Add item to the queue
        mProducerExecutor.submit(producer);
        processQueue();
    }

    private void processQueue() {
        Consumer consumer = new Consumer(mDelayedEvents, mDelayTimeInSeconds, new PrintEventTask());
        // Execute a task in the queue async
        mFuture = mConsumerExecutor.submit(consumer);
    }

    public void shutDown() {
        try {
            if (mFuture.get()) {
                mProducerExecutor.shutdown();
                mConsumerExecutor.shutdown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
