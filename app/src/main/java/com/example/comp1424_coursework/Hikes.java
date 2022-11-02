package com.example.comp1424_coursework;

public class Hikes {
    private int _id;
    private String _hikename;
    private String _hikelocation;
    private String _hikedate;
    private String _hikeparking;
    private String _hikelenght;
    private String _hikedifficulty;
    private String _hikedesc;
    private String _hikeweather;
    private String _hikeheartrate;

    public Hikes(){

    }
    public Hikes(String hikename, String hikelocation,String hikedate,
                 String hikeParking, String hikelenght, String hikedifficulty,
                 String hikeweather, String hikeheartrate, String hikedesc){
    this._hikename = hikename;
    this._hikelocation = hikelocation;
    this._hikedate = hikedate;
    this._hikeparking = hikeParking;
    this._hikelenght = hikelenght;
    this._hikedifficulty = hikedifficulty;
    this._hikedesc = hikedesc;
    this._hikeweather = hikeweather;
    this._hikeheartrate = hikeheartrate;
    }
    public int get_id(){
        return _id;
    }
    public String get_hikename(){
        return _hikename;
    }
    public void set_hikename(String _hikename){
        this._hikename = _hikename;
    }
    public void set_id(int _id){
        this._id = _id;
    }
    public void set_hikelocation(String _hikelocation){
        this._hikelocation = _hikelocation;
    }
    public String get_hikelocation(){
        return _hikelocation;
    }
    public void set_hikedate(String _hikedate){
        this._hikedate = _hikedate;
    }
    public String get_hikedate(){
        return _hikedate;
    }
    public void set_hikeparking(String _hikeparking){
        this._hikeparking = _hikeparking;
    }
    public String get_hikeparking(){
        return _hikeparking;
    }
    public void set_hikelenght(String _hikelenght){
        this._hikelenght = _hikelenght;
    }
    public String get_hikelenght(){
        return _hikelenght;
    }
    public void set_hikedifficulty(String _hikedifficulty){
        this._hikedifficulty = _hikedifficulty;
    }
    public String get_hikedifficulty(){
        return _hikedifficulty;
    }
    public String get_hikeweather(){
        return _hikeweather;
    }
    public void set_hikeweather(String _hikeweather){
        this._hikeweather = _hikeweather;
    }
    public void set_hikeheartrate(String _hikeheartrate){
        this._hikeheartrate = _hikeheartrate;
    }
    public String get_hikeheartrate(){
        return _hikeheartrate;
    }
    public void set_hikedesc(String _hikedesc){
        this._hikedesc = _hikedesc;
    }
    public String get_hikedesc(){
        return _hikedesc;
    }

}
