package com.example.weatheradvisor;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import Util.Utils;
import data.CityPrefs;
import data.WeatherConnect;
import data.WeatherParser;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView pressure;
    private TextView humidity;
    private TextView sunset;
    private TextView sunrise;
    private TextView wind;
    private TextView updates;

    ImageView imageView;

    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityName = (TextView) findViewById(R.id.cityId);
        iconView = (ImageView) findViewById(R.id.icons);
        temp = (TextView) findViewById(R.id.temperature);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        sunrise = (TextView) findViewById(R.id.sunRise);
        sunset = (TextView) findViewById(R.id.sunSet);
        wind = (TextView) findViewById(R.id.wind);
        updates = (TextView) findViewById(R.id.updates);
        description = (TextView) findViewById(R.id.clouds);

        new GetTipsversion().execute();

        CityPrefs cityPrefs = new CityPrefs(MainActivity.this);

        renderWeatherData(cityPrefs.getCity());
    }


    public void renderWeatherData(String city){
        AtomicReference<WeatherTask> weatherTask = new AtomicReference<>(new WeatherTask());
        weatherTask.get().execute(new String[]{city + "&units=metric"});
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String[] params = new String[0];
            return downloadImage(params[0]) ;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            iconView.setImageBitmap(bitmap);
        }

        private Bitmap downloadImage(String code)
        {
            try {
                URL url = new URL(Utils.Icon_URL + code + ".png");
                Log.d("Data : ", url.toString());
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap currentBitmap = BitmapFactory.decodeStream(input);
                return currentBitmap;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     *
     */
    public class WeatherTask extends AsyncTask<String, Void, Weather> {
        @SuppressLint("WrongThread")
        @Override
        public Weather doInBackground(String... params)
        {
            String data = ((new WeatherConnect()).getWeatherData(params[0]));
            weather.iconData = weather.currentCondition.getIcon();

            try
            {
                weather = WeatherParser.getWeather(data);
                if (weather != null)
                {
                    Log.v("Data: ", weather.locations.getCity());
                }

                new DownloadImageAsyncTask().execute(weather.iconData);



            } catch (Exception e) {
                System.out.println(e);

            }
            return weather;
        }

        @Override
        public void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df =  DateFormat.getDateInstance();
            String sunriseDate = df.format(new Date(weather.locations.getSunrise()));
            String sunsetDate = df.format(new Date(weather.locations.getSunset()));
            String UpdateDate = df.format(new Date(weather.locations.getUpdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempformat = decimalFormat.format(weather.currentCondition.getTemperature());

            cityName.setText(weather.locations.getCity() + "," + weather.locations.setCountry() +"");
            temp.setText("" + tempformat + "C");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hpa");
            sunrise.setText("Sunrise: "+ weather.locations.getSunrise());
            sunset.setText("Sunset: " + weather.locations.setSunset());
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            wind.setText("Wind: "+ weather.winds.setSpeed() + "mps");
            updates.setText("Updated" + weather.locations.getLastUpdate());
            description.setText("Conditions: " + weather.currentCondition.getConditions() + "(" + weather.currentCondition.getDescriptions() + ")");

        }
    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Name City");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Portland,US");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPrefs cityPrefs = new CityPrefs(MainActivity.this);
                CityPrefs.setCity(cityInput.getText().toString());

                String newCity = cityPrefs.getCity();
                renderWeatherData(newCity);
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if( id == R.id.cityId ){
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetTipsversion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
        @Override
        protected void onPostExecute(String s)
        {
            if( weather != null)
            {
                updateAlertDialog();
            }
        }
    }

    private void updateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Do not forget to carry and Umbrella if it to rain.");
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}