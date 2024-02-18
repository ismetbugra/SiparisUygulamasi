package com.example.bitirmeprojesi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bitirmeprojesi.databinding.ActivityMainBinding
import com.example.bitirmeprojesi.databinding.FragmentSepetBinding
import com.example.bitirmeprojesi.ui.activity.FeedActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=Firebase.auth

        //var olan kullanıcıyı alma işlemi -- kullanıcı varsa direkt feedactivitye gidecek
        val currentUser=auth.currentUser
        if(currentUser!=null){
            val intent=Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.girisYapButton.setOnClickListener {
            girisYap(it)
            /*val intent= Intent(this,FeedActivity::class.java)
            startActivity(intent)*/
        }

        binding.kayitOlButton.setOnClickListener {
            kayitOl(it)
        }


    }

    fun kayitOl(view:View){
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        if(email.equals("")||password.equals("")){
            Snackbar.make(view,"Lütfen email veya parolanızı giriniz!", Snackbar.LENGTH_LONG).show()

        }else{
            if(password.length<6){
                binding.textInputLayout2.error="Parolanızda en az 6 karakter olmalıdır!"
            }else{
                //kullanıcı kaydı yapıldı
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    //kullanıcı basarılı sekılde olusuturulsa sonrasdında asenkron olarak ne calısacak
                    Snackbar.make(view,"Hesabınız başarılı bir şekilde oluşturulmuştur",Snackbar.LENGTH_LONG).show()
                    val intent=Intent(this,FeedActivity::class.java)
                    startActivity(intent)
                    finish()


                }.addOnFailureListener {
                    //hata olursa
                    Snackbar.make(view,it.localizedMessage, Snackbar.LENGTH_LONG).show()

                }

            }
        }
    }

    fun girisYap(view: View){
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        if(email.equals("")||password.equals("")){
            Snackbar.make(view,"Lütfen email veya parolanızı giriniz!",Snackbar.LENGTH_LONG).show()

        }else{
            if(password.length<6){
                binding.textInputLayout2.error="Parolanızda en az 6 karakter olmalıdır!"
            }else{
                //kullanıcı kaydı yapıldı
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    //kullanıcı basarılı sekılde giriş yapılırsa sonrasdında asenkron olarak ne calısacak
                    val intent=Intent(this,FeedActivity::class.java)
                    startActivity(intent)
                    finish()


                }.addOnFailureListener {
                    //hata olursa
                    Snackbar.make(view,"Parolanız yanlış lütfen kontrol ediniz!",Snackbar.LENGTH_LONG).show()

                }

            }
        }
    }
}