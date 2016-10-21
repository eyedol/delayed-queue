### DelayQueue 
Process `Events` based on configured delay time otherwise the default 60 seconds delay time is used.

### Example usage

```java
DelayedQueue<Event> delayedQueue = new DelayedQueueAsync();
delayedQueue.subscribe(new PrintEventTask());
delayedQueue.publish(new Event("key", "Message one"));
delayedQueue.publish(new Event("key", "Message two"));
delayedQueue.publish(new Event("key", "Message three"));
```


### To run sample code

```
$ cd delay-queues
$ ./gradlew delayedQueue
```
