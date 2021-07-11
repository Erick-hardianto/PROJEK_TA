package com.example.erick.ta_projek_erick;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class reservasi_kamar extends Fragment{
    Calendar calendarCekin, calendarCekout;
    EditText  nokamar, lantai, namacust, nohp, days;
    DatePickerDialog datePickerDialog;
    Button tglcekout, tglcekin;
    CardView simpan;
    Spinner spinnertipekamar;
    DBController cnt = new DBController(getActivity());
    int day, month, year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        // Layout tampilan untuk fragment ini

        View view = inflater.inflate(R.layout.activity_reservasi_kamar, parent, false);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tambah Data Reservasi");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_add);

        spinnertipekamar = (Spinner) view.findViewById(R.id.sp_nameTipekamar);
        loadSpinner();
        setHasOptionsMenu(true);

        calendarCekin = Calendar.getInstance();
        calendarCekout = Calendar.getInstance();


        day = calendarCekin.get(Calendar.DAY_OF_MONTH);
        month = calendarCekin.get(Calendar.MONTH);
        year = calendarCekin.get(Calendar.YEAR);

        day = calendarCekout.get(Calendar.DAY_OF_MONTH);
        month = calendarCekout.get(Calendar.MONTH);
        year = calendarCekout.get(Calendar.YEAR);

        lantai= (EditText) view.findViewById(R.id.edLantai);
        nokamar= (EditText) view.findViewById(R.id.edNokmr);
        tglcekin = (Button) view.findViewById(R.id.btnTglCekin);
        tglcekout = (Button) view.findViewById(R.id.btnTglCekout);
        namacust = (EditText) view.findViewById(R.id.edNamacust);
        nohp = (EditText) view.findViewById(R.id.edNoHp);
        days = (EditText) view.findViewById(R.id.eddays);

        simpan = (CardView) view.findViewById(R.id.cardSimpan);

        tglcekout.setEnabled(false);

        tglcekin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                calendarCekin = Calendar.getInstance();
                int mYear = calendarCekin.get(Calendar.YEAR); // current year
                int mMonth = calendarCekin.get(Calendar.MONTH); // current month
                int mDay = calendarCekin.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tglcekin.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);

                        tglcekout.setText((dayOfMonth + 1) + "-"
                                + (monthOfYear + 1) + "-" + year);
                        tglcekout.setEnabled(true);
                        tampilday();

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(calendarCekin.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        tglcekout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                String getfromdate = tglcekin.getText().toString().trim();
                String getfrom[] = getfromdate.split("-");
                int mYear = Integer.parseInt(getfrom[2]);
                int mMonth = (Integer.parseInt(getfrom[1]) -1); // current month
                int mDay = Integer.parseInt(getfrom[0]); // current day
                 calendarCekout = Calendar.getInstance();
                calendarCekout.set(mYear, mMonth, mDay+1);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // set day of month , month and year value in the edit text

                        tglcekout.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        tampilday();

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(calendarCekout.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(namacust.getText().toString().equals("")||nohp.getText().toString().equals("") || tglcekin.getText().toString().equals("") || tglcekout.getText().toString().equals("") || spinnertipekamar.getSelectedItem().toString().equals("Pilih Tipe Kamar"))
                {
                    Toast.makeText(getActivity(), "Data harus diisi terlebih dahulu!", Toast.LENGTH_LONG).show();
                }

                else {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked

                                    try {
                                        DBController cnt = new DBController(getActivity());
                                        cnt.Reservasi(namacust.getText().toString(), nohp.getText().toString(), tglcekin.getText().toString(), tglcekout.getText().toString(), spinnertipekamar.getSelectedItem().toString(), nokamar.getText().toString(), lantai.getText().toString(), Integer.parseInt(days.getText().toString()));
                                        Toast.makeText(getActivity(), "Nama : " + namacust.getText().toString() + " Berhasil di input", Toast.LENGTH_LONG).show();
                                        clearEditText();
                                        Fragment fragment;
                                        fragment = new konfirmasitagihan();
                                        loadFragment(fragment);
                                    }
                                    catch(RuntimeException exc) {
                                        Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    catch (Exception ex){
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


        return view; // return view here instead of notaki

    }

    private void tampilday(){
        try {
            String d1 = tglcekin.getText().toString ();
            String d2 = tglcekout.getText() .toString ();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = simpleDateFormat.parse(d1);
            Date date2 = simpleDateFormat.parse(d2);

            long difference = Math.abs(date1.getTime() - date2.getTime());


            long difftDays = difference / (24 * 60 * 60 * 1000);
            Log.i("Testing","days" +difftDays);
            days.setText(String.valueOf(difftDays));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void clearEditText(){
        namacust.setText("");
        nohp.setText("");
        tglcekin.setText("Tanggal Cekin");
        tglcekout.setText("Tanggal Cekout");
        days.setText("");
        spinnertipekamar.setSelection(0);
        nokamar.setText("");
        lantai.setText("");
    }

    private void loadSpinner() {
        //tampil data ke listview
        cnt = new DBController(getActivity());
        // Spinner Drop down elements
        final List<String> lables = cnt.ambiltipekamar();

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.color_spinner_layout, lables) {
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


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        // attaching data adapter to spinner
        spinnertipekamar.setAdapter(dataAdapter);

        spinnertipekamar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                            (getContext(), "Tipe Kamar : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();

                    cnt = new DBController(getActivity());



                    // Spinner Drop down elements
                    List<String> lables = cnt.getNokamar(selectedItemText);
                    String cleanString = lables.toString().replaceAll("\\[",  "");
                    nokamar.setText(cleanString.replaceAll("\\]","" ));

                    List<String> lables1 = cnt.getLantai(selectedItemText);
                    String cleanString1 = lables1.toString().replaceAll("\\[",  "");
                    lantai.setText(cleanString1.replaceAll("\\]","" ));

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.optionmenureserv, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment;
        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        switch (item.getItemId()) {
            case R.id.ketersediaankamar:
                fragment = new Ketersediaankmr();
                loadFragment(fragment);
                return true;
            case R.id.datareservcekin:
                fragment = new lihatdatareservasi();
                loadFragment(fragment);
                return true;
            case R.id.datareservcekout:
                fragment = new DataCekout();
                loadFragment(fragment);
                return true;
            case R.id.datasemuareservasi:
                fragment = new DataSemuaReservasi();
                loadFragment(fragment);
                return true;
        }
        return onOptionsItemSelected(item);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    }
