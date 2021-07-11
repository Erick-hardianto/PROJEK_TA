package com.example.erick.ta_projek_erick;

public class TipeKamar {

    int id;
    String nokamar;
    String lantai;
    String biaya;
    String status;
    String tipekamar;
    public TipeKamar(String nokamar,  String tipekamar,String lantai, String biaya, String status)
    {
        this.nokamar = nokamar;
        this.tipekamar = tipekamar;
        this.lantai = lantai;
        this.biaya = biaya;
        this.status = status;
    }

    public TipeKamar(int id, String no,  String tipe,String lan, String biay, String sttus)
    {
        this.id = id;
        this.nokamar = no;
        this.tipekamar = tipe;
        this.lantai = lan;
        this.biaya = biay;
        this.status = sttus;
    }
    public String getLantai() {
        return lantai;
    }

    public void setLantai(String lantai) {
        this.lantai = lantai;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNokamar() {

        return nokamar;
    }

    public void setNokamar(String nokamar) {
        this.nokamar = nokamar;
    }

    public String getTipekamar() {
        return tipekamar;
    }

    public void setTipekamar(String tipekamar) {
        this.tipekamar = tipekamar;
    }


}
