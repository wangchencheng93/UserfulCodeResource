package com.baidu.location.a;

import android.os.HandlerThread;

public class i {
    private static HandlerThread a = null;

    public static synchronized HandlerThread a() {
        HandlerThread handlerThread;
        synchronized (i.class) {
            if (a == null) {
                a = new HandlerThread("ServiceStartArguments", 10);
                a.start();
            }
            handlerThread = a;
        }
        return handlerThread;
    }
}
