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

public class detailreservasi extends Fragment {
    TextView textNamacust , textnoHp, textTglcekin , textTglcekout, textHari, textTipekamar, textNokamar, textLantai, textStatus;
    int idReservasi;

    DBController cnt = new DBController(getActivity());

    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detailreservasi, parent, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Reservasi Tamu");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_reservasi);

        textNamacust = view.findViewById(R.id.TxNamaCust);
        textnoHp = view.findViewById(R.id.Txnohp);
        textTglcekin = view.findViewById(R.id.TxTglcekin);
        textTglcekout = view.findViewById(R.id.TxTglCekout);
        textHari = view.findViewById(R.id.TxHari);
        textTipekamar = view.findViewById(R.id.TxTipekamar);
        textNokamar = view.findViewById(R.id.TxNokamar);
        textLantai = view.findViewById(R.id.TxLantai);
        textStatus = view.findViewById(R.id.TxStatus);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        mBundle.getString("a");

        String id = mBundle.getString("a");
        idReservasi = Integer.parseInt(id);

        cnt = new DBController(getActivity());
        ArrayList<HashMap<String ,String>> data = cnt.showAllDataReservasiById(idReservasi);

        textNamacust.setText(data.get(0).get("namacust"));
        textnoHp.setText(data.get(0).get("nohp"));
        textTglcekin.setText(data.get(0).get("tglcekin"));
        textTglcekout.setText(data.get(0).get("tglcekout"));
        textHari.setText(data.get(0).get("hari"));
        textTipekamar.setText(data.get(0).get("kamar.tipekamar"));
        textNokamar.setText(data.get(0).get("reservasi.nokamar"));
        textLantai.setText(data.get(0).get("reservasi.lantai"));
        textStatus.setText(data.get(0).get("reservasi.status"));

        return view;


    }
}
