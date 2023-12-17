package com.example.searchmusic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.searchmusic.adapter.SongAdapter
import com.example.searchmusic.model.SongModel
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var searchBox: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var labelSongs: TextView
    private lateinit var recyclerViewSong: RecyclerView
    private lateinit var songAdapter: SongAdapter
    private lateinit var songList: MutableList<SongModel>
    private lateinit var queue: RequestQueue

    override fun onCreate(instanceSaved: Bundle?) {
        super.onCreate(instanceSaved)
        setContentView(R.layout.activity_main)

        searchBox = findViewById(R.id.searchBoxSong)
        searchButton = findViewById(R.id.searchButton)
        labelSongs = findViewById(R.id.labelSongId)
        recyclerViewSong = findViewById(R.id.recyclerViewSong)

        songList = mutableListOf()
        songAdapter = SongAdapter(songList)
        recyclerViewSong.layoutManager = LinearLayoutManager(this)
        recyclerViewSong.adapter = songAdapter

        queue = Volley.newRequestQueue(this)

        searchButton.setOnClickListener {
            val artistName = searchBox.text.toString()
            if (artistName.isEmpty()) {
                Toast.makeText(this, "Enter here an artist name", Toast.LENGTH_SHORT).show()
            } else {
                fetchProducts(artistName)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchProducts(artistName: String) {
        val url = "https://itunes.apple.com/search?term=$artistName&entity=musicVideo"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val result = results.getJSONObject(i)
                        val trackName = result.getString("trackName")
                        val releaseDate = result.getString("releaseDate")
                        val collectionName = result.getString("collectionName")
                        val artworkUrl = result.getString("artworkUrl100")
                        songList.add(SongModel(trackName, releaseDate, collectionName, artworkUrl))
                    }
                    songAdapter.notifyDataSetChanged()
                    labelSongs.text = "Songs"
                } catch (e: JSONException) {
                    Log.e("Error", "Error parsing JSON response", e)
                }
            }, { error ->
                Log.e("Error", "Error fetching products", error)
            })
        requestQueue.add(jsonObjectRequest)
    }
}
