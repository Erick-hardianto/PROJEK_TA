package com.example.erick.ta_projek_erick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class updatereservasi extends Fragment {

    int idKReservasi;
    EditText nokamar, lantai, namacust, nohp, days, tipekamar;
    Button tglcekout, tglcekin;
    CardView simpan;

    DBController cnt = new DBController(getActivity());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_updatereservasi, parent, false);



        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ubah Data Reservasi");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_update);

        lantai= (EditText) view.findViewById(R.id.edLantai);
        nokamar= (EditText) view.findViewById(R.id.edNokmr);
        tglcekin = (Button) view.findViewById(R.id.btnTglCekin);
        tglcekout = (Button) view.findViewById(R.id.btnTglCekout);
        namacust = (EditText) view.findViewById(R.id.edNamacust);
        nohp = (EditText) view.findViewById(R.id.edNoHp);
        days = (EditText) view.findViewById(R.id.eddays);
        tipekamar = (EditText) view.findViewById(R.id.Edtipekamar);


        simpan = (CardView) view.findViewById(R.id.cardSimpan);

        tglcekout.setEnabled(false);
        lantai.setEnabled(false);
        nokamar.setEnabled(false);
        tglcekin.setEnabled(false);
        days.setEnabled(false);
        tipekamar.setEnabled(false);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        mBundle.getString("b");
        mBundle.getString("c");
        mBundle.getString("d");
        mBundle.getString("e");
        mBundle.getString("f");
        mBundle.getString("g");
        mBundle.getString("h");
        mBundle.getString("i");




        String id = mBundle.getString("a");
        idKReservasi = Integer.parseInt(id);


        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String ,String>> data = cnt.showAllDataReservasiById(idKReservasi);

        namacust.setText(data.get(0).get("namacust"));
        nohp.setText(data.get(0).get("nohp"));
        tglcekin.setText(data.get(0).get("tglcekin"));
        tglcekout.setText(data.get(0).get("tglcekout"));
        tipekamar.setText(data.get(0).get("kamar.tipekamar"));
        nokamar.setText(data.get(0).get("reservasi.nokamar"));
        lantai.setText(data.get(0).get("reservasi.lantai"));
        days.setText(data.get(0).get("hari"));


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(namacust.getText().toString().equals("")||nohp.getText().toString().equals("") || tipekamar.getText().toString().equals("") || tglcekin.getText().toString().equals("") || tglcekout.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Data harus diisi terlebih dahulu!", Toast.LENGTH_LONG).show();
                }

                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    try {
                                        Bundle mBundle = new Bundle();
                                        mBundle = getArguments();
                                        String id = mBundle.getString("a");

                                        HashMap<String, String> queryValues = new HashMap<String, String>();
                                        queryValues.put("idreservasi", id);
                                        queryValues.put("namacust", namacust.getText().toString());
                                        queryValues.put("nohp", nohp.getText().toString());
                                        cnt = new DBController(getActivity());
                                        cnt.updateReservasi(queryValues);
                                        Toast.makeText(getActivity(), "Nama: " + namacust.getText().toString() + " Berhasil di update", Toast.LENGTH_LONG).show();

                                        Fragment fragment;
                                        fragment = new lihatdatareservasi();
                                        loadFragment(fragment);
                                    }
                                    catch(RuntimeException exc) {
                                        Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (Exception ex) {
                                        Toast.makeText(getActivity(), "Something wrong!!!", Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Apakah data yang diinput sudah benar?").setPositiveButton("Ya", dialogClickListener)
                            .setNegativeButton("Tidak", dialogClickListener).show();
                }

            }
        });



        return view;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    }
