package com.uinbdg.kas.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// BaseFragment
public abstract class BaseFragment<P> extends Fragment {
    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(init_view(), container, false);
        presenter = onCreatePresenter();
        onBindPresenter(presenter);
        setupViews(view);
        run(savedInstanceState,view);
        return view;
    }

    protected abstract int init_view();


    private P onCreatePresenter() {
        return null;
    }

    private void onBindPresenter(P presenter) {

    }

    protected abstract void setupViews(View view);

    protected abstract void run(Bundle savedInstanceState, View view);
}

