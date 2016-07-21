package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.os.Build;

import java.util.UUID;

public class AndroidBluetoothAdapter implements IBluetoothAdapter {

    private final BluetoothAdapter bluetoothAdapter;

    public AndroidBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public BluetoothDevice getRemoteDevice(String address) {
        return bluetoothAdapter.getRemoteDevice(address);
    }

    @Override
    public BluetoothDevice getRemoteDevice(byte[] address) {
        return bluetoothAdapter.getRemoteDevice(address);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public BluetoothLeAdvertiser getBluetoothLeAdvertiser() {
        return bluetoothAdapter.getBluetoothLeAdvertiser();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public IBluetoothLeScanner getBluetoothLeScanner() {
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        return new AndroidBluetoothLeScanner(bluetoothLeScanner);
    }

    @Override
    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    @Override
    public boolean startDiscovery() {
        return bluetoothAdapter.startDiscovery();
    }

    @Override
    public boolean cancelDiscovery() {
        return bluetoothAdapter.cancelDiscovery();
    }

    @Override
    public boolean isDiscovering() {
        return bluetoothAdapter.isDiscovering();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean startLeScan(LeScanCallback callback) {
        return bluetoothAdapter.startLeScan(callback);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean startLeScan(UUID[] serviceUuids, LeScanCallback callback) {
        return bluetoothAdapter.startLeScan(serviceUuids, callback);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void stopLeScan(LeScanCallback callback) {
        bluetoothAdapter.stopLeScan(callback);
    }

}
