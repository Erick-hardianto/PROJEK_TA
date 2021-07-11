package com.example.erick.ta_projek_erick;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class homefragment extends Fragment {
    // Method onCreateView dipanggil saat Fragment harus menampilkan layoutnya      // dengan membuat layout tersebut secara manual lewat objek View atau dengan     // membaca file XML

    private SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy'\n\nJam: ' HH:mm:ss");
    DBController cnt = new DBController(getActivity());
    private TextView mClock, textkamartersedia, textkamartdktersedia, textterisi, textsemuakamar, textjmlcekin, textjmlcekout, textjmlsmuatamu;
    private boolean mActive;
    private final Handler mHandler;

    private final Runnable mRunnable = new Runnable() {
        public void run() {
            if (mActive) {
                if (mClock != null) {
                    mClock.setText(getTime());
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };

    public homefragment() {
        mHandler = new Handler();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        // Layout tampilan untuk fragment ini


        View view = inflater.inflate(R.layout.activity_homefragment, parent, false);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_home_black_24dp);

        mClock = (TextView) view.findViewById(R.id.textView4);
        textkamartersedia = (TextView) view.findViewById(R.id.TxKmrtsdia);
        textkamartdktersedia = (TextView) view.findViewById(R.id.TxKmrTdkTsd);
        textterisi = (TextView) view.findViewById(R.id.txKmrTerisi);
        textsemuakamar = (TextView) view.findViewById(R.id.TxJmlSmuKmr);
        textjmlcekin = (TextView) view.findViewById(R.id.TxJumlahCekin);
        textjmlcekout = (TextView) view.findViewById(R.id.TxJumlahcekout);
        textjmlsmuatamu = (TextView) view.findViewById(R.id.TxSemuaTamu);


        //tampil data ke textview
        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String, String>> data = cnt.jumlahKamartersedia();
        textkamartersedia.setText(data.get(0).get("COUNT(idkamar)"));

        final ArrayList<HashMap<String, String>> data1 = cnt.jumlahKamarTdktersedia();
        textkamartdktersedia.setText(data1.get(0).get("COUNT(idkamar)"));

        final ArrayList<HashMap<String, String>> data6 = cnt.jumlahKamarterisi();
        textterisi.setText(data6.get(0).get("COUNT(idkamar)"));

        final ArrayList<HashMap<String, String>> data2 = cnt.jumlahsemuaKamar();
        textsemuakamar.setText(data2.get(0).get("COUNT(idkamar)"));

        final ArrayList<HashMap<String, String>> data3 = cnt.jumlahCekin();
        textjmlcekin.setText(data3.get(0).get("COUNT(idreservasi)"));

        final ArrayList<HashMap<String, String>> data4 = cnt.jumlahCekout();
        textjmlcekout.setText(data4.get(0).get("COUNT(idreservasi)"));

        final ArrayList<HashMap<String, String>> data5 = cnt.jumlahSemuaTamu();
        textjmlsmuatamu.setText(data5.get(0).get("COUNT(idreservasi)"));


        startClock();
        return view; // return view here instead of notaki

    }
    private String getTime() {
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    private void startClock() {
        mActive = true;
        mHandler.post(mRunnable);
    }
    // Method ini dipanggil sesaat setelah onCreateView().
    // Semua pembacaan view dan penambahan listener dilakukan disini (atau          // bisa juga didalam onCreateView)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.optionmenulogout, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                Intent home=new Intent(getActivity(), login.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
                return true;
        }
        return onOptionsItemSelected(item);
    }

}

