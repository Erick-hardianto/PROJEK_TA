package com.example.erick.ta_projek_erick;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailTagihan extends Fragment {
    TextView textNamacust , textnoHp, textTglcekin , textTglcekout, textHari, textTipekamar, textNokamar, textLantai, textbiaya, textTotal;
    int idReservasi;
    CardView btSimpan;
    DBController cnt = new DBController(getActivity());

    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail_tagihan, parent, false);


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Tagihan Tamu");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.ic_action_money);

        textNamacust = view.findViewById(R.id.TxNamaCust);
        textnoHp = view.findViewById(R.id.Txnohp);
        textTglcekin = view.findViewById(R.id.TxTglcekin);
        textTglcekout = view.findViewById(R.id.TxTglCekout);
        textTipekamar = view.findViewById(R.id.TxTipekamar);
        textNokamar = view.findViewById(R.id.TxNokamar);
        textLantai = view.findViewById(R.id.TxLantai);
        textbiaya = view.findViewById(R.id.txBiaya);
        textHari = view.findViewById(R.id.txHari);
        textTotal = view.findViewById(R.id.TxTotal);



        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        mBundle.getString("a");

        String id = mBundle.getString("a");
        idReservasi = Integer.parseInt(id);

        cnt = new DBController(getActivity());
        ArrayList<HashMap<String ,String>> data = cnt.showAllDataReservasiById(idReservasi);

        textNamacust.setText(data.get(0).get("namacust"));
        textTglcekin.setText(data.get(0).get("tglcekin"));
        textTglcekout.setText(data.get(0).get("tglcekout"));
        textTipekamar.setText(data.get(0).get("kamar.tipekamar"));
        textNokamar.setText(data.get(0).get("reservasi.nokamar"));
        textLantai.setText(data.get(0).get("reservasi.lantai"));
        textbiaya.setText(data.get(0).get("kamar.biaya"));
        textHari.setText(data.get(0).get("hari"));
        textTotal.setText(data.get(0).get("total"));


        btSimpan = (CardView) view.findViewById(R.id.cardSimpan);

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String ,String>> data = cnt.showAllDataReservasiById(idReservasi);

                String a =  data.get(0).get("namacust");
                String b = data.get(0).get("tglcekin");
                String c = data.get(0).get("tglcekout");
                String d = data.get(0).get("kamar.tipekamar");
                String e = data.get(0).get("reservasi.nokamar");
                String f = data.get(0).get("reservasi.lantai");
                String g = data.get(0).get("kamar.biaya");
                String h = data.get(0).get("hari");
                String i = data.get(0).get("total");

                createPdf(a, b, c, d, e , f ,g , h, i);
            }
        });

        return view;

        }

    private void createPdf(String nama, String tglcekin, String tglcekout, String tipekamar, String nokamar, String lantai, String biaya, String lama, String total){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawText("RINCIAN TAGIHAN RESERVASI HAW19 HOTEL" , 30, 50, paint);
        canvas.drawText("Nama                        : " + nama, 20, 90, paint);
        canvas.drawText("Tanggal Cekin         : " + tglcekin, 20, 110, paint);
        canvas.drawText("Tanggal Cekout      : " + tglcekout, 20, 130, paint);
        canvas.drawText("Tipe Kamar              : " + tipekamar, 20, 150, paint);
        canvas.drawText("No Kamar                 : " + nokamar, 20, 170, paint);
        canvas.drawText("Lantai                       : " + lantai, 20, 190, paint);
        canvas.drawText("Biaya Per Malam      : " + biaya, 20, 210, paint);
        canvas.drawText("Lama Menginap        : " + lama, 20, 230, paint);
        canvas.drawText("Total                        : " + total, 20, 250, paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page
//        // Create Page 2
//        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
//        page = document.startPage(pageInfo);
//        document.finishPage(page);
        // write the document content
        String targetPdf = "/sdcard/haw19hotel.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
        Toast.makeText(getActivity(), "Rincian tagihan tamu dicetak", Toast.LENGTH_SHORT).show();


        openGeneratedPDF();


    }
    private void openGeneratedPDF(){
        File file = new File("/sdcard/haw19hotel.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getActivity(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
}
