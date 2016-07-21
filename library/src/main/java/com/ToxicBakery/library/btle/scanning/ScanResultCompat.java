package com.ToxicBakery.library.btle.scanning;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class ScanResultCompat implements Parcelable {

    public static final Creator<ScanResultCompat> CREATOR = new Creator<ScanResultCompat>() {
        @Override
        public ScanResultCompat createFromParcel(Parcel source) {
            return new ScanResultCompat(source);
        }

        @Override
        public ScanResultCompat[] newArray(int size) {
            return new ScanResultCompat[size];
        }
    };

    // Remote bluetooth device.
    private BluetoothDevice mDevice;

    // Received signal strength.
    private int mRssi;

    // Device timestamp when the result was last seen.
    private long mTimestampNanos;

    /**
     * Constructor of scan result.
     *
     * @param device         Remote bluetooth device that is found.
     * @param rssi           Received signal strength.
     * @param timestampNanos Device timestamp when the scan result was observed.
     */
    public ScanResultCompat(
            BluetoothDevice device,
            int rssi,
            long timestampNanos) {

        mDevice = device;
        mRssi = rssi;
        mTimestampNanos = timestampNanos;
    }

    private ScanResultCompat(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mDevice != null) {
            dest.writeInt(1);
            mDevice.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }

        dest.writeInt(mRssi);
        dest.writeLong(mTimestampNanos);
    }

    private void readFromParcel(Parcel in) {
        if (in.readInt() == 1) {
            mDevice = BluetoothDevice.CREATOR.createFromParcel(in);
        }
        mRssi = in.readInt();
        mTimestampNanos = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Returns the remote bluetooth device identified by the bluetooth device address.
     */
    public BluetoothDevice getDevice() {
        return mDevice;
    }

    /**
     * Returns the received signal strength in dBm. The valid range is [-127, 127].
     */
    public int getRssi() {
        return mRssi;
    }

    /**
     * Returns timestamp since boot when the scan record was observed.
     */
    public long getTimestampNanos() {
        return mTimestampNanos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mDevice, mRssi, mTimestampNanos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ScanResultCompat other = (ScanResultCompat) obj;
        String a1 = mDevice.getAddress();
        String a2 = other.mDevice.getAddress();
        return a1.equals(a2);
    }

    @Override
    public String toString() {
        return "ScanResultCompat{"
                + "mDevice=" + mDevice
                + ", mRssi=" + mRssi
                + ", mTimestampNanos=" + mTimestampNanos
                + '}';
    }

}
