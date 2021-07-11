package com.example.erick.ta_projek_erick;

public class User {
    //class user dibuat untuk menerima data dari class login

    //variables
    int id;
    String uname;
    String pssword;

    // Constructor dengan dua parameter username dan password
    public User(String username,String password)
    {
        this.uname=username;
        this.pssword=password;
    }
    //Konstruktor parameter berisi ketiga parameter
    public User(int id,String name,String psd)
    {
        this.id=id;
        this.uname=name;
        this.pssword=psd;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPssword() {
        return pssword;
    }

    public void setPssword(String pssword) {
        this.pssword = pssword;
    }
}