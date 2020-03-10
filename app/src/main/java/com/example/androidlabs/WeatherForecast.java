package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);



    }


    private class ForecastQuery extends AsyncTask <String, Integer,String>{
        private String temp;
        private String minTemp;
        private String maxTemp;
        private double uvRate;
        private Bitmap image;






        protected String doInBackground(String... strings) {


            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
            try {
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream res = connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( res  , "UTF-8");

                String parameter = null;
                int eventType = xpp.getEventType();


                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            temp = xpp.getAttributeValue(null,    "value");
                            onProgressUpdate(20);

                             minTemp = xpp.getAttributeValue(null, "min");
                            onProgressUpdate(40);

                             maxTemp = xpp.getAttributeValue(null, "max");
                            onProgressUpdate(60);

                        }
/*
                        else if(xpp.getName().equals("AMessage"))
                        {
                            parameter = xpp.getAttributeValue(null, "message"); // this will run for <AMessage message="parameter" >
                        }
                        else if(xpp.getName().equals("Weather"))
                        {
                            parameter = xpp.getAttributeValue(null, "outlook"); //this will run for <Weather outlook="parameter"
                            parameter = xpp.getAttributeValue(null, "windy"); //this will run for <Weather windy="paramter"  >
                        }
                        else if(xpp.getName().equals("Temperature"))
                        {
                            xpp.next(); //move the pointer from the opening tag to the TEXT event
                            parameter = xpp.getText(); // this will return  20
                        }*/
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

            }catch(IOException | XmlPullParserException ex ){
                Log.e("Error", ex.getMessage());
            }


            urlString = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
            try {
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();
                InputStream res = connection.getInputStream();

                BufferedReader reader = new BufferedReader( new InputStreamReader( res, "UTF-8"), 8);

                StringBuilder sb =new StringBuilder();
                String line = null;

                while ((line =reader.readLine()) != null){
                    sb.append(line = "\n");
                }


                String result = sb.toString();
                JSONObject uv = new JSONObject(result);
                uvRate = uv.getDouble("value");
                onProgressUpdate(80);

            }catch(IOException | JSONException ex ){
                Log.e("Error", ex.getMessage());
            }



/*
            String iconName = "rainy";
            urlString = "http://openweathermap.org/img/w/" + iconName + ".png";
            image = null;
            try {
            URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }

                //String imageFile = iconName + ".png";

                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);

                FileInputStream fis = null;
                try {    fis = openFileInput(imagefile);   }
                catch (FileNotFoundException e) {    e.printStackTrace();  }
                Bitmap bm = BitmapFactory.decodeStream(fis);


                outputStream.flush();
                outputStream.close();
                onProgressUpdate(100);
            }catch(IOException ex ){
                Log.e("Error", ex.getMessage());
            }*/
            return "done";
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }


        public void onProgressUpdate(int a){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setProgress(a);
        }

        protected void onPostExecute(String fromDoInBackground) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE );

            TextView tempView = (TextView) findViewById(R.id.temp);
            TextView minView = (TextView) findViewById(R.id.minTemp);
            TextView maxView = (TextView) findViewById(R.id.maxTemp);
            TextView uvView = (TextView) findViewById(R.id.uv);

            tempView.setText(temp);
            minView.setText(minTemp);
            maxView.setText(maxTemp);
            uvView.setText(Double.toString(uvRate));


            Log.i ("HTTP", fromDoInBackground);


        }
    }
}
