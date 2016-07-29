/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ToxicBakery.library.btle.scanning;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * ScanResult for Bluetooth LE scan.
 * <p>
 * This class has been copied from the AOSP code base.
 *
 * @see <a href="http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/5.1.1_r1/android/bluetooth/le/ScanResult.java?av=f">ScanResult.java</a>
 */
public class ScanResultCompat implements Parcelable {

    /**
     * Creator implementation for the parcelable interface.
     */
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

    /**
     * Parcelable constructor.
     *
     * @param in parcel
     */
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

    /**
     * Create the wrapper from the passed parcel.
     *
     * @param in representing the device, Rssi, and timestamp
     */
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
     *
     * @return wrapped device
     */
    public BluetoothDevice getDevice() {
        return mDevice;
    }

    /**
     * Returns the received signal strength in dBm. The valid range is [-127, 127].
     *
     * @return RSSI signal strength
     */
    public int getRssi() {
        return mRssi;
    }

    /**
     * Returns timestamp since boot when the scan record was observed.
     *
     * @return relative timestamp in nano seconds
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
