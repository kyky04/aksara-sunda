package com.uinbdg.kas.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.uinbdg.kas.R;

public class AksaraDasar extends AppCompatActivity implements OnClickListener {
    private Button Angka;
    private Button Dasar;
    private Button Konson;
    private Button Swara;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.menuaksara);

        this.Dasar = (Button) findViewById(R.id.dasar);
        this.Dasar.setOnClickListener(this);
        this.Swara = (Button) findViewById(R.id.swara);
        this.Swara.setOnClickListener(this);
        this.Konson = (Button) findViewById(R.id.konsonan);
        this.Konson.setOnClickListener(this);
        this.Angka = (Button) findViewById(R.id.angka);
        this.Angka.setOnClickListener(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Aksara Sunda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dasar:
                startActivity(new Intent(this, MenuDasar.class));
                return;
            case R.id.swara:
                startActivity(new Intent(this, MenuSwara.class));
                return;
            case R.id.konsonan:
                startActivity(new Intent(this, MenuKonsonan.class));
                return;
            case R.id.angka:
                startActivity(new Intent(this, MenuAngka.class));
                return;
            default:
                return;
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
