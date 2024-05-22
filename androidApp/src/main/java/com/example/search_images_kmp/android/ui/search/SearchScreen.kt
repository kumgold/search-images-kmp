package com.example.search_images_kmp.android.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.search_images_kmp.android.ui.compose.LazyImageGridView
import com.example.search_images_kmp.android.ui.compose.SearchTextField
import com.example.search_images_kmp.android.ui.compose.TitleAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    goToDetailScreen: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
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

        TitleAppBar(
            titleRes = R.string.search
        )
        SearchTextField(
            onSearch = { keyword -> viewModel.searchImages(keyword) }
        )
        LazyImageGridView {
            val screenWidth = configuration.screenWidthDp.dp
            val images = uiState.images

            items(images) { image ->
                AsyncImage(
                    modifier = Modifier
                        .height(screenWidth / 2)
                        .combinedClickable(
                            onLongClick = {
                                viewModel.insertImage(image)
                            }
                        ) {
                            val imageUrl =
                                URLEncoder.encode(image.imageUrl, StandardCharsets.UTF_8.toString())
                            goToDetailScreen(imageUrl)
                        },
                    model = image.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
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

