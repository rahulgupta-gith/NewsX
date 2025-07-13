    package com.example.newsx

    import android.content.Intent
    import android.net.Uri
    import android.os.Bundle
    import android.widget.Toast
    import android.util.Log

    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.newsx.adapter.NewsAdapter
    import com.example.newsx.api.RetrofitClient
    import com.example.newsx.databinding.ActivityMainBinding
    import com.example.newsx.models.NewsResponse
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private lateinit var adapter: NewsAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            binding.searchButton.setOnClickListener {
                val query = binding.searchInput.text.toString()
                if (query.isNotEmpty()) {
                    fetchNews(query)
                } else {
                    Toast.makeText(this, "Enter a topic", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun fetchNews(query: String) {
            val call = RetrofitClient.instance.getNews(
                query = query,
                fromDate = "2025-07-12",
                apiKey =  BuildConfig.NEWS_API_KEY
            )
            Log.d("query", call.request().url().toString())
            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    val articles = response.body()?.articles ?: emptyList()
                    adapter = NewsAdapter(articles){ article->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(article.url)
                        startActivity(intent)
                    }
                    binding.recyclerView.adapter = adapter
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@MainActivity, "Failed to fetch news", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
