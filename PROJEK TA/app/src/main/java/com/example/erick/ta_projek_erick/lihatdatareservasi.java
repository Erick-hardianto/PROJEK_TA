package com.example.erick.ta_projek_erick;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
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

public class lihatdatareservasi extends Fragment{
    SearchView searchView = null;
    SearchView.OnQueryTextListener queryTextListener;
    AlertDialog.Builder dialog;
    DBController cnt = new DBController(getActivity());
    private ListView lstview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lihatdatareservasi, parent, false);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Data Cekin Reservasi");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_cekin);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new reservasi_kamar();
                loadFragment(fragment);
            }
        });

        //tampil data ke listview
        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String, String>> data = cnt.showAllDataReservasiCekin();
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
            Toast.makeText(getActivity(), "Data Reservasi belum tersedia", Toast.LENGTH_LONG).show();
        }

        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // TODO Auto-generated method stub
                final String idreservasi = data.get(position).get("idreservasi");
                final String namacust = data.get(position).get("namacust");
                final String nohp = data.get(position).get("nohp");
                final String tglcekin = data.get(position).get("tglcekin");
                final String tglcekout = data.get(position).get("tglcekout");
                final String tipekamar = data.get(position).get("kamar.tipekamar");
                final String nokamar = data.get(position).get("reservasi.nokamar");
                final String lantai = data.get(position).get("reservasi.lantai");
                final String biaya = data.get(position).get("kamar.biaya");
                final String hari = data.get(position).get("hari");
                final String total = data.get(position).get("total");
                final String status = data.get(position).get("reservasi.status");

                //show dialog menu edit dan data
                final CharSequence[] dialogitem = {"Detail Reservasi Tamu","Detail Tagihan Tamu", "Ubah", "Cekout"};
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
                            case 2:
                                args.putString("a", idreservasi);
                                args.putString("b", namacust);
                                args.putString("c", nohp);
                                args.putString("d", tglcekin);
                                args.putString("e", tglcekout);
                                args.putString("f", hari);
                                args.putString("g", tipekamar);
                                args.putString("h", nokamar);
                                args.putString("i",lantai);

                                fragment = new updatereservasi();
                                fragment.setArguments(args);
                                loadFragment(fragment);
                                break;
                            case 3:
                                AlertDialog diaBox = AskOption(position);
                                diaBox.show();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        return  view;
    }

    //confirm hapus atau tidak
    public AlertDialog AskOption1(final int position, String keyword)
    {
        final ArrayList<HashMap<String, String>> data = cnt.searchReservasi(keyword);
        final String idreservasi = data.get(position).get("idreservasi");
        final String namacust = data.get(position).get("namacust");
        final String nokamar = data.get(position).get("reservasi.nokamar");


        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Cekout")
                .setMessage("Apakah " + namacust.toString() + " yakin ingin cekout?")
                .setIcon(R.drawable.ic_action_cekout)

                .setPositiveButton("Cekout", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Fragment fragment;
                        //your deleting code
                        cnt = new DBController(getActivity());
                        cnt.cekout(Integer.parseInt(idreservasi));
                        Toast.makeText(getActivity(), "Nama : " + namacust.toString() + " dan No kamar : " + nokamar.toString()+  " berhasil cekout", Toast.LENGTH_LONG).show();
                        fragment = new DataCekout();
                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                        loadFragment(fragment);
                    }

                })

                .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    //confirm hapus atau tidak
    public AlertDialog AskOption(final int position)
    {
        final ArrayList<HashMap<String, String>> data = cnt.showAllDataReservasiCekin();
        final String idreservasi = data.get(position).get("idreservasi");
        final String namacust = data.get(position).get("namacust");
        final String nokamar = data.get(position).get("reservasi.nokamar");


        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Cekout")
                .setMessage("Apakah " + namacust.toString() + " yakin ingin cekout?")
                .setIcon(R.drawable.ic_action_cekout)

                .setPositiveButton("Cekout", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Fragment fragment;
                        //your deleting code
                        cnt = new DBController(getActivity());
                        cnt.cekout(Integer.parseInt(idreservasi));
                        Toast.makeText(getActivity(), "Nama : " + namacust.toString() + " dan No kamar : " + nokamar.toString()+  " berhasil cekout", Toast.LENGTH_LONG).show();
                        fragment = new DataCekout();
                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                        loadFragment(fragment);
                    }

                })

                .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    //search menu di menu bar atas
    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater2) {

        inflater2.inflate(R.menu.opetionmenu, menu);
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
                public boolean onQueryTextChange(final String newText) {
                    cnt = new DBController(getActivity());
                    final ArrayList<HashMap<String, String>> data = cnt.searchReservasi(newText);
                    final ArrayList<HashMap<String, String>> data1 = cnt.showAllDataReservasiCekin();

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

                                // TODO Auto-generated method stub
                                final String idreservasi = data1.get(position).get("idreservasi");
                                final String namacust = data1.get(position).get("namacust");
                                final String nohp = data1.get(position).get("nohp");
                                final String tglcekin = data1.get(position).get("tglcekin");
                                final String tglcekout = data1.get(position).get("tglcekout");
                                final String tipekamar = data1.get(position).get("kamar.tipekamar");
                                final String nokamar = data1.get(position).get("reservasi.nokamar");
                                final String lantai = data1.get(position).get("reservasi.lantai");
                                final String biaya = data1.get(position).get("kamar.biaya");
                                final String hari = data1.get(position).get("hari");
                                final String total = data1.get(position).get("total");
                                final String status = data1.get(position).get("reservasi.status");

                                //show dialog menu edit dan data
                                final CharSequence[] dialogitem = {"Detail Reservasi Tamu","Detail Tagihan Tamu", "Ubah", "Cekout"};
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
                                            case 2:
                                                args.putString("a", idreservasi);
                                                args.putString("b", namacust);
                                                args.putString("c", nohp);
                                                args.putString("d", tglcekin);
                                                args.putString("e", tglcekout);
                                                args.putString("f", hari);
                                                args.putString("g", tipekamar);
                                                args.putString("h", nokamar);
                                                args.putString("i",lantai);

                                                fragment = new updatereservasi();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                            case 3:
                                                AlertDialog diaBox = AskOption(position);
                                                diaBox.show();
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

                                // TODO Auto-generated method stub
                                final String idreservasi = data.get(position).get("idreservasi");
                                final String namacust = data.get(position).get("namacust");
                                final String nohp = data.get(position).get("nohp");
                                final String tglcekin = data.get(position).get("tglcekin");
                                final String tglcekout = data.get(position).get("tglcekout");
                                final String tipekamar = data.get(position).get("kamar.tipekamar");
                                final String nokamar = data.get(position).get("reservasi.nokamar");
                                final String lantai = data.get(position).get("reservasi.lantai");
                                final String biaya = data.get(position).get("kamar.biaya");
                                final String hari = data.get(position).get("hari");
                                final String total = data.get(position).get("total");
                                final String status = data.get(position).get("reservasi.status");

                                //show dialog menu edit dan data
                                final CharSequence[] dialogitem = {"Detail Reservasi Tamu","Detail Tagihan Tamu", "Ubah", "Cekout"};
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
                                            case 2:
                                                args.putString("a", idreservasi);
                                                args.putString("b", namacust);
                                                args.putString("c", nohp);
                                                args.putString("d", tglcekin);
                                                args.putString("e", tglcekout);
                                                args.putString("f", hari);
                                                args.putString("g", tipekamar);
                                                args.putString("h", nokamar);
                                                args.putString("i",lantai);

                                                fragment = new updatereservasi();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                            case 3:
                                                AlertDialog diaBox = AskOption1(position, newText);
                                                diaBox.show();
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
