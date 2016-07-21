package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
abstract class LeScanCallback extends ScanCallback implements ILeScanBinder {

    final ILeScanCallback scanCallback;

    @ScanStatusCode
    int errorCode;

    public LeScanCallback(@NonNull ILeScanCallback scanCallback) {
        this.scanCallback = scanCallback;
        errorCode = ScanStatusCode.SCAN_OK;
    }

    @Override
    @ScanStatusCode
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(@ScanStatusCode int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecordBytes) {
        ScanResultCompat scanResult = new ScanResultCompat(device, rssi, System.nanoTime());
        scanCallback.onDeviceFound(scanResult);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        for (ScanResult result : results) {
            onScanResult(ScanSettings.CALLBACK_TYPE_ALL_MATCHES, result);
        }
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        ScanResultCompat scanResultCompat = new ScanResultCompat(
                result.getDevice(),
                result.getRssi(),
                result.getTimestampNanos());

        switch (callbackType) {
            case ScanSettings.CALLBACK_TYPE_ALL_MATCHES:
            case ScanSettings.CALLBACK_TYPE_FIRST_MATCH:
                scanCallback.onDeviceFound(scanResultCompat);
                break;
            case ScanSettings.CALLBACK_TYPE_MATCH_LOST:
                scanCallback.onDeviceLost(scanResultCompat);
                break;
        }
    }

    @Override
    public void onScanFailed(int errorCode) {
        setErrorCode(errorCode);
    }

}
