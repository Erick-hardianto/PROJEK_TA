package com.example.erick.ta_projek_erick;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Ketersediaankmr extends Fragment {
    SearchView searchView = null;
    SearchView.OnQueryTextListener queryTextListener;
    AlertDialog.Builder dialog;
    DBController cnt = new DBController(getActivity());
    private ListView lstview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ketersediaankmr, parent, false);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ketersediaan Kamar");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_cek);

        //tampil data ke listview
        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String, String>> data = cnt.showAllDataKamar();
        lstview = view.findViewById(R.id.listViewSQLite);
        ListAdapter adapter = new SimpleAdapter(
                getActivity(),
                data,
                R.layout.listview,
                new String[]{"nokamar", "tipekamar"},
                new int[]{R.id.EdItemNoKamar, R.id.Editemtipekamar}
        );
        lstview.setAdapter(adapter);
        if(data.size()== 0) {
            Toast.makeText(getActivity(), "Data Kamar belum ada", Toast.LENGTH_LONG).show();
        }
        // long press listview to show edit and delete
        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // TODO Auto-generated method stub
                //show dialog menu edit dan data
                final CharSequence[] dialogitem = {"Detail Kamar"};
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO Auto-generated method stub
                        Fragment fragment;
                        Bundle args = new Bundle();
                        switch (which) {
                            //detail kamar
                            case 0:
                                String Id = data.get(position).get("idkamar");
                                args.putString("a", Id);
                                fragment = new detail_kamar();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu, menu);
        inflater.inflate(R.menu.opetionmenuae, menu);

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
                    final ArrayList<HashMap<String, String>> data = cnt.search(newText);
                    final ArrayList<HashMap<String, String>> data1 = cnt.showAllDataKamar();

                    if(newText.isEmpty()){
                        lstview = getActivity().findViewById(R.id.listViewSQLite);
                        ListAdapter adapter1 = new SimpleAdapter(
                                getActivity(),
                                data1,
                                R.layout.listview,
                                new String[]{"nokamar", "tipekamar"},
                                new int[]{R.id.EdItemNoKamar, R.id.Editemtipekamar}
                        );
                        lstview.setAdapter(adapter1);
                        // long press listview to show edit and delete
                        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                // TODO Auto-generated method stub
                                final String idkamar = data1.get(position).get("idkamar");
                                final String nokamar = data1.get(position).get("nokamar");
                                final String tipekamar = data1.get(position).get("tipekamar");
                                final String lantai = data1.get(position).get("lantai");
                                final String biaya = data1.get(position).get("biaya");
                                final String status = data1.get(position).get("status");

                                //show dialog detail kamar
                                final CharSequence[] dialogitem = {"Detail Kamar"};
                                dialog = new AlertDialog.Builder(getActivity());
                                dialog.setCancelable(true);
                                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO Auto-generated method stub
                                        Fragment fragment;
                                        Bundle args = new Bundle();
                                        switch (which) {
                                            //detail kamar
                                            case 0:
                                                String Id = data1.get(position).get("idkamar");
                                                args.putString("a", Id);
                                                fragment = new detail_kamar();
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
                                new String[]{"nokamar", "tipekamar"},
                                new int[]{R.id.EdItemNoKamar, R.id.Editemtipekamar}
                        );
                        lstview.setAdapter(adapter);
                        if(data.size()== 0) {
                            Toast.makeText(getActivity(), "Data Kamar tidak ditemukan", Toast.LENGTH_LONG).show();
                        }
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
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.tambahreserv:
                fragment = new reservasi_kamar();
                loadFragment(fragment);
                return true;
            case R.id.datacekin:
                fragment = new lihatdatareservasi();
                loadFragment(fragment);
                return true;
            case R.id.datacekout:
                fragment = new DataCekout();
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
