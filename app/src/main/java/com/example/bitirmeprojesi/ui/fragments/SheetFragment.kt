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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Adres
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.SiparisYemekler
import com.example.bitirmeprojesi.databinding.FragmentSheetBinding
import com.example.bitirmeprojesi.ui.viewmodels.SepetViewModel
import com.example.bitirmeprojesi.ui.viewmodels.SheetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentSheetBinding
    private lateinit var viewModel: SepetViewModel
    private lateinit var viewModelSheet:SheetViewModel
    private lateinit var sepetYemeklerList: List<SepetYemekler>
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(SepetViewModel::class.java)
        viewModelSheet=ViewModelProvider(this).get(SheetViewModel::class.java)

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

        auth= Firebase.auth


        binding.siparisOnaylaButton.setOnClickListener {
            /*viewModel.setAdres(binding.siparisAdresEditText.text.toString())
            Log.e("adres",viewModel.adres.value.toString())*/
            var siparisAd=""
            var toplamTutar=0
            var kullaniciAdi=auth.currentUser!!.email.toString()
            for (siparis in sepetYemeklerList){
                siparisAd+="${siparis.yemek_adi}-"
                toplamTutar+=siparis.yemek_fiyat*siparis.yemek_siparis_adet

            }
            var siparisToplamTutar=toplamTutar.toString()
            var adres_adi=binding.siparisAdresEditText.text.toString()
            var adres=Adres(0,adres_adi)
            viewModelSheet.adresEkle(adres)
            viewModelSheet.adresGetir(adres_adi)

            binding.siparisYemeklerTextView.text="Sipariş Detayı: ${siparisAd}"
            binding.tutarTextView.text="Sipariş Tutarı: ${siparisToplamTutar}"

            viewModelSheet.adres.observe(viewLifecycleOwner, Observer {
                var siparis=SiparisYemekler(0,siparisAd,siparisToplamTutar,kullaniciAdi,it.adres_id)
                viewModelSheet.siparisEkle(siparis)
                viewModelSheet.siparisler.observe(viewLifecycleOwner, Observer {
                    Log.e("siparis",it[0].siparis_yemekler)
                })
                //Toast.makeText(requireContext(),"observer tetiklendi ${it.adres_bilgisi} ", Toast.LENGTH_LONG).show()
            })
            //Toast.makeText(requireContext(),"observer tetiklendi $siparisAd ", Toast.LENGTH_LONG).show()
            dismiss()

            //sipariş onaylanıp kapandıktan sonra dialog çagrılcak
            var dialogLayout=LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_layout,null)
            /*var buttonOnayla=dialogLayout.findViewById<Button>(R.id.onaylaButton)
            buttonOnayla.setOnClickListener{
                Snackbar.make(view,"Tıklandı dialog",Snackbar.LENGTH_LONG).show()
            }*/
            val dialog= Dialog(requireContext())
            dialog.setContentView(dialogLayout)
            dialog.create()
            dialog.show()
            //adresEditText.setText("merhaba")

            val buttonOnayla=dialog.findViewById<Button>(R.id.bittiButton)
            buttonOnayla.setOnClickListener {
                dialog.dismiss()
            }
            //Toast.makeText(requireContext(),"Alert",Toast.LENGTH_LONG).show()
        }

        viewModel.sepetYemeklerList.observe(viewLifecycleOwner, Observer {
            /*val yemek=it[0].yemek_adi
            Toast.makeText(requireContext(),"observer tetiklendi $yemek ", Toast.LENGTH_LONG).show()*/
            sepetYemeklerList=it
            //verileri buradan al sheet fragmentta sipariş olustur

            var siparisAd=""
            var toplamTutar=0
            var kullaniciAdi=auth.currentUser!!.email.toString()
            for (siparis in sepetYemeklerList){
                siparisAd+="${siparis.yemek_adi}-"
                toplamTutar+=siparis.yemek_fiyat*siparis.yemek_siparis_adet

            }
            var siparisToplamTutar=toplamTutar.toString()

            binding.siparisYemeklerTextView.text="Sipariş Detayı: ${siparisAd}"
            binding.tutarTextView.text="Sipariş Toplam Tutar: ${siparisToplamTutar}"
        })





    }

    fun siparisBilgileriAl(){

    }


}