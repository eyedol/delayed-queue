package com.addhen.delay.queue;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Make it a FIFO event
 *
 * @author Henry Addo
 */
public class DelayedEvent<E extends Comparable<? super E>>
        implements Comparable<DelayedEvent<E>> {

    private final static AtomicLong sAtomicLong = new AtomicLong();

    private E mEvent;

    private long mSequenceNumber;

    public DelayedEvent(E event) {
        mEvent = event;
        mSequenceNumber = sAtomicLong.getAndIncrement();
    }

    public E getEvent() {
        return mEvent;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((mEvent == null) ? 0 : mEvent.hashCode());
        return result;
    }

    @Override
    public int compareTo(DelayedEvent<E> other) {
        int result = mEvent.compareTo(other.mEvent);
        if (result == 0 && other != mEvent) {
            result = mSequenceNumber < other.mSequenceNumber ? -1 : 1;
        }
        return result;
    }

    @Override
    public String toString() {
        return "DelayedEvent{"
                + "mEvent=" + mEvent
                + ", mSequenceNumber=" + mSequenceNumber
                + '}';
    }
}
