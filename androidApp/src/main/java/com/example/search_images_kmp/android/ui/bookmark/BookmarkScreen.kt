package com.example.search_images_kmp.android.ui.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.search_images_kmp.android.R
import com.example.searchimageskmp.LocalImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModel.Factory)
) {
    Column {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.bookmark)) },
            actions = {
                if (uiState.editMode) {
                    TextButton(
                        onClick = {
                            viewModel.deleteImages()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                } else {
                    IconButton(
                        onClick = {
                            viewModel.changeEditMode()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                }
            }
        )
        if (uiState.isLoading) {
            Text(text = "loading...")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.images) { image ->
                    GridImageItem(
                        image = image,
                        isEditMode = uiState.editMode,
                        editDeleteImageList = { viewModel.editDeleteImageList(image) }
                    )
                }
            }
        }
    }
}

@Composable
fun GridImageItem(
    image: LocalImage,
    isEditMode: Boolean,
    editDeleteImageList: (LocalImage) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box {
        AsyncImage(
            modifier = Modifier.height(screenWidth / 2),
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