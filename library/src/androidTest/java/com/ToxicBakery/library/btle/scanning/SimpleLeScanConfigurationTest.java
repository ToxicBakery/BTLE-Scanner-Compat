package com.ToxicBakery.library.btle.scanning;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;

import junit.framework.TestCase;

public class SimpleLeScanConfigurationTest extends TestCase {
    
    public void testGetBluetoothAdapter() throws Exception {
        IBluetoothAdapter bluetoothAdapter = new NoOpBluetoothAdapter();
        SimpleLeScanConfiguration simpleLeScanConfiguration = new SimpleLeScanConfiguration(bluetoothAdapter);
        assertEquals(bluetoothAdapter, simpleLeScanConfiguration.getBluetoothAdapter());
    }

    public void testGetScanSettings() throws Exception {
        IBluetoothAdapter bluetoothAdapter = new NoOpBluetoothAdapter();
        SimpleLeScanConfiguration simpleLeScanConfiguration = new SimpleLeScanConfiguration(bluetoothAdapter);

        if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            assertNotNull(simpleLeScanConfiguration.getScanSettings());
        } else {
            assertNull(simpleLeScanConfiguration.getScanSettings());
        }
    }

    public void testBuildScanSettings() throws Exception {
        IBluetoothAdapter bluetoothAdapter = new NoOpBluetoothAdapter();
        SimpleLeScanConfiguration simpleLeScanConfiguration = new SimpleLeScanConfiguration(bluetoothAdapter);

        if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            assertNotNull(simpleLeScanConfiguration.getScanSettings());
        } else {
            assertNull(simpleLeScanConfiguration.getScanSettings());
        }
    }
}