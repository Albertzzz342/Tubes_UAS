package com.example.tubes_uas.Model;

import com.google.gson.annotations.SerializedName;

public class UserDAO {
    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("fasilitas")
    private String fasilitas;

    @SerializedName("lama")
    private String lama;

    @SerializedName("password")
    private String password;

    public UserDAO(String id, String nama, String email,
                   String jenis, String fasilitas, String lama,
                   String password){
        this.id     = id;
        this.nama   = nama;
        this.email  = email;
        this.jenis  = jenis;
        this.fasilitas = fasilitas;
        this.lama   = lama;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getLama() {
        return lama;
    }

    public void setLama(String lama) {
        this.lama = lama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
