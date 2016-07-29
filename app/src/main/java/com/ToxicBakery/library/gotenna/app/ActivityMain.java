package com.ToxicBakery.library.gotenna.app;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.ILeScanConfiguration;
import com.ToxicBakery.library.btle.scanning.LeDiscovery;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;
import com.ToxicBakery.library.btle.scanning.SimpleLeScanConfiguration;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, ILeScanCallback {

    private static final String TAG = "ActivityMain";
    private static final int REQUEST_PERMISSIONS = 1001;

    private Button buttonScan;
    private Adapter adapter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new Adapter();

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

    void requestToggleScan() {
        toggleScan();
        updateUI();
    }

    void updateUI() {
        boolean scanning = ScanSingleton.getInstance(getApplicationContext())
                .isScanning();

        buttonScan.setText(scanning ? R.string.button_scan_stop : R.string.button_scan_start);

        if (!isBluetoothLeSupported()) {
            buttonScan.setEnabled(false);
            warnBtleSupport();
        }
    }

    void warnBtleSupport() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_no_btle_title)
                .setMessage(R.string.error_no_btle_message)
                .show();
    }

    void toggleScan() {
        ScanSingleton.getInstance(getApplicationContext())
                .toggleScanning();
    }

    void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS);
    }

    static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<ScanResultCompat> scanResults;

        public Adapter() {
            scanResults = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(android.R.layout.simple_selectable_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ScanResultCompat scanResultCompat = scanResults.get(position);
            holder.bind(scanResultCompat);
        }

        @Override
        public int getItemCount() {
            return scanResults.size();
        }

        void addResult(ScanResultCompat scanResultCompat) {
            if (!scanResults.contains(scanResultCompat)) {
                scanResults.add(scanResultCompat);
            }
        }

        void removeResult(ScanResultCompat scanResultCompat) {
            scanResults.remove(scanResultCompat);
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bind(ScanResultCompat scanResultCompat) {
            BluetoothDevice device = scanResultCompat.getDevice();
            String deviceName = device.getName();
            String address = device.getAddress();
            String display = textView.getResources()
                    .getString(R.string.device_display, deviceName, address);

            textView.setText(display);
        }

    }

    static class ScanSingleton {

        @Nullable
        private static ScanSingleton instance;

        @NonNull
        private Context context;

        private ILeScanBinder leScanBinder;
        private ILeScanCallback scanCallback;

        public ScanSingleton(@NonNull Context context) {
            this.context = context.getApplicationContext();
        }

        @NonNull
        static ScanSingleton getInstance(@NonNull Context context) {
            if (instance == null) {
                synchronized (ScanSingleton.class) {
                    if (instance == null) {
                        instance = new ScanSingleton(context);
                    }
                }
            }

            return instance;
        }

        /**
         * Determine if scanning is in progress.
         *
         * @return true if scanning
         */
        boolean isScanning() {
            return leScanBinder != null;
        }

        void setLeScanCallback(ILeScanCallback scanCallback) {
            this.scanCallback = scanCallback;
        }

        @MainThread
        void toggleScanning() {
            if (isScanning()) {
                leScanBinder.stop();
                leScanBinder = null;
            } else {
                final ILeScanCallback permCallback = new ILeScanCallback() {
                    @Override
                    public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
                        Log.d(TAG, "onDeviceFound: " + scanResult.toString());
                        if (scanCallback != null) {
                            scanCallback.onDeviceFound(scanResult);
                        }
                    }

                    @Override
                    public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
                        Log.d(TAG, "onDeviceLost: " + scanResult.toString());
                        if (scanCallback != null) {
                            scanCallback.onDeviceLost(scanResult);
                        }
                    }
                };

                ILeScanConfiguration scanConfiguration = new SimpleLeScanConfiguration(context);
                if (scanConfiguration.getBluetoothAdapter() != null) {
                    leScanBinder = LeDiscovery.startScanning(permCallback, scanConfiguration);
                }
            }
        }

    }

}
