package com.example.erick.ta_projek_erick;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText edusername, edpassword;
    CardView btlogin;
    ImageView facebook, twit, ig;

    DBController cnt = new DBController(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edusername=(EditText)findViewById(R.id.edUser);
        edpassword=(EditText)findViewById(R.id.edPass);
        btlogin = (CardView) findViewById(R.id.cardLogin);
        facebook = (ImageView) findViewById(R.id.facebook);
        twit = (ImageView) findViewById(R.id.twitter);
        ig = (ImageView) findViewById(R.id.instagram);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edusername.getText().toString();
                String password=edpassword.getText().toString();
                int id= cekUsername(new User(username,password));

                if (edpassword.getText().toString().equals("") && edusername.getText().toString().equals("") ){
                    Toast.makeText(login.this,"Isi username dan password! ",Toast.LENGTH_SHORT).show();
                }
                else if (edusername.getText().toString().equals("")){
                    Toast.makeText(login.this,"Username tidak boleh kosong! ",Toast.LENGTH_SHORT).show();
                }
                else if (edpassword.getText().toString().equals("")){
                    Toast.makeText(login.this,"Password tidak boleh kosong! ",Toast.LENGTH_SHORT).show();
                }
               else if(id==-1)
                {
                    Toast.makeText(login.this,"Username dan password salah!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(login.this,"Login Berhasil! "+username,Toast.LENGTH_SHORT).show();
                    Intent a= new Intent(login.this, menu_utama.class);
                    startActivity(a);
                }


            }
        });

        //menambahkan 1 user untuk resepsionis
        //ini masuk kedalam method adduser di class dbhandler
        cnt.addUser(new User("erick", "123"));
        cnt.hapusDataUser();
        cnt.addTipekamar(new TipeKamar("0", "Pilih Tipe Kamar", "0", "0", "Terisi"));
        cnt.hapusDataTipe();

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/Erick.Hardianto";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/erkerickh";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/_erkh/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
    public int cekUsername(User user)
    {
        return cnt.cekUsername(user);
    }

    }

