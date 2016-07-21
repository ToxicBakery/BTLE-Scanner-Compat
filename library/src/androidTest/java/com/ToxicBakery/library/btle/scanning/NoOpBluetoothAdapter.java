package com.ToxicBakery.library.btle.scanning;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeAdvertiser;

import java.util.UUID;

public class NoOpBluetoothAdapter implements IBluetoothAdapter {

    public NoOpBluetoothAdapter() {
    }

    @Override
    public BluetoothDevice getRemoteDevice(String address) {
        return null;
    }

    @Override
    public BluetoothDevice getRemoteDevice(byte[] address) {
        return null;
    }

    @Override
    public BluetoothLeAdvertiser getBluetoothLeAdvertiser() {
        return null;
    }

    @Override
    public IBluetoothLeScanner getBluetoothLeScanner() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean startDiscovery() {
        return false;
    }

    @Override
    public boolean cancelDiscovery() {
        return false;
    }

    @Override
    public boolean isDiscovering() {
        return false;
    }

    @Override
    public boolean startLeScan(LeScanCallback callback) {
        return false;
    }

    @Override
    public boolean startLeScan(UUID[] serviceUuids, LeScanCallback callback) {
        return false;
    }

    @Override
    public void stopLeScan(LeScanCallback callback) {
    }

}
