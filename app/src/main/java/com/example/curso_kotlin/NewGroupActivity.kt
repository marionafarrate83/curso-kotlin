package com.example.curso_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class NewGroupActivity : AppCompatActivity() {

    private lateinit var txtGroupName: EditText
    private lateinit var txtGroupDescription: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)

        txtGroupName = findViewById(R.id.txtGroupName)
        txtGroupDescription = findViewById(R.id.txtGroupDescription)

        progressBar = findViewById(R.id.progressBar)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("groups")
    }

    fun crearGrupo(view: View){
        val group: String = txtGroupName.text.toString()
        val description: String = txtGroupDescription.text.toString()

        if(!TextUtils.isEmpty(group) && !TextUtils.isEmpty(description)){
            progressBar.visibility=View.VISIBLE
            addGroup(group, description)
        }

    }

    fun addGroup(groupName: String, groupDescription: String) {
        //1
        val dbReference = database.getReference("groups")
        //2
        val key = dbReference.push().key ?: ""
        val group = createGroup(key, groupName, groupDescription)
        //3
        dbReference.child(key)
            .setValue(group)
            .addOnSuccessListener { action() }
            .addOnFailureListener { Toast.makeText(this, "Creación falló", Toast.LENGTH_LONG).show() }
    }


    private fun createGroup(key: String, groupName: String, groupDescription: String): Group {
        val user = auth.currentUser?.uid.toString()
        val timestamp = "ahorita"
        return Group(key, groupName, groupDescription, user, timestamp)
    }

    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))
        Toast.makeText(this, "Grupo Creado", Toast.LENGTH_LONG).show()
    }
}
