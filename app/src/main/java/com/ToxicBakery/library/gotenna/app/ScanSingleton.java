package com.ToxicBakery.library.gotenna.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.LeDiscovery;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;
import com.ToxicBakery.library.btle.scanning.SimpleLeScanConfiguration;

/**
 * Simple scan wrapper to aid in retaining state.
 */
@MainThread
class ScanSingleton {

    /**
     * Log tag.
     */
    private static final String TAG = "ScanSingleton";

    @Nullable
    private static volatile ScanSingleton instance;

    @NonNull
    private Context context;

    private ILeScanBinder leScanBinderFind;
    private ILeScanCallback scanCallback;

    /**
     * Create the scan instance.
     *
     * @param context application context
     */
    private ScanSingleton(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Get or create the scan intance.
     *
     * @param context application context
     * @return the scan instance
     */
    @SuppressWarnings("ConstantConditions")
    @NonNull
    static ScanSingleton getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (ScanSingleton.class) {
                if (instance == null) {
                    instance = new ScanSingleton(context);
                }
            }
        }

        return instance;
    }

    /**
     * Determine if scanning is in progress.
     *
     * @return true if scanning
     */
    boolean isScanning() {
        return leScanBinderFind != null;
    }

    /**
     * The callback for listening to results.
     *
     * @param scanCallback for listening to results or null to stop
     */
    void setLeScanCallback(@Nullable ILeScanCallback scanCallback) {
        this.scanCallback = scanCallback;
    }

    /**
     * Toggle scanning by either stopping the binder or creating a new scan instance.
     */
    @TargetApi(Build.VERSION_CODES.M)
    void toggleScanning() {
        if (isScanning()) {
            leScanBinderFind.stop();
            leScanBinderFind = null;
        } else {
            final ILeScanCallback permCallback = new ILeScanCallback() {
                @Override
                public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
                    Log.d(TAG, "onDeviceFound: " + scanResult.toString());
                    if (scanCallback != null) {
                        scanCallback.onDeviceFound(scanResult);
                    }
                }

                @Override
                public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
                    Log.d(TAG, "onDeviceLost: " + scanResult.toString());
                    if (scanCallback != null) {
                        scanCallback.onDeviceLost(scanResult);
                    }
                }
            };

            SimpleLeScanConfiguration scanConfiguration = new SimpleLeScanConfiguration(context);
            leScanBinderFind = LeDiscovery.startScanning(permCallback, scanConfiguration);
        }
    }
}
