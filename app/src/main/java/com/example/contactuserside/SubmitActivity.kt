package com.example.contactuserside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.contactuserside.Constants.BASE_URL
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SubmitActivity : AppCompatActivity() {

    private lateinit var btn:MaterialButton
    private lateinit var nameEd :TextInputEditText
    private lateinit var progressBar:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        initViews()
        btn.setOnClickListener {
            progressBar.visibility= View.VISIBLE
            btn.visibility=View.GONE
            val name = nameEd.text.toString()
            submitData(name)
        }
    }

    private fun submitData(name: String) {
        val url = "https://us-central1-goway-78cf5.cloudfunctions.net/reportPositive"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.POST,url,
            Response.Listener { response ->
                progressBar.visibility=View.GONE
                btn.visibility=View.VISIBLE
                Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            },
            Response.ErrorListener { error->
                progressBar.visibility=View.GONE
                btn.visibility=View.VISIBLE
                Toast.makeText(this,"Couldn't update the data, please try again after some time.",Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): HashMap<String,String>{
                val map = HashMap<String,String>()
                map["uid"] = name
                return map
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun initViews() {
        btn = findViewById(R.id.submitBtn)
        nameEd = findViewById(R.id.nameEd)
        progressBar=findViewById(R.id.progress_bar)
    }
}

