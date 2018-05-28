package com.uinbdg.kas.ui;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uinbdg.kas.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary gLibrary;
    private GestureOverlayView gesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);

        gesture = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        gesture.addOnGesturePerformedListener(this);
        gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestdasar);
        gLibrary.load();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = this.gLibrary.recognize(gesture);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        if (predictions.size() <= 0 || ((Prediction) predictions.get(0)).score <= 3.0d) {
            ab.setTitle("Salah");
            ab.setMessage("aksara yang anda tulis tidak tepat.");
            ab.setPositiveButton("Coba lagi", null);

        } else if (((Prediction) predictions.get(0)).name.equals("ka")) {
            ab.setTitle("Benar");
            ab.setMessage("aksara yang anda tulis sudah tepat.");
            ab.setPositiveButton("OK", null);

        } else {
            ab.setTitle("Salah");
            ab.setMessage("aksara yang anda tulis tidak tepat.");
            ab.setPositiveButton("Coba lagi", null);
        }

        AlertDialog alertDialog = ab.create();
        alertDialog.show();
    }
}
