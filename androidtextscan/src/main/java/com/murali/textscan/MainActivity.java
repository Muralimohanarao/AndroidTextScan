package com.murali.textscan;

import static android.Manifest.permission.CAMERA;

import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView mTextView;
    SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surface);
        mTextView = findViewById(R.id.text_id);

        if (!checkPermission()) {
            requestPermission();
        } else {
            startScanning();
        }
    }


    private void startScanning(){
        ContentScanner scanner = new ContentScanner(this, surfaceView, new ScannerListener() {
            @Override
            public void onTextDetected(String detections) {
                mTextView.setText(detections);
            }
            @Override
            public void onStateChanged(String state, int i) {
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (cameraAccepted) {
                    startScanning();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied, You cannot access camera.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
