package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val etItem = findViewById<EditText>(R.id.etItem)
        val btnSave = findViewById<Button>(R.id.btnSave)

        etItem.setText(getIntent().getStringExtra("item_text"))

        // Button is clicked when user has done editing
        btnSave.setOnClickListener {
            // create an intent which will contain the results
            val intent = Intent()

            // pass the edited results
            intent.putExtra("item_text", etItem.getText().toString())
            intent.putExtra("item_position", getIntent().getExtras()!!.getInt("item_position"))
            // set the result of the intent
            setResult(RESULT_OK, intent)

            // finish the activity; close screen and go back
            finish()
        }

    }
}