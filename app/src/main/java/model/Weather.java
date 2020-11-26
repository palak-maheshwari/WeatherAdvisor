package model;

public class Weather {
    public Locations locations;
    public String iconData;
    public CurrentCondition  currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Winds winds = new Winds();
    public Clouds clouds = new Clouds();
    public Snow snow = new Snow();
}
