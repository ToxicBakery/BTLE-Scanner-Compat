package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Binder to a scan service for Lollipop and newer devices.
 */
class LeScannerBinderV21 extends LeScanCallback {

    private final IBluetoothLeScanner bluetoothLeScanner;

    /**
     * Creates teh binder for the given callback and scanner implementation
     *
     * @param scanCallback       results callback
     * @param bluetoothLeScanner scanner to be used for scanning
     */
    LeScannerBinderV21(@NonNull ILeScanCallback scanCallback,
                       @NonNull IBluetoothLeScanner bluetoothLeScanner) {

        super(scanCallback);

        this.bluetoothLeScanner = bluetoothLeScanner;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ScanStatusCode
    @Override
    public int stop() {
        bluetoothLeScanner.stopScan(this);
        return getErrorCode();
    }

}
