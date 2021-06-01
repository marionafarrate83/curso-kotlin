package com.example.curso_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    // creating a variable for
    // our Firebase Database.
    var firebaseDatabase: FirebaseDatabase? = null

    private lateinit var auth: FirebaseAuth

    // creating a variable for our
    // Database Reference for Firebase.
    var databaseReference: DatabaseReference? = null

    // variable for Text view.
    private var retriveTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= FirebaseAuth.getInstance()
        val user: FirebaseUser?=auth.currentUser
        val userid:String=user?.uid.toString()

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase?.getReference("User")

        // initializing our object class variable.
        retriveTV = findViewById(R.id.idTVRetriveData)

        // calling method
        // for getting data.
        getdata(userid)
    }

    fun logout(view: View){
        auth.signOut()
        startActivity(Intent(this,LoginActivity::class.java))
        Toast.makeText(this, "Logout correcto XD", Toast.LENGTH_SHORT).show()
    }

    fun createGroup(view:View){
        startActivity(Intent(this,NewGroupActivity::class.java))
    }

    fun gotoListGroupView(view: View){
        startActivity(Intent(this,listGroupActivity::class.java))
    }

    private fun getdata(userid:String) {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                //val value = snapshot.getValue(String::class.java)

                // after getting the value we are setting
                // our value to our text view in below line.
                //retriveTV!!.text = value

                val username = snapshot.child(userid).child("name").getValue(String::class.java)
                retriveTV?.text = username.toString()


            }

            override fun onCancelled(error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(this@MainActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
