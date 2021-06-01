package com.example.curso_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class listGroupActivity : AppCompatActivity(), MyAdapter.OnGroupClickListener {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Group>
    private lateinit var auth: FirebaseAuth
    private val TAG = "marionafarrate83"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_group)

        auth= FirebaseAuth.getInstance()
        val userAuth: FirebaseUser?=auth.currentUser
        val userid:String=userAuth?.uid.toString()

        userRecyclerview = findViewById(R.id.groupList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<Group>()
        getUserData()

    }

    private fun getUserData() {

        val userAuth: FirebaseUser?=auth.currentUser
        val userid:String=userAuth?.uid.toString()

        dbref = FirebaseDatabase.getInstance().getReference("groups")
        //databaseReference.child("User").orderByChild("name").equalTo("Peter").addValueEventListener(object : ValueEventListener{}

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        //var tantos = userSnapshot.childrenCount

                        var user = userSnapshot.getValue(Group::class.java)
                        Log.v(TAG, user?.user.toString());

                        if (userid == user?.user.toString()){
                            userArrayList.add(user!!)
                        }

                    }

                    userRecyclerview.adapter = MyAdapter(userArrayList,this@listGroupActivity)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onItemClick(key: String?) {
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show()
        //startActivity(Intent(this,MainActivity::class.java))
        val intent = Intent(this,GroupTasksActivity::class.java)
        intent.putExtra("groupKey", key)
        startActivity(intent)
    }
}