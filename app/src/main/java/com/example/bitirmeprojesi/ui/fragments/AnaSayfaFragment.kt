package com.example.bitirmeprojesi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentAnaSayfaBinding
import com.example.bitirmeprojesi.databinding.FragmentLoginBinding
import com.example.bitirmeprojesi.ui.adapters.AnasayfaAdapter
import com.example.bitirmeprojesi.ui.viewmodels.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnaSayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnaSayfaBinding
    private lateinit var viewModel: AnasayfaViewModel
    private lateinit var adapter:AnasayfaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(AnasayfaViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAnaSayfaBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.yemeklerList.observe(viewLifecycleOwner, Observer {
            adapter= AnasayfaAdapter(requireContext(),it)
            binding.anasayfaRecyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            binding.anasayfaRecyclerView.adapter=adapter
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.tumYemekleriGetir()
    }


}