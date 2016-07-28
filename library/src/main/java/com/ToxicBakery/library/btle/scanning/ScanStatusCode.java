package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanCallback;
import android.os.Build;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The status of a scan operation.
 */
@Retention(RetentionPolicy.SOURCE)
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@IntDef({
        ScanStatusCode.SCAN_OK,
        ScanCallback.SCAN_FAILED_ALREADY_STARTED,
        ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED,
        ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED,
        ScanCallback.SCAN_FAILED_INTERNAL_ERROR
})
public @interface ScanStatusCode {

    /**
     * Indicate the status to be normal.
     */
    int SCAN_OK = 0;

}
