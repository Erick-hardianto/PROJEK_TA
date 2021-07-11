package com.example.erick.ta_projek_erick;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
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

public class KmrTidakTersedia extends Fragment {

    SearchView searchView = null;
    SearchView.OnQueryTextListener queryTextListener;
    DBController cnt = new DBController(getActivity());
    private ListView lstview;
    AlertDialog.Builder dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Layout tampilan untuk fragment ini
        View view = inflater.inflate(R.layout.activity_kmr_tidak_tersedia, parent, false);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Data Kamar Tidak Tersedia");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_cekout);



        //tampil data ke listview
        cnt = new DBController(getActivity());
        final ArrayList<HashMap<String, String>> data = cnt.showAllDataKamarTdk();
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
                final String idkamar = data.get(position).get("idkamar");
                final String nokamar = data.get(position).get("nokamar");
                final String tipekamar = data.get(position).get("tipekamar");
                final String lantai = data.get(position).get("lantai");
                final String biaya = data.get(position).get("biaya");
                final String status = data.get(position).get("status");

                // TODO Auto-generated method stub
                //show dialog menu edit dan data
                final CharSequence[] dialogitem = {"Detail Kamar", "Ubah", "Hapus"};
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
                            //update data kamar
                            case 1:
                                args.putString("a", idkamar);
                                args.putString("b", nokamar);
                                args.putString("c", tipekamar);
                                args.putString("d", lantai);
                                args.putString("e", biaya);
                                args.putString("f", status);
                                fragment = new updatedatakamar();
                                fragment.setArguments(args);
                                loadFragment(fragment);
                                break;
                            //hapus data kamar
                            case 2:
                                AlertDialog diaBox = AskOption(position);
                                diaBox.show();
                                break;
                        }
                    }
                }).show();

                return false;
            }
        });
        return view;
    }

    //confirm hapus atau tidak
    public AlertDialog AskOption1(final int position, String keyword)
    {
        final ArrayList<HashMap<String, String>> data = cnt.searchTdk(keyword);

        final String idkamar = data.get(position).get("idkamar");
        final String nokamar = data.get(position).get("nokamar");
        final String tipekamar = data.get(position).get("tipekamar");

        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Hapus Data Kamar")
                .setMessage("Apa anda yakin ingin menghapus No kamar: " + nokamar.toString() + " dan Tipe kamar : " + tipekamar.toString()+ "?")
                .setIcon(R.drawable.ic_action_delete)

                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        cnt = new DBController(getActivity());
                        cnt.hapusDataKamar(Integer.parseInt(idkamar));
                        Toast.makeText(getActivity(), "No kamar: " + nokamar.toString() + " dan Tipe kamar : " + tipekamar.toString()+  " berhasil di hapus", Toast.LENGTH_LONG).show();
                        //tampil refresh data kamar terbaru
                        Fragment fragment;
                        fragment = new lihatdatakamar();
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
        final ArrayList<HashMap<String, String>> data = cnt.showAllDataKamarTdk();
        final String idkamar = data.get(position).get("idkamar");
        final String nokamar = data.get(position).get("nokamar");
        final String tipekamar = data.get(position).get("tipekamar");

        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Hapus Data Kamar")
                .setMessage("Apa anda yakin ingin menghapus No kamar: " + nokamar.toString() + " dan Tipe kamar : " + tipekamar.toString()+ "?")
                .setIcon(R.drawable.ic_action_delete)

                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        cnt = new DBController(getActivity());
                        cnt.hapusDataKamar(Integer.parseInt(idkamar));
                        Toast.makeText(getActivity(), "No kamar: " + nokamar.toString() + " dan Tipe kamar : " + tipekamar.toString()+  " berhasil di hapus", Toast.LENGTH_LONG).show();
                        //tampil refresh data kamar terbaru
                        Fragment fragment;
                        fragment = new KmrTidakTersedia();
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu, menu);
        inflater.inflate(R.menu.optionmenu2, menu);
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
                    final ArrayList<HashMap<String, String>> data = cnt.searchTdk(newText);
                    final ArrayList<HashMap<String, String>> data1 = cnt.showAllDataKamarTdk();

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

                                //show dialog menu edit dan data
                                final CharSequence[] dialogitem = {"Detail Kamar","Ubah", "Hapus"};
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
                                            //update data kamar
                                            case 1:
                                                args.putString("a", idkamar);
                                                args.putString("b", nokamar);
                                                args.putString("c", tipekamar);
                                                args.putString("d", lantai);
                                                args.putString("e", biaya);
                                                args.putString("f", status);
                                                fragment = new updatedatakamar();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                            //hapus data kamar
                                            case 2:
                                                AlertDialog diaBox = AskOption(position);
                                                diaBox.show();
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

// long press listview to show edit and delete
                        lstview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                // TODO Auto-generated method stub
                                final String idkamar = data.get(position).get("idkamar");
                                final String nokamar = data.get(position).get("nokamar");
                                final String tipekamar = data.get(position).get("tipekamar");
                                final String lantai = data.get(position).get("lantai");
                                final String biaya = data.get(position).get("biaya");
                                final String status = data.get(position).get("status");

                                final int parameter = 1;
                                //show dialog menu edit dan data
                                final CharSequence[] dialogitem = {"Detail Kamar","Ubah", "Hapus"};
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
                                            //update data kamar
                                            case 1:
                                                args.putString("a", idkamar);
                                                args.putString("b", nokamar);
                                                args.putString("c", tipekamar);
                                                args.putString("d", lantai);
                                                args.putString("e", biaya);
                                                args.putString("f", status);
                                                fragment = new updatedatakamar();
                                                fragment.setArguments(args);
                                                loadFragment(fragment);
                                                break;
                                            //hapus data kamar
                                            case 2:
                                                AlertDialog diaBox = AskOption1(position, newText);
                                                diaBox.show();
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.datakamar:
                fragment = new lihatdatakamar();
                loadFragment(fragment);
                return true;
            case R.id.tambahkamar:
                fragment = new kelolakamar();
                loadFragment(fragment);
                return true;
            case R.id.action_search:
                searchView.setOnQueryTextListener(queryTextListener);
                return true;
            case R.id.dataterisi:
                fragment = new KamarTerisi();
                loadFragment(fragment);
                return true;
            case R.id.datasemuakamar:
                fragment = new DataSemuakamar();
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
