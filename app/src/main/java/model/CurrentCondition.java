package model;

public class CurrentCondition {
    private int weatherId;
    private String conditions;
    private String descriptions;
    private String icon;
    private float pressure;
    private float humidity;
    private float maximum_temp;
    private float minimum_temp;
    private double temperature;

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getMaximum_temp() {
        return maximum_temp;
    }

    public void setMaximum_temp(float maximum_temp) {
        this.maximum_temp = maximum_temp;
    }

    public float getMinimum_temp() {
        return minimum_temp;
    }

    public void setMinimum_temp(float minimum_temp) {
        this.minimum_temp = minimum_temp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
