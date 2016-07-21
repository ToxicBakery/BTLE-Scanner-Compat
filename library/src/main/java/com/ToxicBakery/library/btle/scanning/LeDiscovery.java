package com.ToxicBakery.library.btle.scanning;

import android.annotation.SuppressLint;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.support.annotation.NonNull;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;

import java.util.LinkedList;
import java.util.List;

public class LeDiscovery {

    LeDiscovery() {
    }

    public static ILeScanBinder startScanning(@NonNull ILeScanCallback scanCallback,
                                              @NonNull ILeScanConfiguration scanConfiguration) {
        return startScanning(
                scanCallback,
                scanConfiguration,
                new LinkedList<ScanFilter>()
        );
    }

    @SuppressLint("InlinedApi")
    @SuppressWarnings("deprecation")
    public static ILeScanBinder startScanning(@NonNull ILeScanCallback scanCallback,
                                              @NonNull ILeScanConfiguration scanConfiguration,
                                              @NonNull List<ScanFilter> scanFilterList) {

        IBluetoothAdapter bluetoothAdapter = scanConfiguration.getBluetoothAdapter();

        if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            ScanSettings scanSettings = scanConfiguration.getScanSettings();
            IBluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            LeScanCallback leScannerBinderV21 = new LeScannerBinderV21(scanCallback, bluetoothLeScanner);
            bluetoothLeScanner.startScan(scanFilterList, scanSettings, leScannerBinderV21);
            return leScannerBinderV21;
        } else {
            LeScanCallback leScannerBinder = new LeScannerBinder(scanCallback, bluetoothAdapter);
            if (!bluetoothAdapter.startLeScan(leScannerBinder)) {
                leScannerBinder.setErrorCode(ScanCallback.SCAN_FAILED_INTERNAL_ERROR);
                leScannerBinder.stop();
            }

            return leScannerBinder;
        }
    }

}
