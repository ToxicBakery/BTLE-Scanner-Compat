package com.ToxicBakery.library.btle.scanning.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Recycler adapter for displaying device results.
 */
class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private final List<ScanResultCompat> scanResults;

    /**
     * Create the adapter using an internal list of results that can be added to and removed.
     */
    public DeviceAdapter() {
        scanResults = new ArrayList<>();
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.scan_result_item, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        ScanResultCompat scanResultCompat = scanResults.get(position);
        holder.bind(scanResultCompat);
    }

    @Override
    public int getItemCount() {
        return scanResults.size();
    }

    /**
     * Add a scan result to the display.
     *
     * @param scanResultCompat to be displayed
     */
    void addResult(ScanResultCompat scanResultCompat) {
        if (!scanResults.contains(scanResultCompat)) {
            scanResults.add(scanResultCompat);
        }
    }

    /**
     * Remove a scan result from the display.
     *
     * @param scanResultCompat to be removed from display
     */
    void removeResult(ScanResultCompat scanResultCompat) {
        scanResults.remove(scanResultCompat);
    }


    /**
     * Clear the adapter of all results.
     */
    void clearResults() {
        scanResults.clear();
    }

}
