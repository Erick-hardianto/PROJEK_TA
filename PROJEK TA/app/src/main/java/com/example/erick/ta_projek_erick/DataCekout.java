package com.example.erick.ta_projek_erick;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DataCekout extends Fragment {
    SearchView searchView = null;
    SearchView.OnQueryTextListener queryTextListener;
    AlertDialog.Builder dialog;
    DBController cnt = new DBController(getActivity());
    private ListView lstview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_cekout, parent, false);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Data Cekout Reservasi");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_cekout);


        //tampil data ke listview
        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String, String>> data = cnt.showAllDataReservasiCekout();
        lstview = view.findViewById(R.id.listViewSQLite);
        ListAdapter adapter = new SimpleAdapter(
                getActivity(),
                data,
                R.layout.listview,
                new String[]{"namacust", "nohp"},
                new int[]{R.id.EdItemNoKamar, R.id.Editemtipekamar}
        );
        lstview.setAdapter(adapter);
        if(data.size()== 0) {
            Toast.makeText(getActivity(), "Data Cekout belum tersedia", Toast.LENGTH_LONG).show();
        }

        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //show dialog menu edit dan data
                final CharSequence[] dialogitem = {"Detail Reservasi Tamu","Detail Tagihan Tamu"};
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);

                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment;
                        Bundle args = new Bundle();

                        switch (which){
                            case 0:
                                //detail reservasi
                                String Id = data.get(position).get("idreservasi");
                                args.putString("a", Id);
                                fragment = new detailreservasi();
                                fragment.setArguments(args);
                                loadFragment(fragment);
                                break;
                            case 1:
                                //detail tagihan
                                String Id1 = data.get(position).get("idreservasi");
                                args.putString("a", Id1);
                                fragment = new DetailTagihan();
                                fragment.setArguments(args);
                                loadFragment(fragment);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });


        return view;
    }

    //search menu di menu bar atas
    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater2) {

        inflater2.inflate(R.menu.optionmenuckut, menu);
        inflater2.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    cnt = new DBController(getActivity());
                    final ArrayList<HashMap<String, String>> data = cnt.searchReservasiCekout(newText);
                    final ArrayList<HashMap<String, String>> data1 = cnt.showAllDataReservasiCekout();

                    if(newText.isEmpty()){
                        lstview = getActivity().findViewById(R.id.listViewSQLite);
                        ListAdapter adapter1 = new SimpleAdapter(
                                getActivity(),
                                data1,
                                R.layout.listview,
                                new String[]{"namacust", "nohp"},
                                new int[]{R.id.EdItemNoKamar, R.id.Editemtipekamar}
                        );
                        lstview.setAdapter(adapter1);
                        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                                //show dialog menu edit dan data
                                final CharSequence[] dialogitem = {"Detail Reservasi Tamu","Detail Tagihan Tamu"};
                                dialog = new AlertDialog.Builder(getActivity());
                                dialog.setCancelable(true);

                                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Fragment fragment;
                                        Bundle args = new Bundle();

                                        switch (which){
                                            case 0:
                                                //detail reservasi
                                                String Id = data1.get(position).get("idreservasi");
                                                args.putString("a", Id);
                                                fragment = new detailreservasi();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                            case 1:
                                                //detail tagihan
                                                String Id1 = data1.get(position).get("idreservasi");
                                                args.putString("a", Id1);
                                                fragment = new DetailTagihan();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                        }
                                    }
                                }).show();
                                return false;
                            }
                        });

                    }

                    else if(data != null){
                        lstview = getActivity().findViewById(R.id.listViewSQLite);
                        ListAdapter adapter = new SimpleAdapter(
                                getActivity(),
                                data,
                                R.layout.listview,
                                new String[]{"namacust", "nohp"},
                                new int[]{R.id.EdItemNoKamar, R.id.Editemtipekamar}
                        );
                        lstview.setAdapter(adapter);

                        if(data.size()== 0) {
                            Toast.makeText(getActivity(), "Data Reservasi tidak ditemukan", Toast.LENGTH_LONG).show();
                        }

                        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                                //show dialog menu edit dan data
                                final CharSequence[] dialogitem = {"Detail Reservasi Tamu","Detail Tagihan Tamu"};
                                dialog = new AlertDialog.Builder(getActivity());
                                dialog.setCancelable(true);

                                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Fragment fragment;
                                        Bundle args = new Bundle();

                                        switch (which){
                                            case 0:
                                                //detail reservasi
                                                String Id = data.get(position).get("idreservasi");
                                                args.putString("a", Id);
                                                fragment = new detailreservasi();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                            case 1:
                                                //detail tagihan
                                                String Id1 = data.get(position).get("idreservasi");
                                                args.putString("a", Id1);
                                                fragment = new DetailTagihan();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                        }
                                    }
                                }).show();
                                return false;
                            }
                        });


                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater2);
    }

    //menampilkan search di toolbar
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set dikasih option menu
        setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.setOnQueryTextListener(queryTextListener);
                return true;
            case R.id.ketersediaankamar:
                fragment = new Ketersediaankmr();
                loadFragment(fragment);
                return true;
            case R.id.tambahreserv:
                fragment = new reservasi_kamar();
                loadFragment(fragment);
                return true;
            case R.id.datacekin:
                fragment = new lihatdatareservasi();
                loadFragment(fragment);
                return true;
            case R.id.datasemuareservasi:
                fragment = new DataSemuaReservasi();
                loadFragment(fragment);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
