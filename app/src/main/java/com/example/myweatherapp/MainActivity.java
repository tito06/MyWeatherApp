package com.example.myweatherapp;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHTTPClient;
import model.Weather;
import util.Utils;

public class MainActivity extends AppCompatActivity {


    Weather weather = new Weather();


    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.cityText);
       // iconView = findViewById(R.id.thumbnailIcon);
        temp = findViewById(R.id.tempText);
        description = findViewById(R.id.cloudText);
        humidity = findViewById(R.id.humidText);
        pressure = findViewById(R.id.pressureText);
        wind = findViewById(R.id.windText);
        sunrise = findViewById(R.id.riseText);
        sunset = findViewById(R.id.setText);
        updated = findViewById(R.id.updateText);

        CityPreference cityPreference = new CityPreference(MainActivity.this);



        renderWeatherData(cityPreference.getCity());
    }

   public  void  renderWeatherData(String city){

       WeatherTask weatherTask = new WeatherTask();
       weatherTask.execute(new String[]{city+ "&units=metric" + "&appid=1da1b172b4b79d244221eea61b2406a1"});
       //weatherTask.execute(new String[]{city + "&units=metric"});
    }
















   private class WeatherTask extends AsyncTask<String, Void, Weather>{


        @Override
        protected Weather doInBackground(String... params) {

         //   String data =((new WeatherHTTPClient()).getWeatherData(params[0]));
            String data = ((new WeatherHTTPClient()).getWeatherData(params[0]));
            weather = JSONWeatherParser.getWeather(data);

            Log.v("Data:" , weather.place.getCity());


            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getTimeInstance();

            String sunriseDate = df.format(new Date(weather.place.getSunRise()));
            String sunsetDate = df.format(new Date(weather.place.getSunSet()));
            String updateDate = df.format(new Date(weather.place.getLastUpdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            String tempFormat = decimalFormat.format(weather.currentCondition.getTemp());

            cityName.setText(weather.place.getCity()+ "," + weather.place.getCountry());
            temp.setText(""+ tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() +"%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure()+ "hPa");
            wind.setText("Wind: "+ weather.wind.getSpeed()+ "m/sec");
            sunrise.setText("Sunrise: "+ sunriseDate);
            sunset.setText("Sunset: "+ sunsetDate);
            updated.setText("Last Updated: " + updateDate);
            description.setText("Condtion: " + weather.currentCondition.getCondition() + "(" +
                    weather.currentCondition.getDescription()+ ")");
        }

    }


    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Bangalore,IN");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());


                String newCity = cityPreference.getCity();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_city){
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}
