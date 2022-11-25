package com.noveogroup.moviecatalog.presentation.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.noveogroup.moviecatalog.R
import com.noveogroup.moviecatalog.domain.model.MovieDetails
import com.noveogroup.moviecatalog.presentation.components.MovieGenres
import com.noveogroup.moviecatalog.presentation.components.MoviePoster
import com.noveogroup.moviecatalog.presentation.components.MovieRating
import com.noveogroup.moviecatalog.presentation.components.TopAppBarTitle

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel
) {

    MovieDetailsView(
        state = viewModel.state.collectAsState().value,
        onBackClick = navController::navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsView(
    state: MovieDetailsScreenState,
    onBackClick: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                TopAppBarTitle(R.string.movie_details_title)
            },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        },
        content = { paddingValues ->

            val contentPaddings = remember {
                PaddingValues(
                    start = 16.dp,
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp
                )
            }

            when (state) {
                is MovieDetailsScreenState.Error -> {
                    MovieDetailsError(paddingValues = contentPaddings, error = state.message)
                }
                MovieDetailsScreenState.Loading -> {
                    MovieDetailsLoading()
                }
                is MovieDetailsScreenState.Success -> {
                    MovieDetailsContent(
                        paddingValues = contentPaddings,
                        movieDetails = state.movieDetails
                    )
                }
            }

        })
}

@Composable
fun MovieDetailsContent(
    paddingValues: PaddingValues,
    movieDetails: MovieDetails
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MovieTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            title = movieDetails.title,
            originalTitle = movieDetails.originalTitle
        )

        MoviePoster(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(bottom = 8.dp),
            posterUrl = movieDetails.posterUrl
        )

        MovieRating(
            basicFontSize = 22.sp,
            rating = movieDetails.rating,
            voteCount = movieDetails.voteCount
        )

        Spacer(modifier = Modifier.height(8.dp))

        MovieOverview(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            overview = movieDetails.overview
        )

        MovieGenres(modifier = Modifier.fillMaxWidth(), genres = movieDetails.genres)

        MovieReleaseDate(modifier = Modifier.fillMaxWidth(), releaseDate = movieDetails.releaseDate)
    }
}

@Composable
fun MovieDetailsLoading() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()
    }
}

@Composable
fun MovieDetailsError(
    paddingValues: PaddingValues,
    error: String?
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = if (error.isNullOrEmpty()) {
                stringResource(id = R.string.common_unknown_error)
            } else {
                error
            }
        )
    }
}

@Composable
fun MovieOverview(modifier: Modifier, overview: String) {

    Text(
        text = overview,
        modifier = modifier,
        color = Color.DarkGray
    )
}

@Composable
private fun MovieTitle(
    modifier: Modifier,
    title: String,
    originalTitle: String
) {

    Column(modifier = modifier) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        if (title != originalTitle && originalTitle.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = originalTitle,
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }

    }
}

@Composable
private fun MovieReleaseDate(modifier: Modifier, releaseDate: String) {

    Text(
        modifier = modifier,
        text = stringResource(id = R.string.movie_details_release_date_label, releaseDate),
        color = Color.LightGray,
        fontSize = 14.sp
    )
}

@Composable
@Preview
fun MovieDetailsPreview() {

    MovieDetailsView(
        state = MovieDetailsScreenState.Success(
            MovieDetails(
                id = 10L,
                posterUrl = "https://image.tmdb.org/t/p/w500/rcUcYzGGicDvhDs58uM44tJKB9F.jpg",
                rating = 8.6f,
                voteCount = 1000,
                title = "Title",
                originalTitle = "Original title",
                genres = listOf("Drama", "Horror"),
                overview = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis " +
                        "praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias " +
                        "excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui " +
                        "officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum " +
                        "quidem rerum facilis est et expedita distinctio. ",
                tagLine = "Tagline",
                releaseDate = "01-01-2000"
            )
        ),
        onBackClick = {}
    )
}