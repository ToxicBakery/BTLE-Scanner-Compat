package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanSettings;
import android.os.Build;

/**
 * Representation of a scan configuration. Not all devices are capable of all features.
 */
public interface ILeScanConfiguration {

    /**
     * Device to use for scanning.
     *
     * @return scan device
     */
    IBluetoothAdapter getBluetoothAdapter();

    /**
     * Settings for scanning. Only supported for Lollipop (v21) and greater.
     *
     * @return scan settings for device
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    ScanSettings getScanSettings();

}
