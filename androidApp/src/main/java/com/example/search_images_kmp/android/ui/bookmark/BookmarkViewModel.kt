package com.example.search_images_kmp.android.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.search_images_kmp.android.R
import com.example.search_images_kmp.android.di.App
import com.example.search_images_kmp.repository.ImageRepository
import com.example.searchimageskmp.LocalImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BookmarkUiState(
    val images: List<LocalImage> = emptyList(),
    val isLoading: Boolean = false,
    val editMode: Boolean = false,
    val message: Int? = null
)

class BookmarkViewModel(
    private val repository: ImageRepository
) : ViewModel() {

    private val _editMode = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _images: MutableStateFlow<List<LocalImage>> = MutableStateFlow(emptyList())

    init {
        getAllImages()
    }

    private fun getAllImages() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                _images.value = repository.getAllImages()
            } catch (e: Exception) {
                _userMessage.value = R.string.error_message
            }
            _isLoading.value = false
        }
    }

    val uiState: StateFlow<BookmarkUiState> = combine(
        _images, _isLoading, _editMode, _userMessage
    ) { imageResult, isLoading, editMode, message ->
        BookmarkUiState(
            images = imageResult,
            isLoading = isLoading,
            editMode = editMode,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = BookmarkUiState(isLoading = true)
    )

    private val _deleteImages = MutableStateFlow<MutableList<String>>(mutableListOf())

    fun deleteImages() {
        viewModelScope.launch {
            repository.deleteImages(_deleteImages.value)
            changeEditMode()
            getAllImages()
        }
    }

    fun changeEditMode() {
        _editMode.value = !_editMode.value
        _deleteImages.value.clear()
    }

    fun editDeleteImageList(image: LocalImage) {
        if (_deleteImages.value.contains(image.id)) {
            _deleteImages.value.remove(image.id)
        } else {
            _deleteImages.value.add(image.id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val repository = application.container.imageRepository

                BookmarkViewModel(repository = repository)
            }
        }
    }
}