package com.uinbdg.kas.quis;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uinbdg.kas.R;
import com.uinbdg.kas.pref.PrefData;
import com.uinbdg.kas.ui.BaseFragment;
import com.uinbdg.kas.ui.MenuUtama;

import java.util.List;

public class QuizActivity extends BaseFragment {
    private static final String TAG = QuizActivity.class.toString();

    Pertanyaan alur;
    Button butNext, btnPrev;
    List<Pertanyaan> daftarTanya;
    int qid = 0;
    RadioButton rda;
    RadioButton rdb;
    RadioButton rdc;
    int score = 0;
    TextView txtPertanyaan;
    TextView txtCustom;

    boolean status = false;


    @Override
    protected int init_view() {
        return R.layout.kuis;
    }

    @Override
    protected void setupViews(final View view) {
        txtPertanyaan = (TextView) view.findViewById(R.id.textView1);
        rda = (RadioButton) view.findViewById(R.id.radio0);
        rdb = (RadioButton) view.findViewById(R.id.radio1);
        rdc = (RadioButton) view.findViewById(R.id.radio2);
        txtCustom = (TextView) view.findViewById(R.id.aksara);
        butNext = (Button) view.findViewById(R.id.button1);
        btnPrev = (Button) view.findViewById(R.id.button2);

        daftarTanya = new DbHelper(getActivity()).getAllQuestions();
        alur = (Pertanyaan) daftarTanya.get(qid);
        qid++;
        setQuestionView();

        butNext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (qid == 1) {
                    btnPrev.setVisibility(View.VISIBLE);
                }
                RadioButton answer = (RadioButton) view.findViewById(((RadioGroup) view.findViewById(R.id.radioGroup1)).getCheckedRadioButtonId());
                Log.d("pertanyaanmu", new StringBuilder(String.valueOf(alur.getJAWAB())).append(" ").append(answer.getText()).toString());
                if (alur.getJAWAB().equals(answer.getText())) {
                    score++;
                    Toast.makeText(getActivity(), "benar" + score, Toast.LENGTH_SHORT).show();
                    Log.d("score", "Nilaimu " + score);
                }
                if (alur.getJAWAB() != answer.getText()) {
                    Log.i(TAG, "Salah nih");
                }
                if (qid < 10) {
                    alur = (Pertanyaan) daftarTanya.get(qid);
                    qid++;
                    setQuestionView();
                    return;
                }
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.result_hasil,null,false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setMessage("Hasil Evaluasi " + PrefData.getNama(getActivity()))
                        .setView(view1);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView textView = (TextView) view1.findViewById(R.id.tvHasil);
                textView.setText("Score Anda : " + score);
                Button coba,keluar;
                coba = (Button) view1.findViewById(R.id.coba);
                keluar = (Button) view1.findViewById(R.id.keluar);

                coba.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qid = 1;
                        alur = (Pertanyaan) daftarTanya.get(qid-1);
                        setQuestionView();
                        alertDialog.dismiss();
                    }
                });

                keluar.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton answer = (RadioButton) view.findViewById(((RadioGroup) view.findViewById(R.id.radioGroup1)).getCheckedRadioButtonId());
                if (qid == 2) {
                    btnPrev.setVisibility(View.GONE);
                }
                if (alur.getJAWAB().equals(answer.getText())) {
                    score = score + 1 - 1;
                }
                qid--;
                alur = (Pertanyaan) daftarTanya.get(qid - 1);
                setQuestionView();
            }
        });
    }

    @Override
    protected void run(Bundle savedInstanceState, View view) {

    }

    private void setQuestionView() {
        txtPertanyaan.setText(alur.getPERTANYAAN());
        rda.setText(alur.getA());
        rdb.setText(alur.getB());
        rdc.setText(alur.getC());

        txtCustom.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Sundanese.ttf"));
        if (qid == 1) {
            txtCustom.setText(" ");
        } else if (qid == 2) {
            txtCustom.setText("|᮳᮱| ᮇᮊ᮪ᮒᮧᮘᮨᮁ |᮲᮰᮱᮳|");
        } else if (qid == 3) {
            txtCustom.setText("ᮃᮔ᮪ᮓᮢᮧᮄᮓ᮪");
        } else if (qid == 4) {
            txtCustom.setText("ᮏ᮪ᮜ᮪. ᮞᮥᮔᮔ᮪ ᮘᮧᮔᮀ ᮔᮧ. |᮱᮰| ᮒᮥᮘᮔ᮪");
        } else if (qid == 5) {
            txtCustom.setText("ᮉ");
        } else if (qid == 6) {
            txtCustom.setText(" ");
        } else if (qid == 7) {
            txtCustom.setText("ᮐ");
        } else if (qid == 8) {
            txtCustom.setText(" ");
        } else if (qid == 9) {
            txtCustom.setText("ᮂ");
        } else if (qid == 10) {
            txtCustom.setText(" ");
        }
    }

}
