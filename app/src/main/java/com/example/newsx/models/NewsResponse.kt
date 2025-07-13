package com.example.newsx.models

data class NewsResponse(
     val status:String,
     val totalResults: Int,
     val articles:List<Article>
)