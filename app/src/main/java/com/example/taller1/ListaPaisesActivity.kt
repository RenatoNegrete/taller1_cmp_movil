package com.example.taller1
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.databinding.ActivityListaPaisesBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class ListaPaisesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityListaPaisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPaisesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val continente_seleccionado = intent.getStringExtra("RegionSeleccionada")
        val listaPaises = parseJSON(continente_seleccionado.toString())

        val adapter = AdaptadorPais(this, listaPaises)
        binding.listView.adapter = adapter
    }

    private fun parseJSON(region: String): List<Pais> {
        val inputStream: InputStream = assets.open("paises.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(json)
        val paises = mutableListOf<Pais>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.getString("Region") == region) {
                paises.add(
                    Pais(
                        name = jsonObject.getString("Name"),
                        nativeName = jsonObject.getString("NativeName"),
                        AlphaCode = jsonObject.getString("Alpha3Code"),
                        Currency = jsonObject.getString("Currency"),
                        FlagUrl = jsonObject.getString("FlagPng"),
                        NumericCode = jsonObject.getInt("NumericCode").toString()
                    )
                )
            }

        }
        return paises
    }
}
data class Pais(
    val name: String,
    val nativeName: String,
    val AlphaCode: String,
    val Currency: String,
    val FlagUrl: String,
    val NumericCode: String
)