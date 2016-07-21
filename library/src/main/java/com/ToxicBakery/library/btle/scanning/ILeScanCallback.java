package com.ToxicBakery.library.btle.scanning;

import android.support.annotation.NonNull;

public interface ILeScanCallback {

    void onDeviceFound(@NonNull ScanResultCompat scanResult);

    void onDeviceLost(@NonNull ScanResultCompat scanResult);

}
