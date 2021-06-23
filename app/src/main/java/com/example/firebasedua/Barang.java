package com.example.firebasedua;

import java.io.Serializable;

public class Barang implements Serializable {
    private String kode; //kode barang
    private  String nama, code; //key

    public Barang() {
    }

    public String getKode(){
        return kode;
    }

    public  void  setKode(String kd){
        this.kode = kd;
    }

    public String getNama(){
        return nama;
    }

    public  void  setNama(String nama){
        this.nama = nama;
    }

    public String getCode(){
        return code;
    }

    public  void  setCode(String code){
        this.code = code;
    }


    @Override
    public String toString() {

        return "Barang{"+
                "nama='"+nama+'\''+
                ", kode='"+kode+'\''+
                ", code='"+code+'\''+
                '}';
    }

    public  Barang(String kd, String nm){
        kode = kd;
        nama = nm;
    }
}
