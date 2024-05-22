package com.example.search_images_kmp.android.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.search_images_kmp.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleAppBar(
    @StringRes titleRes: Int
) {
    TopAppBar(title = {
        Text(text = stringResource(id = titleRes))
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditImagesAppBar(
    @StringRes titleRes: Int,
    deleteImages: () -> Unit,
    changeEditMode: () -> Unit,
    isEditMode: Boolean
) {
    TopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        actions = {
            if (isEditMode) {
                Row {
                    TextButton(
                        onClick = {
                            deleteImages()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                    TextButton(
                        onClick = {
                            changeEditMode()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            } else {
                TextButton(
                    onClick = {
                        changeEditMode()
                    }
                ) {
                    Text(text = stringResource(id = R.string.edit))
                }
            }
        }
    )
}

@Preview
@Composable
private fun EditImagesAppBarPreview() {
    EditImagesAppBar(
        titleRes = R.string.bookmark,
        deleteImages = {  },
        changeEditMode = {  },
        isEditMode = true
    )
}