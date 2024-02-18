package com.example.bitirmeprojesi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentFavorilerBinding
import com.example.bitirmeprojesi.ui.adapters.FavorilerAdapter
import com.example.bitirmeprojesi.ui.viewmodels.FavorilerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavorilerFragment : Fragment() {
    private lateinit var binding: FragmentFavorilerBinding
    private lateinit var viewModel: FavorilerViewModel
    private lateinit var adapter: FavorilerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(FavorilerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFavorilerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favorilerListesi.observe(viewLifecycleOwner, Observer {
            adapter= FavorilerAdapter(requireContext(),it)
            binding.favorierRecylerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            binding.favorierRecylerView.adapter=adapter
        })


    }


}