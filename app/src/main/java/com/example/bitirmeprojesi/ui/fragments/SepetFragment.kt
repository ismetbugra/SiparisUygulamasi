package com.example.bitirmeprojesi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.databinding.FragmentSepetBinding
import com.example.bitirmeprojesi.ui.adapters.SepetAdapter
import com.example.bitirmeprojesi.ui.viewmodels.SepetViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {
    private lateinit var binding: FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel
    private lateinit var adapter: SepetAdapter
    private lateinit var auth: FirebaseAuth
    private var sepetYemeklerList=ArrayList<SepetYemekler>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(SepetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSepetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth=Firebase.auth
        viewModel.sepettekiYemekleriGetir(auth.currentUser!!.email.toString())
        binding.sepetRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        adapter= SepetAdapter(requireContext(),sepetYemeklerList,viewModel,auth)
        binding.sepetRecyclerView.adapter=adapter

        val sepetYemeklerArrayList=ArrayList<SepetYemekler>()
        adapter= SepetAdapter(requireContext(),sepetYemeklerArrayList,viewModel,auth)
        binding.sepetRecyclerView.adapter=adapter

        viewModel.sepetYemeklerList.observe(viewLifecycleOwner, Observer {
            binding.sepetRecyclerView.layoutManager=LinearLayoutManager(requireContext())
            adapter= SepetAdapter(requireContext(),it,viewModel,auth)
            binding.sepetRecyclerView.adapter=adapter
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriGetir(auth.currentUser!!.email.toString())
    }


}