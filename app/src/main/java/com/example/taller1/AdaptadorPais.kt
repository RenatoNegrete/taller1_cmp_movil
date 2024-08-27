package com.example.taller1

import android.Manifest
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
            .load(binding.flag)


        binding.telefono.setOnClickListener {
         /*   val phoneNumber = "1234567890"
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            } else {

                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 1)
            }

*/
        }
        return view
    }
}