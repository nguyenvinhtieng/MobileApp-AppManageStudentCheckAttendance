package com.example.quanlyhoctap;

public class Subject {
    private String Id;
    private String Name;
    private String Note;
    private String Usercreate;

    public Subject(String id, String name, String note, String usercreate) {
        Id = id;
        Name = name;
        Note = note;
        Usercreate = usercreate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getUsercreate() {
        return Usercreate;
    }

    public void setUsercreate(String usercreate) {
        Usercreate = usercreate;
    }
}
