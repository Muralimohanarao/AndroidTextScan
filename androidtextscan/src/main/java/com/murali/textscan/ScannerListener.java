package com.murali.textscan;

public interface ScannerListener {
    void onTextDetected(String detections);
    void onStateChanged(String state, int i);
}
