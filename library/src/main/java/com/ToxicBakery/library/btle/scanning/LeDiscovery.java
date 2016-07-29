package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.support.annotation.NonNull;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;

import java.util.LinkedList;
import java.util.List;

/**
 * Discovery API for initiating a BTLE scan.
 */
public final class LeDiscovery {

    private LeDiscovery() {
    }

    /**
     * Request for BTLE scanning to start.
     *
     * @param scanCallback      to send results
     * @param scanConfiguration configuration for performing scan
     * @return binder instance for controlling the scan
     */
    public static ILeScanBinder startScanning(@NonNull ILeScanCallback scanCallback,
                                              @NonNull ILeScanConfiguration scanConfiguration) {
        return startScanning(
                scanCallback,
                scanConfiguration,
                new LinkedList<ScanFilter>()
        );
    }

    /**
     * @param scanCallback      to send results
     * @param scanConfiguration configuration for performing scan
     * @param scanFilterList    list of devices to scan for or an empty list to scan for any device
     * @return binder instance for controlling the scan
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
