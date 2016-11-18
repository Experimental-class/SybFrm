package com.example.tyhj.sybfrm.fragement.msgfragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.zzhoujay.richtext.RichText;

import java.io.IOException;

public class Msg_2 extends Fragment {
    View view;
    TextView textView;

    public Msg_2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_msg_2, null);

        return view;
    }
}
