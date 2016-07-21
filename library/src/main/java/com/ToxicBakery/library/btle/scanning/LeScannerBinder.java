package com.ToxicBakery.library.btle.scanning;

import android.support.annotation.NonNull;

class LeScannerBinder extends LeScanCallback {

    final IBluetoothAdapter bluetoothAdapter;

    LeScannerBinder(@NonNull ILeScanCallback scanCallback,
                    @NonNull IBluetoothAdapter bluetoothAdapter) {

        super(scanCallback);

        this.bluetoothAdapter = bluetoothAdapter;
    }

    @SuppressWarnings("deprecation")
    @ScanStatusCode
    @Override
    public int stop() {
        bluetoothAdapter.stopLeScan(this);
        return getErrorCode();
    }

}
