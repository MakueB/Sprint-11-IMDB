package com.example.sprint11imdb

import retrofit2.Call
import retrofit2.http.*

interface ImdbApi {
    @GET("/en/API/SearchMovie/{API_KEY}/{expression}")
    fun getMovie(@Path("API_KEY") token: String, @Path("expression") expression: String): Call<Response>
}