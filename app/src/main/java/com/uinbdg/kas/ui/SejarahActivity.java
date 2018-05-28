package com.uinbdg.kas.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.uinbdg.kas.R;

public class SejarahActivity extends BaseFragment {
    private final String LOCATION_PATH = "Sundanese.ttf";
    private Typeface face;
    private TextView text;

    @Override
    protected int init_view() {
        return R.layout.sejarah;
    }

    @Override
    protected void setupViews(View view) {
        text = (TextView) view.findViewById(R.id.text1);
        face = Typeface.createFromAsset(getActivity().getAssets(), LOCATION_PATH);
        text.setTypeface(face);
    }

    @Override
    protected void run(Bundle savedInstanceState, View view) {

    }
}
