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
package com.addhen.delay.queue.example;

import com.addhen.delay.queue.DelayedQueue;
import com.addhen.delay.queue.DelayedQueueAsync;
import com.addhen.delay.queue.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RunDelayedQueue {

    private static final long DELAY_IN_SECONDS = 60;

    public static void main(String args[]) throws Exception {

        DelayedQueue<Event> delayedQueue = new DelayedQueueAsync<>();
        delayedQueue.subscribe(new PrintEventTask<Event>());
        delayedQueue.publish(new Event("key", "Message one"));
        delayedQueue.publish(new Event("key", "Message two"));
        delayedQueue.publish(new Event("key", "Message three"));
        delayedQueue.publish(new Event("key", "Message three"));
        delayedQueue.publish(new Event("key1", "Message Four"));
        delayedQueue.publish(new Event("key", "Message Five"));
        System.out.println(String.format("%s @%s", "Queue processed",
                new SimpleDateFormat("mm").format(Calendar.getInstance().getTime())));
    }
}
