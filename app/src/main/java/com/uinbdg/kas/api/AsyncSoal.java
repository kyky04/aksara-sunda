package com.uinbdg.kas.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.uinbdg.kas.model.Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by knalb on 21/07/17.
 */

public class AsyncSoal extends AsyncTask<String,String,String> {

    private Context c;
    private onCallBack listener;
    private List<Pojo> list;

    private ProgressDialog dialog;

    public AsyncSoal(Context c,onCallBack listener) {
        this.listener = listener;
        this.c = c;
    }

    public AsyncSoal(Context c) {
        this.c = c;
    }

    @Override
    protected void onPreExecute() {
        list = new ArrayList<>();
        showProgress();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            urlConnection.setDoOutput(true);
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            if(!(br == null)){
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        if(!(s == null) || !s.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for(int i = 0;i < jsonArray.length();i++){
                    JSONObject o = jsonArray.getJSONObject(i);

                    String id = o.getString("id");
                    String pertanyan = o.getString("pertanyaan");
                    String aksara = o.getString("aksara");
                    String jawaban = o.getString("jawaban");
                    String a = o.getString("a");
                    String b = o.getString("b");
                    String c = o.getString("c");

                    Pojo x = new Pojo();
                    x.setId(id);
                    x.setPertanyan(pertanyan);
                    x.setAksara(aksara);
                    x.setJawaban(jawaban);
                    x.setA(a);
                    x.setB(b);
                    x.setC(c);

                    list.add(x);
                }

                if(!(listener == null)){
                    listener.success(list);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if(!(listener == null)){
                    listener.error();
                }
            }
        }
        hideProgress();
    }


    public interface onCallBack{
        void success(List<Pojo> list);
        void error();
    }

    public void showProgress() {
        dialog = new ProgressDialog(c);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Download Kuis. Please Wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void hideProgress() {
        dialog.dismiss();
    }
}
