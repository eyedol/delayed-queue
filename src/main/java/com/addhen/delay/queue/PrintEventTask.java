package com.addhen.delay.queue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Prints the details of an event to the console
 *
 * @author Henry Addo
 */
public class PrintEventTask implements Task {

    @Override
    public void execute(Event event) {
        if (event != null) {
            System.out.println(
                    String.format("Key: %s => Message: %s @%s", event.key, event.message,
                            new SimpleDateFormat("mm").format(Calendar.getInstance().getTime())));
        }
    }
}
