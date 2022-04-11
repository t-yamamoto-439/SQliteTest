package com.example.sqlitetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity() {
    var bookList = ArrayList<Book>()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTest.setOnClickListener {
            val intent = Intent(this,StateActivity::class.java)
            startActivity(intent)
        }
    }

    fun goToAdd(view: View){
        val intent = Intent(this,AddingBookActivity::class.java)
        startActivity(intent)
    }

    fun loadData() {
        val database = DBHelper(this)
        bookList = database.getAllBooks()

        val bookAdapter = BookAdapter(this, bookList)
        listView.adapter = bookAdapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}