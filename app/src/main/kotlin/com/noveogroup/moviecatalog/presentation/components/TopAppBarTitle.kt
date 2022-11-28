package com.noveogroup.moviecatalog.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarTitle(@StringRes stringRes: Int) {
    Text(
        text = stringResource(id = stringRes),
        fontSize = 20.sp
    )
}
