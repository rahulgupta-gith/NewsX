package com.example.newsx.api
import com.example.newsx.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query



interface NewsApiService {
    @GET("everything")
    fun getNews(
        @Query("q") query: String,
        @Query("from") fromDate:String,
        @Query("sortBy") sortBy:String = "publishedAt",
        @Query("apiKey") apiKey:String
    ):Call<NewsResponse>
}