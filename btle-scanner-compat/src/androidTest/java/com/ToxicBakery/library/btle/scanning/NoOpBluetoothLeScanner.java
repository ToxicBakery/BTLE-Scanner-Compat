package com.ToxicBakery.library.btle.scanning;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;

import java.util.List;

public class NoOpBluetoothLeScanner implements IBluetoothLeScanner {

    @Override
    public void startScan(List<ScanFilter> filters, ScanSettings settings, ScanCallback callback) {

    }

    @Override
    public void stopScan(ScanCallback callback) {
    }

}
