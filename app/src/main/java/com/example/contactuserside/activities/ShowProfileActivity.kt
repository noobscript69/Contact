package com.example.contactuserside.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactuserside.adapters.MyAdapter
import com.example.contactuserside.R
import com.example.contactuserside.models.User
import com.example.contactuserside.databinding.ActivityShowProfileBinding
import com.example.contactuserside.db.viewModels.ViewModel
import com.google.firebase.database.*

class ShowProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShowProfileBinding
    private lateinit var database : DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<User>

    private var uidSave=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uidSave= intent.getStringExtra("uid").toString()

        binding.posto.setOnClickListener {
            val intent=Intent(this, ReportPositiveActivity::class.java)
            intent.putExtra("uid",uidSave)
            startActivity(intent)
        }

        readData(uidSave)

        binding.signOutButton.setOnClickListener {
            binding.signOutProBar.visibility=View.VISIBLE
            binding.signOutButton.visibility=View.INVISIBLE
            val viewModel= ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[ViewModel::class.java]

            viewModel.deleteRepo()
            Handler().postDelayed({
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, 1000)
        }

        userRecyclerview = binding.recyclerViewList
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf()
        getUserData()
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("users")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear()
                    val uid = intent.getStringExtra("uid")!!
                    for (userSnapshot in snapshot.child(uid).child("visited").children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerview.adapter = MyAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase","Firebase misbehaving")
            }
        })
    }

    private fun readData(showGraph: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(showGraph).get().addOnSuccessListener{
            binding.progressBarRead.visibility= View.GONE
            if (it.exists()){
                binding.progressBarRead.visibility=View.GONE
                val report = it.child("positive").value
                val temp=report.toString()
                if(temp=="true") {
                    binding.pos.text = "YES"
                    binding.cardView3.setCardBackgroundColor(Color.parseColor("#E44545"))
                }
                else {
                    binding.pos.text = "NO"
                    binding.cardView3.setCardBackgroundColor(Color.parseColor("#42AC30"))
                }
            }else{
                Toast.makeText(this, "Wrong UID Entered", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            binding.progressBarRead.visibility= View.INVISIBLE
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }
    }

}