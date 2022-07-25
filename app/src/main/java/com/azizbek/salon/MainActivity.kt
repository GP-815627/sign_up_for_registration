package com.azizbek.salon

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azizbek.salon.databinding.ActivityMain2Binding
import com.azizbek.salon.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference

    companion object {
        var a=0
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor:SharedPreferences
        var ism=""
        var familya=""
        var  uid:String?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pragresdialog= ProgressDialog(this)
        firebaseAuth= FirebaseAuth.getInstance()
        binding.lottle.playAnimation()
        databaseReference= FirebaseDatabase.getInstance().reference.child("Useri")
    if (a==0){
        oqibolish()
    }


        pragresdialog.setTitle("Загрузка")
        pragresdialog.setMessage("Пожалуйста подождите...")

        
        binding.bottom2.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }
        binding.bottom1.setOnClickListener {
            pragresdialog.show()
            firebaseAuth.signInWithEmailAndPassword(binding.edittext1.text.toString(),binding.edittext2.text.toString()).addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful){
                        pragresdialog.hide()
                        uid=firebaseAuth.currentUser!!.uid
                        saqlash()
                        startActivity(Intent(this,MainActivity3::class.java))
                    }
                })

        }


    }
    fun saqlash(){
        sharedPreferences=getPreferences(MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putString("abc123", uid)
         editor.commit()
    }
    fun oqibolish() {
        sharedPreferences = getPreferences(MODE_PRIVATE)
        uid=sharedPreferences.getString("abc123",null)
        if (uid!=null){
            startActivity(Intent(this,MainActivity3::class.java))

        }
    }
}