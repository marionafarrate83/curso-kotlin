package com.example.curso_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class GroupTasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_tasks)

        if(intent.extras != null){
            var hotkey = intent.getStringExtra("groupKey")
            Toast.makeText(this, "hotkey $hotkey", Toast.LENGTH_SHORT).show()
        }
        
    }
}