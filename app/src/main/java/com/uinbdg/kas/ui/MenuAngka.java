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
import com.uinbdg.kas.angka.DalapanActivity;
import com.uinbdg.kas.angka.DuaActivity;
import com.uinbdg.kas.angka.GenepActivity;
import com.uinbdg.kas.angka.HijiActivity;
import com.uinbdg.kas.angka.LimaActivity;
import com.uinbdg.kas.angka.NolActivity;
import com.uinbdg.kas.angka.OpatActivity;
import com.uinbdg.kas.angka.SalapanActivity;
import com.uinbdg.kas.angka.TiluActivity;
import com.uinbdg.kas.angka.TujuhActivity;

public class MenuAngka extends AppCompatActivity {
    public Button penjelasan;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.menuangka);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Menu Rarangken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void klikTombolMenu(View v) {
        switch (v.getId()) {
            case R.id.angka1:
                startActivity(new Intent(this, HijiActivity.class));
                return;
            case R.id.angka2:
                startActivity(new Intent(this, DuaActivity.class));
                return;
            case R.id.angka3:
                startActivity(new Intent(this, TiluActivity.class));
                return;
            case R.id.angka4:
                startActivity(new Intent(this, OpatActivity.class));
                return;
            case R.id.angka5:
                startActivity(new Intent(this, LimaActivity.class));
                return;
            case R.id.angka6:
                startActivity(new Intent(this, GenepActivity.class));
                return;
            case R.id.angka7:
                startActivity(new Intent(this, TujuhActivity.class));
                return;
            case R.id.angka8:
                startActivity(new Intent(this, DalapanActivity.class));
                return;
            case R.id.angka9:
                startActivity(new Intent(this, SalapanActivity.class));
                return;
            case R.id.angka0:
                startActivity(new Intent(this, NolActivity.class));
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
