package com.ToxicBakery.library.btle.scanning;

import android.test.AndroidTestCase;

public class LeScannerBinderTest extends AndroidTestCase {

    public void testStop() throws Exception {
        IBluetoothAdapter bluetoothAdapter = new NoOpBluetoothAdapter();
        LeScannerBinder leScannerBinder = new LeScannerBinder(new NoOpScanCallback(), bluetoothAdapter);
        leScannerBinder.stop();
    }
    
}