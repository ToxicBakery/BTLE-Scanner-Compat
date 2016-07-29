package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Binder to a scan service for pre Lollipop devices.
 */
class LeScannerBinder extends LeScanCallback {

    private final IBluetoothAdapter bluetoothAdapter;

    /**
     * Creates the binder for the given callback and device.
     *
     * @param scanCallback     results callback
     * @param bluetoothAdapter device performing the scan
     */
    LeScannerBinder(@NonNull ILeScanCallback scanCallback,
                    @NonNull IBluetoothAdapter bluetoothAdapter) {

        super(scanCallback);

        this.bluetoothAdapter = bluetoothAdapter;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ScanStatusCode
    @Override
    public int stop() {
        bluetoothAdapter.stopLeScan(this);
        return getErrorCode();
    }

}
