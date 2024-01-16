package com.example.sprint11imdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private val imdbBaseUrl ="https://imdb-api.com/"

    private var token = "k_zcuw1ytf"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(ImdbApi::class.java)

    private val adapter = MoviesAdapter()

    private val movies = ArrayList<Movie>()

    private lateinit var searchButton: Button
    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView

    private fun search(){
        imdbService.getMovie(token, queryInput.text.toString())
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    when (response.code()){
                        200 -> {
                            if (response.body()?.results?.isNotEmpty() == true){
                                Log.d("Deb", response.body()?.results.toString())
                                movies.clear()
                                movies.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                                showMessage("","")
                            } else {
                                showMessage("Ничего не найдено","")
                                Log.d("Deb", response.body()?.results.toString())
                            }
                        }
                        else -> {
                            showMessage("Что-то пошло не так", response.code().toString())
                        }
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    showMessage("Что-то пошло не так", t.message.toString())
                }
            })
    }

    private fun showMessage(text: String, additionalText: String) {
        if (text.isNotEmpty()){
            placeholderMessage.visibility = View.VISIBLE
            movies.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalText.isNotEmpty()){
                Toast.makeText(this,additionalText, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeholderMessage = findViewById(R.id.placeholderMessage)
        searchButton = findViewById(R.id.searchButton)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.movies)

        adapter.movies = movies
        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        searchButton.setOnClickListener {
            if (queryInput.text.isNotBlank())
                search()
        }

    }
}