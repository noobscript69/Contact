package com.example.contactuserside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.contactuserside.databinding.ActivityReadDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadData : AppCompatActivity() {

    private lateinit var binding : ActivityReadDataBinding
    private lateinit var database : DatabaseReference

    private var uidSave=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.posto)

        button.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }

        val button2 = findViewById<Button>(R.id.recy)

        button2.setOnClickListener {
            if (uidSave!=""){
                val intent=Intent(this,UserlistActivity::class.java)
//                Toast.makeText(this,uidSave,Toast.LENGTH_SHORT).show()
                intent.putExtra("uid", uidSave)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please Enter The UID", Toast.LENGTH_SHORT).show()
            }
        }

        binding.readdataBtn.setOnClickListener{
            val userName : String = binding.etusername.text.toString()
            if (userName.isNotEmpty()){
                binding.progressBarRead.visibility= View.VISIBLE
                binding.readdataBtn.visibility=View.INVISIBLE
                uidSave=userName
                readData(userName)
            }else{
                Toast.makeText(this, "Please Enter The UID", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun readData(showGraph: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(showGraph).get().addOnSuccessListener{
            binding.progressBarRead.visibility= View.INVISIBLE
            binding.readdataBtn.visibility=View.VISIBLE
            if (it.exists()){
//                val name = it.child("name").value
//                val phno = it.child("mobile").value
                val report = it.child("positive").value
//                val location = it.child("visited").child("0").child("address").value
//                val inTime = it.child("visited").child("0").child("inTime").value
//                val outTime = it.child("visited").child("0").child("outTime").value
//                val readerUID = it.child("visited").child("0").child("readerUID").value

                binding.etusername.text.clear()
//                binding.name.text = name.toString()
//                binding.phno.text = phno.toString()
                binding.pos.text = report.toString()
//                binding.place.text = location.toString()
//                binding.intime.text = inTime.toString()
//                binding.out.text = outTime.toString()
//                binding.uid.text = readerUID.toString()

            }else{
                Toast.makeText(this, "Wrong UID Entered", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            binding.progressBarRead.visibility= View.INVISIBLE
            binding.readdataBtn.visibility=View.VISIBLE
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }
    }

}