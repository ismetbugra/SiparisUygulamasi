package com.example.bitirmeprojesi.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitirmeprojesi.MainActivity
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentAccountBinding
import com.example.bitirmeprojesi.databinding.FragmentFavorilerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAccountBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth=Firebase.auth

        binding.accountNameTextView.text=auth.currentUser!!.email.toString()

        binding.cikisYapButton.setOnClickListener {
            auth.signOut()
            val intent=Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }
    }


}