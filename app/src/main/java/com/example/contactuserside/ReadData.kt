package com.example.contactuserside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.contactuserside.databinding.ActivityReadDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadData : AppCompatActivity() {



    private lateinit var binding : ActivityReadDataBinding

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.posto)

        button.setOnClickListener {

            val intent = Intent(this, PostActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        binding.readdataBtn.setOnClickListener{
            val userName : String = binding.etusername.text.toString()
            if (userName.isNotEmpty()){

                readData(userName)

            }else{
                Toast.makeText(this, "Please Enter The UID", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun readData(showGraph: String) {

        database = FirebaseDatabase.getInstance().getReference("users")

        database.child(showGraph).get().addOnSuccessListener{

            if (it.exists()){

                val name = it.child("name").value
                val phno = it.child("mobile").value
                val Report = it.child("positive").value
                val location = it.child("visited").child("0").child("address").value
                val inTime = it.child("visited").child("0").child("inTime").value
                val outTime = it.child("visited").child("0").child("outTime").value
                val readerUID = it.child("visited").child("0").child("readerUID").value





                binding.etusername.text.clear()
                binding.name.text = name.toString()
                binding.phno.text = phno.toString()
                binding.pos.text = Report.toString()
                binding.place.text = location.toString()
                binding.intime.text = inTime.toString()
                binding.out.text = outTime.toString()
                binding.uid.text = readerUID.toString()


            }else{

                Toast.makeText(this, "Wrong UID Entered", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{

            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

        }
    }

}