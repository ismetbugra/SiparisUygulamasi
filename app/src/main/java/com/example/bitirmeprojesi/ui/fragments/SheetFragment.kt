package com.example.bitirmeprojesi.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentSheetBinding
import com.example.bitirmeprojesi.ui.viewmodels.SepetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentSheetBinding
    private lateinit var viewModel: SepetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(SepetViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.siparisOnaylaButton.setOnClickListener {
            viewModel.setAdres(binding.siparisAdresEditText.text.toString())
            Log.e("adres",viewModel.adres.value.toString())
            dismiss()
        }

        viewModel.sepetYemeklerList.observe(viewLifecycleOwner, Observer {
            val yemek=it[0].yemek_adi
            Toast.makeText(requireContext(),"observer tetiklendi $yemek ", Toast.LENGTH_LONG).show()
            //verileri buradan al sheet fragmentta sipariş olustur
        })

        /*viewModel.adres.observe(viewLifecycleOwner, Observer {
            /* Log.e("kontrol","çagrıldı")
             var adresBilgisi=""
             adresBilgisi=it.toString()
             println(adresBilgisi)*/
            Toast.makeText(requireContext(),"observer tetiklendi", Toast.LENGTH_LONG).show()
        })*/


    }


}