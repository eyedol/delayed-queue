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
package com.addhen.delayed.queue;

import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
public class DelayedQueueAsync<E extends Event> implements DelayedQueue<E> {

    private static final int DEFAULT_DELAY_TIME = 60;

    private final Queue<FifoEvent<E>> mEventQueues = new ConcurrentLinkedQueue<>();

    private final Thread mThread;

    private final Set<Task<E>> mTasks = Collections
            .newSetFromMap(new ConcurrentHashMap<>());

    private ExecutorService mExecutorService;

    private long mDelayTime;

    public DelayedQueueAsync() {
        this(DEFAULT_DELAY_TIME, Executors.newSingleThreadExecutor());
    }

    public DelayedQueueAsync(ExecutorService executorService) {
        this(DEFAULT_DELAY_TIME, executorService);
    }

    public DelayedQueueAsync(long delayTime) {
        this(delayTime, Executors.newSingleThreadExecutor());
    }

    public DelayedQueueAsync(long delayTime, ExecutorService executorService) {
        mDelayTime = delayTime;
        mExecutorService = executorService;
        mThread = new Thread(this::runTasks);
        mThread.setDaemon(true);
        mThread.start();
    }

    @Override
    public void subscribe(Task<E> task) {
        mTasks.add(task);
    }

    @Override
    public void unsubscribe(Task<E> task) {
        mTasks.remove(task);
    }

    @Override
    public void publish(E event) {
        if (event == null) {
            return;
        }
        FifoEvent<E> delayedEvent = new FifoEvent<>(event);
        if (!mEventQueues.contains(delayedEvent)) {
            mEventQueues.add(delayedEvent);
        }
    }

    @Override
    public boolean hasPendingEvents() {
        return !mEventQueues.isEmpty();
    }

    private void runTasks() {
        while (true) {
            FifoEvent<E> fifoEvent = mEventQueues.poll();
            if (fifoEvent != null) {
                E event = fifoEvent.getEvent();
                notifySubscribers(event);
            }
        }
    }

    private void notifySubscribers(E event) {
        for (Task<E> task : mTasks) {
            mExecutorService.submit(() -> {
                executeTask(task, event);
                try {
                    TimeUnit.SECONDS.sleep(mDelayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void executeTask(Task<E> task, E event) {
        task.execute(event);
    }
}
