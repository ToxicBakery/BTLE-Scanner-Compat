package com.ToxicBakery.library.btle.scanning;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import java.util.List;

public interface IBluetoothLeScanner {

    /**
     * Start Bluetooth LE scan. The scan results will be delivered through {@code callback}.
     * <p/>
     * Requires {@link Manifest.permission#BLUETOOTH_ADMIN} permission.
     * An app must hold
     * {@link Manifest.permission#ACCESS_COARSE_LOCATION ACCESS_COARSE_LOCATION} or
     * {@link Manifest.permission#ACCESS_FINE_LOCATION ACCESS_FINE_LOCATION} permission
     * in order to get results.
     *
     * @param filters  {@link ScanFilter}s for finding exact BLE devices.
     * @param settings Settings for the scan.
     * @param callback Callback used to deliver scan results.
     * @throws IllegalArgumentException If {@code settings} or {@code callback} is null.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    void startScan(List<ScanFilter> filters, ScanSettings settings, final ScanCallback callback);

    /**
     * Stops an ongoing Bluetooth LE scan.
     * <p/>
     * Requires {@link Manifest.permission#BLUETOOTH_ADMIN} permission.
     *
     * @param callback Callback used to deliver scan results.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    void stopScan(ScanCallback callback);

}
