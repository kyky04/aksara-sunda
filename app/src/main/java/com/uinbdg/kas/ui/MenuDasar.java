package com.uinbdg.kas.ui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import com.uinbdg.kas.ngalagena.BaActivity;
import com.uinbdg.kas.ngalagena.CaActivity;
import com.uinbdg.kas.ngalagena.DaActivity;
import com.uinbdg.kas.ngalagena.GaActivity;
import com.uinbdg.kas.ngalagena.HaActivity;
import com.uinbdg.kas.ngalagena.JaActivity;
import com.uinbdg.kas.ngalagena.KaActivity;
import com.uinbdg.kas.ngalagena.LaActivity;
import com.uinbdg.kas.ngalagena.MaActivity;
import com.uinbdg.kas.ngalagena.NaActivity;
import com.uinbdg.kas.ngalagena.NgaActivity;
import com.uinbdg.kas.ngalagena.NyaActivity;
import com.uinbdg.kas.ngalagena.PaActivity;
import com.uinbdg.kas.ngalagena.RaActivity;
import com.uinbdg.kas.ngalagena.SaActivity;
import com.uinbdg.kas.ngalagena.TaActivity;
import com.uinbdg.kas.ngalagena.WaActivity;
import com.uinbdg.kas.ngalagena.YaActivity;


public class MenuDasar extends AppCompatActivity {
    public Button penjelasan;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.menudasar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Menu Dasar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    public void klikTombolMenu(View v) {
        switch (v.getId()) {
            case R.id.ba:
                startActivity(new Intent(this, BaActivity.class));
                return;
            case R.id.ca:
                startActivity(new Intent(this, CaActivity.class));
                return;
            case R.id.da:
                startActivity(new Intent(this, DaActivity.class));
                return;
            case R.id.ga:
                startActivity(new Intent(this, GaActivity.class));
                return;
            case R.id.ha:
                startActivity(new Intent(this, HaActivity.class));
                return;
            case R.id.ja:
                startActivity(new Intent(this, JaActivity.class));
                return;
            case R.id.ka:
                startActivity(new Intent(this, KaActivity.class));
                return;
            case R.id.la:
                startActivity(new Intent(this, LaActivity.class));
                return;
            case R.id.ma:
                startActivity(new Intent(this, MaActivity.class));
                return;
            case R.id.nga:
                startActivity(new Intent(this, NgaActivity.class));
                return;
            case R.id.nya:
                startActivity(new Intent(this, NyaActivity.class));
                return;
            case R.id.ta:
                startActivity(new Intent(this, TaActivity.class));
                return;
            case R.id.na:
                startActivity(new Intent(this, NaActivity.class));
                return;
            case R.id.pa:
                startActivity(new Intent(this, PaActivity.class));
                return;
            case R.id.ya:
                startActivity(new Intent(this, YaActivity.class));
                return;
            case R.id.ra:
                startActivity(new Intent(this, RaActivity.class));
                return;
            case R.id.wa:
                startActivity(new Intent(this, WaActivity.class));
                return;
            case R.id.sa:
                startActivity(new Intent(this, SaActivity.class));
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
