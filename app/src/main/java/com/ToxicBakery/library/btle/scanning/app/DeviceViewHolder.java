package com.ToxicBakery.library.btle.scanning.app;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

/**
 * Recycler ViewHolder for displaying the device name and address.
 */
class DeviceViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    /**
     * Create the holder from the given view
     *
     * @param itemView view to hold
     */
    public DeviceViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.scan_result_view);
    }

    /**
     * Bind the result to the view.
     *
     * @param scanResultCompat to bind
     */
    public void bind(ScanResultCompat scanResultCompat) {
        BluetoothDevice device = scanResultCompat.getDevice();
        String deviceName = device.getName();
        String address = device.getAddress();
        String display = textView.getResources()
                .getString(R.string.device_display, deviceName, address);

        textView.setText(display);
    }

}
