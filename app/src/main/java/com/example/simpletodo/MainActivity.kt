package com.example.simpletodo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from list
                listOfTasks.removeAt(position)

                // 2. Notify the adapter that something has changed
                adapter.notifyDataSetChanged()

                saveItems()

                // Display a message to user saying Item Deleted
                Toast.makeText(applicationContext, "Item deleted from list", Toast.LENGTH_SHORT).show()
            }

        }

        loadItems()

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up button and input field so that user can enter a task
        val inputText = findViewById<EditText>(R.id.addTaskField)

        // Grabbing a button and then set on click listener on it
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text the user has inputted into @id/addTaskField
            val userInput = inputText.text.toString()

            // 2. Add that string to list of tasks: listOfTasks
            listOfTasks.add(userInput)

            // Notify the adapter that our data has been updated; very important line as our view will not update
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. Reset text field
            inputText.setText("")

            saveItems()

            // Display a message to user saying item has been added
            Toast.makeText(applicationContext, "Item added to list", Toast.LENGTH_SHORT).show()
        }
    }

    // Save the data that the user has inputted; We will save data by reading and writing from a file

    // Create a method to get the file we need
    fun getDataFile(): File {
        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "UserData.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    // Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException:IOException) {
            ioException.printStackTrace()
        }
    }

}