package com.noveogroup.moviecatalog.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.noveogroup.moviecatalog.R
import com.noveogroup.moviecatalog.presentation.theme.HighMarkColor
import com.noveogroup.moviecatalog.presentation.theme.LowMarkColor

@Composable
fun MoviePoster(modifier: Modifier, posterUrl: String) {
    @Composable
    fun PlaceHolder() {
        Image(
            painter = painterResource(id = R.drawable.ic_film),
            contentDescription = stringResource(
                id = R.string.common_content_description_poster_placeholder
            ),
            colorFilter = ColorFilter.tint(Color.LightGray)
        )
    }

    SubcomposeAsyncImage(
        modifier = modifier,
        model = posterUrl,
        contentDescription = stringResource(id = R.string.common_content_description_poster),
        loading = {
            PlaceHolder()
        },
        error = {
            PlaceHolder()
        }
    )
}

@Composable
fun MovieGenres(modifier: Modifier, genres: List<String>) {
    Text(
        modifier = modifier,
        text = genres.joinToString(),
        color = Color.Gray,
        fontSize = 13.sp
    )
}

@Composable
fun MovieRating(basicFontSize: TextUnit, rating: Float, voteCount: Int) {
    if (voteCount > 0) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$rating",
                color = when {
                    rating < 6f -> LowMarkColor
                    rating > 8 -> HighMarkColor
                    else -> MaterialTheme.colorScheme.onBackground
                },
                fontSize = if (rating < 8f) {
                    basicFontSize
                } else {
                    basicFontSize.times(1.2)
                },
                fontWeight = if (rating > 8f) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                }
            )

            Text(
                text = "($voteCount)",
                color = Color.Gray,
                fontSize = basicFontSize.times(0.7)
            )
        }
    }
}
