### DelayedQueue

Process `Events` based on configured delay time otherwise the default 60 seconds delay time is used.
It's like an event bus system but with a delay time in processing events.

## How to integrate into your app?

To integrate the library into your app, you need to make a few changes in the `build.gradle` file 
as follows:

**Step 1.** 
Add the JitPack repository to your build file. Add it in your root `build.gradle` in the `repositories` block:

```java
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
**Step 2.** 
Add the dependency
```java
dependencies {
    compile 'com.github.eyedol:delayed-queue:1.0.0'
}
```

### Usage

Make sure to implement the `Task` interface to define your own task. Then subscribe it to the 
`DelayedQueue` instance. There is a sample `Task` implemented called `PrintEventTask` in the 
example module that demonstrates this.

See sample code for an example usage

```java
DelayedQueue<Event> delayedQueue = new DelayedQueueAsync();
delayedQueue.subscribe(new PrintEventTask<Event>());
delayedQueue.publish(new Event("key", "Message one"));
delayedQueue.publish(new Event("key", "Message two"));
delayedQueue.publish(new Event("key", "Message three"));
```


### Sample code

To run sample code:

```
$ cd delayed-queues
$ ./gradlew delayedQueue
```

This runs as a daemon. To stop it from running press `Ctrl + C`
