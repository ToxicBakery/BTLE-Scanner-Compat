package com.ToxicBakery.library.gotenna.app;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * Activity displaying a scan button and list for results.
 */
public class ActivityMain extends AppCompatActivity implements View.OnClickListener, ILeScanCallback {

    /**
     * Request code for activity results
     */
    private static final int REQUEST_PERMISSIONS = 1001;

    private Button buttonScan;
    private DeviceAdapter adapter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new DeviceAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        buttonScan = (Button) findViewById(R.id.button_scan);
        buttonScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_scan:
                if (Is.greaterThanOrEqual(Build.VERSION_CODES.M)) {
                    int permission = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        requestPermission();
                    } else {
                        requestToggleScan();
                    }
                } else {
                    requestToggleScan();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        break;
                    }
                }

                requestToggleScan();
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI();

        ScanSingleton.getInstance(getApplicationContext())
                .setLeScanCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScanSingleton.getInstance(getApplicationContext())
                .setLeScanCallback(null);
    }

    @Override
    public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
        adapter.addResult(scanResult);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
        // This will not be called without setting the callback mode to
        // ScanSettings.CALLBACK_TYPE_FIRST_MATCH | ScanSettings.CALLBACK_TYPE_MATCH_LOST
        adapter.removeResult(scanResult);
        adapter.notifyDataSetChanged();
    }

    /**
     * Check for BTLE support on the device.
     *
     * @return true if the device supports BTLE
     */
    public boolean isBluetoothLeSupported() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /**
     * Request scanning to start or stop.
     */
    void requestToggleScan() {
        ScanSingleton scanSingleton = ScanSingleton.getInstance(getApplicationContext());
        boolean isScanning = scanSingleton.isScanning();
        scanSingleton.toggleScanning();

        if (isScanning) {
            adapter.clearResults();
            adapter.notifyDataSetChanged();
        }

        updateUI();
    }

    /**
     * Update the UI to reflect the scan state.
     */
    void updateUI() {
        boolean scanning = ScanSingleton.getInstance(getApplicationContext())
                .isScanning();

        buttonScan.setText(scanning ? R.string.button_scan_stop : R.string.button_scan_start);

        if (!isBluetoothLeSupported()) {
            buttonScan.setEnabled(false);
            warnBtleSupport();
        }
    }

    /**
     * Display a warning to the user if the device does not support Bluetooth LE.
     */
    void warnBtleSupport() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_no_btle_title)
                .setMessage(R.string.error_no_btle_message)
                .show();
    }

    /**
     * Request location permission. This is required to use Bluetooth LE on a device.
     */
    void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS);
    }

}
