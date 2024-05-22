package com.example.search_images_kmp.android.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.search_images_kmp.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel = viewModel(factory = ImageDetailViewModel.Factory),
    backToList: () -> Unit,
    imageUrl: String? = null
) {
    if (imageUrl == null) {
        backToList()
    }

    Column {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.image_detail)) },
            navigationIcon = {
                IconButton(onClick = { backToList() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenWidth),
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}