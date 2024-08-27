package com.example.taller1

import android.Manifest
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import  android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.taller1.databinding.TarjetaPaisBinding
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager



class AdaptadorPais (context: Context,private val paises : List <Pais>): ArrayAdapter<Pais>(context,0,paises)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding : TarjetaPaisBinding
        val view: View
        if(convertView == null){
            binding = TarjetaPaisBinding.inflate(LayoutInflater.from(context),parent, false)
            view = binding.root
            view.tag = binding
        } else{
            binding = convertView.tag as TarjetaPaisBinding
            view = convertView
        }
        val pais = paises[position]
        binding.countryName.text = pais.name
        binding.nativeName.text = pais.nativeName
        binding.currency.text = "${pais.AlphaCode} ${pais.Currency}"

        Glide.with(context)
            .load(pais.FlagUrl)
            .into(binding.flag)

        view.setOnClickListener {
            val intent = Intent(context, InfoPaisActivity::class.java)
            intent.putExtra("CountryName", pais.name)
            context.startActivity(intent)
        }

        binding.telefono.setOnClickListener {
            val phoneNumber = pais.NumericCode
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED) {
                context.startActivity(intent)
            } else {
                ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.CALL_PHONE), 1)
            }

        }
        return view
    }
}