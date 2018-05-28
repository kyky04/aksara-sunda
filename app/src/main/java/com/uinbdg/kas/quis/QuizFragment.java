package com.uinbdg.kas.quis;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uinbdg.kas.R;
import com.uinbdg.kas.model.Pojo;
import com.uinbdg.kas.pref.PrefData;
import com.uinbdg.kas.ui.BaseFragment;
import com.uinbdg.kas.ui.HomeFragment;
import com.uinbdg.kas.ui.SejarahActivity;
import com.uinbdg.kas.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends BaseFragment {


    private TextView soal,aksara;
    private Button btnNext,btnPref;
    private RadioButton rda;
    private RadioButton rdb;
    private RadioButton rdc;

    private List<Pojo> list;

    private onListener listener;

    private int index = 0;

    private int benar = 0,salah = 0;

    private List<Boolean> status;

    public interface onListener{
         List<Pojo> getData();
    }

    @Override
    protected int init_view() {
        return R.layout.fragment_quiz;
    }

    @Override
    protected void setupViews(View view) {
        list = listener.getData();

        status = new ArrayList<>();

        soal = (TextView) view.findViewById(R.id.textView1);
        aksara  = (TextView) view.findViewById(R.id.aksara);
        btnNext = (Button) view.findViewById(R.id.button1);
        btnPref = (Button) view.findViewById(R.id.button2);
        rda = (RadioButton) view.findViewById(R.id.radio0);
        rdb = (RadioButton) view.findViewById(R.id.radio1);
        rdc = (RadioButton) view.findViewById(R.id.radio2);
    }

    @Override
    protected void run(Bundle savedInstanceState, final View view) {
        setQuestionView(index,list);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0){
                    /*btnPref.setVisibility(View.VISIBLE);*/
                }

                final RadioButton answer = (RadioButton) view.findViewById(((RadioGroup) view.findViewById(R.id.radioGroup1)).getCheckedRadioButtonId());

                if(!(answer == null)){
                    if(list.get(index).getJawaban().trim().equals(answer.getText().toString().trim())){
                        Log.e("Benar : ", ""+index);
                        status.add(index,true);
                        benar++;
                    }else {
                        Log.e("Salah : ", "" + index);
                        status.add(index, false);
                    }

                    answer.setChecked(false);
                    answer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            answer.setChecked(true);
                        }
                    });

                    if(index < list.size()-1){
                        index++;
                        setQuestionView(index,list);
                    }else{
                        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.result_hasil,null,false);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                .setMessage("Hasil Evaluasi " + PrefData.getNama(getActivity()))
                                .setCancelable(false)
                                .setView(view1);

                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        TextView textView = (TextView) view1.findViewById(R.id.tvHasil);
                        textView.setText("Score Anda : " + benar);
                        Button coba,keluar;
                        coba = (Button) view1.findViewById(R.id.coba);
                        keluar = (Button) view1.findViewById(R.id.keluar);

                        coba.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                index = 0;
                                benar = 0;
                                setQuestionView(index,list);
                                btnPref.setVisibility(View.GONE);
                                alertDialog.dismiss();
                            }
                        });

                        keluar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                // Handle the camera action
                                if (getActivity().findViewById(R.id.container) == null) {
                                    ActivityUtils.addFragment(getActivity(), R.id.container, new HomeFragment());
                                } else {
                                    ActivityUtils.replaceFragment(getActivity(), R.id.container, new HomeFragment());
                                    ActivityUtils.removeFragment(getActivity(),new QuizFragment());
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(getActivity(), "Anda belum mengisi jawaban", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 1){
                    /*btnPref.setVisibility(View.GONE);*/
                }

                if(status.get(index-1)){
                    benar--;
                }

                index--;
                setQuestionView(index,list);
            }
        });
    }

    private void setQuestionView(int index,List<Pojo> list) {
        //set type face textview ke aksara sunda ngalagena
        aksara.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Ngalagena.ttf"));

        soal.setText(list.get(index).getPertanyan().trim());
        aksara.setText(list.get(index).getAksara().trim());
        rda.setText(list.get(index).getA().trim());
        rdb.setText(list.get(index).getB().trim());
        rdc.setText(list.get(index).getC().trim());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (onListener) context;
        }catch (Exception e){
            Toast.makeText(context, "Fragment Salah", Toast.LENGTH_SHORT).show();
        }
    }
}
