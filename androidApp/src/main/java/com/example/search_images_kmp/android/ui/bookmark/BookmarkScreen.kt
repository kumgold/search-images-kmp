package com.example.search_images_kmp.android.ui.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.search_images_kmp.android.R
import com.example.search_images_kmp.android.ui.compose.EditImagesAppBar
import com.example.search_images_kmp.android.ui.compose.LazyImageGridView
import com.example.search_images_kmp.android.ui.compose.SearchTextField
import com.example.searchimageskmp.LocalImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModel.Factory),
    goToDetailScreen: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        EditImagesAppBar(
            titleRes = R.string.bookmark,
            deleteImages = { viewModel.deleteImages() },
            changeEditMode = { viewModel.changeEditMode() },
            isEditMode = uiState.editMode
        )
        SearchTextField(
            onSearch = { keyword ->
                viewModel.stopEditMode()
                viewModel.searchImages(keyword)
            }
        )
        if (uiState.isLoading) {
            Text(text = "loading...")
        } else {
            LazyImageGridView {
                items(uiState.images) { image ->
                    GridImageItem(
                        image = image,
                        isEditMode = uiState.editMode,
                        editDeleteImageList = { viewModel.editDeleteImageList(image) },
                        goToDetailScreen = { id -> goToDetailScreen(id) },
                        changeEditMode = { viewModel.changeEditMode() }
                    )
                }
            }
        }
    }

    uiState.message?.let {
        val context = LocalContext.current
        LaunchedEffect(uiState.message) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = ContextCompat.getString(context, uiState.message!!),
                    duration = SnackbarDuration.Short
                )
            }
            viewModel.snackBarMessageShown()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridImageItem(
    image: LocalImage,
    isEditMode: Boolean,
    editDeleteImageList: (LocalImage) -> Unit,
    goToDetailScreen: (String) -> Unit,
    changeEditMode: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box {
        AsyncImage(
            modifier = Modifier.height(screenWidth / 2)
                .combinedClickable(
                    onLongClick = {
                        changeEditMode()
                    }
                ) {
                    if (!isEditMode) {
                        val imageUrl = URLEncoder.encode(image.imageUrl, StandardCharsets.UTF_8.toString())
                        goToDetailScreen(imageUrl)
                    }
                },
            model = image.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        if (isEditMode) {
            val isChecked = rememberSaveable { mutableStateOf(false) }

            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = !isChecked.value
                    editDeleteImageList(image)
                },
            )
        }
    }
}