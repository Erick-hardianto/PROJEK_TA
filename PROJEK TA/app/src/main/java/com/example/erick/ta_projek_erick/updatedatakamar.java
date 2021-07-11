package com.example.erick.ta_projek_erick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class updatedatakamar extends Fragment {


    EditText edTnokmr, edTtipekmr, edTbiaya;
    CardView btnSimpan;
    Spinner spNamen, spNamelantai;
    int idKamar;
    DBController cnt = new DBController(getActivity());

    String[] spinnerItems = new String[]{
            "Pilih Status",
            "Tersedia",
            "Tidak Tersedia",

    };

    String[] spinnerItems2 = new String[]{
            "Pilih Lantai",
            "1",
            "2",
            "3",

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_updatedatakamar, parent, false);

        edTnokmr = (EditText) view.findViewById(R.id.edNokmr);
        edTtipekmr = (EditText) view.findViewById(R.id.edTipekmr);
        spNamelantai = (Spinner) view.findViewById(R.id.sp_namelantai);
        edTbiaya = (EditText) view.findViewById(R.id.edBiaya);
        btnSimpan = (CardView) view.findViewById(R.id.cardSimpan);

        spNamen = (Spinner) view.findViewById(R.id.sp_name);

        edTnokmr.setEnabled(false);


        final List<String> stringlist = new ArrayList<>(Arrays.asList(spinnerItems));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.color_spinner_layout,stringlist) {
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spNamen.setAdapter(spinnerArrayAdapter);

        spNamen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);



                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.parseColor("#d2cdcf"));
                    Toast.makeText
                            (getContext(), "Status Kamar : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final List<String> stringlist2 = new ArrayList<>(Arrays.asList(spinnerItems2));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(),R.layout.color_spinner_layout,stringlist2) {
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spNamelantai.setAdapter(spinnerArrayAdapter1);

        spNamelantai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //memberikan pesan bahwa user klik status yg dpilih
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.parseColor("#d2cdcf"));
                    Toast.makeText
                            (getContext(), "Lantai : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cnt = new DBController(getActivity());
        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ubah Data Kamar");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_update);

//        spNamen.setSelection(0);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        mBundle.getString("b");
        mBundle.getString("c");
        mBundle.getString("d");
        mBundle.getString("e");
        mBundle.getString("f");


        String id = mBundle.getString("a");
        idKamar = Integer.parseInt(id);

        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String ,String>> data = cnt.showDataKamarById(idKamar);

        edTnokmr.setText(data.get(0).get("nokamar"));
        edTtipekmr.setText(data.get(0).get("tipekamar"));

        spNamelantai.setAdapter(spinnerArrayAdapter1);
        spNamelantai.setSelection(spinnerArrayAdapter1.getPosition(data.get(0).get("lantai")));


        edTbiaya.setText(data.get(0).get("biaya"));

        spNamen.setAdapter(spinnerArrayAdapter);
        spNamen.setSelection(spinnerArrayAdapter.getPosition(data.get(0).get("status")));

        btnSimpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(edTnokmr.getText().toString().equals("")||
                        edTtipekmr.getText().toString().equals("") ||
                        edTbiaya.getText().toString().equals("") ||
                        spNamelantai.getSelectedItem().equals("Pilih Lantai") ||
                        spNamen.getSelectedItem().equals("Pilih Status"))
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
                                    Bundle mBundle = new Bundle();
                                    mBundle = getArguments();
                                    String id = mBundle.getString("a");

                                    try {
                                        HashMap<String, String> queryValues = new HashMap<String, String>();
                                        queryValues.put("idkamar", id);
                                        queryValues.put("nokamar", edTnokmr.getText().toString());
                                        queryValues.put("tipekamar", edTtipekmr.getText().toString());
                                        queryValues.put("lantai", spNamelantai.getSelectedItem().toString());
                                        queryValues.put("biaya", edTbiaya.getText().toString());
                                        queryValues.put("status", spNamen.getSelectedItem().toString());

                                        Fragment fragment;
                                        if(spNamen.getSelectedItem().toString() == "Tersedia" ){
                                            fragment = new lihatdatakamar();
                                            loadFragment(fragment);
                                        }
                                        else if (spNamen.getSelectedItem().toString() == "Tidak Tersedia" ) {
                                            fragment = new KmrTidakTersedia();
                                            loadFragment(fragment);
                                        }

                                        cnt = new DBController(getActivity());
                                        cnt.updateDataKamar(queryValues);
                                        Toast.makeText(getActivity(), "No Kamar: " + edTnokmr.getText().toString() + " Berhasil di update", Toast.LENGTH_LONG).show();
                                        clearEditText();

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


        return  view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void clearEditText(){
        edTnokmr.setText("");
        edTtipekmr.setText("");
        spNamelantai.setSelection(0);
        edTbiaya.setText("");
        spNamen.setSelection(0);
    }
}
