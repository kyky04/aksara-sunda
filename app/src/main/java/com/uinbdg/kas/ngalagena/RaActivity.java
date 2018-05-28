package com.uinbdg.kas.ngalagena;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uinbdg.kas.R;

import java.util.ArrayList;

public class RaActivity extends AppCompatActivity implements OnGesturePerformedListener {
    private GestureLibrary gLibrary;
    private GestureOverlayView gesture;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.ra);

        this.gesture = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        this.gesture.addOnGesturePerformedListener(this);
        this.gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestdasar);
        this.gLibrary.load();
        ImageView myAnimation = (ImageView) findViewById(R.id.myanimation);
        final AnimationDrawable myAnimationDrawable = (AnimationDrawable) myAnimation.getDrawable();
        myAnimation.post(new Runnable() {
            public void run() {
                myAnimationDrawable.start();
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Ra");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = this.gLibrary.recognize(gesture);
        Builder ab;
        if (predictions.size() <= 0 || ((Prediction) predictions.get(0)).score <= 3.0d) {
            ab = new Builder(this);
            ab.setTitle("Salah");
            ab.setMessage("aksara yang anda tulis tidak tepat.");
            ab.setPositiveButton("Coba lagi", null);
            ab.show();
        } else if (((Prediction) predictions.get(0)).name.equals("ra")) {
            ab = new Builder(this);
            ab.setTitle("Benar");
            ab.setMessage("aksara yang anda tulis sudah tepat.");
            ab.setPositiveButton("OK", null);
            ab.show();
        } else {
            ab = new Builder(this);
            ab.setTitle("Salah");
            ab.setMessage("aksara yang anda tulis tidak tepat.");
            ab.setPositiveButton("Coba lagi", null);
            ab.show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
