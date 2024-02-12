package com.picpay.desafio.android.presenter.activity

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presenter.adapter.UserListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var usersList: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupAdapter()
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

    private fun setupAdapter() {
        binding.apply {
            adapter = UserListAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
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