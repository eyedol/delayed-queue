package com.addhen.delay.queue;

/**
 * @author Henry Addo
 */
public class Event implements Comparable<Event> {

    public String message;

    public String key;

    public Event(String key, String message) {
        this.key = key;
        this.message = message;
    }

    @Override
    public int compareTo(Event other) {
        if (this.key.equalsIgnoreCase(other.key) && this.message.equalsIgnoreCase(other.message)) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Event{"
                + "message='" + message + '\''
                + ", key='" + key + '\''
                + '}';
    }
}
