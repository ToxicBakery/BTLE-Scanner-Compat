package com.ToxicBakery.library.btle.scanning;

import android.support.annotation.NonNull;

final class NoOpScanCallback implements ILeScanCallback {

    @Override
    public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
    }

    @Override
    public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
    }

}
