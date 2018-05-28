package com.uinbdg.kas.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uinbdg.kas.R;
import com.uinbdg.kas.api.ApiConfig;
import com.uinbdg.kas.api.AsyncSoal;
import com.uinbdg.kas.model.Pojo;
import com.uinbdg.kas.pref.PrefData;
import com.uinbdg.kas.quis.QuizActivity;
import com.uinbdg.kas.quis.QuizFragment;
import com.uinbdg.kas.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncSoal.onCallBack, QuizFragment.onListener {

    private List<Pojo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = new ArrayList<>();

        if (savedInstanceState == null) {
            getSupportActionBar().setTitle("KAS");
            ActivityUtils.addFragment(this, R.id.container, new HomeFragment());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_utama, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(LayoutInflater.from(this).inflate(R.layout.fragment_about,null,false));

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sejarah) {
            // untuk pindah ke sejarah
            if (findViewById(R.id.container) == null) {
                ActivityUtils.addFragment(this, R.id.container, new SejarahActivity());
            } else {
                ActivityUtils.replaceFragment(this, R.id.container, new SejarahActivity());
            }

            getSupportActionBar().setTitle("Sejarah");
        } else if (id == R.id.nav_kuis) {

            //untuk download quis
            AsyncSoal asyncSoal = new AsyncSoal(this, this);
            asyncSoal.execute(ApiConfig.url_get);
        } else if (id == R.id.nav_home) {

            // untuk pindah ke home
            getSupportActionBar().setTitle("KAS");
            if (findViewById(R.id.container) == null) {
                ActivityUtils.addFragment(this, R.id.container, new HomeFragment());
            } else {
                ActivityUtils.replaceFragment(this, R.id.container, new HomeFragment());
            }
        } else if (id == R.id.nav_rarangken) {

            //untuk pindah ke rarangken
            startActivity(new Intent(this,MenuRarangken.class));
        } else if (id == R.id.nav_about) {
            // untuk pindah ke sejarah
            if (findViewById(R.id.container) == null) {
                ActivityUtils.addFragment(this, R.id.container, new AboutFragment());
            } else {
                ActivityUtils.replaceFragment(this, R.id.container, new AboutFragment());
            }

            getSupportActionBar().setTitle("Bantuan");
        } else if (id == R.id.nav_tulis) {
            startActivity(new Intent(this,AksaraDasar.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //method jika download success
    @Override
    public void success(final List<Pojo> list) {
        this.list = list;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = LayoutInflater.from(MenuUtama.this).inflate(R.layout.form_nama, null, false);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuUtama.this);
                builder.setMessage("Isikan Nama Anda");
                builder.setView(view);

                final AlertDialog dialog = builder.create();
                dialog.show();

                final EditText editText = (EditText) view.findViewById(R.id.etNama);
                Button button = (Button) view.findViewById(R.id.btnNama);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText.getText().toString().equals("")) {
                            dialog.dismiss();
                            PrefData.setNama(MenuUtama.this, editText.getText().toString());
                            if (findViewById(R.id.container) == null) {
                                ActivityUtils.addFragment(MenuUtama.this, R.id.container, new QuizFragment());
                            } else {
                                ActivityUtils.replaceFragment(MenuUtama.this, R.id.container, new QuizFragment());
                            }
                            getSupportActionBar().setTitle("Kuis");
                        } else {
                            editText.setError("Harap mengisi nama");
                        }
                    }
                });
            }
        });
    }

    // method jika donwload error
    @Override
    public void error() {
        Toast.makeText(this, "Error Mendapatkan Soal", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MenuUtama.this, "Cek Koneksi Kamu", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }

    // method untuk getdata
    @Override
    public List<Pojo> getData() {
        return list;
    }
}
