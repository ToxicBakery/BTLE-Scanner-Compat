package com.ToxicBakery.library.btle.scanning;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import java.util.List;
import java.util.UUID;

/**
 * Copy of the Android {@link BluetoothAdapter} class as an interface.
 */
public interface IBluetoothAdapter {

    /**
     * Get a {@link BluetoothDevice} object for the given Bluetooth hardware
     * address.
     * <p>Valid Bluetooth hardware addresses must be upper case, in a format
     * such as "00:11:22:33:AA:BB". The helper {@link #checkBluetoothAddress} is
     * available to validate a Bluetooth address.
     * <p>A {@link BluetoothDevice} will always be returned for a valid
     * hardware address, even if this adapter has never seen that device.
     *
     * @param address valid Bluetooth MAC address
     * @return the device wrapped by this adapter
     */
    BluetoothDevice getRemoteDevice(String address);

    /**
     * Get a {@link BluetoothDevice} object for the given Bluetooth hardware
     * address.
     * <p>Valid Bluetooth hardware addresses must be 6 bytes. This method
     * expects the address in network byte order (MSB first).
     * <p>A {@link BluetoothDevice} will always be returned for a valid
     * hardware address, even if this adapter has never seen that device.
     *
     * @param address Bluetooth MAC address (6 bytes)
     * @return the device wrapped by this adapter
     */
    BluetoothDevice getRemoteDevice(byte[] address);

    /**
     * Returns a {@link BluetoothLeAdvertiser} object for Bluetooth LE Advertising operations.
     * Will return null if Bluetooth is turned off or if Bluetooth LE Advertising is not
     * supported on this device.
     * <p>
     * Use {@link #isMultipleAdvertisementSupported()} to check whether LE Advertising is supported
     * on this device before calling this method.
     *
     * @return the advertiser
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    BluetoothLeAdvertiser getBluetoothLeAdvertiser();

    /**
     * Returns a {@link BluetoothLeScanner} object for Bluetooth LE scan operations.
     *
     * @return the scanner implementation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    IBluetoothLeScanner getBluetoothLeScanner();

    /**
     * Return true if Bluetooth is currently enabled and ready for use.
     * <p>Equivalent to:
     * <code>getBluetoothState() == STATE_ON</code>
     * <p>Requires {@link Manifest.permission#BLUETOOTH}
     *
     * @return true if the local adapter is turned on
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    boolean isEnabled();

    /**
     * Start the remote device discovery process.
     * <p>The discovery process usually involves an inquiry scan of about 12
     * seconds, followed by a page scan of each new device to retrieve its
     * Bluetooth name.
     * <p>This is an asynchronous call, it will return immediately. Register
     * for {@link #ACTION_DISCOVERY_STARTED} and {@link
     * #ACTION_DISCOVERY_FINISHED} intents to determine exactly when the
     * discovery starts and completes. Register for {@link
     * BluetoothDevice#ACTION_FOUND} to be notified as remote Bluetooth devices
     * are found.
     * <p>Device discovery is a heavyweight procedure. New connections to
     * remote Bluetooth devices should not be attempted while discovery is in
     * progress, and existing connections will experience limited bandwidth
     * and high latency. Use {@link #cancelDiscovery()} to cancel an ongoing
     * discovery. Discovery is not managed by the Activity,
     * but is run as a system service, so an application should always call
     * {@link BluetoothAdapter#cancelDiscovery()} even if it
     * did not directly request a discovery, just to be sure.
     * <p>Device discovery will only find remote devices that are currently
     * <i>discoverable</i> (inquiry scan enabled). Many Bluetooth devices are
     * not discoverable by default, and need to be entered into a special mode.
     * <p>If Bluetooth state is not {@link #STATE_ON}, this API
     * will return false. After turning on Bluetooth,
     * wait for {@link #ACTION_STATE_CHANGED} with {@link #STATE_ON}
     * to get the updated value.
     * <p>Requires {@link Manifest.permission#BLUETOOTH_ADMIN}.
     *
     * @return true on success, false on error
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    boolean startDiscovery();

    /**
     * Cancel the current device discovery process.
     * <p>Requires {@link Manifest.permission#BLUETOOTH_ADMIN}.
     * <p>Because discovery is a heavyweight procedure for the Bluetooth
     * adapter, this method should always be called before attempting to connect
     * to a remote device with {@link
     * android.bluetooth.BluetoothSocket#connect()}. Discovery is not managed by
     * the  Activity, but is run as a system service, so an application should
     * always call cancel discovery even if it did not directly request a
     * discovery, just to be sure.
     * <p>If Bluetooth state is not {@link #STATE_ON}, this API
     * will return false. After turning on Bluetooth,
     * wait for {@link #ACTION_STATE_CHANGED} with {@link #STATE_ON}
     * to get the updated value.
     *
     * @return true on success, false on error
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    boolean cancelDiscovery();

    /**
     * Return true if the local Bluetooth adapter is currently in the device
     * discovery process.
     * <p>Device discovery is a heavyweight procedure. New connections to
     * remote Bluetooth devices should not be attempted while discovery is in
     * progress, and existing connections will experience limited bandwidth
     * and high latency. Use {@link #cancelDiscovery()} to cancel an ongoing
     * discovery.
     * <p>Applications can also register for {@link #ACTION_DISCOVERY_STARTED}
     * or {@link #ACTION_DISCOVERY_FINISHED} to be notified when discovery
     * starts or completes.
     * <p>If Bluetooth state is not {@link #STATE_ON}, this API
     * will return false. After turning on Bluetooth,
     * wait for {@link #ACTION_STATE_CHANGED} with {@link #STATE_ON}
     * to get the updated value.
     * <p>Requires {@link Manifest.permission#BLUETOOTH}.
     *
     * @return true if discovering
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    boolean isDiscovering();

    /**
     * Starts a scan for Bluetooth LE devices.
     * <p>
     * <p>Results of the scan are reported using the
     * {@link LeScanCallback#onLeScan} callback.
     * <p>
     * <p>Requires {@link Manifest.permission#BLUETOOTH_ADMIN} permission.
     *
     * @param callback the callback LE scan results are delivered
     * @return true, if the scan was started successfully
     * @deprecated use {@link BluetoothLeScanner#startScan(List, ScanSettings, ScanCallback)}
     * instead.
     */
    @Deprecated
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    boolean startLeScan(LeScanCallback callback);

    /**
     * Starts a scan for Bluetooth LE devices, looking for devices that
     * advertise given services.
     * <p>
     * <p>Devices which advertise all specified services are reported using the
     * {@link LeScanCallback#onLeScan} callback.
     * <p>
     * <p>Requires {@link Manifest.permission#BLUETOOTH_ADMIN} permission.
     *
     * @param serviceUuids Array of services to look for
     * @param callback     the callback LE scan results are delivered
     * @return true, if the scan was started successfully
     * @deprecated use {@link BluetoothLeScanner#startScan(List, ScanSettings, ScanCallback)}
     * instead.
     */
    @Deprecated
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    boolean startLeScan(final UUID[] serviceUuids, final LeScanCallback callback);

    /**
     * Stops an ongoing Bluetooth LE device scan.
     * <p>
     * <p>Requires {@link Manifest.permission#BLUETOOTH_ADMIN} permission.
     *
     * @param callback used to identify which scan to stop
     *                 must be the same handle used to start the scan
     * @deprecated Use {@link BluetoothLeScanner#stopScan(ScanCallback)} instead.
     */
    @Deprecated
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    void stopLeScan(LeScanCallback callback);

}
