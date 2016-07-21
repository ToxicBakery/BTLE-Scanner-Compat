package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.os.Build;

import java.util.List;

public class AndroidBluetoothLeScanner implements IBluetoothLeScanner {

    private final BluetoothLeScanner bluetoothLeScanner;

    public AndroidBluetoothLeScanner(BluetoothLeScanner bluetoothLeScanner) {
        this.bluetoothLeScanner = bluetoothLeScanner;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startScan(List<ScanFilter> filters, ScanSettings settings, ScanCallback callback) {
        bluetoothLeScanner.startScan(filters, settings, callback);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void stopScan(ScanCallback callback) {
        bluetoothLeScanner.stopScan(callback);
    }

}
