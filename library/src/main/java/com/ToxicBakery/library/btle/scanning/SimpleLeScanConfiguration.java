package com.ToxicBakery.library.btle.scanning;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;

public class SimpleLeScanConfiguration implements ILeScanConfiguration {

    @NonNull
    private final IBluetoothAdapter bluetoothAdapter;

    @Nullable
    private final ScanSettings scanSettings;

    @SuppressLint("NewApi")
    public SimpleLeScanConfiguration(@NonNull Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter systemBluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothAdapter = new AndroidBluetoothAdapter(systemBluetoothAdapter);
        scanSettings = buildScanSettings();
    }

    @SuppressLint("NewApi")
    public SimpleLeScanConfiguration(@NonNull IBluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        scanSettings = buildScanSettings();
    }

    @NonNull
    @Override
    public IBluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    @Override
    @Nullable
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScanSettings getScanSettings() {
        return scanSettings;
    }

    @VisibleForTesting
    @SuppressLint("NewApi")
    @Nullable
    ScanSettings buildScanSettings() {
        if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            return new ScanSettings.Builder()
                    .build();
        }

        return null;
    }

}
