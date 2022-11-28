package com.noveogroup.moviecatalog.presentation.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.noveogroup.moviecatalog.R
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.presentation.components.MovieGenres
import com.noveogroup.moviecatalog.presentation.components.MoviePoster
import com.noveogroup.moviecatalog.presentation.components.MovieRating
import com.noveogroup.moviecatalog.presentation.components.TopAppBarTitle
import com.noveogroup.moviecatalog.presentation.navigation.Screen

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect {
            when (it) {
                is Screen.MovieDetails -> navController.navigate(it.route)
                else -> {} // do nothing
            }
        }
    }

    MovieListView(
        state = viewModel.state.collectAsState().value,
        error = viewModel.errorEvents.collectAsState(initial = null).value,
        onItemClicked = viewModel::handleMovieClicked,
        onScrolledToBottom = viewModel::handleListScrolledToEnd,
        onRefreshClicked = viewModel::handleRefreshClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieListView(
    state: MovieListScreenState,
    error: String?,
    onItemClicked: (MovieListItem.MovieItem) -> Unit,
    onScrolledToBottom: () -> Unit,
    onRefreshClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                TopAppBarTitle(R.string.movie_list_title)
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

            if (!error.isNullOrEmpty() && state.items.none { it !is MovieListItem.Loading }) {
                MovieListError(error = error, onRefreshClicked = onRefreshClicked)
            } else {
                MovieListContent(
                    paddingValues = contentPaddings,
                    items = state.items,
                    onItemClicked = onItemClicked,
                    onScrolledToBottom = onScrolledToBottom
                )
            }
        }
    )
}

@Composable
private fun MovieListError(
    error: String,
    onRefreshClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)

        Button(onClick = onRefreshClicked) {
            Text(text = stringResource(id = R.string.movie_list_refresh_button))
        }
    }
}

@Composable
private fun MovieListContent(
    paddingValues: PaddingValues,
    items: List<MovieListItem>,
    onItemClicked: (MovieListItem.MovieItem) -> Unit,
    onScrolledToBottom: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    Box {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues
        ) {
            items(
                count = items.size,
                key = {
                    when (val item = items[it]) {
                        MovieListItem.Loading -> -1
                        is MovieListItem.MovieItem -> item.movie.id
                    }
                }
            ) { index ->
                when (val item = items[index]) {
                    MovieListItem.Loading -> {
                        LoadingListItemView()
                    }
                    is MovieListItem.MovieItem -> {
                        MovieListItemView(
                            movie = item,
                            onClicked = onItemClicked
                        )
                    }
                }
            }
        }
    }

    val isScrolledToBottom = remember {
        derivedStateOf { lazyListState.isScrolledNearBottom() }
    }

    LaunchedEffect(isScrolledToBottom.value) {
        onScrolledToBottom()
    }
}

@Composable
fun LoadingListItemView() {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MovieListItemView(
    movie: MovieListItem.MovieItem,
    onClicked: (MovieListItem.MovieItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClicked(movie)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoviePoster(
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            posterUrl = movie.movie.posterUrl
        )

        Column(
            modifier = Modifier
                .weight(3f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            movie.movie.let {
                MovieTitle(
                    modifier = Modifier.padding(bottom = 8.dp),
                    title = it.title,
                    originalTitle = it.originalTitle
                )

                MovieGenres(
                    modifier = Modifier.padding(bottom = 8.dp),
                    genres = it.genres
                )

                MovieRating(basicFontSize = 14.sp, rating = it.rating, voteCount = it.voteCount)
            }
        }
    }
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
            fontSize = 16.sp
        )

        if (title != originalTitle && originalTitle.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = originalTitle,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun MovieListPreview() {
    MovieListView(
        state = MovieListScreenState(
            items = listOf(
                MovieListItem.MovieItem(
                    Movie(
                        id = 1000L,
                        posterUrl = "",
                        rating = 5.5f,
                        voteCount = 1000,
                        title = "Title",
                        originalTitle = "Original title",
                        genres = listOf("Comedy", "Horror")
                    )
                ),
                MovieListItem.Loading
            )
        ),
        error = null,
        onItemClicked = {},
        onScrolledToBottom = {},
        onRefreshClicked = {}
    )
}

private fun LazyListState.isScrolledNearBottom(): Boolean {
    return (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >= layoutInfo.totalItemsCount - 2
}
