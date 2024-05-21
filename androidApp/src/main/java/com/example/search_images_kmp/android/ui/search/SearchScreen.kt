package com.example.search_images_kmp.android.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.search_images_kmp.android.R
import com.example.search_images_kmp.android.ui.compose.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        val configuration = LocalConfiguration.current
        val scrollState = rememberLazyGridState()
        val endOfList by remember {
            derivedStateOf {
                val last = scrollState.layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false

                (last.index == scrollState.layoutInfo.totalItemsCount - 1)
            }
        }

        LaunchedEffect(endOfList) {
            if (endOfList) {
                viewModel.searchImages(uiState.keyword)
            }
        }

        TopAppBar(title = {
            Text(text = stringResource(id = R.string.search))
        })
        SearchTextField(
            onSearch = { keyword -> viewModel.searchImages(keyword) }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val screenWidth = configuration.screenWidthDp.dp
            val images = uiState.images

            items(images.size) { index ->
                AsyncImage(
                    modifier = Modifier
                        .height(screenWidth / 2)
                        .clickable {
                            viewModel.insertImage(images[index])
                        },
                    model = images[index].imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

