package com.azizbek.salon

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizbek.salon.databinding.ActivityMain2Binding
import com.azizbek.salon.databinding.ActivityMain3Binding
import com.azizbek.salon.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.util.*

class MainActivity3 : AppCompatActivity() {
    lateinit var binding:ActivityMain3Binding
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    val arrayList= ArrayList<Navbat>()
        var ism=""
        var familya=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("navbatlar")
        binding.lottle.playAnimation()
        databaseReference= FirebaseDatabase.getInstance().reference.child("Userlarim").child(MainActivity.uid!!)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                 ism = snapshot.child("name").getValue() as String
                 familya = snapshot.child("surname").getValue() as String
                binding.text1.setText(ism + " " + familya)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.relative.setOnClickListener {
            MainActivity.a = 1

            startActivity(Intent(this@MainActivity3, MainActivity::class.java))

        }
        binding.floatingActionButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity3)

            builder.setTitle("Подписка на очередь")
            builder.setTitle("Укажите время подписки на очередь")
            val view = LayoutInflater.from(this@MainActivity3).inflate(R.layout.vaqt_layaunt, null)
            builder.setView(view)
            val textstart = view.findViewById<TextView>(R.id.textTime1)
            val textstop = view.findViewById<TextView>(R.id.textTime2)
            val bottomtime = view.findViewById<Button>(R.id.buttontime)

            textstart.setOnClickListener {
                val calendar = Calendar.getInstance()
                var hour = calendar.get(Calendar.HOUR_OF_DAY)
                var minute = calendar.get(Calendar.MINUTE)
                val timeSetListner = TimePickerDialog.OnTimeSetListener { it, hour, minute ->
                    if (hour<10){
                        if (minute<10){
                            textstart.setText("0$hour:0$minute")
                        }else{
                            textstart.setText("0$hour:$minute")
                        }

                    }else{
                        textstart.setText("$hour:$minute")
                    }
                }
                val timePickerDialog =TimePickerDialog(this@MainActivity3, timeSetListner, hour, minute, true)
                timePickerDialog.show()



            }
            val dialog=builder.create()
            textstop.setOnClickListener {
                val calendar = Calendar.getInstance()
                var hour = calendar.get(Calendar.HOUR_OF_DAY)
                var minute = calendar.get(Calendar.MINUTE)
                val timeSetListner = TimePickerDialog.OnTimeSetListener { it, hour, minute ->
                    if (hour<10){
                        if (minute<10){
                            textstop.setText("0$hour:0$minute")
                        }else{
                            textstop.setText("0$hour:$minute")
                        }

                    }else{
                        textstop.setText("$hour:$minute")
                    }
                }
                val timePickerDialog =TimePickerDialog(this@MainActivity3, timeSetListner, hour, minute, true)
                timePickerDialog.show()
            }
            bottomtime.setOnClickListener {
                val navbat=Navbat("$ism", "$familya","${textstart.text}","${textstop.text}")
                databaseReference2.push().setValue(navbat)
                dialog.hide()
                Toast.makeText(this@MainActivity3,"Добавлен",Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
        databaseReference2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot in snapshot.children){
                    var navbat=datasnapshot.getValue(Navbat::class.java)
                    arrayList.add(navbat!!)
                    val useradapter=UserAdapter(this@MainActivity3,arrayList)
                    binding.recyclearview.layoutManager=LinearLayoutManager(this@MainActivity3)
                    binding.recyclearview.adapter=useradapter

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

