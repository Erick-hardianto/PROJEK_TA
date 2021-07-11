package com.example.erick.ta_projek_erick;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class detail_kamar extends Fragment {
    TextView textNokamar , textipekamar, textlantai , texbiaya, textstatus;
    int idKamar;

    DBController cnt = new DBController(getActivity());

    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_kamar, parent, false);


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Kamar");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_kamar);


        textNokamar = view.findViewById(R.id.TxNokamar);
        textipekamar = view.findViewById(R.id.Txtipekamar);
        textlantai = view.findViewById(R.id.TxLantai);
        texbiaya = view.findViewById(R.id.TxBiaya);
        textstatus = view.findViewById(R.id.Status);


        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        mBundle.getString("a");

        String id = mBundle.getString("a");
        idKamar = Integer.parseInt(id);

        cnt = new DBController(getActivity());
        ArrayList<HashMap<String ,String>> data = cnt.showDataKamarById(idKamar);

        textNokamar.setText(data.get(0).get("nokamar"));
        textipekamar.setText(data.get(0).get("tipekamar"));
        textlantai.setText(data.get(0).get("lantai"));
        texbiaya.setText(data.get(0).get("biaya"));
        textstatus.setText(data.get(0).get("status"));


        return view;
    }
    }
