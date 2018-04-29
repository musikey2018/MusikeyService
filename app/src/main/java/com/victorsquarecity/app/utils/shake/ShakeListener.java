package com.victorsquarecity.app.utils.shake;

/**
 * Listener class use for accelerometer events
 *
 */
public interface ShakeListener {

    void onAccelerationChanged(float x, float y, float z);

    void onShake(float force);

}
