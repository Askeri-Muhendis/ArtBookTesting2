package com.ibrahimethem.artbooktesting.api

import com.ibrahimethem.artbooktesting.model.ImageResponse
import com.ibrahimethem.artbooktesting.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/") // uzantı yerimiz
    suspend fun imageSearch(
        @Query("q") searchQuery : String, //search için q yu kullanıyoruz
        @Query("key") apiKey : String = API_KEY //api anahtarımız sabit tanımladık
    ) : Response<ImageResponse>
}