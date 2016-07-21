package com.ToxicBakery.library.btle.scanning;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanFilter;
import android.os.Build;
import android.test.AndroidTestCase;

import java.util.LinkedList;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LeDiscoveryTest extends AndroidTestCase {
    
    public void testStartScanning() throws Exception {
        SimpleLeScanConfiguration simpleLeScanConfiguration = new TestableScanConfiguration();
        ILeScanBinder leScanBinder = LeDiscovery.startScanning(new NoOpScanCallback(), simpleLeScanConfiguration);
        leScanBinder.stop();
    }

    public void testStartScanning1() throws Exception {
        SimpleLeScanConfiguration simpleLeScanConfiguration = new TestableScanConfiguration();
        ILeScanBinder leScanBinder = LeDiscovery.startScanning(
                new NoOpScanCallback(),
                simpleLeScanConfiguration,
                new LinkedList<ScanFilter>()
        );
        leScanBinder.stop();
    }

    static class TestableScanConfiguration extends SimpleLeScanConfiguration {

        public TestableScanConfiguration() {
            super(new NoOpBluetoothAdapter() {
                @Override
                public IBluetoothLeScanner getBluetoothLeScanner() {
                    return new NoOpBluetoothLeScanner();
                }
            });
        }

    }

}