package com.example.quanlyhoctap;

public class RoomChat {
    private String idclass;
    private String sender;
    private String noidung;
    private int time;
    private String Thoigian;
    private String idsender;
    public RoomChat(String idclass, String sender, String noidung, int time, String thoigian, String idsender) {
        this.idclass = idclass;
        this.sender = sender;
        this.noidung = noidung;
        this.time = time;
        this.Thoigian = thoigian;
        this.idsender = idsender;
    }

    public String getIdsender() {
        return idsender;
    }

    public void setIdsender(String idsender) {
        this.idsender = idsender;
    }

    public String getThoigian() {
        return Thoigian;
    }

    public void setThoigian(String thoigian) {
        Thoigian = thoigian;
    }

    public String getIdclass() {
        return idclass;
    }

    public void setIdclass(String idclass) {
        this.idclass = idclass;
    }

    public String getsender() {
        return sender;
    }

    public void setsender(String sender) {
        this.sender = sender;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
