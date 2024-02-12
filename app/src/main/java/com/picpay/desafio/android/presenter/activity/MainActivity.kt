package com.picpay.desafio.android.presenter.activity

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.presenter.adapter.UserListAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListAdapter
    private lateinit var usersList: List<Contact>
    private lateinit var topAppBar: MaterialToolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView = findViewById(R.id.recyclerView)
        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter.isLoading = true
//        service.getContacts()
//            .enqueue(object : Callback<List<Contact>> {
//                override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
//                    val message = getString(R.string.error)
//                    recyclerView.visibility = View.GONE
//
//                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//                override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
//                    adapter.contacts = response.body()!!
//                    usersList = adapter.contacts
//                }
//            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            queryHint = getString(R.string.search_hint)
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filter(newText)
                    return false
                }
            })
        }
        return true
    }

    private fun filter(text: String) {
        if (text.isBlank()) {
            adapter.contacts = usersList
            return
        }
        val filteredList = usersList.filter {
            it.name.split(" ")
                .any { fraction -> fraction.lowercase().startsWith(text.lowercase()) } ||
                    it.username.lowercase().startsWith(text.lowercase())
        }
        adapter.contacts = filteredList
    }
}