package com.example.searchmusic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.searchmusic.model.SongModel
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val songList: List<SongModel>): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(val binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SongItemBinding.inflate(inflater, parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongAdapter.SongViewHolder, positionSong: Int) {
        val song = songList[positionSong]
        holder.binding.apply {
            trackName.text = song.trackName
            releaseDate.text = song.releaseDate
            collectionName.text = song.collectionName
        }
    }

    override fun getItemCount() = songList.size

}