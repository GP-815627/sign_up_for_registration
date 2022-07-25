package com.azizbek.salon

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.azizbek.salon.databinding.ActivityMain2Binding
import com.azizbek.salon.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val firebaseauth=FirebaseAuth.getInstance()
        val pragresdialog=ProgressDialog(this)
        binding.lottle.playAnimation()
        pragresdialog.setTitle("Загрузка")
        pragresdialog.setMessage("Пожалуйста подождите...")

        val databaseRedense= FirebaseDatabase.getInstance().reference.child("Userlarim")

        binding.bottom1.setOnClickListener {
            pragresdialog.show()
            firebaseauth.createUserWithEmailAndPassword(binding.edittext5.text.toString(),binding.edittext6.text.toString()).addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful){

                        Toast.makeText(this@MainActivity2,"Успешно!",Toast.LENGTH_SHORT).show()
                        databaseRedense.child("${firebaseauth.uid}")
                        pragresdialog.hide()
                        val user=Userlarim(binding.edittext3.text.toString(),binding.edittext4.text.toString(),binding.edittext5.text.toString(),binding.edittext6.text.toString(),"${firebaseauth.uid}")
                        databaseRedense.child("${firebaseauth.currentUser!!.uid}").setValue(user)
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                })
        }

    }
}