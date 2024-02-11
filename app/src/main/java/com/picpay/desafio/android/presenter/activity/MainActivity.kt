package com.picpay.desafio.android.presenter.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.entity.User
import com.picpay.desafio.android.presenter.adapter.UserListAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListAdapter
    private lateinit var usersList : List<User>
    private lateinit var topAppBar : MaterialToolbar

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val loggingInterceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }

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
        service.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    val message = getString(R.string.error)
                    recyclerView.visibility = View.GONE

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    adapter.users = response.body()!!
                    usersList = adapter.users
                }
            })
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
        if(text.isBlank()) {
            adapter.users = usersList
            return
        }
        val filteredList = usersList.filter {
            it.name.split(" ").any { fraction -> fraction.lowercase().startsWith(text.lowercase()) } ||
            it.username.lowercase().startsWith(text.lowercase())
        }
        adapter.users = filteredList
    }
}