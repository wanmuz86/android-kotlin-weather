package com.muzaffar.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var weatherTextView:TextView;
    lateinit var temperatureTextView: TextView;
    lateinit var pressureTextView:TextView;
    lateinit var humidityTextView:TextView;
    lateinit var cityEditText:EditText;
    lateinit var sunriseTextView: TextView;
    lateinit var sunsetTextView: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherTextView = findViewById(R.id.weatherTextView)
        cityEditText = findViewById(R.id.cityEditText)
        temperatureTextView = findViewById(R.id.tempTextView)
        pressureTextView = findViewById(R.id.pressureTextView)
        humidityTextView = findViewById(R.id.humidityTextView)
        sunriseTextView = findViewById(R.id.sunriseTextView)
        sunsetTextView = findViewById(R.id.sunsetTextView)
    }


    fun buttonPressed(view: View) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?q=${cityEditText.text}&apiKey=9fd7a449d055dba26a982a3220f32aa2"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                var responseJSON = JSONObject(response)
                var temperature =
                    responseJSON.getJSONObject("main").getDouble("temp") - 273.15
                temperatureTextView.text = "%.2f C".format(temperature)

                // Humidity \
                var humidity = responseJSON.getJSONObject("main").getInt("humidity")
                humidityTextView.text = "$humidity"

                // Pressure

                var pressure = responseJSON.getJSONObject("main").getInt("pressure")
                pressureTextView.text = "$pressure"

                var sunrise= responseJSON.getJSONObject("sys").getLong("sunrise")
                sunriseTextView.text = "${Date(sunrise* 1000)}"

                var sunset= responseJSON.getJSONObject("sys").getLong("sunset")
                sunsetTextView.text = "${Date(sunset* 1000)}"

                var weather = responseJSON.getJSONArray("weather")
                    .getJSONObject(0).getString("main")

                weatherTextView.text = weather
            },
            Response.ErrorListener { weatherTextView.text = "That didn't work!" })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}