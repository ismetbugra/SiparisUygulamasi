package com.example.bitirmeprojesi.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.databinding.FragmentSepetBinding
import com.example.bitirmeprojesi.ui.adapters.SepetAdapter
import com.example.bitirmeprojesi.ui.viewmodels.SepetViewModel
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var sepetYemeklerList : List<SepetYemekler>
    private var sepetToplamTutar:Int?=0



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
       /* viewModel.sepettekiYemekleriGetir(auth.currentUser!!.email.toString())
        binding.sepetRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        adapter= SepetAdapter(requireContext(),sepetYemeklerList,viewModel,auth)
        binding.sepetRecyclerView.adapter=adapter*/

       /* val sepetYemeklerArrayList=ArrayList<SepetYemekler>()
        adapter= SepetAdapter(requireContext(),sepetYemeklerArrayList,viewModel,auth)
        binding.sepetRecyclerView.adapter=adapter*/

        viewModel.sepetYemeklerList.observe(viewLifecycleOwner, Observer {
            binding.sepetRecyclerView.layoutManager=LinearLayoutManager(requireContext())
            adapter= SepetAdapter(requireContext(),it,viewModel,auth)
            binding.sepetRecyclerView.adapter=adapter
            sepetToplamTutar=toplamTutarHesap(it)
            binding.siparisTutarTextView.text="Toplam Tutar: ${sepetToplamTutar}₺"
            sepetYemeklerList=it
        })


        binding.siparisVerButton.setOnClickListener {
            siparisVer(it)
        }

        viewModel.adres.observe(viewLifecycleOwner,Observer{
            Toast.makeText(requireContext(),"observer tetiklendi", Toast.LENGTH_LONG).show()
        })

    }

    fun toplamTutarHesap(sepetList:List<SepetYemekler>):Int{
        var toplamTutar=0
        for (yemek in sepetList){
            var yemekTutar=yemek.yemek_fiyat*yemek.yemek_siparis_adet
            toplamTutar+=yemekTutar
        }
        return toplamTutar

    }

    fun siparisVer(view: View){
        /*var dialogLayout=LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_layout,null)
        /*var buttonOnayla=dialogLayout.findViewById<Button>(R.id.onaylaButton)
        buttonOnayla.setOnClickListener{
            Snackbar.make(view,"Tıklandı dialog",Snackbar.LENGTH_LONG).show()
        }*/
        val dialog=Dialog(requireContext())
        dialog.setContentView(dialogLayout)
        dialog.create()
        dialog.show()
        val adresEditText=dialog.findViewById<EditText>(R.id.adresEditText)
        //adresEditText.setText("merhaba")

        val buttonOnayla=dialog.findViewById<Button>(R.id.onaylaButton)
        buttonOnayla.setOnClickListener {
            val adrestext=adresEditText.text.toString()
            Log.e("adres",adrestext)
            Snackbar.make(view,"Tıklandı ${adrestext.toString()}",Snackbar.LENGTH_LONG).show()
        }*/
        //Snackbar.make(view,"Tıklandı",Snackbar.LENGTH_LONG).show()

        /*val action=SepetFragmentDirections.actionSepetFragmentToSheetFragment()
        Navigation.findNavController(view).navigate(action)*/

        SheetFragment().show(requireActivity().supportFragmentManager,SheetFragment().tag)





        //val adres=args.adres
        //Snackbar.make(view,"Tıklandı ${adres.toString()}",Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriGetir(auth.currentUser!!.email.toString())

    }


}