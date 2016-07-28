package com.ToxicBakery.library.btle.scanning;

import android.support.annotation.NonNull;

/**
 * Scan results callback for found and lost devices.
 */
public interface ILeScanCallback {

    /**
     * Callback when a BLE advertisement has been found.
     *
     * @param scanResult the advertisement that has been found
     */
    void onDeviceFound(@NonNull ScanResultCompat scanResult);

    /**
     * Callback when a BLE advertisement is no longer available.
     *
     * @param scanResult the advertisement that has been lost
     */
    void onDeviceLost(@NonNull ScanResultCompat scanResult);

}
