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
import android.widget.Toast;

import com.uinbdg.kas.R;

public class MenuRarangken extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.menurarangken);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Menu Rarangken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void klikTombolMenu(View v) {
        switch (v.getId()) {
            case R.id.pamepet:
                startActivity(new Intent(this, PemepetActivity.class));
                return;
            case R.id.panghulu:
                startActivity(new Intent(this, PanghuluActivity.class));
                return;
            case R.id.panglayar:
                startActivity(new Intent(this, PanglayarActivity.class));
                return;
            case R.id.panyecek:
                startActivity(new Intent(this, PanyecekActivity.class));
                return;
            case R.id.paneuleung:
                startActivity(new Intent(this, PaneuleungActivity.class));
                return;
            case R.id.panyakra:
                startActivity(new Intent(this, PanyakraActivity.class));
                return;
            case R.id.panyiku:
                startActivity(new Intent(this, PanyikuActivity.class));
                return;
            case R.id.panyuku:
                startActivity(new Intent(this, PanyukuActivity.class));
                return;
            case R.id.pamaeh:
                startActivity(new Intent(this, PamaehActivity.class));
                return;
            case R.id.pamingkal:
                startActivity(new Intent(this, PamingkalActivity.class));
                return;
            case R.id.paneleng:
                startActivity(new Intent(this, PanelengActivity.class));
                return;
            case R.id.pangwisad:
                startActivity(new Intent(this, PangwisadActivity.class));
                return;
            case R.id.panolong:
                startActivity(new Intent(this, PanolongActivity.class));
                return;
            default:
                return;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

}
