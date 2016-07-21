package com.ToxicBakery.library.btle.scanning;

import android.bluetooth.BluetoothAdapter;

public interface ILeScanBinder extends BluetoothAdapter.LeScanCallback {

    @ScanStatusCode
    int stop();

    @ScanStatusCode
    int getErrorCode();

    void setErrorCode(@ScanStatusCode int errorCode);

}
