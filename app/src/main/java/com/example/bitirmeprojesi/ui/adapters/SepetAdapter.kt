package com.example.bitirmeprojesi.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.databinding.SepetRecyclerRowBinding
import com.example.bitirmeprojesi.ui.viewmodels.SepetViewModel
import com.google.firebase.auth.FirebaseAuth

class SepetAdapter(var mContext: Context,var sepetYemeklerList:List<SepetYemekler>,var viewModel: SepetViewModel,var auth:FirebaseAuth): RecyclerView.Adapter<SepetAdapter.SepetVH>() {
    class SepetVH(var binding: SepetRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetVH {
        return SepetVH(SepetRecyclerRowBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    override fun getItemCount(): Int {
        return sepetYemeklerList.size
    }

    override fun onBindViewHolder(holder: SepetVH, position: Int) {
        val t=holder.binding
        val gelenYemek=sepetYemeklerList[position]

        t.sepetYemekTextView.text=gelenYemek.yemek_adi
        t.sepetToplamTutarTextView.text="${gelenYemek.yemek_siparis_adet*gelenYemek.yemek_fiyat}₺"
        t.sepetYemekAdetTextView.text="Adet:${gelenYemek.yemek_siparis_adet}"
        t.sepetYemekFiyatTextView.text="Fiyat:${gelenYemek.yemek_fiyat}"
        val url="http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300,300).into(t.sepetYemekImageView)

        t.deleteYemekImageView.setOnClickListener {
            viewModel.sepettekiYemekSil(gelenYemek.sepet_yemek_id.toInt(),auth.currentUser!!.email.toString())
        }
    }
}