package com.example.search_images_kmp.android.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.search_images_kmp.android.di.App
import com.example.search_images_kmp.repository.ImageRepository
import com.example.searchimageskmp.LocalImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImageDetailViewModel(
    private val repository: ImageRepository
) : ViewModel() {

    private val _image = MutableStateFlow<LocalImage?>(null)
    val image: StateFlow<LocalImage?> = _image

    fun getSavedImage(id: String) {
        viewModelScope.launch {
            try {
                _image.value = repository.getImage(id)
            } catch (e: Exception) {

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val repository = application.container.imageRepository

                ImageDetailViewModel(repository = repository)
            }
        }
    }
}