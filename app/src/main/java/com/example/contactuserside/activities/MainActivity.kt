package com.example.contactuserside.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.contactuserside.R
import com.example.contactuserside.databinding.ActivityMainBinding
import com.example.contactuserside.models.dataModel
import com.example.contactuserside.db.viewModels.ViewModel

class MainActivity : AppCompatActivity() {

    private var uid=""
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel=ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewModel::class.java]

        viewModel.allUidItems.observe(this) {
            it?.let {
                if (it.isEmpty()) {
//                    binding.ifEmptyRepos.visibility = View.VISIBLE
                    findViewById<ConstraintLayout>(R.id.fill).visibility=View.VISIBLE
                    val button = binding.fill.navbtn
                    button.setOnClickListener {
                        uid=binding.fill.uidET.text.toString().trim()
                        viewModel.insertUID(dataModel(uid))
                        val intent = Intent(this, ShowProfileActivity::class.java)
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                    }
                }
                else
                {
                    findViewById<ConstraintLayout>(R.id.ifFilled).visibility=View.VISIBLE
                    binding.ifFilled.navbtn.visibility=View.VISIBLE
                    uid=it[0].uid
//                    Toast.makeText(this,it[0].toString(),Toast.LENGTH_SHORT).show()
                    val button = binding.ifFilled.navbtn
                    button.setOnClickListener {
                        val intent = Intent(this, ShowProfileActivity::class.java)
                        // start your next activity
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}