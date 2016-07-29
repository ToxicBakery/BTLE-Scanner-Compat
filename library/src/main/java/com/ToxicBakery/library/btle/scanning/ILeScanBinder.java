package com.ToxicBakery.library.btle.scanning;

import android.bluetooth.BluetoothAdapter;

/**
 * Binder for stopping the scan service and retrieving it's latest error,
 */
public interface ILeScanBinder extends BluetoothAdapter.LeScanCallback {

    /**
     * Request the scanner to stop.
     *
     * @return stop status code
     */
    @ScanStatusCode
    int stop();

    /**
     * Status of the binder
     *
     * @return status code
     */
    @ScanStatusCode
    int getErrorCode();

    /**
     * Status of the binder.
     *
     * @param errorCode status code
     */
    void setErrorCode(@ScanStatusCode int errorCode);

}
