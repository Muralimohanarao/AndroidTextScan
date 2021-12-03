package com.murali.textscan;

// ported from the iOS Swift version at https://github.com/DigitalForms/CCValidator by Mariusz Wisniewski

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class ContentScanner {

    private Activity activity;
    private static int REQUEST_CAMERA = 3;
    private static String SCANNER_LOG = "SCANNER_LOG", scanningState = "loading";
    private SurfaceView surfaceView;
    private boolean showToasts = true;
    private ScannerListener scannerListener;

    public ContentScanner(Activity activity, SurfaceView surfaceView, ScannerListener listener){
        this.activity = activity;
        setSurfaceView(surfaceView);
        setScannerListener(listener);
        scan();
    }

    public void setSurfaceView(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
    }

    public void setScannerListener(ScannerListener scannerListener){
        this.scannerListener = scannerListener;
    }

    public void scan(){
        prepareTextScanning();
    }

    private void prepareTextScanning(){
        TextRecognizer textRecognizer = new TextRecognizer.Builder(activity).build();
        if (!textRecognizer.isOperational()) {

            IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean lowStorage = activity.registerReceiver(null, lowStorageFilter) != null;
            if (lowStorage){
                scanningState = "You have low storage of memory";
                if (showToasts)Toast.makeText(activity, scanningState, Toast.LENGTH_LONG).show();
                Log.e(SCANNER_LOG, scanningState);
                scannerListener.onStateChanged(scanningState, 2);
                return;
            }else{
                scanningState = "Scanner Not ready";
                if (showToasts)Toast.makeText(activity, scanningState, Toast.LENGTH_LONG).show();
                Log.e(SCANNER_LOG, scanningState);
                scannerListener.onStateChanged(scanningState, 3);
                return;
            }

        }
        final CameraSource cameraSource = new CameraSource.Builder(activity, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(SCANNER_LOG, "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {

            @Override
            public void release(){}

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {

                scanningState = "running";
                scannerListener.onStateChanged(scanningState, 1);

                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0) {

                    final StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); ++i) {
                        TextBlock item = items.valueAt(i);

                        if (CCValidator.validate(item.getValue())) {
                            stringBuilder.append("{{- is CC -}}");
                        }

                        if (CCValidator.validateDate(item.getValue())) {
                            stringBuilder.append("{{- is Date -}}");
                        }

                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            scannerListener.onTextDetected(stringBuilder.toString());
                        }
                    });
                    Log.d(SCANNER_LOG, stringBuilder.toString());

                }
            }
        });

    }

}
