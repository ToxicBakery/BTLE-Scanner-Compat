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

/**
 * Wrapper for {@link ScanSettings} permitting a backwards compatible manner of applying a
 * configuration to the device.
 */
public class SimpleLeScanConfiguration implements ILeScanConfiguration {

    @NonNull
    private final IBluetoothAdapter bluetoothAdapter;

    @Nullable
    private final ScanSettings scanSettings;

    /**
     * Create the configuration using the device default adapter.
     *
     * @param context application context
     */
    @SuppressLint("NewApi")
    public SimpleLeScanConfiguration(@NonNull Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter systemBluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothAdapter = new AndroidBluetoothAdapter(systemBluetoothAdapter);
        scanSettings = buildScanSettings();
    }

    /**
     * Create the configuration for the given device.
     *
     * @param bluetoothAdapter btle device
     */
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

    /**
     * Create default scan settings.
     *
     * @return default settings
     */
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
