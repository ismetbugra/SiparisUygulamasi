package com.example.bitirmeprojesi.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.AnasayfaRecyclerRowBinding
import com.example.bitirmeprojesi.ui.fragments.AnaSayfaFragmentDirections

class AnasayfaAdapter(var mContext: Context,var yemeklerListesi:List<Yemekler>): RecyclerView.Adapter<AnasayfaAdapter.AnasayfaVH>() {
    class AnasayfaVH(var binding: AnasayfaRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnasayfaVH {
        return AnasayfaVH(AnasayfaRecyclerRowBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }

    override fun onBindViewHolder(holder: AnasayfaVH, position: Int) {
        val t=holder.binding
        val yemek=yemeklerListesi[position]
        //http://kasimadalan.pe.hu/yemekler/resimler/
        val url="http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300,300).into(t.anasayfaYemekImageView)
        t.anasayfaYemekTextView.text=yemek.yemek_adi
        t.anasayfaYemekFiyatTextView.text="${yemek.yemek_fiyat}₺"

        t.cardView.setOnClickListener {
            val action=AnaSayfaFragmentDirections.actionAnaSayfaFragmentToDetayFragment(yemek)
            Navigation.findNavController(it).navigate(action)
        }


    }
}