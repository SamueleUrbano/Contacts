package com.urbano.contacts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.urbano.contacts.R;

public class QRViewDialog extends Dialog {

    private ImageView qrCodeView;
    private Bitmap qrCode;

    public QRViewDialog(@NonNull Context context, Bitmap qrCode) {
        super(context);
        setContentView(R.layout.dialog_view_qr_code);

        this.qrCode = qrCode;
        this.bindWidgets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.qrCodeView.setImageBitmap(this.qrCode);
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.qrCodeView = findViewById(R.id.qr_code_view);
    }
}
