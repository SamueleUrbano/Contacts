package com.urbano.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.urbano.contacts.R;
import com.urbano.contacts.exception.qrcode.InvalidQRCodeException;
import com.urbano.contacts.util.Utility;

import java.util.List;

public class QRCodeScanActivity extends AppCompatActivity implements BarcodeCallback {

    private BarcodeView qrCodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan);

        this.bindWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.qrCodeReader.decodeContinuous(this);
        this.qrCodeReader.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.qrCodeReader.pause();
        this.qrCodeReader.stopDecoding();
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        ContactsAddActivity.forQRCode = result.toString();
        finish();
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.qrCodeReader = findViewById(R.id.qr_code_reader);
    }
}