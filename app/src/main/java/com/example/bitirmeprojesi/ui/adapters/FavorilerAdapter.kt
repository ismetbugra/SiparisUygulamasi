package com.example.bitirmeprojesi.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.FavoriYemek
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.FavorilerRecyclerRowBinding
import com.example.bitirmeprojesi.ui.fragments.FavorilerFragmentDirections


class FavorilerAdapter(var mContext: Context,var favorilerList:List<FavoriYemek>): RecyclerView.Adapter<FavorilerAdapter.FavorilerVH>() {
    class FavorilerVH(var binding:FavorilerRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorilerVH {
        return FavorilerVH(FavorilerRecyclerRowBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun getItemCount(): Int {
        return favorilerList.size
    }

    override fun onBindViewHolder(holder: FavorilerVH, position: Int) {
        val t=holder.binding
        val favoriYemek=favorilerList[position]
        t.favorilerAdTextView.text=favoriYemek.yemek_adi
        t.favorilerFiyatTextView.text=favoriYemek.yemek_fiyat
        val url="http://kasimadalan.pe.hu/yemekler/resimler/${favoriYemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300,300).into(t.favorilerImageView)
        val gidenYemek=Yemekler(favoriYemek.yemek_id!!,favoriYemek.yemek_adi!!,favoriYemek.yemek_resim_adi!!,favoriYemek.yemek_fiyat!!.toInt())
        t.favorilerCardView.setOnClickListener {
            val action=FavorilerFragmentDirections.actionFavorilerFragmentToDetayFragment(gidenYemek)
            Navigation.findNavController(it).navigate(action)
        }
    }
}