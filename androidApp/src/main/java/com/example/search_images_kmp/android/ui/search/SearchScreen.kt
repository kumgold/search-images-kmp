package com.example.search_images_kmp.android.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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

        TitleAppBar(titleRes = R.string.search)
        SearchTextField(onSearch = { keyword -> viewModel.searchImages(keyword) })
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
            if (images.isNotEmpty()) {
                item(span = { GridItemSpan(this.maxLineSpan) }) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.searchImages(uiState.keyword)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.more_images))
                    }
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

