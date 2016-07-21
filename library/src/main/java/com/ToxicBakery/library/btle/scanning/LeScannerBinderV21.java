package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;

class LeScannerBinderV21 extends LeScanCallback {

    final IBluetoothLeScanner bluetoothLeScanner;

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
