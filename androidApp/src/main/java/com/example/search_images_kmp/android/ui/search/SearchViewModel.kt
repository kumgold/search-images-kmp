package com.example.search_images_kmp.android.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.search_images_kmp.android.di.App
import com.example.search_images_kmp.model.NetworkImage
import com.example.search_images_kmp.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val images: List<NetworkImage> = emptyList(),
    val keyword: String = "",
    val page: Int = 1,
    val message: Int? = null
)

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun searchImages(keyword: String) {
        if (keyword.isEmpty()) return
        if (_uiState.value.keyword != keyword) {
            _uiState.update {
                it.copy(
                    images = emptyList(),
                    page = 1
                )
            }
        }

        viewModelScope.launch {
            val result = repository.searchImages(keyword, _uiState.value.page)

            _uiState.update {
                it.copy(
                    images = it.images + result.images,
                    keyword = keyword,
                    page = it.page + 1
                )
            }
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