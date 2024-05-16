package com.example.search_images_kmp.android.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.search_images_kmp.android.di.App
import com.example.search_images_kmp.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    init {
        searchImages("카리나")
    }

    fun searchImages(keyword: String) {
        viewModelScope.launch {
            val result = repository.searchImages(keyword)
            Log.d("result", "result = $result")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as App)
                val repository = application.container.repository

                SearchViewModel(repository = repository)
            }
        }
    }
}