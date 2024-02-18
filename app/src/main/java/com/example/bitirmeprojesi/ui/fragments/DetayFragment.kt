package com.example.bitirmeprojesi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.FavoriYemek
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.FragmentDetayBinding
import com.example.bitirmeprojesi.ui.viewmodels.DetayViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetayFragment : Fragment() {
    private lateinit var binding: FragmentDetayBinding
    private val args by navArgs<DetayFragmentArgs>()
    private lateinit var viewModel: DetayViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var favCheck:Boolean=false
    private var sepetAdet:Int=1
    private var toplamTutar:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(DetayViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetayBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sepetAdetTextView.text="1"
        auth=Firebase.auth
        firestore=Firebase.firestore
        var collectionFavoriler=firestore.collection("Favoriler")

        var gelenYemek=args.yemek
        binding.toplamTutarTextView.text="${gelenYemek.yemek_fiyat}₺"

        binding.detayYemekFiyatTextView.text="${gelenYemek.yemek_fiyat}₺"
        binding.detayYemekTextView.text=gelenYemek.yemek_adi
        val url="http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
        Glide.with(requireContext()).load(url).override(300,300).into(binding.detayYemekImageView)

        /*collectionFavoriler.addSnapshotListener { value, error ->
            if (value!=null){
                val liste=ArrayList<FavoriYemek>()

                for(d in value.documents){
                   val favoriYemek=d.toObject(FavoriYemek::class.java)
                    if(favoriYemek!=null){
                        if(gelenYemek.yemek_id==favoriYemek.yemek_id){
                            favoriYemek.doc_id=d.id
                            /*favCheck=true
                            binding.favoriteButtonImage.setImageResource(R.drawable.baseline_favorite_24)*/
                            println("ilk kısım")
                            binding.favoriteButtonImage.isChecked=true
                        }else{
                            /*favCheck=false
                            binding.favoriteButtonImage.setImageResource(R.drawable.baseline_favorite_border_24)*/
                            binding.favoriteButtonImage.isChecked=false
                        }
                    }
                }
            }

        }*/

        collectionFavoriler.addSnapshotListener { value, error ->
            if (value != null) {
                for (document in value.documents) {
                    val favoriYemek = document.toObject(FavoriYemek::class.java)
                    if (favoriYemek != null) {
                        if (favoriYemek.yemek_id == gelenYemek.yemek_id) {
                            // Eğer favoriYemek, gelenYemek ile eşleşiyorsa, checkbox'ı seçili yap
                            binding.favoriteButtonImage.isChecked = true
                            return@addSnapshotListener // İşlemi sonlandır
                        }
                    }
                }
                // Eğer favoriYemek eşleşmesi bulunamazsa, checkbox'ı seçili olmayan yap
                binding.favoriteButtonImage.isChecked = false
            }
        }

        // gptye yaptırdım :)
        binding.favoriteButtonImage.setOnCheckedChangeListener { checkbox, isChecked ->
            if (isChecked){
                var favoriId=gelenYemek.yemek_id
                var favoriAd=gelenYemek.yemek_adi.toString()
                var favoriResim=gelenYemek.yemek_resim_adi.toString()
                var favoriFiyat=gelenYemek.yemek_fiyat.toString()
                //binding.favoriteButtonImage.setImageResource(R.drawable.baseline_favorite_24)
                viewModel.favoriKaydet(favoriId,favoriAd,favoriResim,favoriFiyat)
                println("eklendi")
                favCheck=true
            }else{
                collectionFavoriler.whereEqualTo("yemek_id", gelenYemek.yemek_id)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            viewModel.favoriSil(document.id)
                        }
                    }
                    .addOnFailureListener { exception ->
                       // Log.w(TAG, "Error getting documents: ", exception)
                    }
            }
        }

        // tıklama sorunu olusturan kısım
       /* binding.favoriteButtonImage.setOnCheckedChangeListener { compoundButton, b ->
            if (favCheck==false) {
                var favoriId=gelenYemek.yemek_id
                var favoriAd=gelenYemek.yemek_adi.toString()
                var favoriResim=gelenYemek.yemek_resim_adi.toString()
                var favoriFiyat=gelenYemek.yemek_fiyat.toString()
               //binding.favoriteButtonImage.setImageResource(R.drawable.baseline_favorite_24)
                viewModel.favoriKaydet(favoriId,favoriAd,favoriResim,favoriFiyat)
                favCheck=true
            }else{
                collectionFavoriler.addSnapshotListener { value, error ->
                    if (value!=null){
                        val liste=ArrayList<FavoriYemek>()

                        for(d in value.documents){
                            val favoriYemek=d.toObject(FavoriYemek::class.java)
                            if(favoriYemek!=null){
                                if (favoriYemek.yemek_id==gelenYemek.yemek_id){
                                    favoriYemek.doc_id=d.id
                                    binding.favoriteButtonImage.setImageResource(R.drawable.baseline_favorite_border_24)
                                    viewModel.favoriSil(favoriYemek.doc_id.toString())

                                    favCheck=false
                                }
                            }
                        }
                    }

                }




            }
        }*/

        binding.pozitifButton.setOnClickListener {
            sepetAdet+=1
            binding.sepetAdetTextView.text=sepetAdet.toString()
            toplamTutar=gelenYemek.yemek_fiyat*sepetAdet
            binding.toplamTutarTextView.text="${toplamTutar}₺"
        }
        binding.negatifButton.setOnClickListener {
            if(sepetAdet==1){
                Toast.makeText(requireContext(),"Minimum adet 1 olabilir",Toast.LENGTH_LONG).show()
            }else{
                sepetAdet-=1
                binding.sepetAdetTextView.text=sepetAdet.toString()
                toplamTutar=gelenYemek.yemek_fiyat*sepetAdet
                binding.toplamTutarTextView.text="${toplamTutar}₺"
            }
        }

        binding.sepeteEkleButton.setOnClickListener {
            sepeteYemekEkle(gelenYemek)
        }
    }

    fun sepeteYemekEkle(gelenYemek:Yemekler){
        viewModel.sepeteYemekEkle(gelenYemek.yemek_adi,gelenYemek.yemek_resim_adi,gelenYemek.yemek_fiyat,sepetAdet,auth.currentUser?.email.toString())
    }


}