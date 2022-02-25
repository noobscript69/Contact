package com.example.contactuserside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactuserside.databinding.ActivityReadDataBinding
import com.example.contactuserside.db.viewModel
import com.google.firebase.database.*

class ReadData : AppCompatActivity() {

    private lateinit var binding : ActivityReadDataBinding
    private lateinit var database : DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<User>

    private var uidSave=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Toast.makeText(this,"halwa",Toast.LENGTH_SHORT).show()
        uidSave= intent.getStringExtra("uid").toString()
//        Toast.makeText(this,tempUid,Toast.LENGTH_SHORT).show()


        val button = findViewById<Button>(R.id.posto)

        button.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }

//        val button2 = findViewById<Button>(R.id.recy)

//        button2.setOnClickListener {
//            if (uidSave!=""){
//                val intent=Intent(this,UserListActivity::class.java)
////                Toast.makeText(this,uidSave,Toast.LENGTH_SHORT).show()
//                intent.putExtra("uid", uidSave)
//                startActivity(intent)
//            }else{
//                Toast.makeText(this, "Some error occurred, please try again after some time.", Toast.LENGTH_SHORT).show()
//            }
//        }
        readData(uidSave)

        binding.signOutButton.setOnClickListener {
            val viewModel= ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[viewModel::class.java]

            viewModel.deleteRepo()

            Handler().postDelayed({
                val intent=Intent(this,MainActivity::class.java)
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
                TODO("Not yet implemented")
            }
        })
    }

    private fun readData(showGraph: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(showGraph).get().addOnSuccessListener{
            binding.progressBarRead.visibility= View.GONE
            if (it.exists()){
                binding.progressBarRead.visibility=View.GONE
//                val name = it.child("name").value
//                val phno = it.child("mobile").value
                val report = it.child("positive").value
//                val location = it.child("visited").child("0").child("address").value
//                val inTime = it.child("visited").child("0").child("inTime").value
//                val outTime = it.child("visited").child("0").child("outTime").value
//                val readerUID = it.child("visited").child("0").child("readerUID").value
//                binding.name.text = name.toString()
//                binding.phno.text = phno.toString()
                val temp=report.toString()
                if(temp=="true")
                    binding.pos.text="YES"
                else
                    binding.pos.text="NO"
//                binding.place.text = location.toString()
//                binding.intime.text = inTime.toString()
//                binding.out.text = outTime.toString()
//                binding.uid.text = readerUID.toString()

            }else{
                Toast.makeText(this, "Wrong UID Entered", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            binding.progressBarRead.visibility= View.INVISIBLE
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }
    }

}