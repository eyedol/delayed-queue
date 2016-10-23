/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.addhen.delay.queue;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Make it a FIFO event
 *
 * @author Henry Addo
 */
public class FifoEvent<E extends Comparable<? super E>>
        implements Comparable<FifoEvent<E>> {

    private final static AtomicLong sAtomicLong = new AtomicLong();

    private E mEvent;

    private long mSequenceNumber;

    public FifoEvent(E event) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FifoEvent<?> fifoEvent = (FifoEvent<?>) o;

        if (this.getEvent().equals(fifoEvent.getEvent())) {
            return true;
        }

        if (mSequenceNumber != fifoEvent.mSequenceNumber) {
            return false;
        }
        return mEvent != null ? mEvent.equals(fifoEvent.mEvent) : fifoEvent.mEvent == null;

    }

    @Override
    public int compareTo(FifoEvent<E> other) {
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
