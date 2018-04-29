package com.victorsquarecity.app.utils.shake;

import android.content.Context;
import android.content.Intent;

/**
 * Main library class. This class has to be used to initialize or stop the
 * accelerometer service.
 *
 */


public class ShakeIt {

    static ShakeListener shakeListener;
    static int threshold = 25;
    static long interval = 600;

    /**
     * Starts the service and store the listener to be notified when a new shake
     * is generated in phone
     *
     * @param context       used to start the service
     * @param shakeListener to notify when phone is shaked
     */
    public static void initializeShakeService(Context context,
                                              ShakeListener shakeListener) {
        initializeShakeService(context, threshold, interval, shakeListener);
    }

    /**
     * @param context       used to start the service
     * @param threshold     use to configure threshold
     * @param interval      use to configure interval
     * @param shakeListener to notify when phone is shaked
     */
    public static void initializeShakeService(Context context, int threshold,
                                              long interval, ShakeListener shakeListener) {
        ShakeIt.threshold = threshold;
        ShakeIt.interval = interval;
        ShakeIt.shakeListener = shakeListener;
        Intent intent = new Intent(context, ShakeService.class);
        context.startService(intent);
    }

    /**
     * Stops the service and remove the AccelerometerListener added when the
     * ShakePhone was initialized
     *
     * @param context used to stop the service
     */
    public static void stopShakeService(Context context) {
        ShakeIt.shakeListener = null;
        Intent intent = new Intent(context, ShakeService.class);
        context.stopService(intent);
    }

}
