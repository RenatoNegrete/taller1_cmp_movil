package com.example.taller1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.taller1.databinding.ActivityInfoPaisBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class InfoPaisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoPaisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoPaisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val country_name = intent.getStringExtra("CountryName")

        val jsonString = loadJSONFromAsset()
        val jsonArray = JSONArray(jsonString)

        val country = country_name?.let { findCountry(it, jsonArray) }

        if(country != null) {
            val flag = country.getString("FlagPng")
            val name = country.getString("Name")
            val nameOg = country.getString("NativeName")
            val alphaCode = country.getString("Alpha2Code") + " - " + country.getString("Alpha3Code")
            val language = "Language: " + country.getString("NativeLanguage")
            val location = country.getString("Region") + "/" + country.getString("SubRegion") + " (" + country.getString("Latitude") + "," + country.getString("Longitude") + ")"
            val currency = country.getString("CurrencyName") + " " + country.getString("CurrencyCode") + " (" + country.getString("CurrencySymbol") + ")"

            Glide.with(this)
                .load(flag)
                .into(binding.bandera)

            binding.nombreEng.text = name
            binding.nombreOg.text = nameOg
            binding.siglas.text = alphaCode
            binding.idioma.text = language
            binding.location.text = location
            binding.currency.text = currency
        } else {
            binding.nombreEng.text = "Country not found"
            binding.nombreOg.text = "Country not found"
            binding.siglas.text = "Country not found"
            binding.idioma.text = "Country not found"
            binding.location.text = "Country not found"
            binding.currency.text = "Country not found"
        }
    }

    private fun findCountry(name: String, countries :JSONArray): JSONObject? {
        for(i in 0 until countries.length()){
            val country = countries.getJSONObject(i)
            if(country.getString("Name") == name) {
                return country
            }
        }
        return null
    }

    private fun loadJSONFromAsset(): String {
        val json: String
        json = try {
            val inputStream = this.assets.open("paises.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch(ex: IOException){
            ex.printStackTrace()
            ""
        }
        return json
    }
}