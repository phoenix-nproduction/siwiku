package com.example.siwiku;

import java.io.Serializable;

public class Wisata implements Serializable {

    //Model Class
    String nama_wisata;
    String alamat;
    String img_url;
    String Fasilitas;
    String deskripsi;
    String lokasi;
    String tiket;

    public Wisata() {
    }

    public Wisata(String nama_wisata, String alamat, String img_url, String fasilitas, String deskripsi, String lokasi, String tiket) {
        this.nama_wisata = nama_wisata;
        this.alamat = alamat;
        this.img_url = img_url;
        Fasilitas = fasilitas;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.tiket = tiket;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getFasilitas() {
        return Fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        Fasilitas = fasilitas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTiket() {
        return tiket;
    }

    public void setTiket(String tiket) {
        this.tiket = tiket;
    }
}
