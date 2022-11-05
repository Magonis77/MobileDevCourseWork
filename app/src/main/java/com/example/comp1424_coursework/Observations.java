package com.example.comp1424_coursework;

public class Observations {
    private int _id;
    private String _obsname;
    private String _obstime;
    private String _obscomment;

    public Observations() {

    }

    public Observations(String obsname, String obstime, String obscomment) {
        this._obsname = obsname;
        this._obstime = obstime;
        this._obscomment = obscomment;
    }

    public int get_id(){
        return _id;
    }
    public void set_id(int _id){
        this._id = _id;
    }

    public String get_obsname(){
        return _obsname;
    }
    public void set_obsname(String _obsname){
        this._obsname = _obsname;
    }

    public void set_obstime(String _obstime){
        this._obstime = _obstime;
    }
    public String get_obstime(){
        return _obstime;
    }

    public void set_obscomment(String _obscomment){
        this._obscomment = _obscomment;
    }
    public String get_obscomment(){
        return _obscomment;
    }

}
