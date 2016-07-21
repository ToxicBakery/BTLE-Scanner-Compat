BTLE Scanner Compat
===================

Utility library for simplifying BTLE scanning for API 19 and up. By using compat versions of internal classes combined with wrapper classes it is possible to write tests for BTLE scanning.

#Getting Started (Gradle / Android Studio)

Add gradle dependency to your application.
```gradle
compile 'com.ToxicBakery.library.btle.scanning:library:1.0.0@aar'
```

After configuration, optionally use the [SimpleLeScanConfiguration](https://github.com/ToxicBakery/BTLE-Scanner-Compat/blob/master/library/src/main/java/com/ToxicBakery/library/btle/scanning/SimpleLeScanConfiguration.java) implementation to initiate a configured BTLE scan.

```java
ILeScanCallback leScanCallback = new ILeScanCallback() {
    @Override
    public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
    }

    @Override
    public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
    }
}
SimpleLeScanConfiguration scanConfiguration = new SimpleLeScanConfiguration(context);
ILeScanBinder leScanBinder = LeDiscovery.startScanning(leScanCallback, scanConfiguration);
```

Note that `ScanResultCompat` implements a `hashCode()` and `equals(Object)` so it may be used in common Java data types such as maps, sets, and collections.
