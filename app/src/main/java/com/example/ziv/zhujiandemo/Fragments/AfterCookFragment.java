package com.example.ziv.zhujiandemo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ziv.zhujiandemo.R;

public class AfterCookFragment extends Fragment {
    View view;
    public AfterCookFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_after_cook,container,false);
        return view;
    }
}
