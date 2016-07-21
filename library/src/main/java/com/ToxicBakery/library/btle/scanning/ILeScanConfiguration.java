package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanSettings;
import android.os.Build;

public interface ILeScanConfiguration {

    IBluetoothAdapter getBluetoothAdapter();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    ScanSettings getScanSettings();

}
