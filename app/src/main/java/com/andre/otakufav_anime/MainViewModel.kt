package com.andre.otakufav_anime

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andre.otakufav_anime.data.AnimeRepository
import com.andre.otakufav_anime.data.model.IsLikedAnime
import com.andre.otakufav_anime.data.remote.AnimeApiResponse
import com.example.animeapp.data.model.Anime
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class MainViewModel(application: Application): AndroidViewModel(application) {

   private val animeRepository = AnimeRepository(application.applicationContext)


    private val _apiVersion = MutableLiveData<Int>()
    val apiVersion: LiveData<Int>
        get() = _apiVersion

   private val _anime = MutableLiveData<List<Anime>>()
    val anime: LiveData<List<Anime>>
        get() = _anime

    private val _randomAnime = MutableLiveData<AnimeApiResponse>()
    val randomAnime: LiveData<AnimeApiResponse>
        get() = _randomAnime

    init {
        loadDataToDatabase()
    }
    fun loadDataToDatabase() {
        viewModelScope.launch {
            animeRepository.loadDataToDatabase()
            getRandomAnime()
        }
    }

    fun getRandomAnime(){
        _randomAnime.value = animeRepository.getRandomAnime().value
    }
}
